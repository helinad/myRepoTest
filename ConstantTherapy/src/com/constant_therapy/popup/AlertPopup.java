package com.constant_therapy.popup;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.widget.CTTextView;


public class AlertPopup {
	
	public static void showSaveToast(Activity context) {
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.save_task,(ViewGroup) context.findViewById(R.id.custom_toast_layout_id));
		
		ImageView imgStatus = (ImageView)layout.findViewById(R.id.imgStatus);
		CTTextView tvStatus = (CTTextView)layout.findViewById(R.id.tvStatus);
		
		
		imgStatus.setImageResource(R.drawable.checkmark);
		tvStatus.setText(context.getResources().getString(R.string.save_task));
		
		// Create Custom Toast
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
		
	}
	
	
	public static Toast showCorrectToast(Activity context) {
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.save_task,(ViewGroup) context.findViewById(R.id.custom_toast_layout_id));
		
		ImageView imgStatus = (ImageView)layout.findViewById(R.id.imgStatus);
		CTTextView tvStatus = (CTTextView)layout.findViewById(R.id.tvStatus);
		
		
		imgStatus.setImageResource(R.drawable.checkmark);
		tvStatus.setText(context.getResources().getString(R.string.correct));
		
		// Create Custom Toast
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(0);
		toast.setView(layout);
		toast.show();
		return toast;
		
	}
	
	
	
	public static Toast showWrongToast(Activity context) {
		LayoutInflater inflater = context.getLayoutInflater();
		View layout = inflater.inflate(R.layout.save_task,(ViewGroup) context.findViewById(R.id.custom_toast_layout_id));
		
		ImageView imgStatus = (ImageView)layout.findViewById(R.id.imgStatus);
		CTTextView tvStatus = (CTTextView)layout.findViewById(R.id.tvStatus);
		
		
		imgStatus.setImageResource(R.drawable.xmark);
		tvStatus.setText(context.getResources().getString(R.string.incorrect));
		
		// Create Custom Toast
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(0);
		toast.setView(layout);
		toast.show();
		
		return toast;
	}
	
	
	
	public static void showVersionAlert(Activity context, String title, String message) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog);
		dialog.setCancelable(true);
		CTTextView text = (CTTextView) dialog.findViewById(R.id.tv);
		text.setText(title);
		CTTextView mess = (CTTextView) dialog.findViewById(R.id.etsearch);
		if (message != null) {

			mess.setText(message);
			mess.setVisibility(View.VISIBLE);
		} else {
			mess.setVisibility(View.GONE);
		}
		Button dialogButton = (Button) dialog.findViewById(R.id.btncancel);
		// if button is clicked, close the custom dialog

		if (title.equalsIgnoreCase(context.getResources().getString(
				R.string.app_version))) {
			dialogButton.setText("Ok");
		} else {
			dialogButton.setText("Okay");
		}
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});

		dialog.show();

	}
}
