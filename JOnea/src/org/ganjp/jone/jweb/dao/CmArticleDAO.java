/**
 * CmArticleDAO.java
 * 
 * Created by Gan Jianping on 20/07/13.
 * Copyright (c) 2013 DBS. All rights reserved.
 */
package org.ganjp.jone.jweb.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ganjp.jlib.core.Const;
import org.ganjp.jlib.core.dao.DAO;
import org.ganjp.jlib.core.dao.DatabaseHelper;
import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jone.common.JOneApplication;
import org.ganjp.jone.common.JOneConst;
import org.ganjp.jone.common.PreferenceUtil;
import org.ganjp.jone.jweb.dao.JWebDaoFactory.DAOType;
import org.ganjp.jone.jweb.entity.CmArticle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * <p>Article table DAO</p>
 * <pre>
 * CmArticleDAO cmArticleDAO = (CmArticleDAO) (DAOFactory.getInstance().getDAO(DAOType.LOANPRODUCT, getApplication()));
 * </pre>
 * 
 * @author Gan Jianping
 *
 */
public class CmArticleDAO extends DAO {
	private static final String TAG = "CmArticleDAO"; 
	private static final String TABLE_NAME = "cm_article"; 
	
	// Article Table Columns names
	public static final String COLUMN_ARTICLE_ID = "article_id";
	public static final String COLUMN_ARTICLE_CD = "article_cd";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_SUMMARY = "summary";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_AUTHORE = "author";
	public static final String COLUMN_IMAGE_URL = "imageUrl";
	public static final String COLUMN_ORIGIN_URL = "originUrl";
	public static final String COLUMN_TAG = "tag";
	public static final String COLUMN_DISPLAY_NO = "displayNo";
	public static final String COLUMN_ROLE_IDS = "roleIds";
	public static final String COLUMN_OPERATOR_ID = "operatorId";
	public static final String COLUMN_OPERATOR_NAME = "operatorName";
	
	//Field name : NULL, INTEGER, REAL, TEXT, BLOB
	protected static final String createSQL = new StringBuffer().append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append(" (")
			.append(COLUMN_ARTICLE_ID).append(" TEXT primary key, ")
			.append(COLUMN_ARTICLE_CD).append(" TEXT, ")
			.append(COLUMN_TITLE).append(" TEXT, ")
			.append(COLUMN_SUMMARY).append(" TEXT, ")
			.append(COLUMN_CONTENT).append(" TEXT, ")
			.append(COLUMN_AUTHORE).append(" TEXT, ")
			.append(COLUMN_IMAGE_URL).append(" TEXT, ")
			.append(COLUMN_ORIGIN_URL).append(" TEXT, ")
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
	 * <p>Article DAO</p>
	 * 
	 * @param context
	 * @param dbHelper
	 */
	public CmArticleDAO(Context context, DatabaseHelper dbHelper) {
		super(context, TABLE_NAME, dbHelper);
	}

	/**
	 * <p>Create cm_article Table</p>
	 */
	@Override
	protected void createTable() {
		SQLiteDatabase db = null;
		try { 
			db = this.getDatabase();
			db.execSQL(createSQL);
		} catch( Exception ex ) {
			Log.d("CmArticleDAO", "createTable:exception:"+ex);
		} finally {
			db.close();
		}
	}	
	
