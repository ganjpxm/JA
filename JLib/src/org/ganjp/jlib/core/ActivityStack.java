/**
 * ActivityUtil.java
 * 
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jlib.core;

import java.util.Stack;

import android.app.Activity;

/**
 * <p>Manage Activity by stack</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class ActivityStack {
	protected static Stack<Activity> sActivityStack;
	protected static ActivityStack sBaseInstance;
	
	protected ActivityStack() { }
	
	/**
	 * <p>Popup last activity</p>
	 */
	public void popLastActivity(){
		Activity activity = getLastActivity();
		if (activity!=null) {
			popActivity(activity);
		}
	}
	
	/**
	 * <p>Pop the activity</p>
	 * 
	 * @param activity
	 */
	public void popActivity(Activity activity){
		activity.finish();
		sActivityStack.remove(activity);
		activity = null;
	}
	
	/**
	 * <p>Get the last activity</p>
	 * 
	 * @return Activity
	 */
	public Activity getLastActivity(){
		if (sActivityStack==null || sActivityStack.isEmpty()) {
			return null;
		}
		return sActivityStack.lastElement();
	}
	
	/**
	 * <p>Push the activity</p>
	 * 
	 * @param activity
	 */
	public void pushActivity(Activity activity){
		if (sActivityStack == null) {
			sActivityStack = new Stack<Activity>();
		}
		sActivityStack.add(activity);
	}
	
	/**
	 * <p>Pop all the activities</p>
	 */
	public void popAllActivity() {
		while(true){
			Activity activity = getLastActivity();
			if (activity!=null) {
				popActivity(activity);
			} else {
				break;
			}
		}
	}
}
