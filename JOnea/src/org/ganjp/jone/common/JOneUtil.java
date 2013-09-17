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

import org.ganjp.jlib.core.Const;
import org.ganjp.jlib.core.util.ImageUtil;
import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jlib.core.util.SystemUtil;
import org.ganjp.jone.jweb.dao.BmConfigDAO;
import org.ganjp.jone.jweb.entity.BmConfig;

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
		if (StringUtil.isNotEmpty(sPictureAlbumPath)) {
			return sPictureAlbumPath;
		}
		File file = null;
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
	public static void setImage(ImageView imageIv, String imageName) {
		String imagePath = JOneUtil.getPicturesAlbumPath() + File.separatorChar + imageName;
		if (new File(imagePath).exists()) {
			ImageUtil.setImg(imageIv, imagePath);
			imageIv.setVisibility(View.VISIBLE);
		}
	}
}