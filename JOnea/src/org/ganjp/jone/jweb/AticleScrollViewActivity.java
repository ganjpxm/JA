package org.ganjp.jone.jweb;

import java.util.List;

import org.ganjp.jlib.core.util.WebViewUtil;
import org.ganjp.jlib.core.view.ScrollViewGroup;
import org.ganjp.jlib.core.view.ScrollViewGroup.PageChangedListener;
import org.ganjp.jone.R;
import org.ganjp.jone.common.HomeFragmentActivity;
import org.ganjp.jone.common.JOneActivity;
import org.ganjp.jone.common.JOneConst;
import org.ganjp.jone.common.JOneUtil;
import org.ganjp.jone.common.PreferenceUtil;
import org.ganjp.jone.jweb.dao.CmArticleDAO;
import org.ganjp.jone.jweb.dao.CmPhotoDAO;
import org.ganjp.jone.jweb.entity.CmArticle;
import org.ganjp.jone.jweb.entity.CmPhoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AticleScrollViewActivity extends JOneActivity {
	private ScrollViewGroup pagers;
	private CmArticle mCmArticle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jweb_article_scroll_view);
		super.onCreate(savedInstanceState);
		
		String uuid = (String)getIntent().getExtras().getString(JOneConst.KEY_UUID);
		mCmArticle = CmArticleDAO.getInstance().getCmArticle(uuid);
		mTitleTv.setText(getString(R.string.article));
		
		this.initComponent();
		this.registerListener();
		pageChanged(0);
	}

	@Override
	public void onClick(View view) {
    	super.onClick(view);
    	if (view == mBackBtn) {
    		startActivity(new Intent(this, HomeFragmentActivity.class));
    		super.transitBack();
		}
    }
	public void initComponent() {
		pagers = (ScrollViewGroup) findViewById(R.id.pagers);
		LayoutInflater inflater = getLayoutInflater();
		pagers.init();
		
		View view = inflater.inflate(R.layout.jweb_view_article, null);
//		view.setLayoutParams(new RelativeLayout.LayoutParams(getScreenWidth(), getScreenHeight()));
		
		ScrollView scrollView = (ScrollView)view.findViewById(R.id.scroll_view);
		scrollView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getScreenHeight()-100));
		
		TextView titleTv = (TextView)view.findViewById(R.id.title_tv);
		titleTv.setText(mCmArticle.getTitle());
		titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	pageChanged(1);
            }
        });
		
		ImageView coverIv = (ImageView)view.findViewById(R.id.cover_iv);
		JOneUtil.setImageNormal(coverIv, mCmArticle.getImageUrl());
		coverIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	pageChanged(1);
            }
        });
		
		WebView webView = (WebView)view.findViewById(R.id.article_wv);
		WebViewUtil.initWebView(webView);
		webView.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth()-20, LinearLayout.LayoutParams.MATCH_PARENT));
		String htmlContent = mCmArticle.getContent();
		webView.loadData(htmlContent, "text/html", "utf-8");
		pagers.addView(view);
		
		TextView moreTv = (TextView)view.findViewById(R.id.more_tv);
		moreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	pageChanged(1);
            }
        });
		
		if (mCmArticle!=null) {
			List<CmPhoto> cmPhotos = CmPhotoDAO.getInstance().getCmPhotos(mCmArticle.getArticleId(), PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG));
			for (CmPhoto cmPhoto : cmPhotos) {
				if (JOneUtil.isExist(cmPhoto.getUrl())) {
					view = inflater.inflate(R.layout.jweb_view_photo_webview, null);
					view.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight()));
					ImageView photoIv = (ImageView) view.findViewById(R.id.photo_iv);
					JOneUtil.setImageNormal(photoIv, cmPhoto.getUrl());
					photoIv.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight()-350));
					webView = (WebView)view.findViewById(R.id.web_view);
					WebViewUtil.initWebView(webView);
					webView.loadData(cmPhoto.getRemark(), "text/html", "utf-8");
					webView.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), 200));
					pagers.addView(view);
				}
			}
		}
	}
	
	public void pageChanged(int page) {
		pagers.moveToPage(page);
	}

	public void registerListener() {
		pagers.setPageChangedListener(new PageChangedListener() {
			@Override
			public void pageScroll(float location) {
				
			}
			@Override
			public void pageChanged(int page) {
				
			}
		});
	}

}
