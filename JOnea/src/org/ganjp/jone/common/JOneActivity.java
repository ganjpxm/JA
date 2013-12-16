/**
 * JpActivity.java
 *
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import org.ganjp.jlib.core.BaseActivity;
import org.ganjp.jone.R;

import com.flurry.android.FlurryAgent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>Base Activity</p>
 * 
 * @author GanJianping
 *
 */
public abstract class JOneActivity extends BaseActivity {
	
	protected Button mBackBtn;
	protected TextView mTitleTv;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FlurryAgent.setReportLocation(true);
		FlurryAgent.onStartSession(getContext(), JOneConst.APP_FLURRY_KEY);
		mTitleTv = (TextView)findViewById(R.id.title_tv);
		mBackBtn = (Button)findViewById(R.id.left_btn);
		if (mBackBtn!=null) {
			mBackBtn.setOnClickListener(this);
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(getContext());
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
