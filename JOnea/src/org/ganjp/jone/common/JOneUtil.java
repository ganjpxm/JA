/**
 * Copyright (c) Gan Jianping, 2013. All rights reserved.
 * Author: GanJianping
 */
package org.ganjp.jone.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.ganjp.jlib.core.Const;
import org.ganjp.jlib.core.util.DateUtil;
import org.ganjp.jlib.core.util.FileUtil;
import org.ganjp.jlib.core.util.HtmlUtil;
import org.ganjp.jlib.core.util.HttpConnection;
import org.ganjp.jlib.core.util.ImageUtil;
import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jlib.core.util.SystemUtil;
import org.ganjp.jone.jweb.dao.BmConfigDAO;
import org.ganjp.jone.jweb.dao.CmArticleDAO;
import org.ganjp.jone.jweb.dao.CmPhotoDAO;
import org.ganjp.jone.jweb.entity.BmConfig;
import org.ganjp.jone.jweb.entity.CmArticle;
import org.ganjp.jone.jweb.entity.CmPhoto;

import android.os.Environment;
import android.view.View;
import android.widget.ImageView;


public abstract class JOneUtil {
	private static String sPictureAlbumPath = null;
	
	private static List<String> sCategorys;
	private static Map<String,String> sCategoryTags;
	
	public static List<String> getCategorys() {
		if (sCategorys == null) {
			sCategorys = new ArrayList<String>();
		}
		return sCategorys;
	}
	public static void setCategorys(List<String> categorys) {
		JOneUtil.sCategorys = categorys;
	}
	public static Map<String, String> getCategoryTagsMap() {
		if (sCategoryTags == null) {
			sCategoryTags = new HashMap<String,String>();
		}
		return sCategoryTags;
	}
	public static void setCategoryTags(Map<String, String> categoryTags) {
		JOneUtil.sCategoryTags = categoryTags;
	}
	public static void putCategoryTags(String category, String tagsWithComma) {
		getCategoryTagsMap().put(category, tagsWithComma);
	}
	
	public static String getTags() {
		String tags = "";
		for (String tag : getCategoryTagsMap().values()) {
			tags += tag + ",";
		}
		return tags;
	}
	
