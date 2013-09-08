package org.ganjp.jone.common.adapt;

import java.util.ArrayList;
import java.util.List;

import org.ganjp.jone.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	List<String[]> list;

    public MenuListAdapter(Context c, String[] menuItems) {
		this.inflater = LayoutInflater.from(c);
		
		list = new ArrayList<String[]>();
		for (String menuItemWithComma : menuItems) {
			String[] menuItemArr = menuItemWithComma.split(";");
			for (int i=0; i<menuItemArr.length; i++) {
				list.add(menuItemArr[i].split(":"));
			}
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	View view = inflater.inflate(R.layout.template_list_item_menu, null);
    	String title="";
    	String description="";
    	String[] itemArr = list.get(position);
    	TextView titleTv = (TextView) view.findViewById(R.id.section_tv);
		if (itemArr.length == 3) {
			title=itemArr[1];
			description=itemArr[2];
			titleTv.setText(itemArr[0]);
			titleTv.setVisibility(View.VISIBLE);
		} else {
			title=itemArr[0];
			description=itemArr[1];
			titleTv.setVisibility(View.GONE);
		}
		TextView itemTitleTv = (TextView) view.findViewById(R.id.item_title_tv);
		itemTitleTv.setText(title);
		TextView itemDescriptionTv = (TextView) view.findViewById(R.id.item_description_tv);
		itemDescriptionTv.setText(description);
		return view;
    }

}