	/**
	 * <p>Insert Or Update a record of Article</p>
	 * 
	 * @param values
	 * @param articleId
	 * @return
	 */
	public long insertOrUpdate(CmArticle[] cmArticles) {
		long latestTime = PreferenceUtil.getLong(JOneConst.KEY_PREFERENCE_ARTICLE_LAST_TIME);
		for (CmArticle cmArticle : cmArticles) {
			ContentValues cv = new ContentValues();
			
			cv.put(COLUMN_ARTICLE_ID, cmArticle.getArticleId());
			cv.put(COLUMN_ARTICLE_CD, cmArticle.getArticleCd());
			cv.put(COLUMN_TITLE, cmArticle.getTitle());
			cv.put(COLUMN_SUMMARY, cmArticle.getSummary());
			cv.put(COLUMN_CONTENT, cmArticle.getContent());
			
			cv.put(COLUMN_AUTHORE, cmArticle.getAuthor());
			cv.put(COLUMN_IMAGE_URL, cmArticle.getImageUrl());
			cv.put(COLUMN_ORIGIN_URL, cmArticle.getOriginUrl());
			cv.put(COLUMN_TAG, cmArticle.getTag());
			cv.put(COLUMN_DISPLAY_NO, cmArticle.getDisplayNo());
			cv.put(COLUMN_ROLE_IDS, cmArticle.getRoleIds());
			cv.put(COLUMN_OPERATOR_ID, cmArticle.getOperatorId());
			cv.put(COLUMN_OPERATOR_NAME, cmArticle.getOperatorName());
			
			cv.put(Const.COLUMN_LANG, cmArticle.getLang());
			cv.put(Const.COLUMN_CREATE_TIME, cmArticle.getCreateDateTime().getTime());
			cv.put(Const.COLUMN_MODIFY_TIMESTAMP, cmArticle.getModifyTimestamp().getTime());
			cv.put(Const.COLUMN_DATA_STATE, cmArticle.getDataState());
			super.insertOrUpdateWithTime(cv, COLUMN_ARTICLE_ID + " = ?", new String[]{cmArticle.getArticleId()});
			if (latestTime<cmArticle.getModifyTimestamp().getTime()) {
				latestTime = cmArticle.getModifyTimestamp().getTime();
			}
		}
		return latestTime;
	}
	
	/**
	 * <p>Insert Or Update a record of Article</p>
	 * 
	 * @param values
	 * @param articleId
	 * @return
	 */
	public boolean insertOrUpdate(ContentValues values, String articleId) {
		return super.insertOrUpdateWithTime(values, COLUMN_ARTICLE_ID + " = ?", new String[]{articleId});
	}

	/**
	 * <p>Get all the cmArticle data</p>
	 * 
	 * @return List<CmArticle>
	 */
	public List<CmArticle> getCmArticles(String lang) {
		String sql = "SELECT * FROM " + TABLE_NAME;
		if (StringUtil.isNotEmpty(lang)) {
			sql += " where " + Const.COLUMN_LANG + "='" + lang + "'";
		}
		return getCmArticlesBySql(sql);
	}
	
	/**
	 * <p>Get all the cmArticle data</p>
	 * 
	 * @return List<CmArticle>
	 */
	public List<CmArticle> getCmArticles(String tag, String lang) {
		String sql = "SELECT * FROM " + TABLE_NAME + " where 1=1 ";
		if (StringUtil.isNotEmpty(lang)) {
			sql += " and " + Const.COLUMN_LANG + "='" + lang + "'";
		}
		if (StringUtil.isNotEmpty(tag)) {
			sql += " and " + COLUMN_TAG + " like '%" + tag + "%'";
		}
		sql += " order by " + Const.COLUMN_MODIFY_TIMESTAMP + " desc";
		return getCmArticlesBySql(sql);
	}
	
