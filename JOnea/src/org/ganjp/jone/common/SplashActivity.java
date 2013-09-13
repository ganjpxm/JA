/**
 * SplashActivity.java
 *
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.ganjp.jlib.core.util.HttpConnection;
import org.ganjp.jone.R;
import org.ganjp.jone.jweb.dao.BmConfigDAO;
import org.ganjp.jone.jweb.entity.BmConfig;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    String message = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_activity_splash);
		progress = (ProgressBar) findViewById(R.id.progress);
		progress.setVisibility(TextView.VISIBLE);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					HttpConnection h = new HttpConnection(false);
					ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
					pairs.add(new BasicNameValuePair("os", "android"));
					h.post("http://192.168.1.103:8080/jp/mobile/getBmConfigs", new UrlEncodedFormEntity(pairs));
					message = HttpConnection.processEntity(h.getResponse().getEntity());
					System.out.println(message);
					Log.e("SplashActivity", message);
					ObjectMapper mapper = new ObjectMapper();
					BmConfig[] bmConfigs = mapper.readValue(message, BmConfig[].class);
					BmConfigDAO.getBmConfigDAO().insertOrUpdate(bmConfigs);
				} catch (Exception e) {
					e.printStackTrace();
				}
				progress.post(new Runnable() {
					public void run() {
						finish();
						if (!mIsBackButtonPressed) {
		                    Intent intent = new Intent(SplashActivity.this, HomeFragmentActivity.class);
		                    SplashActivity.this.startActivity(intent);
		                    transitForward();
		                }
					}
				});
			}
		}).start();
//		// run a thread after 3 seconds to start the Loan Activity
//		ThreadUtil.run(new Runnable() {
//            @Override
//            public void run() {
//                // make sure we close the splash screen so the user won't come back when it presses back key
//                finish();
//                
//                // start the Loan Activity if the back button wasn't pressed already 
//                if (!mIsBackButtonPressed) {
//                    Intent intent = new Intent(SplashActivity.this, HomeFragmentActivity.class);
//                    SplashActivity.this.startActivity(intent);
//                    transitForward();
//                }
//            }
//         }, JOneConst.DURATION_SPLASH);
	}
	
	@Override
	public void onBackPressed() {
		mIsBackButtonPressed = true; // set the flag to true so the next activity won't start up
		super.onBackPressed();
	}

}