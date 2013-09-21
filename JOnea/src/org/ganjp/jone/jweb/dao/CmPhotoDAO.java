/**
 * CmPhotoDAO.java
 * 
 * Created by Gan Jianping on 20/07/13.
 * Copyright (c) 2013 DBS. All rights reserved.
 */
package org.ganjp.jone.jweb.dao;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.ganjp.jlib.core.Const;
import org.ganjp.jlib.core.dao.DAO;
import org.ganjp.jlib.core.dao.DatabaseHelper;
import org.ganjp.jlib.core.util.DateUtil;
import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jone.common.JOneApplication;
import org.ganjp.jone.common.JOneConst;
import org.ganjp.jone.common.PreferenceUtil;
import org.ganjp.jone.jweb.dao.JWebDaoFactory.DAOType;
import org.ganjp.jone.jweb.entity.CmPhoto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * <p>Photo table DAO</p>
 * <pre>
 * CmPhotoDAO cmPhotoDAO = (CmPhotoDAO) (DAOFactory.getInstance().getDAO(DAOType.LOANPRODUCT, getApplication()));
 * </pre>
 * 
 * @author Gan Jianping
 *
 */
public class CmPhotoDAO extends DAO {
	private static final String TAG = "CmPhotoDAO"; 
	private static final String TABLE_NAME = "cm_photo"; 
	
	// Photo Table Columns names
	public static final String COLUMN_PHOTO_ID = "photo_id";
	public static final String COLUMN_PHOTO_NAME = "photo_name";
	public static final String COLUMN_REF_ARTICLE_ID = "refArticleId";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_THUMB_URL = "thumbUrl";
	public static final String COLUMN_URL = "url";
	public static final String COLUMN_ORIGIN_URL = "originUrl";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_TAG = "tag";
	public static final String COLUMN_DISPLAY_NO = "displayNo";
	public static final String COLUMN_ROLE_IDS = "roleIds";
	public static final String COLUMN_OPERATOR_ID = "operatorId";
	public static final String COLUMN_OPERATOR_NAME = "operatorName";
	
	//Field name : NULL, INTEGER, REAL, TEXT, BLOB
	protected static final String createSQL = new StringBuffer().append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append(" (")
			.append(COLUMN_PHOTO_ID).append(" TEXT primary key, ")
			.append(COLUMN_PHOTO_NAME).append(" TEXT, ")
			.append(COLUMN_REF_ARTICLE_ID).append(" TEXT, ")
			.append(COLUMN_TITLE).append(" TEXT, ")
			.append(COLUMN_THUMB_URL).append(" TEXT, ")
			.append(COLUMN_URL).append(" TEXT, ")
			.append(COLUMN_ORIGIN_URL).append(" TEXT, ")
			.append(COLUMN_REMARK).append(" TEXT, ")
			.append(COLUMN_TAG).append(" TEXT, ")
			.append(COLUMN_DISPLAY_NO).append(" INTEGER, ")
			.append(COLUMN_ROLE_IDS).append(" TEXT, ")
			.append(COLUMN_OPERATOR_ID).append(" TEXT, ")
			.append(COLUMN_OPERATOR_NAME).append(" TEXT, ")
			
			.append(Const.COLUMN_LANG).append(" TEXT, ")
			.append(Const.COLUMN_CREATE_TIME).append(" INTEGER, ")
			.append(Const.COLUMN_MODIFY_TIMESTAMP).append(" INTEGER, ")
			.append(Const.COLUMN_DATA_STATE).append(" TEXT)")
			.toString();

	/**
	 * <p>Photo DAO</p>
	 * 
	 * @param context
	 * @param dbHelper
	 */
	public CmPhotoDAO(Context context, DatabaseHelper dbHelper) {
		super(context, TABLE_NAME, dbHelper);
	}

	/**
	 * <p>Create cm_photo Table</p>
	 */
	@Override
	protected void createTable() {
		SQLiteDatabase db = null;
		try { 
			db = this.getDatabase();
			db.execSQL(createSQL);
		} catch( Exception ex ) {
			Log.d("CmPhotoDAO", "createTable:exception:"+ex);
		} finally {
			db.close();
		}
	}	
	
