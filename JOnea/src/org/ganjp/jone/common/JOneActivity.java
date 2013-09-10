/**
 * JpActivity.java
 *
 * Created by Gan Jianping on 07/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import org.ganjp.jlib.core.BaseActivity;
import org.ganjp.jone.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

/**
 * <p>Base Activity</p>
 * 
 * @author GanJianping
 *
 */
public abstract class JOneActivity extends BaseActivity {
	
	protected Button mBackBtn;
	protected TextView mTitleTv;
	
	/**
	 * Called when the activity is first created
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mTitleTv = (TextView)findViewById(R.id.title_tv);
		mBackBtn = (Button)findViewById(R.id.left_btn);
		if (mBackBtn!=null) {
			mBackBtn.setOnClickListener(this);
		}
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
}	
