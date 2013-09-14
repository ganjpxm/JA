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
	
	//----------------------------- JWeb -----------------
	public static final String SERVER_IP = "http://192.168.0.100:8080/jp";
	public static final String URL_LOGIN = SERVER_IP + "/login";
	public static final String URL_GET_BM_CONFIGS = SERVER_IP + "/mobile/getBmConfigs";
	
	public static final String KEY_LOGIN_USER_CD_OR_EMAIL = "userCdOrEmail";
	public static final String KEY_LOGIN_USER_PASSWORD = "userPassword";
	public static final String KEY_CONFIG_CDS = "configCds";
	public static final String KEY_CONFIG_LAST_TIME = "bmConfigLastTime";
	public static final String KEY_LAST_TIME = "lastTime";
	public static final String KEY_CONFIG_CD_AUDIO_URL = "AudioUrl";
	public static final String KEY_CONFIG_CD_IMAGE_URL = "ImageUrl";
	public static final String KEY_CONFIG_CD_KEY_VIDEO_URL = "VideoUrl";
	public static final String KEY_CONFIG_CD_KEY_FILE_URL = "FileUrl";
	
	public static final String VALUE_LOGIN_USER_CD = "mobile";
	public static final String VALUE_LOGIN_PASSWORD = "1";
	public static final String VALUE_CONFIG_TAGS = "('AudioUrl','ImageUrl','VideoUrl','FileUrl')";
	
	//----------------------------- Knowledge --------------------------
	public static final String KNOWLEDGE_CATAGORY_ID = "knowledge_catagory_id";
	public static final int PROGRAM_ANDROID = 1;
	public static final int PROGRAM_IOS = 2;
	public static final int NEWS_MOBILE_APP = 11;
}