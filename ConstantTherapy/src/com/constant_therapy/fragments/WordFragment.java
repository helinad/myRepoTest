package com.constant_therapy.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.constant_therapy.constantTherapy.DoingTaskActivity;
import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.Tasks;
import com.constant_therapy.widget.CTTextView;

public class WordFragment extends Fragment {
	public CTTextView choice;
	int id = 0;

	// The container Activity must implement this interface so the frag can
	// deliver messages

	public static WordFragment newInstance(String choice) {
		WordFragment fragmentDemo = new WordFragment();
		Bundle args = new Bundle();

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
		View v = inflater.inflate(R.layout.word_frame, null, false);

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

	public void updateArticleView(String task) {

		choice.setText(task);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		choice = (CTTextView) getActivity().findViewById(R.id.taskWord);
		choice.setTextColor(Color.WHITE);
		choice.setTextSize(TypedValue.COMPLEX_UNIT_PX, getActivity().getResources().getDimension(R.dimen.txt_fragment));
		
		DoingTaskActivity.tvChoice = choice;
		
		Bundle args = getArguments();
		if (args != null) {
			updateArticleView(args.getString("choice"));
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

	}

}
