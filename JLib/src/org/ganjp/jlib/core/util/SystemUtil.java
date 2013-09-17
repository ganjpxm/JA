/**
 * SystemUtil.java
 * 
 * Created by Gan Jianping on 09/09/13.
 * Copyright (c) 2013 DBS. All rights reserved.
 */
package org.ganjp.jlib.core.util;

import android.os.Environment;

/**
 * <p>System Utility</p>
 *
 * @author GanJianping
 * @since 1.0
 */
public class SystemUtil {
	
	/**
	 * <p>has sd card</p>
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
}
