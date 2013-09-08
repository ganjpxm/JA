/**
 * ActivityManager.java
 * 
 * Created by Gan Jianping on 13/06/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import org.ganjp.jlib.core.ActivityStack;



/**
 * <p>Manage Activity by stack</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class JpActivityManager extends ActivityStack {
	private static JpActivityManager sInstance;
	
	public static JpActivityManager getInstance() {
		if (sInstance == null) {
			sInstance = new JpActivityManager();
		}
		return sInstance;
	}
	
}
