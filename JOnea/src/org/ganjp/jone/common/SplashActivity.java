/**
 * SplashActivity.java
 *
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.ganjp.jlib.core.Const;
import org.ganjp.jlib.core.util.DateUtil;
import org.ganjp.jlib.core.util.HttpConnection;
import org.ganjp.jlib.core.util.NetworkUtil;
import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jlib.core.util.ThreadUtil;
import org.ganjp.jone.R;
import org.ganjp.jone.jweb.dao.BmConfigDAO;
import org.ganjp.jone.jweb.dao.CmArticleDAO;
import org.ganjp.jone.jweb.dao.CmPhotoDAO;
import org.ganjp.jone.jweb.entity.BmConfig;
import org.ganjp.jone.jweb.entity.CmArticle;
import org.ganjp.jone.jweb.entity.CmPhoto;
import org.json.JSONObject;

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
			new Thread(new Runnable() {
				public void run() {
					try {
						if (NetworkUtil.connect(JOneConst.SERVER_IP)) {
							HttpConnection h = new HttpConnection(false);
							//login
							ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
							pairs.add(new BasicNameValuePair(JOneConst.KEY_LOGIN_USER_CD_OR_EMAIL, JOneConst.VALUE_LOGIN_USER_CD));
							pairs.add(new BasicNameValuePair(JOneConst.KEY_LOGIN_USER_PASSWORD, JOneConst.VALUE_LOGIN_PASSWORD));
							h.post(JOneConst.URL_LOGIN, new UrlEncodedFormEntity(pairs));
							String jsonData = HttpConnection.processEntity(h.getResponse().getEntity());
							JSONObject datas = new JSONObject(jsonData);
				            String result = datas.getString(Const.KEY_RESULT);
				            System.out.println(result);
				            //get bmConfigs
							pairs = new ArrayList<NameValuePair>();
							pairs.add(new BasicNameValuePair(JOneConst.KEY_CONFIG_CDS, JOneConst.VALUE_CONFIG_CDS));
							pairs.add(new BasicNameValuePair(JOneConst.KEY_LAST_TIME, String.valueOf(PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_CONFIG_LAST_TIME)) ));
							h.post(JOneConst.URL_GET_BM_CONFIGS, new UrlEncodedFormEntity(pairs));
							jsonData = HttpConnection.processEntity(h.getResponse().getEntity());
							if (jsonData.startsWith("[") && !jsonData.equals("[]")) {
								ObjectMapper mapper = new ObjectMapper();
								BmConfig[] bmConfigs = mapper.readValue(jsonData, BmConfig[].class);
								long lastTime = BmConfigDAO.getInstance().insertOrUpdate(bmConfigs);
								PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_CONFIG_LAST_TIME, lastTime);
							}
							//get cmArticles
							pairs = new ArrayList<NameValuePair>();
							String startDate = DateUtil.getDdMmYYYYHhMmSsFormate(PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_ARTICLE_LAST_TIME));
							pairs.add(new BasicNameValuePair(JOneConst.KEY_START_DATE, startDate));
							h.post(JOneConst.URL_GET_CM_ARTICLES, new UrlEncodedFormEntity(pairs));
							jsonData = HttpConnection.processEntity(h.getResponse().getEntity());
							if (jsonData.startsWith("[") && !jsonData.equals("[]")) {
								ObjectMapper mapper = new ObjectMapper();
								CmArticle[] cmArticles = mapper.readValue(jsonData, CmArticle[].class);
								long lastTime = CmArticleDAO.getInstance().insertOrUpdate(cmArticles);
								PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_ARTICLE_LAST_TIME, lastTime);
								for (CmArticle cmArticle : cmArticles) {
									if (StringUtil.isNotEmpty(cmArticle.getImageUrl()) && cmArticle.getImageUrl().indexOf("/")==-1) {
										BmConfig bmConfig = BmConfigDAO.getInstance().getBmConfig(JOneConst.KEY_CONFIG_CD_IMAGE_URL, cmArticle.getLang());
										if (bmConfig!=null && StringUtil.isNotEmpty(bmConfig.getConfigValue())) {
											String url = bmConfig.getConfigValue() + "/" + cmArticle.getImageUrl();
											h.get(url);
											HttpConnection.processFileEntity(h.getResponse().getEntity(), new File(JOneUtil.getPicturesAlbumPath(), cmArticle.getImageUrl()));
										}
									}
								}
							}
							//get cmPhotos
							pairs = new ArrayList<NameValuePair>();
							startDate = DateUtil.getDdMmYYYYHhMmSsFormate(PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME));
							pairs.add(new BasicNameValuePair(JOneConst.KEY_START_DATE, startDate));
							h.post(JOneConst.URL_GET_CM_PHOTOS, new UrlEncodedFormEntity(pairs));
							jsonData = HttpConnection.processEntity(h.getResponse().getEntity());
							if (jsonData.startsWith("[") && !jsonData.equals("[]")) {
								ObjectMapper mapper = new ObjectMapper();
								CmPhoto[] cmPhotos = mapper.readValue(jsonData, CmPhoto[].class);
								long lastTime = CmPhotoDAO.getInstance().insertOrUpdate(cmPhotos);
								PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME, lastTime);
								for (CmPhoto cmPhoto : cmPhotos) {
									if (StringUtil.isNotEmpty(cmPhoto.getUrl()) && cmPhoto.getUrl().indexOf("/")==-1) {
										BmConfig bmConfig = BmConfigDAO.getInstance().getBmConfig(JOneConst.KEY_CONFIG_CD_IMAGE_URL, cmPhoto.getLang());
										if (bmConfig!=null && StringUtil.isNotEmpty(bmConfig.getConfigValue())) {
											String url = bmConfig.getConfigValue() + "/" +cmPhoto.getUrl();
											h.get(url);
											HttpConnection.processFileEntity(h.getResponse().getEntity(), new File(JOneUtil.getPicturesAlbumPath(), cmPhoto.getUrl()));
										}
									}
								}
							}
						}
						forward();
					} catch (Exception e) {
						e.printStackTrace();
						forward();
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
	
	