	/**
	 * <p>Get CmArticles By Sql</p>
	 * 
	 * @param sql
	 * @return
	 */
	private List<CmArticle> getCmArticlesBySql(String sql) {
		List<CmArticle> cmArticles = new LinkedList<CmArticle>();
		
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.getDatabase();
			cursor = db.rawQuery(sql, null);
			if( cursor != null && cursor.getCount() > 0 ) {
				cursor.moveToFirst();

				while(cursor.isAfterLast()==false) {
					CmArticle cmArticle = new CmArticle();
					setCmArticle(cmArticle, cursor);
					
					cmArticles.add(cmArticle);
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
		return cmArticles;
	}
	/**
	 * <p>Get a CmArticle object</p>
	 * 
	 * @param articleId
	 * @return CmArticle
	 */
	public CmArticle getCmArticle(String articleId) {
		String query = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_ARTICLE_ID + "='" + articleId + "'";
		Log.d(TAG, "query:"+query);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		CmArticle cmArticle = new CmArticle();
		try {
			db = this.getDatabase();
			cursor = db.rawQuery(query, null);
			if (cursor!=null && cursor.getCount()>0) {
				cursor.moveToFirst();
				setCmArticle(cmArticle, cursor);
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
		return cmArticle;
	}
	
	/**
	 * <p>Get all the CmArticle ID</p>
	 * 
	 * @return 
	 */
	public List<String> getArticleIds() {
		String query = "SELECT " + COLUMN_ARTICLE_ID + " FROM " + TABLE_NAME;
		Log.d(TAG, "query:"+query);
		SQLiteDatabase db = null;
		Cursor cursor = null;
		List<String> articleIds = new ArrayList<String>();
		try {
			db = this.getDatabase();
			cursor = db.rawQuery(query, null);
			if (cursor!=null && cursor.getCount()>0) {
				cursor.moveToFirst();
				articleIds.add(cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_ID)));
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
		return articleIds;
	}
	
	/**
	 * <p>Delete table data by articleId</p>
	 * 
	 * @param articleId
	 * @return
	 */
	public boolean deleteByProductId(String articleId) {
		return super.delete(COLUMN_ARTICLE_ID + " = ?", new String[]{articleId});
	}
	
	/**
	 * <p>Set cmArticle record data to CmArticle object</p>
	 * 
	 * @param cmArticle
	 * @param cursor
	 */
	private void setCmArticle(CmArticle cmArticle, Cursor cursor) {
		if (cmArticle == null) {
			cmArticle = new CmArticle();
		}
		
		cmArticle.setArticleId(cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_ID)));
		cmArticle.setArticleCd(cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_CD)));
		cmArticle.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
		cmArticle.setSummary(cursor.getString(cursor.getColumnIndex(COLUMN_SUMMARY)));
		cmArticle.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));
		cmArticle.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHORE)));
		cmArticle.setImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)));
		cmArticle.setOriginUrl(cursor.getString(cursor.getColumnIndex(COLUMN_ORIGIN_URL)));
		cmArticle.setTag(cursor.getString(cursor.getColumnIndex(COLUMN_TAG)));
		cmArticle.setDisplayNo(cursor.getInt(cursor.getColumnIndex(COLUMN_DISPLAY_NO)));
		cmArticle.setRoleIds(cursor.getString(cursor.getColumnIndex(COLUMN_ROLE_IDS)));
		cmArticle.setOperatorId(cursor.getString(cursor.getColumnIndex(COLUMN_OPERATOR_ID)));
		cmArticle.setOperatorName(cursor.getString(cursor.getColumnIndex(COLUMN_OPERATOR_NAME)));
		
		cmArticle.setLang(cursor.getString(cursor.getColumnIndex(Const.COLUMN_LANG)));
		cmArticle.setCreateDateTime(new Timestamp(cursor.getLong(cursor.getColumnIndex(Const.COLUMN_CREATE_TIME))));
		cmArticle.setModifyTimestamp(new Timestamp(cursor.getLong(cursor.getColumnIndex(Const.COLUMN_MODIFY_TIMESTAMP))));
		cmArticle.setDataState(cursor.getString(cursor.getColumnIndex(Const.COLUMN_DATA_STATE)));
	}
	
	/**
	 * <p>Insert or update column's value base on article id</p>
	 * 
	 * @param articleId
	 * @param collumnName
	 * @param value
	 */
	public static void insertOrUpdate(String articleId, String columnName, String value) {
		CmArticleDAO cmArticleDao = (CmArticleDAO) (JWebDaoFactory.getInstance().getDAO(DAOType.CM_ARTICLE, JOneApplication.getAppContext()));
		ContentValues cv = new ContentValues();
		cv.put(columnName, value);
		cmArticleDao.insertOrUpdate(cv, articleId);
	}
	
	/**
	 * <p>Get CmArticle DAO</p>
	 */
	public static CmArticleDAO getInstance() {
		return (CmArticleDAO) (JWebDaoFactory.getInstance().getDAO(DAOType.CM_ARTICLE, JOneApplication.getAppContext()));
	}
	
}
