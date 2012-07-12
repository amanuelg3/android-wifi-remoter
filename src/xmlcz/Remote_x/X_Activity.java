package xmlcz.Remote_x;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class X_Activity extends Activity {
	
	public static boolean DBG = true;
	
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
	
	private String AMsgOnReceiveBroadcast = "X_Activity receive Broadcast.";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏 
        
        mMenu = new X_Menu(X_Activity.this);

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
        }
        
        filter = new IntentFilter();
        filter.addAction(X_Menu.MENU_EXIT);
        filter.addAction(X_Menu.MENU_IPSETUP);
        filter.addAction(X_Menu.MENU_VIBRATE);
        mCmd = new MenuCmd();
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(!MenuCmdisRegister){
			MenuCmdisRegister = true;
			registerReceiver(mCmd, filter);
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
		if(DBG) System.out.println("destroy.");
		super.onDestroy();
	}

    
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		vibrate();
		super.finish();
		if(DBG) System.out.println("finish.");
	}

	public static void vibrate(){
		if(enadleVibrator) mVibrator.vibrate(30);
//		long[] i = {100,20};
//		if(enadleVibrator) mVibrator.vibrate(i, 10);
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
			if(DBG) System.out.println(AMsgOnReceiveBroadcast);
			
			if(intent.getAction().equals(X_Menu.MENU_EXIT)){
				if(DBG) System.out.println(X_Menu.MENU_EXIT);
				finish();
			}else if(intent.getAction().equals(X_Menu.MENU_IPSETUP)){
				if(DBG) System.out.println(X_Menu.MENU_IPSETUP);
				String ip = intent.getExtras().getString(X_Menu.MENU_IPSETUP_IP);
				int port = intent.getExtras().getInt(X_Menu.MENU_IPSETUP_PORT);
				mSocket.PacketSetup(ip, port);
			}else if(intent.getAction().equals(X_Menu.MENU_VIBRATE)){
				if(DBG) System.out.println(X_Menu.MENU_VIBRATE);
				if(enadleVibrator)enadleVibrator = false;
				else enadleVibrator = true;
			}
		}
	}
}