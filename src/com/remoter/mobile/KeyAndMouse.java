package com.remoter.mobile;

import xmlcz.Remote_x.R;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SlidingDrawer;

public class KeyAndMouse extends X_Activity {
	/** Called when the activity is first created. */
	private String KeyFlag = "k";

	public SlidingDrawer slidingdrawer;
	// keyboard line 1
	public Button Btn11 = null;
	public Button Btn12 = null;
	public Button Btn13 = null;
	public Button Btn14 = null;
	public Button Btn15 = null;
	public Button Btn16 = null;
	public Button Btn17 = null;
	public Button Btn18 = null;
	public Button Btn19 = null;
	public Button Btn110 = null;
	public Button Btn111 = null;
	public Button Btn112 = null;
	public Button Btn113 = null;
	public Button Btn114 = null;
	public Button Btn115 = null;
	// keyboard line 2
	public Button Btn21 = null;
	public Button Btn22 = null;
	public Button Btn23 = null;
	public Button Btn24 = null;
	public Button Btn25 = null;
	public Button Btn26 = null;
	public Button Btn27 = null;
	public Button Btn28 = null;
	public Button Btn29 = null;
	public Button Btn210 = null;
	public Button Btn211 = null;
	public Button Btn212 = null;
	public Button Btn213 = null;
	public Button Btn214 = null;
	public Button Btn215 = null;
	// keyboard line 3
	public Button Btn31 = null;
	public Button Btn32 = null;
	public Button Btn33 = null;
	public Button Btn34 = null;
	public Button Btn35 = null;
	public Button Btn36 = null;
	public Button Btn37 = null;
	public Button Btn38 = null;
	public Button Btn39 = null;
	public Button Btn310 = null;
	public Button Btn311 = null;
	public Button Btn312 = null;
	public Button Btn313 = null;
	public Button Btn314 = null;
	public Button Btn315 = null;
	// keyboard line 4
	public Button Btn41 = null;
	public Button Btn42 = null;
	public Button Btn43 = null;
	public Button Btn44 = null;
	public Button Btn45 = null;
	public Button Btn46 = null;
	public Button Btn47 = null;
	public Button Btn48 = null;
	public Button Btn49 = null;
	public Button Btn410 = null;
	public Button Btn411 = null;
	public Button Btn412 = null;
	public Button Btn413 = null;
	public Button Btn414 = null;
	// keyboard line 5
	public Button Btn51 = null;
	public Button Btn52 = null;
	public Button Btn53 = null;
	public Button Btn54 = null;
	public Button Btn55 = null;
	public Button Btn56 = null;
	public Button Btn57 = null;
	public Button Btn58 = null;
	public Button Btn59 = null;
	public Button Btn510 = null;
	public Button Btn511 = null;
	public Button Btn512 = null;
	public Button Btn513 = null;
	public Button Btn514 = null;
	// keyboard line 6
	public Button Btn61 = null;
	public Button Btn62 = null;
	public Button Btn63 = null;
	public Button Btn64 = null;
	public Button Btn65 = null;
	public Button Btn66 = null;
	public Button Btn67 = null;
	public Button Btn68 = null;
	public Button Btn69 = null;
	public Button Btn610 = null;
	public Button Btn611 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keyandmouse);
		// talk to PC that I am in keyandmouse mode.
		mSocket.sendData("keyandmouse");
		// init components
		slidingdrawer = (SlidingDrawer) findViewById(R.id.controler_switch);
		Button_init();

	}

	private void Button_init() {
		// keyboard line 1
		Btn11 = (Button) findViewById(R.id.Btn11);
		Btn11.setOnClickListener(new BtnOnClick());
		Btn12 = (Button) findViewById(R.id.Btn12);
		Btn12.setOnClickListener(new BtnOnClick());
		Btn13 = (Button) findViewById(R.id.Btn13);
		Btn13.setOnClickListener(new BtnOnClick());
		Btn14 = (Button) findViewById(R.id.Btn14);
		Btn14.setOnClickListener(new BtnOnClick());
		Btn15 = (Button) findViewById(R.id.Btn15);
		Btn15.setOnClickListener(new BtnOnClick());
		Btn16 = (Button) findViewById(R.id.Btn16);
		Btn16.setOnClickListener(new BtnOnClick());
		Btn17 = (Button) findViewById(R.id.Btn17);
		Btn17.setOnClickListener(new BtnOnClick());
		Btn18 = (Button) findViewById(R.id.Btn18);
		Btn18.setOnClickListener(new BtnOnClick());
		Btn19 = (Button) findViewById(R.id.Btn19);
		Btn19.setOnClickListener(new BtnOnClick());
		Btn110 = (Button) findViewById(R.id.Btn110);
		Btn110.setOnClickListener(new BtnOnClick());
		Btn111 = (Button) findViewById(R.id.Btn111);
		Btn111.setOnClickListener(new BtnOnClick());
		Btn112 = (Button) findViewById(R.id.Btn112);
		Btn112.setOnClickListener(new BtnOnClick());
		Btn113 = (Button) findViewById(R.id.Btn113);
		Btn113.setOnClickListener(new BtnOnClick());
		Btn114 = (Button) findViewById(R.id.Btn114);
		Btn114.setOnClickListener(new BtnOnClick());
		Btn115 = (Button) findViewById(R.id.Btn115);
		Btn115.setOnClickListener(new BtnOnClick());
		// keyboard line 2
		Btn21 = (Button) findViewById(R.id.Btn21);
		Btn21.setOnClickListener(new BtnOnClick());
		Btn22 = (Button) findViewById(R.id.Btn22);
		Btn22.setOnClickListener(new BtnOnClick());
		Btn23 = (Button) findViewById(R.id.Btn23);
		Btn23.setOnClickListener(new BtnOnClick());
		Btn24 = (Button) findViewById(R.id.Btn24);
		Btn24.setOnClickListener(new BtnOnClick());
		Btn25 = (Button) findViewById(R.id.Btn25);
		Btn25.setOnClickListener(new BtnOnClick());
		Btn26 = (Button) findViewById(R.id.Btn26);
		Btn26.setOnClickListener(new BtnOnClick());
		Btn27 = (Button) findViewById(R.id.Btn27);
		Btn27.setOnClickListener(new BtnOnClick());
		Btn28 = (Button) findViewById(R.id.Btn28);
		Btn28.setOnClickListener(new BtnOnClick());
		Btn29 = (Button) findViewById(R.id.Btn29);
		Btn29.setOnClickListener(new BtnOnClick());
		Btn210 = (Button) findViewById(R.id.Btn210);
		Btn210.setOnClickListener(new BtnOnClick());
		Btn211 = (Button) findViewById(R.id.Btn211);
		Btn211.setOnClickListener(new BtnOnClick());
		Btn212 = (Button) findViewById(R.id.Btn212);
		Btn212.setOnClickListener(new BtnOnClick());
		Btn213 = (Button) findViewById(R.id.Btn213);
		Btn213.setOnClickListener(new BtnOnClick());
		Btn214 = (Button) findViewById(R.id.Btn214);
		Btn214.setOnClickListener(new BtnOnClick());
		Btn215 = (Button) findViewById(R.id.Btn215);
		Btn215.setOnClickListener(new BtnOnClick());
		// keyboard line 3
		Btn31 = (Button) findViewById(R.id.Btn31);
		Btn31.setOnClickListener(new BtnOnClick());
		Btn32 = (Button) findViewById(R.id.Btn32);
		Btn32.setOnClickListener(new BtnOnClick());
		Btn33 = (Button) findViewById(R.id.Btn33);
		Btn33.setOnClickListener(new BtnOnClick());
		Btn34 = (Button) findViewById(R.id.Btn34);
		Btn34.setOnClickListener(new BtnOnClick());
		Btn35 = (Button) findViewById(R.id.Btn35);
		Btn35.setOnClickListener(new BtnOnClick());
		Btn36 = (Button) findViewById(R.id.Btn36);
		Btn36.setOnClickListener(new BtnOnClick());
		Btn37 = (Button) findViewById(R.id.Btn37);
		Btn37.setOnClickListener(new BtnOnClick());
		Btn38 = (Button) findViewById(R.id.Btn38);
		Btn38.setOnClickListener(new BtnOnClick());
		Btn39 = (Button) findViewById(R.id.Btn39);
		Btn39.setOnClickListener(new BtnOnClick());
		Btn310 = (Button) findViewById(R.id.Btn310);
		Btn310.setOnClickListener(new BtnOnClick());
		Btn311 = (Button) findViewById(R.id.Btn311);
		Btn311.setOnClickListener(new BtnOnClick());
		Btn312 = (Button) findViewById(R.id.Btn312);
		Btn312.setOnClickListener(new BtnOnClick());
		Btn313 = (Button) findViewById(R.id.Btn313);
		Btn313.setOnClickListener(new BtnOnClick());
		Btn314 = (Button) findViewById(R.id.Btn314);
		Btn314.setOnClickListener(new BtnOnClick());
		Btn315 = (Button) findViewById(R.id.Btn315);
		Btn315.setOnClickListener(new BtnOnClick());
		// keyboard line 4
		Btn41 = (Button) findViewById(R.id.Btn41);
		Btn41.setOnClickListener(new BtnOnClick());
		Btn42 = (Button) findViewById(R.id.Btn42);
		Btn42.setOnClickListener(new BtnOnClick());
		Btn43 = (Button) findViewById(R.id.Btn43);
		Btn43.setOnClickListener(new BtnOnClick());
		Btn44 = (Button) findViewById(R.id.Btn44);
		Btn44.setOnClickListener(new BtnOnClick());
		Btn45 = (Button) findViewById(R.id.Btn45);
		Btn45.setOnClickListener(new BtnOnClick());
		Btn46 = (Button) findViewById(R.id.Btn46);
		Btn46.setOnClickListener(new BtnOnClick());
		Btn47 = (Button) findViewById(R.id.Btn47);
		Btn47.setOnClickListener(new BtnOnClick());
		Btn48 = (Button) findViewById(R.id.Btn48);
		Btn48.setOnClickListener(new BtnOnClick());
		Btn49 = (Button) findViewById(R.id.Btn49);
		Btn49.setOnClickListener(new BtnOnClick());
		Btn410 = (Button) findViewById(R.id.Btn410);
		Btn410.setOnClickListener(new BtnOnClick());
		Btn411 = (Button) findViewById(R.id.Btn411);
		Btn411.setOnClickListener(new BtnOnClick());
		Btn412 = (Button) findViewById(R.id.Btn412);
		Btn412.setOnClickListener(new BtnOnClick());
		Btn413 = (Button) findViewById(R.id.Btn413);
		Btn413.setOnClickListener(new BtnOnClick());
		Btn414 = (Button) findViewById(R.id.Btn414);
		Btn414.setOnClickListener(new BtnOnClick());
		// keyboard line 5
		Btn51 = (Button) findViewById(R.id.Btn51);
		Btn51.setOnClickListener(new BtnOnClick());
		Btn52 = (Button) findViewById(R.id.Btn52);
		Btn52.setOnClickListener(new BtnOnClick());
		Btn53 = (Button) findViewById(R.id.Btn53);
		Btn53.setOnClickListener(new BtnOnClick());
		Btn54 = (Button) findViewById(R.id.Btn54);
		Btn54.setOnClickListener(new BtnOnClick());
		Btn55 = (Button) findViewById(R.id.Btn55);
		Btn55.setOnClickListener(new BtnOnClick());
		Btn56 = (Button) findViewById(R.id.Btn56);
		Btn56.setOnClickListener(new BtnOnClick());
		Btn57 = (Button) findViewById(R.id.Btn57);
		Btn57.setOnClickListener(new BtnOnClick());
		Btn58 = (Button) findViewById(R.id.Btn58);
		Btn58.setOnClickListener(new BtnOnClick());
		Btn59 = (Button) findViewById(R.id.Btn59);
		Btn59.setOnClickListener(new BtnOnClick());
		Btn510 = (Button) findViewById(R.id.Btn510);
		Btn510.setOnClickListener(new BtnOnClick());
		Btn511 = (Button) findViewById(R.id.Btn511);
		Btn511.setOnClickListener(new BtnOnClick());
		Btn512 = (Button) findViewById(R.id.Btn512);
		Btn512.setOnClickListener(new BtnOnClick());
		Btn513 = (Button) findViewById(R.id.Btn513);
		Btn513.setOnClickListener(new BtnOnClick());
		Btn514 = (Button) findViewById(R.id.Btn514);
		Btn514.setOnClickListener(new BtnOnClick());
		// keyboard line 6
		Btn61 = (Button) findViewById(R.id.Btn61);
		Btn61.setOnClickListener(new BtnOnClick());
		Btn62 = (Button) findViewById(R.id.Btn62);
		Btn62.setOnClickListener(new BtnOnClick());
		Btn63 = (Button) findViewById(R.id.Btn63);
		Btn63.setOnClickListener(new BtnOnClick());
		Btn64 = (Button) findViewById(R.id.Btn64);
		Btn64.setOnClickListener(new BtnOnClick());
		Btn65 = (Button) findViewById(R.id.Btn65);
		Btn65.setOnClickListener(new BtnOnClick());
		Btn66 = (Button) findViewById(R.id.Btn66);
		Btn66.setOnClickListener(new BtnOnClick());
		Btn67 = (Button) findViewById(R.id.Btn67);
		Btn67.setOnClickListener(new BtnOnClick());
		Btn68 = (Button) findViewById(R.id.Btn68);
		Btn68.setOnClickListener(new BtnOnClick());
		Btn69 = (Button) findViewById(R.id.Btn69);
		Btn69.setOnClickListener(new BtnOnClick());
		Btn610 = (Button) findViewById(R.id.Btn610);
		Btn610.setOnClickListener(new BtnOnClick());
		Btn611 = (Button) findViewById(R.id.Btn611);
		Btn611.setOnClickListener(new BtnOnClick());
	}

	//
	// public boolean onTouchEvent(MotionEvent event) {
	// // TODO Auto-generated method stub
	// command = "m"; //the mouse sign
	// if(mouseTchSw){
	// if(event.getAction() == MotionEvent.ACTION_DOWN) command += "d";
	// if(event.getAction() == MotionEvent.ACTION_UP) command += "u";
	// command += "x" + event.getX() + "y" + event.getY();
	// handler.post(send_r);
	// }
	// return super.onTouchEvent(event);
	// }

	class BtnOnClick implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			vibrate();
			Button btn = (Button) v;
			String cmd = KeyFlag + btn.getText().toString();
			mSocket.sendData(cmd);
		}
	}
}