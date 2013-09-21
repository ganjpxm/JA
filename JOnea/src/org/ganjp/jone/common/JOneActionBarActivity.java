/**
 * JpActionBarActivity.java
 *
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import org.ganjp.jlib.core.BaseActionBarActivity;
import org.ganjp.jone.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * <p>Base Fragment Activity support android 2.2+</p>
 * 
 * @author GanJianping
 *
 */
public abstract class JOneActionBarActivity extends BaseActionBarActivity {
	
	/**
	 * Called when the activity is first created
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JOneActivityManager.getInstance().pushActivity(this);
	}
	
	/**
	 * <p>Inflate the menu; this adds items to the action bar if it is present.</p>
	 *  
	 * @param menu 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * <p>Click menu items event</p>
	 * 
	 *  @param item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.global_menu_exit :
				JOneActivityManager.getInstance().popAllActivity();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public void showToastFromBackground(final String message) {
    	runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	if (message.equals(JOneConst.FAIL)) {
					Toast.makeText(JOneApplication.getAppContext(), JOneApplication.getAppContext().getString(R.string.fail), Toast.LENGTH_SHORT).show();
				} else if (message.equals(JOneConst.SUCCESS)) {
					Toast.makeText(JOneApplication.getAppContext(), JOneApplication.getAppContext().getString(R.string.success), Toast.LENGTH_SHORT).show();
				} else if (message.equals(JOneConst.TIMEOUT)) {
					Toast.makeText(JOneApplication.getAppContext(), JOneApplication.getAppContext().getString(R.string.timeout), Toast.LENGTH_SHORT).show();
				}
	        }
    	});
    }
}	
