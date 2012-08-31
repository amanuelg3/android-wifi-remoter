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
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class Remote_x_Start extends X_Activity implements OnClickListener {

	private static boolean DBG = true;
	private static String TAG = "Remote_x_Start";

	protected MenuCmd mCmd; 
	private static String CheckSignal = "Hi,I am here.";   //用于回应PC的广播信号
	private Button kButton;        //切换到鼠标键盘模式的按钮
	private Button jButton;         //切换到手柄模式的按钮
	private ProgressBar initbar;  //初始化检测广播时显示的动作条
	boolean isStart = false;         //检测时用于对按键进行锁定，即检测中按键无反应
	
	 LinearLayout linearLayout; //admob
	 AdView adView ; //admob

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_x_start);
		vibrate();                                     //软件运行起来的一个震动提示
		new Pre_Start().execute();         //软件运行后的一个并行任务，用于检测广播信号
		kButton = (Button) findViewById(R.id.keyandmouse);   //绑定控件
		kButton.setOnClickListener(this);  //按钮绑定监听器
		jButton = (Button) findViewById(R.id.joystick);   //控件绑定
		jButton.setOnClickListener(this);  //按钮绑定监听器
		initbar = (ProgressBar) findViewById(R.id.initing);  //控件绑定
		 linearLayout = (LinearLayout)findViewById(R.id.linearlayout1);
		 //admob\
		 adView = new AdView(this, AdSize.BANNER, " a14fe4b3c976c93"); //admob
		 linearLayout.addView(adView); //admob
		 adView.loadAd(new AdRequest()); //admob
	}

	@Override
	public void onClick(View v) {  //切换模式按钮的监听器
		// TODO Auto-generated method stub
		if (isStart) {  //如果还在检测广播，则此时isStart是false,按下按钮则无效。
			vibrate(); //按下按钮的震动反馈
			Intent intent = new Intent();  
			if (v.getId() == R.id.keyandmouse) {  //根据按下的按钮 ID，判断是哪个按钮，然后启动相应的Activity。
				intent.setClass(Remote_x_Start.this, KeyAndMouse.class);
			} else if (v.getId() == R.id.joystick) {
				intent.setClass(Remote_x_Start.this, JoyStick.class);
			}
			startActivity(intent); //启动相应的Activity
		}
	}

	@Override
	protected void onDestroy() {  //程序关闭
		// TODO Auto-generated method stub
		mVibrator.cancel();  //取消掉震动，减少耗电
		if (isFirstOpen) { //全局变量的一个标志变量，限制变量启动次数
			isFirstOpen = false; 
			mSocket.disconnect(); //关闭全局Socket
		}
		 if (adView != null) { //admob
		 adView.destroy(); //admob
		 } //admob
		super.onDestroy();
	}

	class Pre_Start extends AsyncTask<Object, Boolean, Object> { //软件运行后的一个并行任务，用于检测广播信号

		@Override
		protected Object doInBackground(Object... arg0) {  
			// TODO Auto-generated method stub
			String tmp;
			int trytime = 2;
			while (!((tmp = mSocket.receiveData()).equals(CheckSignal))
					&& (trytime > 0))    //判断Socket接收到的数据是否与CheckSignal一样 
				trytime--;   //判断次数为2次

			if (!tmp.equals(CheckSignal)) {  
				publishProgress(false);  //如果接收到的数据与CheckSignal不一样，则向onProgressUpdate函数传递 false
			} else {  //反之则反
				if (DBG)
					Log.i(TAG, mSocket.getTargetAddr());
				mSocket.PacketSetup(mSocket.getTargetAddr()); //并且从数据包中提取数据源的IP和端口，用于设置手机Socket的数据包
				mSocket.sendData(CheckSignal); //然后回发检测信号
				publishProgress(true);//向onProgressUpdate函数传递 true
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Boolean... values) { 
			// TODO Auto-generated method stub
			if (!values[0].booleanValue()) //若从上面函数中得到的值为false，则显示链接失败的对话框
				showDialog();
			initbar.setVisibility(View.GONE); //不管怎么，取消动作条
			isStart = true; // 对按键进行解锁
			super.onProgressUpdate(values); 
		}
	}

	private void showDialog() { //链接失败的对话框
		AlertDialog.Builder b = new AlertDialog.Builder(Remote_x_Start.this);
		AlertDialog a = b
				.setMessage(
						Remote_x_Start.this.getResources().getString(
								R.string.checkconnection))
				.setPositiveButton( //忽略按钮
						Remote_x_Start.this.getResources().getString(
								R.string.ignore),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								//不做处理
							}
						})
				.setNegativeButton(   //关闭按钮
						Remote_x_Start.this.getResources().getString(
								R.string.close),  
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();   //结束整个程序
							}
						}).create();
		a.show(); //显示
	}
}
