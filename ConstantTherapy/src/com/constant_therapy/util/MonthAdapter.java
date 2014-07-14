package com.constant_therapy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.Compliance;
import com.constant_therapy.widget.CTTextView;

public class MonthAdapter extends BaseAdapter {
	String rgbMin = "DEE3E8";
	String rgbMax = "8DA1BF";
	int[] rgbMaxCode = { 141, 161, 191 };
	int[] rgbMinCode = { 222, 227, 232 };
	private GregorianCalendar mCalendar;
	private Calendar mCalendarToday;
	private Context mContext;
	private DisplayMetrics mDisplayMetrics;
	private List<String> mItems;
	private int mMonth;
	private int mYear;
	private int mDaysShown;
	private int mDaysLastMonth;
	private int mDaysNextMonth;
	private int mTitleHeight, mDayHeight;
	private ArrayList<Compliance> compilance = new ArrayList<Compliance>();
	private ArrayList<Calendar> date = new ArrayList<Calendar>();
	private ArrayList<Integer> taskCount = new ArrayList<Integer>();
	private ArrayList<Boolean> isClinic = new ArrayList<Boolean>();
	private final String[] mDays = { "SUN", "MON", "TUE", "WED", "THU", "FRI",
			"SAT" };
	private final int[] mDaysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
			31, 30, 31 };
	private LayoutInflater inflater;

	public MonthAdapter(Context c, int month, int year, DisplayMetrics metrics) {
		mContext = c;
		mMonth = month;
		mYear = year;
		mCalendar = new GregorianCalendar(mYear, mMonth, 1);
		mCalendarToday = Calendar.getInstance();
		mDisplayMetrics = metrics;
		this.compilance = new ArrayList<Compliance>();
		populateMonth();
		inflater = LayoutInflater.from(c);

	}

	public class CalendarCell {
		public CTTextView tv;
	}

	private Calendar getStringToDate(Long calendar) {
		Date df = new java.util.Date(calendar * 1000);
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		String str = null;
		str = sf.format(df);
		final Calendar c = Calendar.getInstance();
		try {
			c.setTime(sf.parse(str));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return c;

	}
	
	 public void setItems(ArrayList<Compliance> items) {
		 for (int i = 0; i < items.size(); i++) {
				date.add(getStringToDate(items.get(i).getDate()));
				taskCount.add(items.get(i).getCompletedTaskCount());
				isClinic.add(items.get(i).getInClinic());
				
			}
	    	this.compilance = items;
	    }

	public void populateMonth() {

		

		mItems = new ArrayList<String>();
		for (String day : mDays) {
			mItems.add(day);
			mDaysShown++;
		}

		int firstDay = getDay(mCalendar.get(Calendar.DAY_OF_WEEK));
		
		int addRow = 21 - (7 - firstDay);
		int prevDay;
		if (mMonth == 0)
			prevDay = (daysInMonth(11) - firstDay + 1) - addRow;
		else
			prevDay = daysInMonth(mMonth - 1) - addRow + 1;
		
		for (int i = 0; i < addRow; i++) {
			mItems.add(String.valueOf(prevDay + i));
			mDaysLastMonth++;
			mDaysShown++;
		}

		int daysInMonth = daysInMonth(mMonth);
		for (int i = 1; i <= daysInMonth; i++) {
			if (mDaysShown < 49) {
				mItems.add(String.valueOf(i));
				mDaysShown++;
			}
		}

		mDaysNextMonth = 1;
		while (mDaysShown % 7 != 0) {
			mItems.add(String.valueOf(mDaysNextMonth));
			mDaysShown++;
			mDaysNextMonth++;
		}

		mTitleHeight = (int) (getBarHeight() * 0.746);
		int rows = (mDaysShown / 7);
		/*
		 * mDayHeight = (mDisplayMetrics.heightPixels - mTitleHeight - (rows *
		 * 3) - getBarHeight()) / (rows - 1);
		 */
		mDayHeight = (int) (mTitleHeight * 2.8);

	}

	private int daysInMonth(int month) {
		int daysInMonth = mDaysInMonth[month];
		if (month == 1 && mCalendar.isLeapYear(mYear))
			daysInMonth++;
		return daysInMonth;
	}

	private int getBarHeight() {
		switch (mDisplayMetrics.densityDpi) {
		case DisplayMetrics.DENSITY_HIGH:
			return 30;
		case DisplayMetrics.DENSITY_MEDIUM:
			return 30;
		case DisplayMetrics.DENSITY_LOW:
			return 24;
		case DisplayMetrics.DENSITY_TV:
			return 30;
		default:
			return 42;
		}
	}

	/**
	 * For week starting Monday
	 * 
	 * @param day
	 *            from Calendar.DAY_OF_WEEK
	 * @return day week starting Monday
	 */
	private int getDay(int day) {
		switch (day) {
		case Calendar.SUNDAY:
			return 0;
		case Calendar.MONDAY:
			return 1;
		case Calendar.TUESDAY:
			return 2;
		case Calendar.WEDNESDAY:
			return 3;
		case Calendar.THURSDAY:
			return 4;
		case Calendar.FRIDAY:
			return 5;
		case Calendar.SATURDAY:
			return 6;

		default:
			return 0;
		}
	}

	

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CalendarCell cell;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.cell_label, null);
			cell = new CalendarCell();
			cell.tv = (CTTextView) convertView
					.findViewById(R.id.calendar_cell_tv);
			cell.tv.setText(mItems.get(position));
		} else {
			cell = (CalendarCell) convertView.getTag();
		}

		if (position <= 6) {
			// names
			cell.tv.setBackgroundColor(mContext.getResources().getColor(
					R.color.bg_light_gray));
			cell.tv.setHeight(mTitleHeight);
			cell.tv.setTextSize(12);
			cell.tv.setClickable(false);
			cell.tv.setClickable(false);
		} else if (position <= mDaysLastMonth + 6) {
			// previous month
			// cell.tv.setBackgroundColor(Color.rgb(234, 234, 245));

			cell.tv.setHeight((int) (mDayHeight / 1.5));
			cell.tv.setTextSize(12);
		} else if (position <= mDaysShown - mDaysNextMonth) {
			// current month
			cell.tv.setHeight((int) (mDayHeight / 1.5));
			int day = position - (mDaysLastMonth + 6);
			cell.tv.setTextSize(12);
			for (int i = 0; i < date.size(); i++) {
				if (date.get(i).get(Calendar.DAY_OF_MONTH) == day && date.get(i).get(Calendar.MONTH) == mMonth) {

					if (taskCount.get(i) > 0) {
						if (isClinic.get(i)) {
							ShapeDrawable circle = new ShapeDrawable(
									new OvalShape());
							int[] code = getColorCode(taskCount.get(i));
							circle.getPaint().setColor(
									Color.rgb(code[0], code[1], code[2]));
							circle.setBounds(1, 1, 1, 1);
							cell.tv.setBackgroundDrawable(circle);
							break;
						} else {
							int[] code = getColorCode(taskCount.get(i));
							cell.tv.setBackgroundColor(Color.rgb(code[0],
									code[1], code[2]));
							break;
						}
					}
				}
			}
		} else {
			// next month
			cell.tv.setHeight((int) (mDayHeight / 1.5));
			cell.tv.setTextSize(12);
			cell.tv.setTextColor(Color.rgb(141, 161, 191));
		}

		convertView.setTag(cell);
		return convertView;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Compliance getItem(int position) {
		if(position > 6){
			return getCompilance(Integer.parseInt(mItems.get(position)), position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private Compliance getCompilance(int item, int position) {
		for (int i = 0; i < compilance.size(); i++) {
			if (date.get(i).get(Calendar.DAY_OF_MONTH) == item) {
				return compilance.get(i);
			} else {
				if (position <= mDaysLastMonth + 6) {
					return compilance.set(i, new Compliance(0L, item + "/"
							+ (mMonth) + "/" + mYear, 0, false));
				} else {
					return compilance.set(i, new Compliance(0L, item
							+ "/" + (mMonth + 1) + "/" + mYear, 0, false));
				}
			}
		}
		return null;
	}

	private int[] getColorCode(int task) {

		int[] code = new int[3];

		for (int i = 0; i < rgbMinCode.length; i++) {
			if (rgbMinCode[i] - task > rgbMaxCode[i]) {

				code[i] = (rgbMinCode[i] - (task + i));
			} else {

				code[i] = rgbMaxCode[i];
			}
		}

		return code;
	}
}
