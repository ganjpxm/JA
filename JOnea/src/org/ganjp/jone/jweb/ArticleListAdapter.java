/**
 * KnowledgeListAdapter.java
 *
 * Created by Gan Jianping on 09/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.jweb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ganjp.jlib.core.util.ImageUtil;
import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jone.R;
import org.ganjp.jone.common.JOneUtil;
import org.ganjp.jone.jweb.entity.Item;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <p>Knowledge list adapter</p>
 * 
 * @author Gan Jianping
 * @since 1.0.0
 */
public class ArticleListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
    private List<Item> mItems = null;

	public void addItems(List<Item> items) {
    	mItems.addAll(items);
        notifyDataSetChanged();
    }
	 
	public ArticleListAdapter(Context context) {
        super();
        this.inflater = LayoutInflater.from(context);
        mItems = new ArrayList<Item>();
    }
	
	public ArticleListAdapter(Context context, List<Item> items) {
        super();
        this.inflater = LayoutInflater.from(context);
        mItems = items;
    }

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Item getItem(int position) {
		if(position >= mItems.size()){
	    	return null;
	    }
	    return mItems.get(position);
	}

    @Override
    public long getItemId(int position) {
        return 0;
    }
    
	public String getItemUuid(int position) {
		if(position < mItems.size() && mItems.get(position)!=null){
	    	return mItems.get(position).getItemUuid();
	    }
	    return "";
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	View view = inflater.inflate(R.layout.jweb_list_item, null);
    	
    	
    	ImageView imageIv = (ImageView) view.findViewById(R.id.item_image_ib);
    	if (StringUtil.isNotEmpty(mItems.get(position).getImagePath())) {
    		JOneUtil.setImageSmall(imageIv, mItems.get(position).getImagePath());
    	}
    	
    	TextView itemTitleTv = (TextView) view.findViewById(R.id.item_title_tv);
		itemTitleTv.setText(Html.fromHtml(mItems.get(position).getTitle()));
		TextView itemDescriptionTv = (TextView) view.findViewById(R.id.item_summary_tv);
		itemDescriptionTv.setText(Html.fromHtml(mItems.get(position).getSummary()));
		
        return view;
    }

    
}
