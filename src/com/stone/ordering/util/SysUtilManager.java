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
 * ������SysUtilManager ������ϵͳ�����࣬���һЩͨ�÷����� ��˾:�������οƽ�߿Ƽ��ɷ����޹�˾ ���ߣ�yuchengkuan
 * �汾��V1.0 ����ʱ�䣺2015��9��10�� ����޸�ʱ�䣺2015��9��10��
 */
public class SysUtilManager {

	/**
	 * Ĭ��ʱ���ʽ ���� yyyy��MM��dd�� hh:mm
	 */
	public static String defaultFormatStringCN = "yyyy��MM��dd��HHʱmm��";

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
	 * ��һ��byte[]д��SDCard����Ϊһ��ͼƬ�ļ�
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
	 * ��ȡ��ǰ�汾��
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
	 * �����ļ�
	 * 
	 * @param sourceFile
	 *            Դ�ļ� ��������·�����ļ���
	 * @param toFile
	 *            Ŀ���ļ� ��������·�����ļ���
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
	 * ��SDCard�ϴ����ļ�
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
	 * ��SDCard�ϴ����ļ���
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
	 * ���ؼ���
	 * 
	 * @param mcontext
	 */
	public static void keyBoardHide(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * Ϊ�ֳ����ջ��߲ɼ�ָ����������ͼƬ����������
	 * 
	 * @param idCard
	 *            ���֤��
	 * @param type
	 *            ָ��ͼƬ�����ֳ�������Ƭ��0��ʾָ�ƣ�1��ʾ�߳�������Ƭ
	 * @param fingerIndex
	 *            ָλ��� �����Ϊ�������ò���������������
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
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static float getSystemDensity(Context context) {
		float scale = context.getResources().getDisplayMetrics().density;
		return scale;
	}

	/**
	 * ��bitmap����SDCard��
	 * 
	 * @param fingerprint_bitmap
	 * @return ��������·��
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
	 * ɾ��������ļ�
	 * 
	 * @param path
	 *            ͼƬ·��
	 * @return �ܵ�Ӱ����ļ���
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
	 * MD5��д����
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
	 * MD5Сд����
	 */
	public static String getMD5LowerStr(String str) {
		return getMD5UpperStr(str).toLowerCase();
	}
	
	/**
	 * ������е���һ��id
	 * 
	 * @return ���ɵ�id
	 * @throws AppBaseException
	 *             ҵ���쳣
	 */
	public static String getNextId() {
		return UUIDService.getInstance().simpleHex();
	}


	/**
	 * �����֤�ź���Ա��Ž���MD5�������Ϊ����
	 * 
	 * @return ���ɵ�id
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
	 * �豸���
	 */
	public static String getDeviceModel() {
		String device_model = Build.MODEL;
		return device_model.toUpperCase();
	}

	/**
	 * ���������Ϣ����־�ļ���
	 * 
	 * @param ex
	 * @return �����ļ�����,���ڽ��ļ����͵�������
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
	 * �����ɵ�ǰʱ����ɵ��ַ������������ļ�����
	 */
	public static String getSystemTimeStringForFileName() {
		long timestamp = System.currentTimeMillis();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String time = formatter.format(new Date());
		String fileName = time + "-" + timestamp;
		return fileName;
	}

	/**
	 * ��ȡϵͳ��ǰʱ��
	 */
	public static String getSystemTimeString() {
		DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	/* ȡ��ϵͳ���� */
	@SuppressLint("DefaultLocale")
	public static String getSystemTime() {

		/* get current time for file */
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);// month+1��ǰʱ��
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String time = String.format("-%4d.%02d.%02d-%02d.%02d.%02d", year, (month + 1), day, hour, minute, second);
		return time;
	}

	/**
	 * ���ϵͳʱ��
	 * 
	 * @return ϵͳʱ��
	 */
	public static Date getSystemDateTime() {
		return new Date();
	}

	/**
	 * ׷��ʱ��
	 * 
	 * @param sourceTime
	 *            Դʱ��
	 * @param timeType
	 *            ʱ������ 1��2��3��4ʱ5��
	 * @param time
	 *            ׷�ӵ�ʱ��ֵ
	 * @return yyyy��MM��dd�� HH:mm
	 */
	public static String addTimeType(String sourceTime, int timeType, int time) {
		String tempString = "";
		if (!TextUtils.isEmpty(sourceTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat(defaultFormatStringCN);
			try {
				Date date = sdf.parse(sourceTime);
				switch (timeType) {
				case 1:// ��
					date.setYear(date.getYear() + time);
					break;
				case 2: // ��
					date.setMonth(date.getMonth() + time);
					break;
				case 3: // ��
					date.setDate(date.getDate() + time);
					break;
				case 4: // ʱ
					date.setHours(date.getHours() + time);
					break;
				case 5: // ��
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
	 * ���ϵͳʱ�� ��yyyy��MM��dd�� HH:mm��
	 * 
	 * @return ϵͳʱ�� ��yyyy��MM��dd�� HH:mm��
	 */
	public static String getSystemDateTimeString2() {
		String datetime = DateUtil.getDateTime(defaultFormatStringCN, getSystemDateTime());
		return datetime;
	}

	/**
	 * ת��ʱ��
	 * 
	 * @param sourceTime
	 *            Դʱ���ַ��� yyyy��MM��dd�� HH:mm
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
	 * �Ա��ʽ��,������תΪ���֡�
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
			value = "��";
			break;
		case 2:
			value = "Ů";
			break;
		default:
			value = "Ů";
			break;
		}
		return value;
	}

	/**
	 * ɾ��Ŀ¼�µ������ļ�
	 * 
	 * @param dir
	 *            ��Ҫɾ�����ļ�Ŀ¼
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
	 * ��assets�ļ����е��ļ�
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
	 * inputstreamתfile
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

			// ������־�ļ�
			saveCrashInfo2File(e);
		}

	}

	/**
	 * ������־��Ϣ���ļ���
	 * 
	 * @param ex
	 *            addToFile true��ʾ׷�� false��ʾ��д
	 * @return �����ļ�����,���ڽ��ļ����͵�������
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
	 * ��ȡapp��ǰ�汾��
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
