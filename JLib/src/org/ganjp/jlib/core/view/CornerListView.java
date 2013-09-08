package org.ganjp.jlib.core.view;

import org.ganjp.jlib.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

public class CornerListView extends ListView {
	public CornerListView(Context context) {
		super(context);
	}

	public CornerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			int itemnum = pointToPosition(x, y);
			if (itemnum == AdapterView.INVALID_POSITION)
				break;
			else {
				if (itemnum == 0) {
					if (itemnum == (getAdapter().getCount() - 1)) {
						setSelector(R.drawable.style_list_round);
					} else {
						setSelector(R.drawable.style_list_top_round);
					}
				} else if (itemnum == (getAdapter().getCount() - 1))
					setSelector(R.drawable.style_list_bottom_round);
				else {
					setSelector(R.drawable.style_list_center_round);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onTouchEvent(ev);
	}
}