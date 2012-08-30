package com.remoter.mobile;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class Socket_UDP extends Thread implements X_Socket{
	
	private boolean DBG = true;
	private String TAG = "Socket_UDP";
	
	private int SocketTimeout = 500;  //接收超时时间
	private DatagramSocket mSocket_UDP;
	private DatagramPacket mPacket_UDP;
	private DatagramPacket mPacket_UDP_R;
	private static int DATA_MAX_LENTH =128;  
	private byte[] data = new byte[DATA_MAX_LENTH];
	
	private String targetIP_str;
	private InetAddress targetIP;
	private int targetPort;
	private String locateIP_str;
	private int locatePort;
	
	private String Socketopen = "clientopen";  //Socket通路后的发送的一个信号
	private String Socketclose = "clientisclose.";  //Socket关闭后的发送的一个信号
 
	public Socket_UDP() {
		//-------------------default values-----------------------
		targetIP_str = "192.168.1.5";
		targetPort = 40001;
		locateIP_str = "127.0.0.1";
		locatePort = 40002;
	}

	public Socket_UDP(String targetip,int targetport,String locateip,int locateport) {
		super();
		//-------------------setup addr---------------------------
		targetIP_str = targetip;
		targetPort = targetport;
		locateIP_str = locateip;
		locatePort = locateport;
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
		if(DBG) Log.i(TAG,"init socket.");
		connect();
	}
	
	public void PacketSetup(String addr){ //实现该接口
		try {
			targetIP = InetAddress.getByName(addr.substring(0, addr.indexOf(':')));
			targetPort = Integer.valueOf(addr.substring(addr.indexOf(':') + 1,addr.length()));
			mPacket_UDP.setAddress(targetIP);
			mPacket_UDP.setPort(targetPort);
			if(DBG) Log.i(TAG,"Packet setup success.");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			if (DBG) Log.i(TAG,"Packet setup fail.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void connect() {//实现该接口
		// TODO Auto-generated method stub
		//
		try {
			//get InetAddress by String.
			targetIP = InetAddress.getByName(targetIP_str);
			// Constructs a UDP datagram socket which is bound to the specific port aPort on the localhost. 
			mSocket_UDP = new DatagramSocket(locatePort); 
			if (DBG)
				Log.i(TAG,"mSocket created.");
			//Constructs a new DatagramPacket object to send data to the port aPort of the address host. 
			mPacket_UDP = new DatagramPacket(data,DATA_MAX_LENTH,targetIP, targetPort);
			mPacket_UDP_R = new DatagramPacket(data, DATA_MAX_LENTH);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			if (DBG)
				Log.i(TAG,"mSocket create fail.");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Tell PC 
		this.sendData(Socketopen);
	}
	
	@Override
	public void disconnect() {//实现该接口
		// TODO Auto-generated method stub
		destroy();
	}
	
	public void sendData(String Data){//实现该接口
		data = null;
		//translate String to byte array
		data = Data.getBytes();
		mPacket_UDP.setData(data);//添加发送数据
		mPacket_UDP.setLength(data.length);//设置数据长度
		try {
			mSocket_UDP.send(mPacket_UDP);
			if (DBG)
				Log.i(TAG,"mSocket send \""+ Data + "\" success.\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (DBG)
				Log.i(TAG,"mSocket send fail.");//错误捕捉
			e.printStackTrace();
		}
	}
	
	public String receiveData(){//实现该接口：接收数据
		String Data =" ";
		try {
			mSocket_UDP.setSoTimeout(SocketTimeout);//设置接收超时时间
			mSocket_UDP.receive(mPacket_UDP_R);//开始监听locatePort端口
			
			Data = new String(mPacket_UDP_R.getData(),mPacket_UDP_R.getOffset(),mPacket_UDP_R.getLength());//将接收到的数据转化成string
			if(DBG)	Log.i(TAG,"receiveData: " + Data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(DBG) Log.i(TAG,"receiveData error.");
			e.printStackTrace();
		}
		return Data;
	}
	
	@Override
	public String getAddr(){//返回本地地址
		String tmp = locateIP_str + ":" + String.valueOf(locatePort);
		if(DBG) Log.i(TAG,"addr: " + tmp);
		return tmp;
	}
	
	public String getTargetAddr(){//通过接收到的数据包，获取目标IP和端口
		return mPacket_UDP_R.getAddress().toString().substring(1)+":"+mPacket_UDP_R.getPort();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	@Override
	public void destroy() { 
		// TODO Auto-generated method stub
		sendData(Socketclose);
		mSocket_UDP.close();
		if (DBG)
			Log.i(TAG,"mSocket close.");
	}

}
