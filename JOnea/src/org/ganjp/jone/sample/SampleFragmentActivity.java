/**
 * HomeActivity.java
 *
 * Created by Gan Jianping on 18/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.sample;

import org.ganjp.jlib.core.view.PagerSlidingTabStrip;
import org.ganjp.jone.R;
import org.ganjp.jone.common.HomeFragmentActivity;
import org.ganjp.jone.common.JOneActionBarActivity;
import org.ganjp.jone.common.JOneApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

/**
 * <p>
 * Sample Fragment Activity
 * </p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class SampleFragmentActivity extends JOneActionBarActivity {

	private PagerSlidingTabStrip pagerSlidingTabStrip;
	private ViewPager viewPager;
	private SamplePagerAdapter samplePageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_fragment_activity);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(getString(R.string.sample));
		
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		samplePageAdapter = new SamplePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(samplePageAdapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		viewPager.setPageMargin(pageMargin);
		pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pager_sliding_tab_strip);
		pagerSlidingTabStrip.setViewPager(viewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
        	Intent intent = new Intent(SampleFragmentActivity.this, HomeFragmentActivity.class);
        	SampleFragmentActivity.this.startActivity(intent);
	        transitBack();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	 
	public class SamplePagerAdapter extends FragmentPagerAdapter {
		private final String[] tabItems = JOneApplication.getAppContext().getResources().getStringArray(R.array.sample_tab_items);

		public SamplePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tabItems[position];
		}

		@Override
		public int getCount() {
			return tabItems.length;
		}

		@Override
		public Fragment getItem(int position) {
			return SampleMenuFragment.newInstance(tabItems[position]);
		}
	}

}
