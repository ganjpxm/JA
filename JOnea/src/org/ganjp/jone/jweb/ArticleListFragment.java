/**
 * KnowledgeFragment.java
 *
 * Created by Gan Jianping on 09/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.jweb;

import java.util.ArrayList;
import java.util.List;

import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jone.R;
import org.ganjp.jone.common.JOneConst;
import org.ganjp.jone.common.PreferenceUtil;
import org.ganjp.jone.jweb.dao.CmArticleDAO;
import org.ganjp.jone.jweb.entity.CmArticle;
import org.ganjp.jone.jweb.entity.Item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * <p>Knowledge Fragment</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class ArticleListFragment extends Fragment implements OnItemClickListener {

	private String tag = "";
	private ListView mKnowledgeListView = null;
	private ArticleListAdapter mArticleListAdapter = null;

	public static ArticleListFragment newInstance(String aTag) {
		ArticleListFragment f = new ArticleListFragment();
		Bundle b = new Bundle();
		b.putString(JOneConst.KEY_TAG, aTag);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tag = getArguments().getString(JOneConst.KEY_TAG);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.jweb_list_view, container, false);
		List<Item> items = new ArrayList<Item>();
		if (StringUtil.isNotEmpty(tag)) {
			List<CmArticle> cmArticles = CmArticleDAO.getInstance().getCmArticles(tag, PreferenceUtil.getString(JOneConst.KEY_PREFERENCE_LANG));
			for (CmArticle cmArticle : cmArticles) {
				String imageName = cmArticle.getImageUrl();
				if (cmArticle.getImageUrl().indexOf("/")!=-1) {
					imageName = imageName.substring(imageName.lastIndexOf("/")+1);
				}
				items.add(new Item(cmArticle.getArticleId(), imageName, cmArticle.getTitle(), cmArticle.getSummary()));
			}
		}
		mKnowledgeListView = (ListView) view.findViewById(R.id.listview);
		mArticleListAdapter = new ArticleListAdapter(getActivity().getApplicationContext(), items);
		mKnowledgeListView.setAdapter(mArticleListAdapter);
		mKnowledgeListView.setOnItemClickListener(this);
		return view;
	}
	
	@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(mKnowledgeListView)) {
        	Item item = mArticleListAdapter.getItem(position);
        	if (item!=null) {
        		Intent intent = new Intent(getActivity(), AticleScrollViewActivity.class);
        		intent.putExtra(JOneConst.KEY_UUID, item.getItemUuid());
        		getActivity().startActivity(intent);
        		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        	}
        }
    }

}