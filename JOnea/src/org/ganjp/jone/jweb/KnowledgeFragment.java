/**
 * KnowledgeFragment.java
 *
 * Created by Gan Jianping on 09/09/13.
 * Copyright (c) 2013 GANJP. All rights reserved.
 */
package org.ganjp.jone.jweb;

import java.util.ArrayList;
import java.util.List;

import org.ganjp.jone.R;
import org.ganjp.jone.common.JOneConst;
import org.ganjp.jone.jweb.entity.Item;
import org.ganjp.jone.sample.SampleActivity;

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
public class KnowledgeFragment extends Fragment implements OnItemClickListener {

	private int mKnowledgeCatagoryId = 0;
	private ListView mKnowledgeListView = null;
	private KnowledgeListAdapter mKnowledgeListAdapter = null;

	public static KnowledgeFragment newInstance(int categoryId) {
		KnowledgeFragment f = new KnowledgeFragment();
		Bundle b = new Bundle();
		b.putInt(JOneConst.KNOWLEDGE_CATAGORY_ID, categoryId);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mKnowledgeCatagoryId = getArguments().getInt(JOneConst.KNOWLEDGE_CATAGORY_ID);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.knowledge_list_view, container, false);
		List<Item> items = new ArrayList<Item>();
		if (JOneConst.PROGRAM_ANDROID == mKnowledgeCatagoryId) {
			for (int i=0;i<10;i++) {
				items.add(new Item("android ","descrption"));
			}
		} else if (JOneConst.PROGRAM_IOS == mKnowledgeCatagoryId) {
			for (int i=0;i<10;i++) {
				items.add(new Item("ios " + i,"descrption"));
			}
		} else if (JOneConst.NEWS_MOBILE_APP == mKnowledgeCatagoryId) {
			for (int i=0;i<10;i++) {
				items.add(new Item("mobile app " + i,"descrption"));
			}
		}
		
		mKnowledgeListView = (ListView) view.findViewById(R.id.listview);
		mKnowledgeListAdapter = new KnowledgeListAdapter(getActivity().getApplicationContext(), items);
		mKnowledgeListView.setAdapter(mKnowledgeListAdapter);
		mKnowledgeListView.setOnItemClickListener(this);
	        
		return view;
	}
	
	@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(mKnowledgeListView)) {
        	if (position==0) {
        		 Intent intent = new Intent(getActivity(), SampleActivity.class);
        		 getActivity().startActivity(intent);
        	}
        }
    }

}