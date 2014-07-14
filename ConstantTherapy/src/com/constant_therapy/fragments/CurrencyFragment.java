package com.constant_therapy.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.fragments.WordProblemFragment.onCheckWordProblemClickListener;
import com.constant_therapy.widget.CTTextView;
import com.constant_therapy.widget.CurlFlipLayout;
import com.constant_therapy.widget.DrawView;

public class CurrencyFragment extends Fragment implements OnClickListener{
	CTTextView firstNo, secondNo, tvSymbol, tvCheckAnswer;
	LinearLayout llCheckAnswer;
	ImageView imgWrong;
	CTTextView answerFirst, answerSecond;
	int id = 0;
	
	ImageView imgClose;

	DrawView drawView;
	CurlFlipLayout llDrawView;
	ViewGroup mRootView;

	onCheckCurrencyClickListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface onCheckCurrencyClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onCheckCurrencyClick(View v, String firstAns, String secondAns);
    }
	// The container Activity must implement this interface so the frag can
	// deliver messages

	public static CurrencyFragment newInstance(String choice, Boolean isNumber) {
		CurrencyFragment fragmentDemo = new CurrencyFragment();
		Bundle args = new Bundle();
		args.putBoolean("isNumber", isNumber);
		args.putString("text", choice);
		fragmentDemo.setArguments(args);
		return fragmentDemo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.

		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.currency_frame, null, false);

		return v;
	}

	@Override
	public void onStart() {
		super.onStart();

		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already
		// been
		// applied to the fragment at this point so we can safely call the
		// method
		// below that sets the article text.

	}

	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		answerFirst = (CTTextView)getActivity().findViewById(R.id.answer_first);
		answerSecond = (CTTextView)getActivity().findViewById(R.id.answer_second);
		tvCheckAnswer = (CTTextView)getActivity().findViewById(R.id.checkAns);
		llCheckAnswer = (LinearLayout)getActivity().findViewById(R.id.llCheckAns);
		imgWrong = (ImageView)getActivity().findViewById(R.id.imgWrong);
		
		llCheckAnswer.setOnClickListener(this);
		llCheckAnswer.setTag("");
		answerFirst.setText("");
		answerFirst.setTag("answerFirst");
		answerFirst.setTextColor(getResources().getColor(R.color.black));
		answerSecond.setText("");
		answerSecond.setTag("answerSecond");
		answerSecond.setTextColor(getResources().getColor(R.color.black));
		
		tvCheckAnswer.setText("");
		imgWrong.setVisibility(View.INVISIBLE);
		answerFirst.setOnClickListener(this);
		answerSecond.setOnClickListener(this);
		
		imgClose = (ImageView) getActivity().findViewById(R.id.imgClose);
		
		llDrawView = (CurlFlipLayout)getActivity().findViewById(R.id.llNotePad);
		
		drawView = new DrawView(getActivity());
		llDrawView.addView(drawView);
		drawView.changeColour(Color.BLACK);
		drawView.requestFocus();
		DoingTaskActivity.drawView = drawView;
		imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				llDrawView.next();
				drawView.clearPoints();
			}
		});
		
		DoingTaskActivity.tvCheckAnswer = tvCheckAnswer;
		DoingTaskActivity.imgWrongSymbol = imgWrong;
		DoingTaskActivity.etAnswerfirst = answerFirst;
		DoingTaskActivity.etAnswersecond = answerSecond;
		
		Bundle args = getArguments();
		if (args != null) {
			if(args.getBoolean("isNumber")){
				getActivity().findViewById(R.id.question).setVisibility(View.VISIBLE);
			}
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the current article selection in case we need to recreate the
		// fragment
		// outState.putInt(ARG_POSITION, mCurrentPosition);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
            mCallback = (onCheckCurrencyClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		mCallback.onCheckCurrencyClick(v, answerFirst.getText().toString(), answerSecond.getText().toString());
		
	}

}


