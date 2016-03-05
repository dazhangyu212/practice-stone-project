package com.stone.ordering.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/*
 * */
public class DebugUtilHelper {

	/*
	 * Parameters List_Items
	 */
	private static final String TAG = "UtilHelper";
	public static final String DATA_PATH_SPLITFLAG = "@";
	public static final String DATA_PATH_REPLACER = "\u001E";
	public static final String FING_PATH_REPLACER = ",";

	/*
	 * Function List_Items
	 */
	public static void print(String info) {
		Log.d(TAG, info);
		// Log.e(TAG, info);
	}

	public static void printHint(Context mCnt, String info) {
		Toast.makeText(mCnt, info, Toast.LENGTH_SHORT).show();
	}

	private static Toast mToast = null;

	public static void ShowToast(Context context, String msg, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, duration);
		} else {
			// Update the text in a Toast that was previously created using one
			// of the makeText() methods.
			mToast.setText(msg);
		}
		mToast.show();
	}

	public static byte[] imgToBytes(String imgPath) {
		ByteArrayOutputStream outStream;
		FileInputStream fin;
		byte[] data = null;
		try {
			byte[] buffer = new byte[1024];
			int len = -1;
			fin = new FileInputStream(imgPath);

			outStream = new ByteArrayOutputStream();
			while ((len = fin.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			data = outStream.toByteArray();
			fin.close();
			outStream.close();

		} catch (Exception e) {

		}

		return data;

	}

	/* 读取配置信息 */
	public static List<String> readLineData(String InfoPath) {

		List<String> StrgyTable = new ArrayList<String>();
		BufferedReader br = null;
		try {
			FileInputStream fis = new FileInputStream(InfoPath);
			InputStreamReader isr = new InputStreamReader(fis, "GBK");// 防止字符串乱码（注意）
			br = new BufferedReader(isr);
			// br = new BufferedReader(new InputStreamReader(new
			// FileInputStream(InfoPath)));

			String line = br.readLine();// 读取一行
			while (line != null) { // 如果 line 为空说明读完
				StrgyTable.add(line);// 将读到的内容添加到 List 中
				line = br.readLine(); // 读取下一行
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		print("StrgyTable.size->" + StrgyTable.size());
		return StrgyTable;
	}

	// 判断文件或目录是否存在(如果不存在可选建立文件)
	public static boolean fileIsExists(String fileName, boolean isCreate) {
		try {
			// true if this file exists, false otherwise
			File file = new File(fileName);
			if (!file.exists())// 不存在
			{
				if (isCreate) {
					file.createNewFile();
				}
			} else// 存在
			{
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	// 对话框提示信息
	public static void showDialog(Context ctx, String title, String msg) {
		new AlertDialog.Builder(ctx).setTitle(title).setIcon(android.R.drawable.ic_dialog_info).setMessage(msg).setPositiveButton("确定", null).show();
	}

	// 判断文件或目录是否存在
	public static boolean fileIsExists(String fileName) {
		try {
			File f = new File(fileName);
			if (!f.exists()) {// true if this file exists, false otherwise.
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 保存数据到指定文件
	 * 
	 * @param data
	 *            保存数据
	 * @param filename
	 *            保存文件名
	 */
	public static void writeStrToFile(String data, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(filename));
			Writer os = new OutputStreamWriter(fos, "GBK");
			os.write(data);
			os.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 读取tlv文件到byte数组
	 * 
	 * @param filename
	 *            tlv文件名
	 * @return byte数组
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filename) throws IOException {

		File f = new File(filename);
		if (!f.exists()) {
			throw new FileNotFoundException(filename);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	public static Bitmap GetBitMapFromFilePath(Context ctxPt, String probe_path, int width, int height) {
		print("GetBitMapFromFilePath probePath->" + probe_path);
		print("GetBitMapFromFilePath newWidth->" + width + "-->newHeight->" + height);

		if (probe_path == null) {
			print("probe_path is null");
			return null;
		}
		Uri probe_uri = Uri.fromFile(new File(probe_path));
		String ldbResultStatus;
		int imgSampleSize;
		Bitmap bitmap = null;
		try {
			ContentResolver cr = ctxPt.getContentResolver();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;// 只描边，不读取数据
			BitmapFactory.decodeStream(cr.openInputStream(probe_uri), null, options);

			if (options.outWidth > options.outHeight) // must < 1024*768*2
				imgSampleSize = (options.outWidth + 360) / width; // >1000 ->
																	// resample
			else
				imgSampleSize = (options.outHeight + 360) / height;

			options.inSampleSize = imgSampleSize;

			Log.i("avin", "Input bitmap samplesize=" + options.inSampleSize + " mod: w=" + options.outWidth + ",h=" + options.outHeight);

			options.inJustDecodeBounds = false;// 读取数据
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			bitmap = BitmapFactory.decodeStream(cr.openInputStream(probe_uri), null, options);

		} catch (FileNotFoundException e) {
			ldbResultStatus = "图片解码错误！";
			Toast.makeText(ctxPt, ldbResultStatus, Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			ldbResultStatus = "内存不足错误！";
			Toast.makeText(ctxPt, ldbResultStatus, Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		return bitmap;
	}

	// 执行控制台命令，参数为命令行字符串方式，申请Root控制权限（-后续可以考虑永久获取Root权限的方法）
	public static boolean RootCommand(Context context, String command) {
		// File execpath = new File("/data/data/com.hisign.matchmobile/lib");
		String packPath = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/../lib";
		File execpath = new File(packPath);
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");// 执行这一句，superuser.apk就会弹出授权对话框
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("cd " + execpath + "\n");
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			Log.d("*** DEBUG ***", "ROOT REE" + e.getMessage());
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		Log.d("*** DEBUG ***", "Root SUC ");
		return true;
	}
}
