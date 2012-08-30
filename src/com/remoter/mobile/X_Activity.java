package com.remoter.mobile;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class X_Activity extends Activity {

	private static boolean DBG = true;
	private static String TAG = "X_Activity";

	private X_Menu mMenu;

	private IntentFilter filter;
	private MenuCmd mCmd;
	private boolean MenuCmdisRegister = false;

	protected static boolean isFirstOpen = false;

	protected static Vibrator mVibrator;
	protected static boolean enadleVibrator = false;

	protected static Socket_UDP mSocket;
	protected static WifiManager mWifiManager;
	protected static String targetIP_str;
	protected static int targetPort;
	protected static String locateIP_str;
	protected static int locatePort;

	protected X_Sensor mSensor;
	private int TheSourceMode = X_Menu.TouchMode;
	private String TheMsgAboutReceiveBroadcastis = "X_Activity receive Broadcast";

	public static int Screen_x, Screen_y;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

		if (!isFirstOpen) { //全局变量的开关
			isFirstOpen = true;
			// ---------------------vibrate---------------------- //获取震动服务
			mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
			enadleVibrator = true;
			// ---------------------socket----------------------//初始化socket的值
			// ---------------------target----------------------
			targetIP_str = "192.168.0.104";
			targetPort = 40001;
			// ---------------------locate---------------------//获取本地ip，可以去除
			mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiinfo = mWifiManager.getConnectionInfo();
			int i = wifiinfo.getIpAddress();
			locateIP_str = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "."
					+ ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
			locatePort = 40002;
			// ---------------------init socket--------------------
			mSocket = new Socket_UDP(targetIP_str, targetPort, locateIP_str,
					locatePort);
			mSocket.start();
			// ---------------------screen size-------------------//获取手机屏幕尺寸，之后发给PC端
			getScreenSize(); 
		}

		mMenu = new X_Menu(X_Activity.this);
		mSensor = new X_Sensor(
				(SensorManager) getSystemService(Context.SENSOR_SERVICE),
				mSocket);

		filter = new IntentFilter(); //创建anroid广播过滤器
		filter.addAction(X_Menu.MENU_EXIT);
		filter.addAction(X_Menu.MENU_IPSETUP);
		filter.addAction(X_Menu.MENU_VIBRATE);
		filter.addAction(X_Menu.MENU_SOURCEMODE);
		mCmd = new MenuCmd(); //广播接收器
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (!MenuCmdisRegister) {
			MenuCmdisRegister = true;
			registerReceiver(mCmd, filter);
		}
		if (TheSourceMode == X_Menu.SensorMode) {
			mSensor.Sensors_register();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (MenuCmdisRegister) {
			MenuCmdisRegister = false;
			unregisterReceiver(mCmd);
		}
		if (TheSourceMode == X_Menu.SensorMode) { //如果现在是SensorMode，就是注销传感器
			mSensor.Sensors_unregister();
		}
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (DBG)
			Log.i(TAG, "destroy.");
		super.onDestroy();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		vibrate();
		super.finish();
		if (DBG)
			Log.i(TAG, "finish.");
	}

	public static void vibrate() {
		if (enadleVibrator) //整个程序震动接口的开关
			mVibrator.vibrate(30);  //震动的时间
		// long[] i = {100,20};
		// if(enadleVibrator) mVibrator.vibrate(i, 10);
	}

	public void getScreenSize() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Screen_x = metrics.widthPixels; //将得到的值保存在静态变量中
		Screen_y = metrics.heightPixels;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(mMenu.CreateOptionsMenu(menu));//通过X_menu创建菜单
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(mMenu.OptionsItemSelected(item));//通过X_menu对菜单进行响应
	}

	class MenuCmd extends BroadcastReceiver { //广播接收器
		Context mContext;

		public MenuCmd() {
		}

		public MenuCmd(Context context) {
			super();
			mContext = context;
		}

		@Override
		public void onReceive(Context context, Intent intent) {  //对广播进行响应，完成特定动作
			// TODO Auto-generated method stub
			if (DBG)
				Log.i(TAG,
						TheMsgAboutReceiveBroadcastis + ": "
								+ intent.getAction());

			if (intent.getAction().equals(X_Menu.MENU_EXIT)) {
				finish();
			} else if (intent.getAction().equals(X_Menu.MENU_VIBRATE)) { //开启或者关闭震动
				if (enadleVibrator)
					enadleVibrator = false;  
				else
					enadleVibrator = true;
			} else if (intent.getAction().equals(X_Menu.MENU_IPSETUP)) { //根据X_menu那边发过来的数据对地址进行设置
				String ip = intent.getExtras()
						.getString(X_Menu.MENU_IPSETUP_IP);
				int port = intent.getExtras().getInt(X_Menu.MENU_IPSETUP_PORT);
				mSocket.PacketSetup(ip + ":" + port);
			} else if (intent.getAction().equals(X_Menu.MENU_SOURCEMODE)) { // 选择触控作为控制源或者传感器作为控制源
				if (intent.getIntExtra(X_Menu.TheSourceMode, X_Menu.TouchMode) == X_Menu.TouchMode) {
					if (DBG)
						Log.i(TAG, "touch");
					TheSourceMode = X_Menu.TouchMode;//标志当前模式
					mSensor.Sensors_unregister();
					setContentView(R.layout.keyandmouse);//更改界面布局
				} else {
					if (DBG)
						Log.i(TAG, "sensor");
					TheSourceMode = X_Menu.SensorMode; //标志当前模式
					setContentView(R.layout.s_activity); //更改界面布局
					sensor_btns_init();
				}
			}
		}
		private void sensor_btns_init(){  //切换到传感器界面，并对按键进行初始化
			Button switcher = (Button) findViewById(R.id.switcher);  //按下按键时控制鼠标
			if (switcher != null) {
				switcher.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							mSensor.Sensors_register();
						} else if (event.getAction() == MotionEvent.ACTION_UP) {
							mSensor.Sensors_unregister();
						}
						return false;
					}
				});

				Button leftkey = (Button) findViewById(R.id.leftkey);
				leftkey.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mSensor.leftclick();
					}
				});

				Button midkey = (Button) findViewById(R.id.midkey);
				midkey.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mSensor.midclick();
					}
				});

				Button rightkey = (Button) findViewById(R.id.rightkey);
				rightkey.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mSensor.rightclick();
					}
				});
			}
		}
	}
}