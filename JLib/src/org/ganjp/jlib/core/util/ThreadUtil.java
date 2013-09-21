/**
 * ThreadUtil.java
 *
 * Created by Gan Jianping on 04/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jlib.core.util;

import android.os.Handler;

/**
 * <p>Thread Util</p>
 *
 * @author GanJianping
 * @since 1.0
 */
public class ThreadUtil {
	
	/**
	 * <p>Run a thread after durationTime</p>
	 * <pre>
	   ThreadUtil.run(new Runnable() {
       		@Override
            public void run() {
                
            }
       }, Const.DURATION_SPLASH);
	 * </pre>
	 * @param runnable
	 * @param delayMillis
	 * @return
	 */
	public static Handler run(Runnable runnable, long delayMillis) {
		Handler handler = new Handler();
        handler.postDelayed(runnable, delayMillis);
        return handler;
	}

}
