/**
 * Copyright (c) Gan Jianping, 2013. All rights reserved.
 * Author: GanJianping
 */
package org.ganjp.jone.common;


public abstract class JOneConst {
	//------------------------------ App info -----------------------------
	public static final String APP_VENDOR = "JAPP";
	public static final String APP_PACKAGE = "org.ganjp.jone";
	public static final String APP_VERSION = "1.0.0";
	
	//----------------------------- JWeb -----------------
//	public static final String SERVER_IP = "http://192.168.0.103:8080/jp";
	public static final String SERVER_IP = "http://www.ganjianping.com";
	public static final String URL_LOGIN = SERVER_IP + "/login";
	public static final String URL_GET_BM_CONFIGS = SERVER_IP + "/mobile/getBmConfigs";
	public static final String URL_GET_CM_ARTICLES = SERVER_IP + "/mobile/getCmArticles";
	public static final String URL_GET_CM_PHOTOS = SERVER_IP + "/mobile/getCmPhotos";
	
	public static final String LANG_EN_SG = "en_SG";
	public static final String LANG_ZH_CN = "zh_CN";
	public static final String FAIL = "fail";
	public static final String SUCCESS = "success";
	public static final String TIMEOUT = "timeout";
	
	public static final String KEY_TAG = "tag";
	public static final String KEY_UUID = "uuid";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_LOGIN_USER_CD_OR_EMAIL = "userCdOrEmail";
	public static final String KEY_LOGIN_USER_PASSWORD = "userPassword";
	public static final String KEY_CONFIG_CDS = "configCds";
	public static final String KEY_LAST_TIME = "lastTime";
	public static final String KEY_START_DATE = "startDate";
	public static final String KEY_CONFIG_CD_AUDIO_URL = "AudioUrl";
	public static final String KEY_CONFIG_CD_IMAGE_URL = "ImageUrl";
	public static final String KEY_CONFIG_CD_VIDEO_URL = "VideoUrl";
	public static final String KEY_CONFIG_CD_FILE_URL = "FileUrl";
	public static final String KEY_CONFIG_CD_MOBILE_TAGS = "MobileTags";
	
	public static final String KEY_PREFERENCE_CONFIG_LAST_TIME = APP_PACKAGE + ".bmConfigLastTime";
	public static final String KEY_PREFERENCE_ARTICLE_LAST_TIME = APP_PACKAGE + ".cmArticleLastTime";
	public static final String KEY_PREFERENCE_PHOTO_LAST_TIME = APP_PACKAGE + ".cmPhotoLastTime";
	public static final String KEY_PREFERENCE_LANG = APP_PACKAGE + ".lang";
	
	public static final String VALUE_LOGIN_USER_CD = "mobile";
	public static final String VALUE_LOGIN_PASSWORD = "1";
	public static final String VALUE_CONFIG_CDS = "('AudioUrl','ImageUrl','VideoUrl','FileUrl','MobileTags')";
	
	//----------------------------- Knowledge --------------------------
	public static final int PROGRAM_ANDROID = 1;
	public static final int PROGRAM_IOS = 2;
	public static final int NEWS_MOBILE_APP = 11;
}