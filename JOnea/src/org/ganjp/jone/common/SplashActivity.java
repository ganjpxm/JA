/**
 * SplashActivity.java
 *
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.ganjp.jlib.core.Const;
import org.ganjp.jlib.core.util.HttpConnection;
import org.ganjp.jlib.core.util.NetworkUtil;
import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jlib.core.util.ThreadUtil;
import org.ganjp.jone.R;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * <p>Splash screen activity</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class SplashActivity extends JOneActivity {
	
	private boolean mIsBackButtonPressed; // used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
    ProgressBar progress;
    boolean isTimeout = false;
    String jsonData = "";
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_activity_splash);
		progress = (ProgressBar) findViewById(R.id.progress);
		progress.setVisibility(TextView.VISIBLE);
		
		//set lang for first time
		if (StringUtil.isEmpty(PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG))) {
			Configuration conf = getResources().getConfiguration();  
		    String locale = conf.locale.getDisplayName(conf.locale);//English (United States)
			if (locale.indexOf("中文")!=-1) {
				PreferenceUtil.saveString(JOneConst.KEY_PREFERENCE_LANG, JOneConst.LANG_ZH_CN);
			} else {
				PreferenceUtil.saveString(JOneConst.KEY_PREFERENCE_LANG, JOneConst.LANG_EN_SG);
			}
		}
		
		Resources resources = getResources();
	    Configuration config = resources.getConfiguration();
	    DisplayMetrics dm = resources.getDisplayMetrics();
	    String lang = PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG);
	    if (lang.equals(JOneConst.LANG_ZH_CN)) {
			config.locale = Locale.SIMPLIFIED_CHINESE;
		} else {
			config.locale = Locale.ENGLISH;
		}
	    resources.updateConfiguration(config, dm);
				
		if (NetworkUtil.isWifiAvailable(getApplicationContext())) {
			
			ThreadUtil.run(new Runnable() {
	       		@Override
	            public void run() {
	       			if (StringUtil.isEmpty(jsonData)) {
	       				isTimeout = true;
	       				forward();
	       				showToastFromBackground(JOneConst.TIMEOUT);
	       			}
	            }
	    	}, 15*1000);
			
			new Thread(new Runnable() {
				public void run() {
					try {
						HttpConnection httpConnection = new HttpConnection(false);
						//login
						ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
						pairs.add(new BasicNameValuePair(JOneConst.KEY_LOGIN_USER_CD_OR_EMAIL, JOneConst.VALUE_LOGIN_USER_CD));
						pairs.add(new BasicNameValuePair(JOneConst.KEY_LOGIN_USER_PASSWORD, JOneConst.VALUE_LOGIN_PASSWORD));
						httpConnection.post(JOneConst.URL_LOGIN, new UrlEncodedFormEntity(pairs));
						jsonData = HttpConnection.processEntity(httpConnection.getResponse().getEntity());
						if (isTimeout==false) { 
							JOneUtil.getDataFromJWeb(httpConnection, true);
							forward();
						}
					} catch (Exception e) {
						e.printStackTrace();
						forward();
						showToastFromBackground(JOneConst.FAIL);
					}
				}
			}).start();
		} else {
			// run a thread after 3 seconds to start the Loan Activity
			ThreadUtil.run(new Runnable() {
	            @Override
	            public void run() {
	                // start the Loan Activity if the back button wasn't pressed already 
	                forward();
	            }
	         }, Const.DURATION_SPLASH);
		}
	}
	
	@Override
	public void onBackPressed() {
		mIsBackButtonPressed = true; // set the flag to true so the next activity won't start up
		super.onBackPressed();
	}

	public void forward() {
		finish();
		if (!mIsBackButtonPressed) {
	        Intent intent = new Intent(SplashActivity.this, HomeFragmentActivity.class);
	        SplashActivity.this.startActivity(intent);
	        transitForward();
	    }
	}
}
	
	