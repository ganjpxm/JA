package org.ganjp.jone.common.fragment;

import org.ganjp.jone.R;
import org.ganjp.jone.common.JpApplication;
import org.ganjp.jone.common.adapt.MenuListAdapter;
import org.ganjp.jone.sample.SampleActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuFragment extends Fragment implements OnClickListener, OnItemClickListener {

	private ListView menuListView = null;
	private MenuListAdapter mMenuListAdapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.common_fragment_menu, container, false);
		
		String[] menuItems = JpApplication.getAppContext().getResources().getStringArray(R.array.menu_items);
		
        menuListView = (ListView) view.findViewById(R.id.menu_lv);
        mMenuListAdapter = new MenuListAdapter(getActivity().getApplicationContext(), menuItems);
        menuListView.setAdapter(mMenuListAdapter);
        menuListView.setOnItemClickListener(this);
	        
		return view;
	}

	@Override
    public void onClick(View v) {

    }
	
	@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(menuListView)) {
        	if (position==0) {
        		 Intent intent = new Intent(getActivity(), SampleActivity.class);
        		 getActivity().startActivity(intent);
        	}
        }
    }
}
