/**
 * Copyright (c) Gan Jianping, 2013. All rights reserved.
 * Author: GanJianping
 */
package org.ganjp.jone.common;

public abstract class JpConst {
	// Change this to the project id from your API project created at code.google.com, as shown in the url of your project.
	public static final String SENDER_ID = null;
  	// Change this to match your server.
  	public static final String SERVER_URL = null;

  	public static final String TAG = "sqisland";

  	public static final String ACTION_ON_REGISTERED = "com.sqisland.android.gcm_client.ON_REGISTERED";

  	public static final String FIELD_REGISTRATION_ID = "registration_id";
  	public static final String FIELD_MESSAGE = "msg";
  
	//------------------------------ Time -----------------------------
	public static final int DURATION_SPLASH = 3 * 1000;  // 3 seconds 
	public static final long TIMEOUT_CONNECT = 30 * 1000; // 10 seconds 
	public static final long TIMEOUT_SUBMIT_FORM = 2 * 60 * 1000; //2
	public static final long UPLOAD_TIMEOUT = 20 * 60000;
	public static final long EXPIRED_TIME_FILE = 1 * 24 * 60 * 60000;
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
}