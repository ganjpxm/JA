package org.ganjp.jone.sample;

import java.io.File;

import org.ganjp.jlib.core.util.StringUtil;
import org.ganjp.jone.R;
import org.ganjp.jone.common.BrowserActivity;
import org.ganjp.jone.common.JOneApplication;
import org.ganjp.jone.common.JOneConst;
import org.ganjp.jone.common.MenuListAdapter;
import org.ganjp.jone.sample.thirdparty.VudroidPdfActivity;
import org.ganjp.jone.sample.ui.UiDialogActivity;
import org.ganjp.jone.sample.ui.UiScrollViewPagerActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SampleMenuFragment extends Fragment implements OnItemClickListener {

	private String tag = "";
	private ListView menuListView = null;
	private MenuListAdapter mMenuListAdapter = null;
	
	public static SampleMenuFragment newInstance(String tag) {
		SampleMenuFragment fragment = new SampleMenuFragment();
		Bundle b = new Bundle();
		b.putString(JOneConst.KEY_TAG, tag);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tag = getArguments().getString(JOneConst.KEY_TAG).toLowerCase();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.common_list_view, container, false);
		
		String[] menuItems = null;
	
		if (tag.indexOf("android")!=-1) {
			menuItems = JOneApplication.getAppContext().getResources().getStringArray(R.array.sample_android_menu_items);
		} else if (tag.indexOf("jquery")!=-1) {
			menuItems = JOneApplication.getAppContext().getResources().getStringArray(R.array.sample_jquery_mobile_menu_items);
		} else if (tag.indexOf("sencha")!=-1) {
			menuItems = JOneApplication.getAppContext().getResources().getStringArray(R.array.sample_sencha_touch_menu_items);
		} else if (tag.indexOf("kendo")!=-1) {
			menuItems = JOneApplication.getAppContext().getResources().getStringArray(R.array.sample_kendo_mobile_menu_items);
		}
		
        menuListView = (ListView) view.findViewById(R.id.listview);
        mMenuListAdapter = new MenuListAdapter(getActivity().getApplicationContext(), menuItems);
        menuListView.setAdapter(mMenuListAdapter);
        menuListView.setOnItemClickListener(this);
	        
		return view;
	}
	
	@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String item = mMenuListAdapter.getTitle(position);
        if (StringUtil.isNotEmpty(item)) {
        	item = item.toLowerCase();
        	if (tag.indexOf("android")!=-1) {
        		if (item.indexOf("dialog")!=-1) {
            		getActivity().startActivity(new Intent(getActivity(), UiDialogActivity.class));
            	} else if (item.indexOf("scroll view")!=-1){
            		getActivity().startActivity(new Intent(getActivity(), UiScrollViewPagerActivity.class));
            	} else if (item.indexOf("vudroid")!=-1){
            		 File file = new File("/mnt/sdcard/tnc.pdf"); 
            		 Uri pdfUri = Uri.fromFile(file); 
            		final Intent intent = new Intent(Intent.ACTION_VIEW, pdfUri);
                    intent.setClass(getActivity(), VudroidPdfActivity.class);
                    startActivity(intent);
            	}
        		
    		} else if (tag.indexOf("jquery")!=-1) {
    			
    		} else if (tag.indexOf("sencha")!=-1) {
    			
    		} else if (tag.indexOf("kendo")!=-1) {
    			Intent intent = new Intent(getActivity(), BrowserActivity.class);
    			intent.putExtra(JOneConst.KEY_TITLE, "Kendo Mobile");
    			if (item.indexOf("form")!=-1) {
    				intent.putExtra(JOneConst.KEY_URL, "file:///android_asset/www/kendo/form.html");
            	} else if (item.indexOf("listview")!=-1) {
    				intent.putExtra(JOneConst.KEY_URL, "file:///android_asset/www/kendo/listview.html");
            	} 
    			getActivity().startActivity(intent);
    		}
        	getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }
}