	/**
	 * <p>setCategoryTagsValueFromWeb</p>
	 */
	public static void setCategoryTagsValueFromWeb() {
		BmConfig bmConfig = BmConfigDAO.getInstance().getBmConfig(JOneConst.KEY_CONFIG_CD_MOBILE_TAGS, 
				PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG));
		String categorys = bmConfig.getConfigValue();
		String categoryTags = bmConfig.getDescription();
		if (StringUtil.isNotEmpty(categorys)) {
			sCategorys = Arrays.asList(categorys.split(","));
		}
		if (StringUtil.isNotEmpty(categoryTags) && categoryTags.indexOf(":")!=-1) {
			String[] categoryTagArr = categoryTags.split(";");
			for (String categoryTag : categoryTagArr) {
				if (categoryTag.indexOf(":")!=-1) {
					String[] categoryTagsArr = categoryTag.split(":");
					putCategoryTags(categoryTagsArr[0], categoryTagsArr[1]);
				}
			}
		}
	}
	
	/**
	 * <p>Get the app root directory path</p>
	 * 
	 * @return
	 */
	public static String getPicturesAlbumPath() {
		File file = null;
		if (StringUtil.isNotEmpty(sPictureAlbumPath)) {
			file = new File(sPictureAlbumPath);
			if (!file.exists()) {
		    	file.mkdirs();
		    }
			return sPictureAlbumPath;
		}
		
		if(SystemUtil.hasSdcard()){
	        file = Environment.getExternalStorageDirectory();
	        file = new File(file.getPath() + File.separatorChar + JOneConst.APP_VENDOR + File.separatorChar + JOneConst.APP_PACKAGE + 
	        		File.separatorChar + Const.PICTURE_ALBUM);
		} else {
			//storage/sdcard0/Android/data/org.ganjp.jone/files/Pictures
		    file = JOneApplication.getAppContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		}
		if (!file.exists()) {
	    	file.mkdirs();
	    }
	    if (file != null) {
	    	sPictureAlbumPath = file.getAbsolutePath();
		}
		return sPictureAlbumPath;
	}
	
	/**
	 * <p>setImage</p>
	 * 
	 * @param imageIv
	 * @param imageName
	 */
	public static void setImageNormal(ImageView imageIv, String imagePath) {
		if (StringUtil.isNotEmpty(imagePath) && imagePath.indexOf("/")==-1) {
			imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imagePath;
		}
		if (imagePath.indexOf("http")!=-1 || new File(imagePath).exists()) {
			ImageUtil.setImgNormal(imageIv, imagePath);
			imageIv.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * <p>setImage</p>
	 * 
	 * @param imageIv
	 * @param imageName
	 */
	public static void setImageSmall(ImageView imageIv, String imagePath) {
		if (StringUtil.isNotEmpty(imagePath) && imagePath.indexOf("/")==-1) {
			imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imagePath;
		}
		if (imagePath.indexOf("http")!=-1 || new File(imagePath).exists()) {
			ImageUtil.setImgSmall(imageIv, imagePath);
			imageIv.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * <p>isExist</p>
	 * 
	 * @param imagePath
	 * @return
	 */
	public static boolean isExist(String imagePath) {
		if (StringUtil.isNotEmpty(imagePath)) {
			if (imagePath.indexOf("/")==-1) {
				imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imagePath;
			} else if (imagePath.indexOf("http")!=-1){
				return true;
			}
		}
		return new File(imagePath).exists();
	}
	
	/**
	 * <p>getDataFromJWeb</p>
	 * 
	 * @param h
	 * @param isUpdate
	 * @throws Exception
	 */
	public static void getDataFromJWeb(HttpConnection h, boolean isUpdate) throws Exception {
		if (isUpdate==false) {
			FileUtil.delete(new File(JOneUtil.getPicturesAlbumPath()));
		}
	    //get bmConfigs
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair(JOneConst.KEY_CONFIG_CDS, JOneConst.VALUE_CONFIG_CDS));
		if (isUpdate) {
			pairs.add(new BasicNameValuePair(JOneConst.KEY_LAST_TIME, String.valueOf(PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_CONFIG_LAST_TIME))));
		}
		h.post(JOneConst.URL_GET_BM_CONFIGS, new UrlEncodedFormEntity(pairs));
		String jsonData = HttpConnection.processEntity(h.getResponse().getEntity());
		if (jsonData.startsWith("[") && !jsonData.equals("[]")) {
			ObjectMapper mapper = new ObjectMapper();
			BmConfig[] bmConfigs = mapper.readValue(jsonData, BmConfig[].class);
			long lastTime = BmConfigDAO.getInstance().insertOrUpdate(bmConfigs);
			PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_CONFIG_LAST_TIME, lastTime);
		}
		//get cmArticles
		pairs = new ArrayList<NameValuePair>();
		String startDate = DateUtil.getDdMmYYYYHhMmSsFormate(PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_ARTICLE_LAST_TIME));
		if (isUpdate) {
			pairs.add(new BasicNameValuePair(JOneConst.KEY_START_DATE, startDate));
		} else {
			PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME, 0);
		}
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
						String url = HtmlUtil.encodeURL(bmConfig.getConfigValue() + "/" + cmArticle.getImageUrl());
						h.get(url);
						HttpConnection.processFileEntity(h.getResponse().getEntity(), new File(JOneUtil.getPicturesAlbumPath(), cmArticle.getImageUrl()));
					}
				}
			}
		}
		//get cmPhotos
		pairs = new ArrayList<NameValuePair>();
		startDate = DateUtil.getDdMmYYYYHhMmSsFormate(PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME));
		if (isUpdate) {
			pairs.add(new BasicNameValuePair(JOneConst.KEY_START_DATE, startDate));
		} else {
			PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME, 0);
		}
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
						String url = HtmlUtil.encodeURL(bmConfig.getConfigValue() + "/" + cmPhoto.getUrl());
						h.get(url);
						HttpConnection.processFileEntity(h.getResponse().getEntity(), new File(JOneUtil.getPicturesAlbumPath(), cmPhoto.getUrl()));
					}
				}
			}
		}
	}

}