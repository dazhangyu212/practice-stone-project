package com.stone.ordering.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public final class ByteUtils {

	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	public static short toShort(byte[] bytes) {
		return (short) (((-(short) Byte.MIN_VALUE + (short) bytes[0]) << 8) - (short) Byte.MIN_VALUE + (short) bytes[1]);
	}

	public static byte[] toBytes(int value) {
		byte[] result = new byte[4];
		for (int i = 3; i >= 0; i--) {
			result[i] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
			value >>>= 8;
		}
		return result;
	}

	public static byte[] toBytes(short value) {
		byte[] result = new byte[2];
		for (int i = 1; i >= 0; i--) {
			result[i] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
			value >>>= 8;
		}
		return result;
	}

	/**
	 * Convert Byte Array to Hex Value
	 * 
	 * @param buf
	 *            Byte Array to convert to Hex Value
	 * @param offset
	 *            Starting Offset for Conversion
	 * @param length
	 *            Length to convery
	 * @param value
	 *            Hex Value
	 */
	public static void toHexValue(byte[] buf, int offset, int length, int value) {
		do {
			buf[offset + --length] = Constants.HEX_DIGITS[value & 0x0f];
			value >>>= 4;
		} while (value != 0 && length > 0);

		while (--length >= 0) {
			buf[offset + length] = Constants.HEX_DIGITS[0];
		}
	}
	
    /**
     * @description 把byte数组写入文件中
     * @param data 要写入的byte数组
     * @param filePath 要保存成的完整的文件路径
     * @return true:写入成功 | false:写入失败
     */
    public static boolean byteArrToFile(byte[] data, String filePath){
    	if(data == null) return false;
    	FileOutputStream stream;
		try {
			stream = new FileOutputStream(filePath);
			stream.write(data);
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
    }
    
	/**
	 * 将图像水平镜像
	 * @param bmp 源图像
	 * @return 水平镜像后的图像
	 */
	public static Bitmap horizontalMirror(Bitmap bmp) {
		int w = bmp.getWidth();
		int h = bmp.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(-1, 1);
		Bitmap convertBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);
		return convertBmp;
	}

}
