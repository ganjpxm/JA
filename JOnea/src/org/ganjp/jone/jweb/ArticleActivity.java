package org.ganjp.jone.jweb;

import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jone.R;
import org.ganjp.jone.common.HomeFragmentActivity;
import org.ganjp.jone.common.JOneActivity;
import org.ganjp.jone.common.JOneConst;
import org.ganjp.jone.common.JOneUtil;
import org.ganjp.jone.jweb.dao.CmArticleDAO;
import org.ganjp.jone.jweb.entity.CmArticle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleActivity extends JOneActivity {
	private ImageView mCoverIv;
	private TextView mContentTv;	
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jweb_activity_article);
		super.onCreate(savedInstanceState);
		
		mCoverIv=(ImageView)findViewById(R.id.cover_iv);
		mCoverIv.setOnClickListener(this);
		mContentTv = (TextView)findViewById(R.id.content_tv);
		mContentTv.setOnClickListener(this);
		
		String uuid = (String)getIntent().getExtras().getString(JOneConst.KEY_UUID);
		CmArticle cmArticle = CmArticleDAO.getInstance().getCmArticle(uuid);
		if (cmArticle!=null) {
			mTitleTv.setText(cmArticle.getTitle());
			mContentTv.setText(Html.fromHtml(cmArticle.getContent()));
			if (StringUtil.isNotEmpty(cmArticle.getImageUrl()) && cmArticle.getImageUrl().indexOf("/")==-1) {
				JOneUtil.setImage(mCoverIv, cmArticle.getImageUrl());
			}
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