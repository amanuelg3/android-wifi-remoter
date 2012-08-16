package com.remoter.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class Remote_x_Start extends X_Activity implements OnClickListener {

	private static boolean DBG = true;
	private static String TAG = "Remote_x_Start";

	protected MenuCmd mCmd;
	private static String CheckSignal = "Hi,I am here.";
	private Button kButton;
	private Button jButton;
	private ProgressBar initbar;
	boolean isStart = false;
	
	// LinearLayout linearLayout; //admob
	// AdView adView ; //admob

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_x_start);
		vibrate();
		new Pre_Start().execute();
		kButton = (Button) findViewById(R.id.keyandmouse);
		kButton.setOnClickListener(this);
		jButton = (Button) findViewById(R.id.joystick);
		jButton.setOnClickListener(this);
		initbar = (ProgressBar) findViewById(R.id.initing);
		// linearLayout = (LinearLayout)findViewById(R.id.linearlayout1);
		// //admob\
		// adView = new AdView(this, AdSize.BANNER, " a14fe4b3c976c93"); //admob
		// linearLayout.addView(adView); //admob
		// adView.loadAd(new AdRequest()); //admob
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (isStart) {
			vibrate();
			Intent intent = new Intent();
			if (v.getId() == R.id.keyandmouse) {
				intent.setClass(Remote_x_Start.this, KeyAndMouse.class);
			} else if (v.getId() == R.id.joystick) {
				intent.setClass(Remote_x_Start.this, JoyStick.class);
			}
			startActivity(intent);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mVibrator.cancel();
		if (isFirstOpen) {
			isFirstOpen = false;
			mSocket.disconnect();
		}
		// if (adView != null) { //admob
		// adView.destroy(); //admob
		// } //admob
		super.onDestroy();
	}

	class Pre_Start extends AsyncTask<Object, Boolean, Object> {

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			String tmp;
			int trytime = 2;
			while (!((tmp = mSocket.receiveData()).equals(CheckSignal))
					&& (trytime > 0))
				trytime--;

			if (!tmp.equals(CheckSignal)) {
				publishProgress(false);
			} else {
				if (DBG)
					Log.i(TAG, mSocket.getTargetAddr());
				mSocket.PacketSetup(mSocket.getTargetAddr());
				mSocket.sendData(CheckSignal);
				publishProgress(true);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {
			// TODO Auto-generated method stub
			if (!values[0].booleanValue())
				showDialog();
			    initbar.setVisibility(View.GONE);
			isStart = true;
			super.onProgressUpdate(values);
		}
	}

	private void showDialog() {
		AlertDialog.Builder b = new AlertDialog.Builder(Remote_x_Start.this);
		AlertDialog a = b
				.setMessage(
						Remote_x_Start.this.getResources().getString(
								R.string.checkconnection))
				.setPositiveButton(
						Remote_x_Start.this.getResources().getString(
								R.string.ignore),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						})
				.setNegativeButton(
						Remote_x_Start.this.getResources().getString(
								R.string.close),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						}).create();
		a.show();
	}
}
