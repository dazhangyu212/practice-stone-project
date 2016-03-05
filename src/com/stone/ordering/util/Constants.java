package com.stone.ordering.util;

import android.os.Environment;

/**
 * 类名:Constants
 * 描述:
 * 公司:北京海鑫科技高科技股份有限公司
 * 作者:zhangyu
 * 创建时间:2016年3月5日
 */
public class Constants {
	/**
	 * SD卡路径名
	 */
	public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getPath();
	/**
	 * 应用程序名
	 */
	public static final String PRO_NAME = "StoneOrdering";
	/**
	 * 项目路径
	 */
	public static final String PRO_PATH = PATH_SDCARD + "/" + PRO_NAME + "/";
	/**
	 * 日志路径
	 */
	public static final String LOG_PATH = PRO_PATH + "Log/";
	/**
	 * 数据库路径
	 */
	public static final String DATA_PATH = PRO_PATH + "dbs/";
	/**
	 * 现场采集和比对结果数据库
	 */
	public static final String COLLECT_DB_PATH = DATA_PATH + "main.db";
	
	static public final byte HEX_DIGITS[] = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8',
			(byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f' };

}
