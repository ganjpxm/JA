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
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.ObjectMapper;
import org.ganjp.jlib.core.Const;
import org.ganjp.jlib.core.util.DateUtil;
import org.ganjp.jlib.core.util.FileUtil;
import org.ganjp.jlib.core.util.HtmlUtil;
import org.ganjp.jlib.core.util.HttpConnection;
import org.ganjp.jlib.core.util.ImageUtil;
import org.ganjp.jlib.core.util.StringUtil;
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
	private static final boolean STORE_IN_EXTERNAL = false;
	
	private static List<String> sArticleCategorys;
	private static Map<String,String> sArticleCategoryAndTagWithComma;
	private static Map<String,String> sUrlTypeLangAndUrls;
	
	public static List<String> getArticleCategorys() {
		if (sArticleCategorys == null) {
			setArticleCategoryAndTagsWithCommaFromWeb(PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG));
		}
		return sArticleCategorys;
	}
	public static void setArticleCategorys(List<String> articleCategorys) {
		JOneUtil.sArticleCategorys = articleCategorys;
	}
	public static Map<String, String> getArticleCategoryAndTagWithComma() {
		if (sArticleCategoryAndTagWithComma == null) {
			setArticleCategoryAndTagsWithCommaFromWeb(PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG));
		}
		return sArticleCategoryAndTagWithComma;
	}
	public static void setArticleCategoryAndTagWithComma(Map<String, String> articleCategoryAndTagWithComma) {
		sArticleCategoryAndTagWithComma = articleCategoryAndTagWithComma;
	}
	public static void putArticleCategoryAndTagWithComma(String articleCategory, String tagWithComma) {
		if (sArticleCategoryAndTagWithComma == null) {
			sArticleCategoryAndTagWithComma = new HashMap<String,String>();
		}
		sArticleCategoryAndTagWithComma.put(articleCategory, tagWithComma);
	}
	
	public static String getArticleTagWithComma() {
		String tags = "";
		if (sArticleCategoryAndTagWithComma==null ) {
			setArticleCategoryAndTagsWithCommaFromWeb(PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG));
		} else {
			for (String tag : sArticleCategoryAndTagWithComma.values()) {
				if (tags.indexOf(tag+",")==-1) {
					tags += tag + ",";
				}
			}
			if (StringUtil.isNotEmpty(tags) && tags.endsWith(",")) {
				tags = tags.substring(0,tags.length()-1);
			}
		}
		return tags;
	}
	
	public static String getAllLangArticleTagWithComma() {
		String tags = "";
		setArticleCategoryAndTagsWithCommaFromWeb(JOneConst.LANG_EN_SG);
		Map<String,String> map = sArticleCategoryAndTagWithComma;
		setArticleCategoryAndTagsWithCommaFromWeb(JOneConst.LANG_ZH_CN);
		if (sArticleCategoryAndTagWithComma!=null) {
			map.putAll(sArticleCategoryAndTagWithComma);
		}
		setArticleCategoryAndTagsWithCommaFromWeb(PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG));
		if (sArticleCategoryAndTagWithComma!=null) {
			for (String tag : sArticleCategoryAndTagWithComma.values()) {
				if (tags.indexOf(tag+",")==-1) {
					tags += tag + ",";
				}
			}
			if (StringUtil.isNotEmpty(tags) && tags.endsWith(",")) {
				tags = tags.substring(0,tags.length()-1);
			}
		}
		return tags;
	}
	
	/**
	 * <p>setCategoryTagsValueFromWeb</p>
	 */
	public static void setArticleCategoryAndTagsWithCommaFromWeb(String aLang) {
		BmConfig bmConfig = BmConfigDAO.getInstance().getBmConfig(JOneConst.KEY_CONFIG_CD_MOBILE_TAGS, aLang);
		String categorys = bmConfig.getConfigValue();
		String categoryTags = bmConfig.getDescription();
		if (StringUtil.isNotEmpty(categorys)) {
			sArticleCategorys = Arrays.asList(categorys.split(","));
		}
		if (StringUtil.isNotEmpty(categoryTags) && categoryTags.indexOf(":")!=-1) {
			String[] categoryTagArr = categoryTags.split(";");
			for (String categoryTag : categoryTagArr) {
				if (categoryTag.indexOf(":")!=-1) {
					String[] categoryTagsArr = categoryTag.split(":");
					putArticleCategoryAndTagWithComma(categoryTagsArr[0], categoryTagsArr[1]);
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
		return getPicturesAlbumPath(STORE_IN_EXTERNAL);
	}
	
	/**
	 * <p>Get the app root directory path</p>
	 * 
	 * @param isExternal
	 * @return
	 */
	public static String getPicturesAlbumPath(boolean isExternal) {
		if (StringUtil.isNotEmpty(sPictureAlbumPath)) {
			return sPictureAlbumPath;
		}
		File file = null;
		if (isExternal) {
	        file = Environment.getExternalStorageDirectory();
	        file = new File(file.getPath() + File.separatorChar + JOneConst.APP_VENDOR + File.separatorChar + JOneConst.APP_PACKAGE + 
	        		File.separatorChar + Const.PICTURE_ALBUM);
		} else {
			//storage/sdcard0/Android/data/com.dbs.ocr/files/Pictures
			file = JOneApplication.getAppContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		}
		if (file!=null && !file.exists()) {
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
		if (StringUtil.isNotEmpty(imagePath)) {
			if (imagePath.indexOf("/")==-1) {
				imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imagePath;
			} else {
				imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imagePath.substring(imagePath.lastIndexOf("/")+1);
			}
			if (new File(imagePath).exists()) {
				ImageUtil.setImgNormal(imageIv, imagePath);
				imageIv.setVisibility(View.VISIBLE);
			}
		}
	}
	
	/**
	 * <p>setImage</p>
	 * 
	 * @param imageIv
	 * @param imageName
	 */
	public static void setImageSmall(ImageView imageIv, String imagePath) {
		if (StringUtil.isNotEmpty(imagePath)) {
			if (imagePath.indexOf("/")==-1) {
				imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imagePath;
			} else {
				imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imagePath.substring(imagePath.lastIndexOf("/")+1);
			}
			if (new File(imagePath).exists()) {
				ImageUtil.setImgSmall(imageIv, imagePath);
				imageIv.setVisibility(View.VISIBLE);
			}
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
			} else {
				imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imagePath.substring(imagePath.lastIndexOf("/")+1);
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
		h.post(JOneConst.URL_GET_BM_CONFIGS, new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		String jsonData = HttpConnection.processEntity(h.getResponse().getEntity());
		if (jsonData.startsWith("[") && !jsonData.equals("[]")) {
			ObjectMapper mapper = new ObjectMapper();
			BmConfig[] bmConfigs = mapper.readValue(jsonData, BmConfig[].class);
			long lastTime = BmConfigDAO.getInstance().insertOrUpdate(bmConfigs);
			PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_CONFIG_LAST_TIME, lastTime);
		}
		
		String allLangArticleTagWithComma = getAllLangArticleTagWithComma();
		if (StringUtil.isNotEmpty(allLangArticleTagWithComma)) {
			//get cmArticles
			pairs = new ArrayList<NameValuePair>();
			String startDate = DateUtil.getDdMmYYYYHhMmSsFormate(PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_ARTICLE_LAST_TIME));
			if (isUpdate) {
				pairs.add(new BasicNameValuePair(JOneConst.KEY_START_DATE, startDate));
			} else {
				PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME, 0);
			}
			
			pairs.add(new BasicNameValuePair(JOneConst.KEY_TAG, allLangArticleTagWithComma));
			h.post(JOneConst.URL_GET_CM_ARTICLES, new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
			jsonData = HttpConnection.processEntity(h.getResponse().getEntity());
			if (jsonData.startsWith("[") && !jsonData.equals("[]")) {
				ObjectMapper mapper = new ObjectMapper();
				CmArticle[] cmArticles = mapper.readValue(jsonData, CmArticle[].class);
				long lastTime = CmArticleDAO.getInstance().insertOrUpdate(cmArticles);
				PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_ARTICLE_LAST_TIME, lastTime);
				for (CmArticle cmArticle : cmArticles) {
					String imageUrl = getDownloadFileUrl(JOneConst.KEY_CONFIG_CD_IMAGE_URL, cmArticle.getLang(), cmArticle.getImageUrl());
					if (StringUtil.isNotEmpty(imageUrl) && imageUrl.indexOf("http")!=-1) {
						String imageName = cmArticle.getImageUrl();
						if (imageName.indexOf("/")!=-1) {
							imageName = imageName.substring(imageName.lastIndexOf("/")+1);
						}
						h.get(imageUrl);
						HttpConnection.processFileEntity(h.getResponse().getEntity(), new File(JOneUtil.getPicturesAlbumPath(), imageName));
					}
				}
			}
		}
		if (StringUtil.isNotEmpty(allLangArticleTagWithComma)) {
			//get cmPhotos
			pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair(JOneConst.KEY_TAG, allLangArticleTagWithComma));
			String startDate = DateUtil.getDdMmYYYYHhMmSsFormate(PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME));
			if (isUpdate) {
				pairs.add(new BasicNameValuePair(JOneConst.KEY_START_DATE, startDate));
			} else {
				PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME, 0);
			}
			h.post(JOneConst.URL_GET_CM_PHOTOS, new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
			jsonData = HttpConnection.processEntity(h.getResponse().getEntity());
			if (jsonData.startsWith("[") && !jsonData.equals("[]")) {
				ObjectMapper mapper = new ObjectMapper();
				CmPhoto[] cmPhotos = mapper.readValue(jsonData, CmPhoto[].class);
				long lastTime = CmPhotoDAO.getInstance().insertOrUpdate(cmPhotos);
				PreferenceUtil.saveLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME, lastTime);
				for (CmPhoto cmPhoto : cmPhotos) {
					String imageUrl = getDownloadFileUrl(JOneConst.KEY_CONFIG_CD_IMAGE_URL, cmPhoto.getLang(), cmPhoto.getUrl());
					if (StringUtil.isNotEmpty(imageUrl) && imageUrl.indexOf("http")!=-1) {
						String imageName = cmPhoto.getUrl();
						if (imageName.indexOf("/")!=-1) {
							imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
						}
						h.get(imageUrl);
						HttpConnection.processFileEntity(h.getResponse().getEntity(), new File(JOneUtil.getPicturesAlbumPath(), imageName));
					}
				}
			}
		}
	}
	
	public static String getDownloadFileUrl(String aUrlType, String aLang, String aUrl) {
		if (StringUtil.isNotEmpty(aUrl)) {
			if (aUrl.indexOf("http")!=-1) {
				return HtmlUtil.encodeURL(aUrl);
			} else if (aUrl.indexOf("/")!=-1) {
				return HtmlUtil.encodeURL(JOneConst.SERVER_IP + aUrl);
			} else {
				String rootUrl = null;
				if (sUrlTypeLangAndUrls==null) {
					sUrlTypeLangAndUrls = new HashMap<String,String>();
				}
				if (sUrlTypeLangAndUrls.containsKey(aUrlType + aLang)) {
					rootUrl = sUrlTypeLangAndUrls.get(aUrlType + aLang);
				} 
				if (StringUtil.isEmpty(rootUrl)) {
					BmConfig bmConfig = BmConfigDAO.getInstance().getBmConfig(aUrlType, aLang);
					if (bmConfig!=null) {
						rootUrl = bmConfig.getConfigValue();
						if (StringUtil.isNotEmpty(rootUrl)) {
							sUrlTypeLangAndUrls.put(aUrlType + aLang, rootUrl);
						}
					}
				}
				if (StringUtil.isNotEmpty(rootUrl)) {
					return HtmlUtil.encodeURL(rootUrl + "/" + aUrl);
				}
			}
		}
		return "";
	}

}