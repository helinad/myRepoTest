package com.constant_therapy.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;

public class ChoiceFragment extends Fragment implements OnClickListener {
	CTTextView choice;
	int id = 0;
	

	OnChoiceSelectedListener mCallback;

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnChoiceSelectedListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onChoiceSelected(View v, String tag);
	}

	public static ChoiceFragment newInstance(Boolean isCorrect, String choice) {
		ChoiceFragment fragmentDemo = new ChoiceFragment();
		Bundle args = new Bundle();
		args.putBoolean("isCorrect", isCorrect);
		args.putString("choice", choice);
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
		View v = inflater.inflate(R.layout.choice_frame, null, false);

		
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
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			updateArticleView(args.getString("choice"), args.getBoolean("isCorrect"));
		}
	}

	public void updateArticleView(String task, Boolean isBoolean) {
		
		choice = (CTTextView) getActivity().findViewById(R.id.tvChoice);
		choice.setOnClickListener(this);
		choice.setText(task);
		choice.setTag(isBoolean);
		choice.setId(id);
		DoingTaskActivity.viewList.add(choice);
		id++;

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
			mCallback = (OnChoiceSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mCallback.onChoiceSelected(v, "" + choice.getTag());
		if (v.getTag().toString().equalsIgnoreCase("false")) {
			
			if(DoingTaskActivity.viewList.size() == 2){
				for(int i = 0; i < DoingTaskActivity.viewList.size(); i++){
					if(DoingTaskActivity.viewList.get(i).getText().toString().equalsIgnoreCase(choice.getText().toString())){
						DoingTaskActivity.viewList.get(i).setBackgroundColor(getResources().getColor(R.color.wrong_choice_red));
					}else{
						DoingTaskActivity.viewList.get(i).setBackgroundColor(getResources().getColor(R.color.correct_choice_green));
					}
				}
			}else if(DoingTaskActivity.viewList.size() >= 3){
				for(int i = 0; i < DoingTaskActivity.viewList.size(); i++){
					if(DoingTaskActivity.viewList.get(i).getText().toString().equalsIgnoreCase(choice.getText().toString())){
						DoingTaskActivity.viewList.get(i).setBackgroundColor(getResources().getColor(R.color.wrong_choice_red));
					}else if(DoingTaskActivity.viewList.get(i).getTag().toString().equalsIgnoreCase("true")){
						DoingTaskActivity.viewList.get(i).setBackgroundColor(getResources().getColor(R.color.correct_choice_green));
					}else{
						DoingTaskActivity.viewList.get(i).setBackgroundDrawable(getResources().getDrawable(
								R.drawable.selector));
					}
				}
			}
		}
			
			
	}
}
