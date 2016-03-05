package com.stone.ordering.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.stone.ordering.app.MyApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 类名：SysUtilManager 描述：系统帮助类，存放一些通用方法。 公司:北京海鑫科金高科技股份有限公司 作者：yuchengkuan
 * 版本：V1.0 创建时间：2015年9月10日 最后修改时间：2015年9月10日
 */
public class SysUtilManager {

	/**
	 * 默认时间格式 中文 yyyy年MM月dd日 hh:mm
	 */
	public static String defaultFormatStringCN = "yyyy年MM月dd日HH时mm分";

	public static byte[] getBytesFromFile(File file) {
		if (file == null) {
			return null;
		}
		if (file != null && !file.exists()) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 将一个byte[]写入SDCard，成为一个图片文件
	 */
	public static File writeToSDCardFromInput(String path, byte[] buffer) {
		File file = null;
		OutputStream outputStream = null;
		try {
			file = createFileInSDCard(path);
			outputStream = new FileOutputStream(file);
			InputStream inputStream = new ByteArrayInputStream(buffer);
			byte data[] = new byte[4 * 1024];
			int tmp;
			while ((tmp = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, tmp);
			}
			outputStream.flush();
		} catch (Exception e) {
			SysUtilManager.saveCrashInfo2File(e);
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
			}
		}
		return file;
	}

