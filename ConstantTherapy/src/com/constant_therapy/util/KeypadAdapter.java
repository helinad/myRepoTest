package com.constant_therapy.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.constant_therapy.constantTherapy.R;

public class KeypadAdapter extends BaseAdapter {
	private Context mContext;
	private OnClickListener onClickListener;

	public KeypadAdapter(Context c) {
		mContext = c;
	}

	// Method to set button click listener variable
	public void setOnButtonClickListener(OnClickListener listener) {
		this.onClickListener = listener;
	}

	public int getCount() {
		return mButtons.length;
	}

	public Object getItem(int position) {
		return mButtons[position];
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ButtonView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		Button btn;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes

			btn = new Button(mContext);
			KeypadButton keypadButton = mButtons[position];

			switch (keypadButton.mCategory) {

			case ONE:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case TWO:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case THREE:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case FOUR:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case FIVE:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case SIX:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case SEVEN:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case EIGHT:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case NINE:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case ZERO:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case DONE:
				btn.setBackgroundResource(R.drawable.keypad1);
				btn.setText(mButtons[position].getText());
				
				break;

			case CLEAR:
				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});

			default:
				btn.setBackgroundResource(R.drawable.keypad1);
				break;
			}
			// Set OnClickListener of the button to mOnButtonClick

			// Set CalculatorButton enumeration as tag of the button so that we
			// will use this information from our main view to identify what to
			// do
			btn.setTag(keypadButton);

			
		} else {
			btn = (Button) convertView;
		}

		btn.setText(mButtons[position].getText());
		btn.setTextColor(Color.parseColor("#1862BE"));
		btn.setTextSize(20);
		btn.setOnClickListener(onClickListener);
		return btn;
	}

	// Create and populate keypad buttons array with CalculatorButton enum
	// values
	private KeypadButton[] mButtons = { KeypadButton.SEVEN, KeypadButton.EIGHT,
			KeypadButton.NINE, KeypadButton.FOUR, KeypadButton.FIVE,
			KeypadButton.SIX, KeypadButton.ONE, KeypadButton.TWO,
			KeypadButton.THREE, KeypadButton.LESS, KeypadButton.ZERO,
			KeypadButton.DONE };

}
