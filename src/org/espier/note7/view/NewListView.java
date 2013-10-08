package org.espier.note7.view;

import org.espier.note7.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NewListView extends ListView {
	private LinearLayout llHeader;
	private int headHeight, headWidth;
	private int startX, startY;
	private int state;
	private static final int DOWN = 1;
	private static final int REFRESHING = 2;
	private boolean isRecord;
	public NewListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}

	private void init(Context context){
		llHeader = (LinearLayout) View.inflate(context, R.layout.head_list,
				null);
		measureView(llHeader);
		headHeight = llHeader.getMeasuredHeight();
		headWidth = llHeader.getMeasuredWidth();
		llHeader.setPadding(0, -1 * headHeight, 0, 0);
		llHeader.invalidate();
		addHeaderView(llHeader);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!isRecord) {
				startX = (int) ev.getX();
				startY = (int) ev.getY();
			}

			System.out.println("startY===" + startY);
			break;
		case MotionEvent.ACTION_MOVE:
			int tempY = (int) ev.getY();
			if ((tempY - startY) > 0 && !isRecord) {
				llHeader.setPadding(0, -1 * headHeight + (tempY - startY) / 3,
						0, 0);
			}
			System.out.println("tempY===" + tempY);
			break;
		case MotionEvent.ACTION_UP:
			int tempY1 = (int) ev.getY();
			System.out.println("tempY===" + tempY1);
			if ((tempY1 - startY) < headHeight && !isRecord) {
				llHeader.setPadding(0, -1 * headHeight, 0, 0);
			} else {
				llHeader.setPadding(0, 0, 0, 0);
				isRecord = true;
			}


			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		// switch (ev.getAction()) {
		// case MotionEvent.ACTION_DOWN:
		// startX = (int) ev.getX();
		// startY = (int) ev.getY();
		// case MotionEvent.ACTION_MOVE:
		// int tempX = (int) ev.getX();
		// int tempY = (int) ev.getY();
		// if (Math.abs(tempX - startX) > Math.abs(tempY - startY)) {
		// // System.out.println("===action_move==false");
		// return false;
		//
		// }
		// }
		return super.dispatchTouchEvent(ev);
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
}
