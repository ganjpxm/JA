/**
 * Copyright (c) Gan Jianping, 2013. All rights reserved.
 * Author: GanJianping
 */
package org.ganjp.jone.common;

public abstract class JOneConst {
	//------------------------------ App info -----------------------------
	public static final String APP_VENDOR = "GANJP";
	public static final String APP_PACKAGE = "org.ganjp.jone";
	public static final String APP_VERSION = "1.0.0";
	
	//------------------------------ Time -----------------------------
	public static final int DURATION_SPLASH = 3 * 1000;  // 3 seconds 
	public static final long TIMEOUT_CONNECT = 30 * 1000; // 10 seconds 
	public static final long TIMEOUT_SUBMIT_FORM = 2 * 60 * 1000; //2
	public static final long UPLOAD_TIMEOUT = 20 * 60000;
	public static final long EXPIRED_TIME_FILE = 1 * 24 * 60 * 60000;
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	//----------------------------- Database -------------------------- 
	public static final String DATABASE_NAME = "dbs.db";
	public static final int DATABASE_VERSION = 1;
	public static final String COLUMN_CREATE_TIME = "create_time";
	public static final String COLUMN_MODIFY_TIME = "modify_time";
	
	//----------------------------- Knowledge --------------------------
	public static final String KNOWLEDGE_CATAGORY_ID = "knowledge_catagory_id";
	public static final int PROGRAM_ANDROID = 1;
	public static final int PROGRAM_IOS = 2;
	public static final int NEWS_MOBILE_APP = 11;
	
}