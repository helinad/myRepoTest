package com.constant_therapy.constantTherapy;

import com.constant_therapy.user.CTUser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;

public class SplashActivity extends Activity {
	CountDown _tik;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		setContentView(R.layout.frontscreen);
		
		CTUser.getInstance().init(getApplicationContext());
		
		// let login screen decide what to do - it has full infrastructure for logins
		//_tik = new CountDown(1500, 1500, this, WizarDroidTutorialActivity.class);
		_tik = new CountDown(1500, 1500, this, LoginActivity.class);
		_tik.start();
	}
/*
 * Count down timer class with 1.5 sec delay for checking the availability of login.
 */
	public class CountDown extends CountDownTimer {
		private Activity _act;
		@SuppressWarnings("rawtypes")
		private Class _cls;
		
		@SuppressWarnings("rawtypes")
		public CountDown(long millisInFuture, long countDownInterval,
				Activity act, Class cls) {
			super(millisInFuture, countDownInterval);
			_act = act;
			_cls = cls;
		}

		@Override
		public void onFinish() {
			_act.startActivity(new Intent(_act, _cls));
			_act.finish();
		}

		@Override
		public void onTick(long millisUntilFinished) {

		}
	}

}
