package com.constant_therapy.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.renderscript.Program.TextureType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.constantTherapy.DoingTaskActivity.MyDragEventListener;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.util.WrittenDropAdapter.Holder;
import com.constant_therapy.widget.CTTextView;

public class ActiveDropAdapter extends BaseAdapter {

	private Context mContext;
	public ArrayList<Tasks> tasks = new ArrayList<Tasks>();

	List<Integer> resources = new ArrayList<Integer>();
	List<String> itemList = new ArrayList<String>();
	String name = null;
	View pView;
	int pos;
	char[] nameArray;

	private LayoutInflater layoutInflator;
	MyDragEventListener myDragEventListener;

	int size;
	private DisplayMetrics mDisplayMetrics;

	public ActiveDropAdapter(Context c, List<Integer> resources, List<String> itemList,
			MyDragEventListener myDragEventListener) {
		// TODO Auto-generated constructor stub
		mContext = c;
		this.resources = resources;
		this.itemList = itemList;
		this.myDragEventListener = myDragEventListener;
		Collections.sort(resources, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		
		layoutInflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDisplayMetrics = c.getResources().getDisplayMetrics();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		final Holder holder = new Holder();
		View rowView;

		rowView = layoutInflator.inflate(R.layout.written_word, null);
		holder.taskText = (CTTextView) rowView.findViewById(R.id.taskWord);
		size = getBarHeight(6);
		holder.taskText.getLayoutParams().height = (int) (size/1.5);
		holder.taskText.setTextSize(mContext.getResources().getDimension(R.dimen.btn_ok));
		holder.taskText.setTypeface(null, Typeface.NORMAL);
		if (getMissing(resources) != position) {
			
			holder.taskText.setTag(itemList.get(position));
			holder.taskText.setText(""+itemList.get(position));
			holder.taskText.setBackground(mContext.getResources().getDrawable(R.drawable.black_border));
			holder.taskText.setVisibility(View.INVISIBLE);
			DoingTaskActivity.dropViewList.add(rowView);
		} else {
			holder.taskText.setText("");
			holder.taskText.setTag(itemList.get(position));
			rowView.setOnDragListener(myDragEventListener);
			DoingTaskActivity.answerDropList.add(holder.taskText);
		}

		return rowView;
	}

	// references to our images

	public class Holder {

		CTTextView taskText;
	}

	private int getMissing(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (i != list.get(i))
				return i;
		}
		return 0;
	}

	private Boolean isViewExist(String item) {
		for (int i = 0; i < DoingTaskActivity.dropViewList.size(); i++) {
			if (item.equalsIgnoreCase(DoingTaskActivity.dropViewList.get(i)
					.findViewById(R.id.taskWord).getTag().toString())) {
				return true;
			}
		}

		return false;
	}

	private int getBarHeight(int count) {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH: {
			switch (count) {
			case 6:
				return 250;

			case 12:

				return 165;
			case 20:

				return 125;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_MEDIUM: {
			switch (count) {
			case 6:
				return 250;

			case 12:

				return 165;
			case 20:

				return 120;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_LOW: {
			switch (count) {
			case 6:
				return 250;

			case 12:

				return 130;
			case 20:

				return 180;
			case 24:

				return 120;
			case 30:

				return 120;

			default:
				break;
			}
			return 170;
		}

		case DisplayMetrics.DENSITY_TV: {
			switch (count) {
			case 6:
				return 220;

			case 12:

				return 150;
			case 20:

				return 125;
			case 24:

				return 120;
			case 30:

				return 95;

			default:
				break;
			}
			return 170;
		}

		default:
			switch (count) {
			case 6:
				return 350;

			case 12:

				return 230;
			case 20:

				return 170;
			case 24:

				return 170;
			case 30:

				return 135;

			default:
				break;
			}

		}
		return 0;
	}

}

