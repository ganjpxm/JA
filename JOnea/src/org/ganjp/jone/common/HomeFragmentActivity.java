/**
 * HomeActivity.java
 *
 * Created by Gan Jianping on 09/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.common;

import java.util.Arrays;
import java.util.Locale;

import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jlib.core.view.PagerSlidingTabStrip;
import org.ganjp.jone.R;
import org.ganjp.jone.jweb.ArticleListFragment;
import org.ganjp.jone.sample.SampleFragmentActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

/**
 * <p>
 * Home Fragment Activity
 * </p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class HomeFragmentActivity extends JOneActionBarActivity {

	private final Handler handler = new Handler();

	private PagerSlidingTabStrip pagerSlidingTabStrip;
	private ViewPager viewPager;
	private HomePagerAdapter homePageAdapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF2dcc70;
	
	private static String sCurrentCategory = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (JOneUtil.getCategorys().isEmpty()) {
			JOneUtil.setCategoryTagsValueFromWeb();
		}
		if (StringUtil.isEmpty(sCurrentCategory) && JOneUtil.getCategorys()!=null && !JOneUtil.getCategorys().isEmpty()) {
			sCurrentCategory = JOneUtil.getCategorys().get(0);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_activity_home);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setTitle(getString(R.string.app_title));
		actionBar.setSubtitle(getString(R.string.app_sub_title));
		actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_LIST);
		SpinnerAdapter spinnerAdapter = null;
		if (JOneUtil.getCategorys().isEmpty()) {
			spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_dropdown_item);
			JOneUtil.setCategorys(Arrays.asList(getResources().getStringArray(R.array.category_list)));
			sCurrentCategory = JOneUtil.getCategorys().get(0);
		} else {
			spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, JOneUtil.getCategorys());
		}
		actionBar.setListNavigationCallbacks(spinnerAdapter, new DropDownListener());
		actionBar.setSelectedNavigationItem(JOneUtil.getCategorys().indexOf(sCurrentCategory));
		
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		homePageAdapter = new HomePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(homePageAdapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		viewPager.setPageMargin(pageMargin);

		pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pager_sliding_tab_strip);
		pagerSlidingTabStrip.setViewPager(viewPager);

		changeColor(currentColor);
	}

	private void changeColor(int newColor) {
		pagerSlidingTabStrip.setIndicatorColor(newColor);

		Drawable colorDrawable = new ColorDrawable(newColor);
		Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
		LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

		if (oldBackground == null) {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				ld.setCallback(drawableCallback);
			} else {
				getSupportActionBar().setBackgroundDrawable(ld);
			}
		} else {
			TransitionDrawable td = new TransitionDrawable(new Drawable[] {oldBackground, ld });
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				td.setCallback(drawableCallback);
			} else {
				getSupportActionBar().setBackgroundDrawable(td);
			}
			td.startTransition(200);
		}
		oldBackground = ld;
		currentColor = newColor;
		getSupportActionBar().setDisplayShowTitleEnabled(true);
	}

	public void onColorClicked(View v) {
		int color = ((ColorDrawable) v.getBackground()).getColor();
		changeColor(color);
	}

	private class DropDownListener implements OnNavigationListener {
		@Override
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			String itemName = JOneUtil.getCategorys().get(itemPosition);
			if (!sCurrentCategory.equals(itemName)) {
				sCurrentCategory = itemName;
				homePageAdapter = new HomePagerAdapter(getSupportFragmentManager());
				viewPager.setAdapter(homePageAdapter);
				pagerSlidingTabStrip.setViewPager(viewPager);
			}
			return true;
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getSupportActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	/**
	 * <p>Click menu items event</p>
	 * 
	 *  @param item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
			case R.id.language :
				Resources resources = getResources();
			    Configuration config = resources.getConfiguration();
			    DisplayMetrics dm = resources.getDisplayMetrics();
				String lang = PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG);
				if (lang.equals(JOneConst.LANG_ZH_CN)) {
					PreferenceUtil.saveString(JOneConst.KEY_PREFERENCE_LANG, JOneConst.LANG_EN_SG);
					config.locale = Locale.ENGLISH;
				} else {
					PreferenceUtil.saveString(JOneConst.KEY_PREFERENCE_LANG, JOneConst.LANG_ZH_CN);
					config.locale = Locale.SIMPLIFIED_CHINESE;
				}
			    resources.updateConfiguration(config, dm);
			    JOneUtil.setCategoryTagsValueFromWeb();
			    sCurrentCategory = "";
				finish();
		        startActivity(getIntent());
		        transitSlideDown();
				return true;
			case R.id.sample :
				Intent intent = new Intent(HomeFragmentActivity.this, SampleFragmentActivity.class);
		        startActivity(intent);
		        transitForward();
			    return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	public class HomePagerAdapter extends FragmentPagerAdapter {
		private String[] tabItems = null;

		public HomePagerAdapter(FragmentManager fm) {
			super(fm);
			String tags = JOneUtil.getCategoryTagsMap().get(sCurrentCategory);
			if (StringUtil.isEmpty(tags)) {
				tabItems = new String[] { getString(R.string.menu) };
			} else {
				tabItems = tags.split(",");
			}
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
			String tag = tabItems[position];
			if (JOneUtil.getTags().indexOf(tag)!=-1) {
				return ArticleListFragment.newInstance(tag);
			} else {
				return WhiteCardFragment.newInstance(position);
			}
		}
	}

}
