/**
 * Const.java
 *
 * Created by Gan Jianping on 15/06/13.
 * Copyright (c) 2013 DBS. All rights reserved.
 */
package org.ganjp.jlib.core;


/**
 * <p>Global Constant</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class Const {
	//------------------------------ Time -----------------------------
	public static final int DURATION_SPLASH = 2 * 1000;  // 2 seconds 
	public static final int TIMEOUT_CONNECT = 15 * 1000; // 10 seconds 
	public static final int TIMEOUT_SUBMIT_FORM = 2 * 60 * 1000; //2
	public static final int UPLOAD_TIMEOUT = 20 * 60000;
	public static final long EXPIRED_TIME_FILE = 1 * 24 * 60 * 60000;
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	//------------------------------ File -----------------------------
	public static final String PICTURE_ALBUM = "Pictures";
	public static final String IMG_PREFIX = "IMG_";
	public static final String IMG_SUFFIX = ".jpg";
	public static final int IMG_RESOLUCTION_UPLOAD = 500;
	public static final int IMG_RESOLUCTION_OCR = 1280;
	
	//----------------------------- Database -------------------------- 
	public static final String DATABASE_NAME = "jone.db";
	public static final int DATABASE_VERSION = 1;
	public static final String COLUMN_LANG = "lang";
	public static final String COLUMN_CREATE_TIME = "create_date_time";
	public static final String COLUMN_MODIFY_TIMESTAMP = "modify_timestamp";
	public static final String COLUMN_DATA_STATE = "data_state";
	
	//----------------------------- Key and value ------------------
	public static final short FLAG_YES = 1;
	public static final short FLAG_NO = 0;
	public static final short FLAG_ON = 1;
	public static final short FLAG_OFF = 0;
	public static final String KEY_RESULT = "result";
	
	//---------------------------------------- Network ----------------------------
	public static final String GOOGLE_DOC_VIEW_URL = "https://docs.google.com/gview?embedded=true&url=";
	
}
