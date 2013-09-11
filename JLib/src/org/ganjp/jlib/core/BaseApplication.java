/**
 * BaseApplication.java
 *
 * Created by Gan Jianping on 08/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jlib.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;

/**
 * <p>Base Application</p>
 * 
 * @author GanJianping
 *
 */
public class BaseApplication extends Application {
	
	private boolean mIsScreenLarge;
	private boolean mIsScreenExtraLarge;

	private static Context context;

	/**
	 * <p>Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.</p>
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		BaseApplication.context = getApplicationContext();
		mIsScreenLarge = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE;
		mIsScreenExtraLarge = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	/**
	 * <p>Get application context</p>
	 * 
	 * @return Context
	 */
	public static Context getAppContext() {
		return BaseApplication.context;
	}
	
	/**
	 * <p>Is airplane mode on</p>
	 * 
	 * @return boolean
	 */
	public boolean isAirplaneModeOn() {
		return Settings.System.getInt(this.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}
	
	/**
	 * <p>is tablet</p>
	 * 
	 * @return boolean
	 */
	public boolean isTablet() {
		return mIsScreenExtraLarge;
	}

	/**
	 * <p>Is screen large or extra large</p>
	 * 
	 * @return boolean
	 */
	public boolean isScreenLargeOrExtraLarge() {
		return mIsScreenLarge | mIsScreenExtraLarge;
	}
	
	/**
	 * <p>Is screen large </p>
	 * 
	 * @return boolean
	 */
	public boolean isScreenLarge() {
		return mIsScreenLarge;
	}
	
	/**
	 * <p>Is screen extra large </p>
	 * 
	 * @return boolean
	 */
	public boolean isScreenExtraLarge() {
		return mIsScreenExtraLarge;
	}

}