	/**
	 * <p>Insert Or Update a record of Photo</p>
	 * 
	 * @param values
	 * @param photoId
	 * @return
	 */
	public long insertOrUpdate(CmPhoto[] cmPhotos) {
		long latestTime = PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_PHOTO_LAST_TIME);
		for (CmPhoto cmPhoto : cmPhotos) {
			ContentValues cv = new ContentValues();
			
			cv.put(COLUMN_PHOTO_ID, cmPhoto.getPhotoId());
			cv.put(COLUMN_PHOTO_NAME, cmPhoto.getPhotoName());
			cv.put(COLUMN_REF_ARTICLE_ID, cmPhoto.getRefArticleId());
			cv.put(COLUMN_TITLE, cmPhoto.getTitle());
			cv.put(COLUMN_THUMB_URL, cmPhoto.getThumbUrl());
			cv.put(COLUMN_URL, cmPhoto.getUrl());
			cv.put(COLUMN_ORIGIN_URL, cmPhoto.getOriginUrl());
			cv.put(COLUMN_REMARK, cmPhoto.getRemark());
			cv.put(COLUMN_TAG, cmPhoto.getTag());
			cv.put(COLUMN_DISPLAY_NO, cmPhoto.getDisplayNo());
			cv.put(COLUMN_ROLE_IDS, cmPhoto.getRoleIds());
			cv.put(COLUMN_OPERATOR_ID, cmPhoto.getOperatorId());
			cv.put(COLUMN_OPERATOR_NAME, cmPhoto.getOperatorName());
			
			cv.put(Const.COLUMN_LANG, cmPhoto.getLang());
			cv.put(Const.COLUMN_CREATE_TIME, cmPhoto.getCreateDateTime().getTime());
			cv.put(Const.COLUMN_MODIFY_TIMESTAMP, cmPhoto.getModifyTimestamp().getTime());
			cv.put(Const.COLUMN_DATA_STATE, cmPhoto.getDataState());
			super.insertOrUpdateWithTime(cv, COLUMN_PHOTO_ID + " = ?", new String[]{cmPhoto.getPhotoId()});
			if (latestTime<cmPhoto.getModifyTimestamp().getTime()) {
				latestTime = cmPhoto.getModifyTimestamp().getTime();
				String date = DateUtil.getDdMmYYYYHhMmSsFormate(latestTime);
				System.out.println(date);
				System.out.println(DateUtil.getNowYyyyMmDmHhMmSs());
			}
		}
		return latestTime;
	}
	
	/**
	 * <p>Insert Or Update a record of Photo</p>
	 * 
	 * @param values
	 * @param photoId
	 * @return
	 */
	public boolean insertOrUpdate(ContentValues values, String photoId) {
		return super.insertOrUpdateWithTime(values, COLUMN_PHOTO_ID + " = ?", new String[]{photoId});
	}

	/**
	 * <p>Get all the cmPhoto data</p>
	 * 
	 * @return List<CmPhoto>
	 */
	public List<CmPhoto> getCmPhotos(String refArticleId, String lang) {
		String sql = "SELECT * FROM " + TABLE_NAME + " where 1=1 ";
		if (StringUtil.isNotEmpty(refArticleId)) {
			sql += " and " + COLUMN_REF_ARTICLE_ID + "='" + refArticleId + "'";
		}
		if (StringUtil.isNotEmpty(lang)) {
			sql += " and " + Const.COLUMN_LANG + "='" + lang + "'";
		}
		sql += " order by " + COLUMN_DISPLAY_NO;
		return getCmPhotosBySql(sql);
	}
	
	public List<CmPhoto> getCmPhotosBySql(String sql) {
		List<CmPhoto> cmPhotos = new LinkedList<CmPhoto>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.getDatabase();
			cursor = db.rawQuery(sql, null);
			if( cursor != null && cursor.getCount() > 0 ) {
				cursor.moveToFirst();
				while(cursor.isAfterLast()==false) {
					CmPhoto cmPhoto = new CmPhoto();
					setCmPhoto(cmPhoto, cursor);
					cmPhotos.add(cmPhoto);
					cursor.moveToNext();
				}
			}
		} catch( Exception ex ) {
			Log.e(TAG, ex.getMessage());
		}finally{
			if ( cursor!=null ){
				cursor.close();	
				cursor = null;
			}
			if (db!=null) {
				db.close();
			}
		}
		return cmPhotos;
	}
	/**
	 * <p>Get a CmPhoto object</p>
	 * 
	 * @param photoId
	 * @return CmPhoto
	 */
	public CmPhoto getCmPhoto(String photoId) {
		String query = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_PHOTO_ID + "='" + photoId + "'";
		Log.d(TAG, "query:"+query);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		CmPhoto cmPhoto = new CmPhoto();
		try {
			db = this.getDatabase();
			cursor = db.rawQuery(query, null);
			if (cursor!=null && cursor.getCount()>0) {
				cursor.moveToFirst();
				setCmPhoto(cmPhoto, cursor);
			}
		} catch( Exception ex ) {
		}finally{
			if ( cursor!=null ){
				cursor.close();	
				cursor = null;
			}
			if (db!=null) {
				db.close();
			}
		}
		return cmPhoto;
	}
	
	/**
	 * <p>Delete table data by photoId</p>
	 * 
	 * @param photoId
	 * @return
	 */
	public boolean deleteByProductId(String photoId) {
		return super.delete(COLUMN_PHOTO_ID + " = ?", new String[]{photoId});
	}
	
	/**
	 * <p>Set cmPhoto record data to CmPhoto object</p>
	 * 
	 * @param cmPhoto
	 * @param cursor
	 */
	private void setCmPhoto(CmPhoto cmPhoto, Cursor cursor) {
		if (cmPhoto == null) {
			cmPhoto = new CmPhoto();
		}
		cmPhoto.setPhotoId(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_ID)));
		cmPhoto.setPhotoName(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO_NAME)));
		cmPhoto.setRefArticleId(cursor.getString(cursor.getColumnIndex(COLUMN_REF_ARTICLE_ID)));
		cmPhoto.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
		cmPhoto.setThumbUrl(cursor.getString(cursor.getColumnIndex(COLUMN_THUMB_URL)));
		cmPhoto.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
		cmPhoto.setOriginUrl(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGIN_URL)));
		cmPhoto.setRemark(cursor.getString(cursor.getColumnIndex(COLUMN_REMARK)));
		cmPhoto.setTag(cursor.getString(cursor.getColumnIndex(COLUMN_TAG)));
		cmPhoto.setDisplayNo(cursor.getInt(cursor.getColumnIndex(COLUMN_DISPLAY_NO)));
		cmPhoto.setRoleIds(cursor.getString(cursor.getColumnIndex(COLUMN_ROLE_IDS)));
		cmPhoto.setOperatorId(cursor.getString(cursor.getColumnIndex(COLUMN_OPERATOR_ID)));
		cmPhoto.setOperatorName(cursor.getString(cursor.getColumnIndex(COLUMN_OPERATOR_NAME)));
		
		cmPhoto.setLang(cursor.getString(cursor.getColumnIndex(Const.COLUMN_LANG)));
		cmPhoto.setCreateDateTime(new Timestamp(cursor.getLong(cursor.getColumnIndex(Const.COLUMN_CREATE_TIME))));
		cmPhoto.setModifyTimestamp(new Timestamp(cursor.getLong(cursor.getColumnIndex(Const.COLUMN_MODIFY_TIMESTAMP))));
		cmPhoto.setDataState(cursor.getString(cursor.getColumnIndex(Const.COLUMN_DATA_STATE)));
	}
	
	/**
	 * <p>Insert or update column's value base on photo id</p>
	 * 
	 * @param photoId
	 * @param collumnName
	 * @param value
	 */
	public static void insertOrUpdate(String photoId, String columnName, String value) {
		CmPhotoDAO cmPhotoDao = (CmPhotoDAO) (JWebDaoFactory.getInstance().getDAO(DAOType.CM_PHOTO, JOneApplication.getAppContext()));
		ContentValues cv = new ContentValues();
		cv.put(columnName, value);
		cmPhotoDao.insertOrUpdate(cv, photoId);
	}
	
	/**
	 * <p>Get CmPhoto DAO</p>
	 */
	public static CmPhotoDAO getInstance() {
		return (CmPhotoDAO) (JWebDaoFactory.getInstance().getDAO(DAOType.CM_PHOTO, JOneApplication.getAppContext()));
	}
	
}
