package org.ganjp.jone.jweb;

import org.ganjp.jlib.core.util.WebViewUtil;
import org.ganjp.jone.R;
import org.ganjp.jone.common.HomeFragmentActivity;
import org.ganjp.jone.common.JOneActivity;
import org.ganjp.jone.common.JOneConst;
import org.ganjp.jone.jweb.dao.CmArticleDAO;
import org.ganjp.jone.jweb.entity.CmArticle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class ArticleActivity extends JOneActivity {
	private WebView mWebView;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jweb_activity_article);
		super.onCreate(savedInstanceState);
		
		mWebView = (WebView)findViewById(R.id.web_view);
		WebViewUtil.initWebView(mWebView);
		
		
		String uuid = (String)getIntent().getExtras().getString(JOneConst.KEY_UUID);
		CmArticle cmArticle = CmArticleDAO.getInstance().getCmArticle(uuid);
		if (cmArticle!=null) {
			String html = cmArticle.getContent();
			mWebView.loadData(html, "text/html", "utf-8");
			mTitleTv.setText(cmArticle.getTitle());
		} else {
			mTitleTv.setText(getString(R.string.title));
		}
	}
	
	@Override
    public void onClick(View view) {
    	super.onClick(view);
    	if (view == mBackBtn) {
    		startActivity(new Intent(this, HomeFragmentActivity.class));
    		super.transitBack();
		}
    }
}