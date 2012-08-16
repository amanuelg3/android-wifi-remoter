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
import android.view.Window;
import android.view.WindowManager;

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
	
	public static int Screen_x,Screen_y;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏 
        
        if(!isFirstOpen){
        	isFirstOpen = true;
        	//---------------------vibrate----------------------
        	mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        	enadleVibrator = true;
        	//---------------------socket----------------------
        	//---------------------target----------------------
        	targetIP_str = "192.168.0.104";
        	targetPort = 40001;
        	//---------------------locate---------------------
        	mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        	WifiInfo wifiinfo= mWifiManager.getConnectionInfo();
        	int i = wifiinfo.getIpAddress();
        	locateIP_str = (i & 0xFF)+ "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) +"."+((i >> 24 ) & 0xFF );
        	locatePort = 40002;
        	//---------------------init socket--------------------
        	mSocket = new Socket_UDP(targetIP_str,targetPort,locateIP_str,locatePort);
        	mSocket.start();
        	//---------------------screen size-------------------
        	getScreenSize();
        }
        
        mMenu = new X_Menu(X_Activity.this);
        mSensor = new X_Sensor((SensorManager) getSystemService(Context.SENSOR_SERVICE),mSocket);
        
        filter = new IntentFilter();
        filter.addAction(X_Menu.MENU_EXIT);
        filter.addAction(X_Menu.MENU_IPSETUP);
        filter.addAction(X_Menu.MENU_VIBRATE);
        filter.addAction(X_Menu.MENU_SOURCEMODE);
        mCmd = new MenuCmd();
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(!MenuCmdisRegister){
			MenuCmdisRegister = true;
			registerReceiver(mCmd, filter);
		}
		if(TheSourceMode == X_Menu.SensorMode){
			mSensor.Sensors_register();
		}
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if(MenuCmdisRegister){
			MenuCmdisRegister = false;
			unregisterReceiver(mCmd);
		}
		if(TheSourceMode == X_Menu.SensorMode){
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
		if(DBG) Log.i(TAG,"destroy.");
		super.onDestroy();
	}

    
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		vibrate();
		super.finish();
		if(DBG) Log.i(TAG,"finish.");
	}

	public static void vibrate(){
		if(enadleVibrator) mVibrator.vibrate(30);
//		long[] i = {100,20};
//		if(enadleVibrator) mVibrator.vibrate(i, 10);
	}

	public void getScreenSize(){
		 DisplayMetrics metrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
		 Screen_x = metrics.heightPixels;
		 Screen_y = metrics.widthPixels;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(mMenu.CreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(mMenu.OptionsItemSelected(item));
	}

	class MenuCmd extends BroadcastReceiver{
		Context mContext;
		public MenuCmd(){
		}
		public MenuCmd(Context context){
			super();
			mContext = context;
		}
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(DBG) Log.i(TAG,TheMsgAboutReceiveBroadcastis+": "+intent.getAction());
			
			if(intent.getAction().equals(X_Menu.MENU_EXIT)){
				finish();
			}else if(intent.getAction().equals(X_Menu.MENU_VIBRATE)){
				if(enadleVibrator)enadleVibrator = false;
				else enadleVibrator = true;
			}else if(intent.getAction().equals(X_Menu.MENU_IPSETUP)){
				String ip = intent.getExtras().getString(X_Menu.MENU_IPSETUP_IP);
				int port = intent.getExtras().getInt(X_Menu.MENU_IPSETUP_PORT);
				mSocket.PacketSetup(ip+":"+port);
			}else if(intent.getAction().equals(X_Menu.MENU_SOURCEMODE)){
				if(intent.getIntExtra(X_Menu.TheSourceMode, X_Menu.TouchMode) == X_Menu.TouchMode){
					if(DBG) Log.i(TAG,"touch");
					TheSourceMode = X_Menu.TouchMode;
					mSensor.Sensors_unregister();
				}else{
					if(DBG) Log.i(TAG,"sensor");
					TheSourceMode = X_Menu.SensorMode;
					mSensor.Sensors_register();
				}
			}
		}
	}
}