package xmlcz.Remote_x;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Remote_x_Start extends X_Activity implements OnClickListener{

	protected MenuCmd mCmd;
	private String CheckOK = "checkconnection";
	private Button kButton;
	private Button jButton;
//	LinearLayout linearLayout;      //admob
//	AdView adView ;     //admob

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_x_start);
		vibrate();
		checkconnection();
		kButton = (Button)findViewById(R.id.keyandmouse);
		kButton.setOnClickListener(this);
		jButton = (Button)findViewById(R.id.joystick);
		jButton.setOnClickListener(this);
//		linearLayout = (LinearLayout)findViewById(R.id.linearlayout1);      //admob\
//		adView = new AdView(this, AdSize.BANNER, " a14fe4b3c976c93");     //admob
//		linearLayout.addView(adView);       //admob
//		adView.loadAd(new AdRequest());     //admob
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		vibrate();
		Intent intent = new Intent();
		if(v.getId()==R.id.keyandmouse){
			intent.setClass(Remote_x_Start.this, KeyAndMouse.class);
		}else if(v.getId()==R.id.joystick){
			intent.setClass(Remote_x_Start.this, JoyStick.class);
		}
		startActivity(intent);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(isFirstOpen){
			isFirstOpen = false;
			mSocket.destroy();
		}
//	    if (adView != null) {              //admob
//    	      adView.destroy();            //admob
//   	    }                                  //admob
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stubs
		super.onBackPressed();
		finish();
	}
	
	private void checkconnection() {
		mSocket.sendData(CheckOK);
		mSocket.sendData(mSocket.getAddr());
		if (!mSocket.receiveData().equals(CheckOK)){
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
}
