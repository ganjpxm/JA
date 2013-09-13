/**
 * BaseModel.java
 *
 * Created by Gan Jianping on 12/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.jweb.entity;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import android.util.Log;

/**
 * <p>all model must extend BaseModel</p> 
 *
 * @author GanJianping
 * @since 1.0.0
 */
public abstract class BaseModel {

	private String queryFilters = null;
	
	private String lang;
	private Timestamp createDateTime;
	private Timestamp modifyTimestamp;
	private String dataState;
	
	public String getQueryFilters() {
		return queryFilters;
	}
	
	public void setQueryFilters(String queryFilters) {
		this.queryFilters = queryFilters;
	}
	
	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
		
	}

	public Timestamp getModifyTimestamp() {
		return modifyTimestamp;
	}

	public void setModifyTimestamp(Timestamp modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	/**
	 * <p>hashCode algorithm</p>
	 */
	public int hashCode() {
		int result = 17;
		Class<? extends BaseModel> c = this.getClass();
		Field[] fields = c.getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				f.setAccessible(true);
				result = 37 * result + (f.get(this) == null ? 0 : f.get(this).hashCode());
			}
		} catch (Exception e) {
			Log.e("Base Model", e.getMessage());
		}
		return result;
	}

	/**
	 * <p>to String</p> 
	 */
	public String toString() {
		Class<? extends BaseModel> c = this.getClass();
		Field[] fields = c.getDeclaredFields();
		StringBuffer buffer = new StringBuffer();
		buffer.append(c.getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
		try {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				f.setAccessible(true);
				buffer.append(f.getName()).append("='").append(f.get(this)).append("' ");
			}
			buffer.append("]");
		} catch (Exception e) {
			Log.e("Base Model", e.getMessage());
		}
		return buffer.toString();
	}
	
}
