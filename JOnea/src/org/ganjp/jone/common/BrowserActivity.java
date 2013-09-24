package org.ganjp.jone.common;

import org.ganjp.jlib.core.util.WebViewUtil;
import org.ganjp.jone.R;
import org.ganjp.jone.sample.SampleFragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BrowserActivity extends JOneActivity {
	ProgressBar progress;
	boolean isTimeout = false;
	
	private WebView mWebView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.common_activity_browser);
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		mTitleTv.setText(bundle.getString(JOneConst.KEY_TITLE));
		
		mWebView = (WebView)findViewById(R.id.web_view);
		WebViewUtil.initWebView(mWebView);
		
		mWebView.setWebViewClient(new WebViewClient() {
       		@Override
       		public void onPageStarted(WebView view, String url, Bitmap favicon) {
       			super.onPageStarted(view, url, favicon);
       			progress = (ProgressBar) findViewById(R.id.progress);
       			progress.setVisibility(TextView.VISIBLE);
       		}
       		@Override
       		public void onPageFinished(WebView webView, String url) {
       			super.onPageFinished(webView, url);
       			progress.setVisibility(View.GONE);
       		}
       		@Override
       		public boolean shouldOverrideUrlLoading(WebView view, String url) {
       			view.loadUrl(url);
       			return true;
       		}
       		@Override  
       		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {  
               handler.proceed();  
       		}  
       });

       mWebView.setWebChromeClient(new WebChromeClient() {
           @Override
           public void onReceivedTitle(WebView view, String title) {
               setTitle(title);
               super.onReceivedTitle(view, title);
           }
       });
       
       mWebView.loadUrl(bundle.getString(JOneConst.KEY_URL));
	}
	
	@Override
    public void onClick(View view) {
    	super.onClick(view);
    	if (view == mBackBtn) {
    		finish();
    		startActivity(new Intent(this, SampleFragmentActivity.class));
    		super.transitBack();
		}
    }
}