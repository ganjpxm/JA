/**
 * DAOFactory.java
 * 
 * Created by Gan Jianping on 12/09/13.
 * Copyright (c) 2013 DBS. All rights reserved.
 */
package org.ganjp.jone.jweb.dao;

import java.util.concurrent.ConcurrentHashMap;

import org.ganjp.jlib.core.dao.DAO;
import org.ganjp.jlib.core.dao.DatabaseHelper;
import org.ganjp.jlib.core.dao.SystemDAO;

import android.content.Context;

/**
 * <p>Database Access Object Factory</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class JWebDaoFactory {
	
	protected static JWebDaoFactory sInstance = null;
	
	protected DatabaseHelper mDbHelper = null;
	protected ConcurrentHashMap<DAOType, DAO> mDaoMaps = null;
	protected JWebDaoFactory() {
		mDaoMaps = new ConcurrentHashMap<DAOType, DAO>();
	}
	
	//support two table :system and loan product
	public enum DAOType {
		SYSTEM(1), 
		BM_CONFIG(2),
		CM_ARTICLE(3),
		CM_PHOTO(4);
		
		private int value;
		DAOType (int value) { 
			this.value = value; 
		}
		public int value() { 
			return value; 
		}
		public static DAOType fromValue(int value) {
			for (DAOType e : DAOType.values()) {
				if (e.value() == value) {
					return e;
				}
			}
			return null;
		}
	}
	
	/**
	 * <p>Finalize the database connection</p>
	 */
	protected void finalize() {
		try {
			super.finalize();
			if (mDbHelper!=null) {
				mDbHelper.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Close the database connection</p>
	 */
	public void close(){
		try {
			if (mDbHelper != null) {
				mDbHelper.close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Get the singleton instance</p>
	 * 
	 * @return DAOFactory
	 */
	public synchronized static JWebDaoFactory getInstance() {
		if (sInstance == null) {
			sInstance = new JWebDaoFactory();
		}
		return sInstance;
	}
	
	/**
	 * <p>Get the DAO</p>
	 * <pre>
	 *   SystemDAO systemDao = (SystemDAO) (DAOFactory.getInstance().getDAO(DAOType.SYSTEM, DbsOcrApplication.getAppContext()));
	 * </pre>
	 * 
	 * @param <T>
	 * @param type
	 * @return
	 */
	public DAO getDAO(DAOType type, Context ctx) {
		synchronized(this.mDaoMaps) {
			if (mDbHelper==null) {
				mDbHelper = new DatabaseHelper(ctx);
			}
			if(this.mDaoMaps.containsKey(type)) {
				return this.mDaoMaps.get(type);
			}
			
			DAO dao = null;
			switch(type) {
				case SYSTEM:
					dao = new SystemDAO(ctx, mDbHelper);
					break;
				case BM_CONFIG:
					dao = new BmConfigDAO(ctx, mDbHelper);
					break;	
				case CM_ARTICLE:
					dao = new CmArticleDAO(ctx, mDbHelper);
					break;
				case CM_PHOTO:
					dao = new CmPhotoDAO(ctx, mDbHelper);
					break;
			}
			if (dao!=null){ 
				this.mDaoMaps.put(type, dao);
			}
			return dao;
		}
	}
}