	/**
	 * 获取当前版本号
	 */
	public static String getCurrentAppVersion(Context context) {
		String versionName = "";
		try {
			PackageManager pManager = context.getPackageManager();
			PackageInfo pInfo = pManager.getPackageInfo(context.getPackageName(), 0);
			versionName = pInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 拷贝文件
	 * 
	 * @param sourceFile
	 *            源文件 包括完整路径和文件名
	 * @param toFile
	 *            目标文件 包括完整路径和文件名
	 */
	public static void copyFile(String sourceFile, String toFile) {
		if (sourceFile == null || toFile == null)
			return;
		File source = new File(sourceFile);
		File to = new File(toFile);

		if (!source.exists())
			return;
		try {
			if (!to.exists()) {
				to.getParentFile().mkdirs();
				to.createNewFile();
			}
			FileInputStream input = new FileInputStream(source);
			FileOutputStream output = new FileOutputStream(to);
			byte[] buffer = new byte[512];
			while (input.read(buffer) > 0) {
				output.write(buffer);
			}
			input.close();
			output.close();
		} catch (Exception e) {
			saveCrashInfo2File(e);
		}
	}

	/**
	 * 在SDCard上创建文件
	 */
	public static File createFileInSDCard(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		try {
			File parent = file.getParentFile();
			if (!parent.exists())
				parent.mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			SysUtilManager.saveCrashInfo2File(e);
		}
		return file;
	}

	/**
	 * 在SDCard上创建文件夹
	 */
	public static File createPathInSDCard(String path) {
		File file = new File(path);
		if (file.exists())
			return file;
		try {
			file.mkdirs();
		} catch (Exception e) {
		}
		return file;
	}

	/**
	 * 隐藏键盘
	 * 
	 * @param mcontext
	 */
	public static void keyBoardHide(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 为现场拍照或者采集指纹锁产生的图片进行命名。
	 * 
	 * @param idCard
	 *            身份证号
	 * @param type
	 *            指纹图片或者现场人脸照片。0表示指纹，1表示线程人脸照片
	 * @param fingerIndex
	 *            指位编号 。如果为人脸，该参数可以随意输入
	 */
	public static String createFileName(String idCard, int type, int fingerIndex) {
		if (idCard == null) {
			return null;
		}
		StringBuilder fileName = new StringBuilder();
		switch (type) {
		case 0:
			fileName.append("finger_");
			break;
		case 1:
			fileName.append("face_");
			break;
		default:
			break;
		}
		fileName.append(idCard);
		fileName.append("_");
		fileName.append(System.currentTimeMillis());
		fileName.append("_");
		switch (type) {
		case 0:
			fileName.append("_");
			fileName.append(fingerIndex);
			break;
		case 1:
			break;
		default:
			break;
		}
		fileName.append(".jpg");

		return fileName.toString();
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static float getSystemDensity(Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return scale;
	}

	/**
	 * 将bitmap存在SDCard中
	 * 
	 * @param fingerprint_bitmap
	 * @return 返回所存路径
	 */
	public static void saveBitmapToSDCard(Bitmap fingerprint_bitmap, String filePath) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			if (fos != null) {
				fingerprint_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			}
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除缓存的文件
	 * 
	 * @param path
	 *            图片路径
	 * @return 受到影响的文件数
	 */
	public static void deleteTmpFile(String path) {
		if (path == null)
			return;
		String sdState = Environment.getExternalStorageState();
		if ((Environment.MEDIA_MOUNTED).equals(sdState)) {
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}
	}

	/**
	 * MD5大写加密
	 */
	public static String getMD5UpperStr(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] md5Bytes = messageDigest.digest();

		// for (int i = 0; i < byteArray.length; i++) {
		// if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
		// md5StrBuff.append("0").append(Integer.toHexString(0xFF &
		// byteArray[i]));
		// else
		// md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		// }

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString().toUpperCase();
	}

	/**
	 * MD5小写加密
	 */
	public static String getMD5LowerStr(String str) {
		return getMD5UpperStr(str).toLowerCase();
	}
	
	/**
	 * 获得序列的下一个id
	 * 
	 * @return 生成的id
	 * @throws AppBaseException
	 *             业务异常
	 */
	public static String getNextId() {
		return UUIDService.getInstance().simpleHex();
	}


	/**
	 * 将身份证号和人员编号进行MD5编码后作为主键
	 * 
	 * @return 生成的id
	 */
	@SuppressLint("SimpleDateFormat")
	public static String makeResultIdCardIDToMD5(String idNum, String personId) {
		StringBuilder sb = new StringBuilder();
		if (idNum != null) {
			sb.append(idNum.trim());
		}
		if (personId != null) {
			sb.append(personId.trim());
		}
		String id = getMD5UpperStr(sb.toString());
		return id;
	}

	/**
	 * 设备编号
	 */
	public static String getDeviceModel() {
		String device_model = Build.MODEL;
		return device_model.toUpperCase();
	}

	/**
	 * 保存错误信息到日志文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	public static void saveCrashInfo2File(Throwable ex) {

		StringBuffer sb = new StringBuffer();

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time = formatter.format(new Date());
			String fileName = "crash-" + time + "-" + timestamp + ".txt";
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String path = Constants.LOG_PATH;
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 返回由当前时间组成的字符串，用来给文件命名
	 */
	public static String getSystemTimeStringForFileName() {
		long timestamp = System.currentTimeMillis();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String time = formatter.format(new Date());
		String fileName = time + "-" + timestamp;
		return fileName;
	}

	/**
	 * 获取系统当前时间
	 */
	public static String getSystemTimeString() {
		DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	/* 取得系统日期 */
	@SuppressLint("DefaultLocale")
	public static String getSystemTime() {

		/* get current time for file */
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);// month+1当前时间
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String time = String.format("-%4d.%02d.%02d-%02d.%02d.%02d", year, (month + 1), day, hour, minute, second);
		return time;
	}

	/**
	 * 获得系统时间
	 * 
	 * @return 系统时间
	 */
	public static Date getSystemDateTime() {
		return new Date();
	}

	/**
	 * 追加时间
	 * 
	 * @param sourceTime
	 *            源时间
	 * @param timeType
	 *            时间类型 1年2月3日4时5分
	 * @param time
	 *            追加的时间值
	 * @return yyyy年MM月dd日 HH:mm
	 */
	public static String addTimeType(String sourceTime, int timeType, int time) {
		String tempString = "";
		if (!TextUtils.isEmpty(sourceTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat(defaultFormatStringCN);
			try {
				Date date = sdf.parse(sourceTime);
				switch (timeType) {
				case 1:// 年
					date.setYear(date.getYear() + time);
					break;
				case 2: // 月
					date.setMonth(date.getMonth() + time);
					break;
				case 3: // 日
					date.setDate(date.getDate() + time);
					break;
				case 4: // 时
					date.setHours(date.getHours() + time);
					break;
				case 5: // 分
					date.setMinutes(date.getMinutes() + time);
					break;
				default:
					break;
				}
				tempString = sdf.format(date);
			} catch (ParseException e) {

			}
		}
		return tempString;
	}

	/**
	 * 获得系统时间 （yyyy年MM月dd日 HH:mm）
	 * 
	 * @return 系统时间 （yyyy年MM月dd日 HH:mm）
	 */
	public static String getSystemDateTimeString2() {
		String datetime = DateUtil.getDateTime(defaultFormatStringCN, getSystemDateTime());
		return datetime;
	}

	/**
	 * 转换时间
	 * 
	 * @param sourceTime
	 *            源时间字符串 yyyy年MM月dd日 HH:mm
	 * @return Calendar
	 */
	public static Calendar timeStringToCalendar(String sourceTime, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(sourceTime);
		} catch (ParseException e) {

		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}


	/**
	 * 性别格式化,把数字转为文字。
	 */
	public static String sexFormet(String type) {
		if (type == null) {
			return "";
		}
		int ct = 0;
		try {
			ct = Integer.parseInt(type);
		} catch (NumberFormatException e) {
			return type;
		}
		String value = null;
		switch (ct) {
		case 1:
			value = "男";
			break;
		case 2:
			value = "女";
			break;
		default:
			value = "女";
			break;
		}
		return value;
	}

	/**
	 * 删除目录下的所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			File child = null;
			for (int i = 0; i < children.length; i++) {
				child = children[i];
				if (!child.isDirectory()) {
					child.delete();
				}
			}
		}
	}

	/**
	 * 打开assets文件夹中的文件
	 * 
	 * @param path
	 * @param assetManager
	 * @return
	 */
	public static InputStream openAssets(String path, AssetManager assetManager) {
		try {
			return assetManager.open(path);
		} catch (Exception e) {

			LogUtil.insertSysLogTOFile(e);
		}
		return null;
	}

	/**
	 * inputstream转file
	 * 
	 * @param ins
	 * @param file
	 */
	public static void inputstreamtofile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[7024];
			while ((bytesRead = ins.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.flush();
			ins.close();
			os.close();
		} catch (Exception e) {

			// 保存日志文件
			saveCrashInfo2File(e);
		}

	}

	/**
	 * 保存日志信息到文件中
	 * 
	 * @param ex
	 *            addToFile true表示追加 false表示重写
	 * @return 返回文件名称,便于将文件传送到服务器
	 */
	public static void saveLog2File(String conent, boolean addToFile) {

		try {

			String fileName = "mylog.txt";

			String path = Constants.LOG_PATH;
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File logs = new File(path + fileName);
			if (!logs.exists())
				logs.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logs, addToFile)));
			out.write(conent);
			out.write("\n");
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 获取app当前版本号
	 */
	public static String getAppVersionName() {
		String versionName = "";
		try {
			PackageManager packageManager = MyApplication.getMyApplication().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo("com.stone.ordering", 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

}
