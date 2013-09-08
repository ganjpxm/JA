
package org.ganjp.jone.common;

import org.ganjp.jlib.core.view.PagerSlidingTabStrip;
import org.ganjp.jone.R;
import org.ganjp.jone.common.fragment.MenuFragment;
import org.ganjp.jone.common.fragment.SuperAwesomeCardFragment;

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
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;

public class HomeActivity extends ActionBarActivity {

	private final Handler handler = new Handler();

	private PagerSlidingTabStrip pagerSlidingTabStrip;
	private ViewPager viewPager;
	private HomePagerAdapter homePageAdapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF2dcc70;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_activity_home);

		pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pager_sliding_tab_strip);
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		homePageAdapter = new HomePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(homePageAdapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		viewPager.setPageMargin(pageMargin);

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

			TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				td.setCallback(drawableCallback);
			} else {
				getSupportActionBar().setBackgroundDrawable(td);
			}

			td.startTransition(200);

		}

		oldBackground = ld;

		currentColor = newColor;

		// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(true);

	}

	public void onColorClicked(View v) {

		int color = ((ColorDrawable) v.getBackground()).getColor();
		changeColor(color);

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

	public class HomePagerAdapter extends FragmentPagerAdapter {
		private final String[] homeTabItem = JpApplication.getAppContext().getResources().getStringArray(R.array.home_tab_items);
		
		public HomePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return homeTabItem[position];
		}

		@Override
		public int getCount() {
			return homeTabItem.length;
		}

		@Override
		public Fragment getItem(int position) {
			if (position==0) {
				return new MenuFragment();
			}
			return SuperAwesomeCardFragment.newInstance(position);
		}

	}

}
