package org.ganjp.jone.sample.ui;

import org.ganjp.jlib.core.view.PageScrollBar;
import org.ganjp.jlib.core.view.ScrollViewGroup;
import org.ganjp.jlib.core.view.ScrollViewGroup.PageChangedListener;
import org.ganjp.jone.R;
import org.ganjp.jone.common.JOneActivity;
import org.ganjp.jone.sample.SampleActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UiScrollViewPagerActivity extends JOneActivity {
	private int colors[] = { Color.GREEN, Color.YELLOW, Color.MAGENTA };
	private PageScrollBar scrollBar;
	private ScrollViewGroup pagers;
	private ImageView pageImgs[];
	private int pageNum = 3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sample_ui_view_pager_scroll);
		super.onCreate(savedInstanceState);
		
		mTitleTv.setText("Scroll View Pager");
		
		this.initComponent();
		this.registerListener();
		pageChanged(0);
	}

	@Override
	public void onClick(View view) {
    	super.onClick(view);
    	if (view == mBackBtn) {
    		startActivity(new Intent(this, SampleActivity.class));
		}
    }
	public void initComponent() {
		pageImgs = new ImageView[pageNum];
		pageImgs[0] = (ImageView) findViewById(R.id.pageNum1);
		pageImgs[1] = (ImageView) findViewById(R.id.pageNum2);
		pageImgs[2] = (ImageView) findViewById(R.id.pageNum3);
		pagers = (ScrollViewGroup) findViewById(R.id.pagers);
		scrollBar = (PageScrollBar) findViewById(R.id.scrollbar);
		LayoutInflater inflater = getLayoutInflater();
		pagers.init();
		scrollBar.init(pageNum);
		for (int i = 0; i < pageNum; i++) {
			View view = inflater.inflate(R.layout.sample_ui_view_pager_scroll_page, null);
			TextView title = (TextView) view.findViewById(R.id.pagetitle);
			title.setText("" + i);
			view.setBackgroundColor(colors[i]);
			pagers.addView(view);
		}
	}

	public void pageChanged(int page) {
		pagers.moveToPage(page);
		for (int i = 0; i < pageImgs.length; i++) {
			ImageView view = pageImgs[i];
			if (i == page) {
				view.setImageResource(R.drawable.icon_circle_page_now);
			} else {
				view.setImageResource(R.drawable.icon_circle_page);
			}
		}
	}

	public void registerListener() {
		for (int i = 0; i < pageImgs.length; i++) {
			ImageView view = pageImgs[i];
			view.setOnClickListener(new OnClickListenerImp(i));
		}
		pagers.setPageChangedListener(new PageChangedListener() {
			@Override
			public void pageScroll(float location) {
				scrollBar.updateScrollBlock(location);
			}
			@Override
			public void pageChanged(int page) {
				for (int i = 0; i < pageImgs.length; i++) {
					ImageView view = pageImgs[i];
					if (i == page) {
						view.setImageResource(R.drawable.icon_circle_page_now);
					} else {
						view.setImageResource(R.drawable.icon_circle_page);
					}
				}
			}
		});
	}

	private class OnClickListenerImp implements OnClickListener {
		int page;
		public OnClickListenerImp(int page) {
			this.page = page;
		}
		@Override
		public void onClick(View v) {
			pageChanged(page);
		}
	}

}
