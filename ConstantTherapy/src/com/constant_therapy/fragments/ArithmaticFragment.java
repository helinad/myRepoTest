package com.constant_therapy.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class ArithmaticFragment extends Fragment implements OnClickListener{
	CTTextView firstNo, secondNo, tvSymbol, tvCheckAnswer;
	LinearLayout llCheckAnswer;
	ImageView imgWrong;
	CTTextView answer;
	int id = 0;

	onCheckAnswerClickListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface onCheckAnswerClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onCheckAnswerClick(View v, String inputAns);
    }
	// The container Activity must implement this interface so the frag can
	// deliver messages

	public static ArithmaticFragment newInstance(String choice, String symbol) {
		ArithmaticFragment fragmentDemo = new ArithmaticFragment();
		Bundle args = new Bundle();
		args.putString("symbol", symbol);
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
		View v = inflater.inflate(R.layout.addition_frame, null, false);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


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

	public void updateArithmaticView(String task, String symbol) {
		String[] numbers = task.split(",");
		firstNo.setText(numbers[0]);
		secondNo.setText(numbers[1]);
		tvSymbol.setText(symbol);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		firstNo = (CTTextView) getActivity().findViewById(R.id.firstNo);
		secondNo = (CTTextView) getActivity().findViewById(R.id.secondNo);
		tvSymbol = (CTTextView) getActivity().findViewById(R.id.symbol);
		answer = (CTTextView)getActivity().findViewById(R.id.answer);
		tvCheckAnswer = (CTTextView)getActivity().findViewById(R.id.checkAns);
		llCheckAnswer = (LinearLayout)getActivity().findViewById(R.id.llCheckAns);
		imgWrong = (ImageView)getActivity().findViewById(R.id.imgWrong);
		
		llCheckAnswer.setOnClickListener(this);
		llCheckAnswer.setTag("");
		answer.setOnClickListener(this);
		answer.setText("");
		answer.setTag("answer");
		answer.setTextColor(getResources().getColor(R.color.black));
		tvCheckAnswer.setText("");
		imgWrong.setVisibility(View.INVISIBLE);
		
		DoingTaskActivity.tvCheckAnswer = tvCheckAnswer;
		DoingTaskActivity.imgWrongSymbol = imgWrong;
		DoingTaskActivity.etAnswer = answer;
		
		Bundle args = getArguments();
		if (args != null) {
			updateArithmaticView(args.getString("text"), args.getString("symbol"));
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
            mCallback = (onCheckAnswerClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mCallback.onCheckAnswerClick(v, answer.getText().toString());
		
	}

}

