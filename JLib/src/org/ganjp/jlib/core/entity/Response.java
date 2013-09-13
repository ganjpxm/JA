/**
 * Response.java
 * 
 * Created by Gan Jianping on 20/06/13.
 * Copyright (c) 2013 DBS. All rights reserved.
 */
package org.ganjp.jlib.core.entity;


public class Response {
	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_FAIL = "fail";
	
	private String status;
	private String msg;
	private String uuid;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Result [status=" + status + ", msg=" + msg + ", uuid=" + uuid + "]";
	}
	
}
