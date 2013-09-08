package org.ganjp.jone.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ganjp.jlib.core.view.CornerListView;
import org.ganjp.jone.R;
import org.ganjp.jone.common.JpActivity;
import org.ganjp.jone.sample.ui.UiDialogActivity;
import org.ganjp.jone.sample.ui.UiScrollViewPagerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SampleActivity extends JpActivity implements OnItemClickListener {
	private CornerListView listView_1, listView_2;
	private ArrayList<Map<String, String>> listData, listData2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sample_activity);
		
		listView_1 = (CornerListView) findViewById(R.id.mylistview_1);
		listView_2 = (CornerListView) findViewById(R.id.mylistview_2);

		listView_1.setAdapter(getSimpleAdapter_1());
		listView_2.setAdapter(getSimpleAdapter_2());

		listView_1.setOnItemClickListener(this);
		listView_2.setOnItemClickListener(this);

		setListViewHeightBasedOnChildren(listView_1);
		setListViewHeightBasedOnChildren(listView_2);
	}

	@Override
	public void onClick(View view) {
    	super.onClick(view);
    }
	
	private SimpleAdapter getSimpleAdapter_1() {
		listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "Form");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "Dialog");
		listData.add(map);
		
		map = new HashMap<String, String>();
		map.put("text", "ͨList View");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "Grid View");
		listData.add(map);
		
		map = new HashMap<String, String>();
		map.put("text", "Fragment");
		listData.add(map);
		
		map = new HashMap<String, String>();
		map.put("text", "View Pager");
		listData.add(map);
		
		return new SimpleAdapter(SampleActivity.this, listData, R.layout.sample_activity_list_item, new String[] { "text" }, new int[] { R.id.tv_list_item });
	}

	private SimpleAdapter getSimpleAdapter_2() {
		listData2 = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "GCM");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "Map");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "Broadcast");
		listData2.add(map);

		return new SimpleAdapter(SampleActivity.this, listData2, R.layout.sample_activity_list_item, new String[] { "text" },
				new int[] { R.id.tv_list_item });

	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent == listView_1) {
			Map<String, String> map = listData.get(position);
//			Toast.makeText(SampleActivity.this, map.get("text"), 1).show();
			String itemName = map.get("text");
			if (itemName.equals("View Pager")) {
				startActivity(new Intent(this, UiScrollViewPagerActivity.class));
			} else if (itemName.equals("Dialog")) {
				startActivity(new Intent(this, UiDialogActivity.class));
			}
		} else if (parent == listView_2) {
			Map<String, String> map = listData2.get(position);
//			Toast.makeText(SampleActivity.this, map.get("text"), 1).show();
		}
	}

}
