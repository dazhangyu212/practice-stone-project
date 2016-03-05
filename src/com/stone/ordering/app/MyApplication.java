package com.stone.ordering.app;

import java.io.File;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.stone.ordering.R;
import com.stone.ordering.data.DatabaseHelper;
import com.stone.ordering.exception.CrashHandler;
import com.stone.ordering.util.Constants;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;

public class MyApplication extends Application {

	private static MyApplication myApplication = null;

	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = this;
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		initDir();
		initDB();
		initImageLoader();
//		resetPower();
	}

	private void initImageLoader() {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory(false)
				.cacheOnDisk(false)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

//		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // resource
//				// or
//				// drawable
//				.showImageForEmptyUri(R.drawable.img_cardphoto) // resource or
//				// drawable
//				.showImageOnFail(R.drawable.img_cardphoto) // resource or
//				// drawable
//				.resetViewBeforeLoading(false) // default
//				.delayBeforeLoading(1000).cacheInMemory(false) // default
//				.cacheOnDisk(false) // default
//				.considerExifParams(false) // default
//				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
//				.bitmapConfig(Bitmap.Config.ARGB_8888) // default
//				.displayer(new SimpleBitmapDisplayer()) // default
//				.handler(new Handler()) // default
//				.build();

		ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options).build());
	}

	private void initDir() {
//		File data_1v1 = new File(Constant.DATA_1V1_PATH);
//		if (!data_1v1.exists()) {
//			data_1v1.mkdirs();
//		}
//		File data_1vn = new File(Constant.DATA_1VN_PATH);
//		if (!data_1vn.exists()) {
//			data_1vn.mkdirs();
//		}
//		File data_car = new File(Constant.DATA_CAR_PATH);
//		if (!data_car.exists()) {
//			data_car.mkdirs();
//		}
		File dbs = new File(Constants.DATA_PATH);
		if (!dbs.exists()) {
			dbs.mkdirs();
		}
//		File temp = new File(Constant.TEMP_PATH);
//		if (!temp.exists()) {
//			temp.mkdirs();
//		}

	}

	private void initDB() {
		// TODO Auto-generated method stub
		File file = new File(Constants.COLLECT_DB_PATH);
		if (!file.exists()) {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
				File parent = file.getParentFile();
				parent.mkdirs();
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Constants.COLLECT_DB_PATH, null);
				DatabaseHelper orm;
				orm = DatabaseHelper.getHelper();
				orm.onCreate(db);
				db.close();
			}
		} else {
//			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
//				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Constant.COLLECT_DB_PATH, null);
//				DatabaseHelper orm;
//
//				try {
//					orm = DatabaseHelper.getHelper();
//					orm.onUpgrade(db, db.getVersion(), getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
//					db.close();
//				} catch (NameNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			
//			SysUtilManager.inputstreamtofile(SysUtilManager.openAssets("efis_config.db", myApplication.getAssets()), new File(Constant.DATA_PATH+"efis_config.db"));
		}
	
//		File systemDb = new File(Constant.SYSTEM_DB_PATH);
//		if (!systemDb.exists()) {
//			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
//				File parent = systemDb.getParentFile();
//				parent.mkdirs();
//				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Constant.SYSTEM_DB_PATH, null);
//				DatabaseHelper2SystemDB orm;
//				orm = DatabaseHelper2SystemDB.getHelper();
//				orm.onCreate(db);
//				db.close();
//				
//			}
//		}
//
//		File faceDb = new File(Constant.FACE_DB_PATH);
//		if (!faceDb.exists()) {
//			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
//				File parent = faceDb.getParentFile();
//				parent.mkdirs();
//				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Constant.FACE_DB_PATH, null);
//				DatabaseHelper2FaceDB orm;
//				orm = DatabaseHelper2FaceDB.getHelper();
//				orm.onCreate(db);
//				db.close();
//				
//			}
//		}
//
//		File fingerDb = new File(Constant.FINGER_DB_PATH);
//		if (!fingerDb.exists()) {
//			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == true) {
//				File parent = fingerDb.getParentFile();
//				parent.mkdirs();
//				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Constant.FINGER_DB_PATH, null);
//				DatabaseHelper2FingerDB orm;
//				orm = DatabaseHelper2FingerDB.getHelper();
//				orm.onCreate(db);
//				db.close();
//				
//			}
//		}
 
	}

	/**
	 * 
	 * @return
	 */
	public static MyApplication getMyApplication() {
		return myApplication;
	}
	 
}
