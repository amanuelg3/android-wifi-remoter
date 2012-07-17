package com.remoter.mobile;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Socket_UDP extends Thread implements X_Socket{
	
	private boolean DBG = true;
	
	private DatagramSocket mSocket_UDP;
	private DatagramPacket mPacket_UDP;
	private static int DATA_MAX_LENTH =128;
	private byte[] data = new byte[DATA_MAX_LENTH];
	
	private String targetIP_str;
	private InetAddress targetIP;
	private int targetPort;
	private String locateIP_str;
	private int locatePort;
	
	private String Socketopen = "open";
	private String Socketclose = "close";
 
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
		if(DBG) System.out.println("init socket.");
		connect();
	}
	
	public void PacketSetup(String ip,int port){ 
		try {
			targetIP = InetAddress.getByName(ip);
			targetPort = port;
			mPacket_UDP.setAddress(targetIP);
			mPacket_UDP.setPort(targetPort);
			if(DBG) System.out.println("Packet setup success.");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			if (DBG) System.out.println("Packet setup fail.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		//
		try {
			//get InetAddress by String.
			targetIP = InetAddress.getByName(targetIP_str);
			// Constructs a UDP datagram socket which is bound to the specific port aPort on the localhost. 
			mSocket_UDP = new DatagramSocket(locatePort); 
			if (DBG)
				System.out.println("mSocket created.");
			//Constructs a new DatagramPacket object to send data to the port aPort of the address host. 
			mPacket_UDP = new DatagramPacket(data,DATA_MAX_LENTH,targetIP, targetPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			if (DBG)
				System.out.println("mSocket create fail.");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Tell PC 
		this.sendData(Socketopen);
	}
	
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		destroy();
	}
	
	public void sendData(String Data){
		data = null;
		//translate String to byte array
		data = Data.getBytes();
		mPacket_UDP.setData(data);
		mPacket_UDP.setLength(data.length);
		try {
			mSocket_UDP.send(mPacket_UDP);
			if (DBG)
				System.out.println("mSocket send \""+ Data + "\" success.\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (DBG)
				System.out.println("mSocket send fail.");
			e.printStackTrace();
		}
	}
	
	public String receiveData(){
		String Data ="";
		byte[] datatmp = new byte[DATA_MAX_LENTH];
		DatagramPacket Packet = new DatagramPacket(datatmp,DATA_MAX_LENTH);
		try {
			mSocket_UDP.setSoTimeout(100);
			mSocket_UDP.receive(Packet);
			Data = new String(Packet.getData(),Packet.getOffset(),Packet.getLength());
			if(DBG)	System.out.println(Data+"<<");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(DBG) System.out.println("receiveData error.");
			e.printStackTrace();
		}
		return Data;
	}
	
	@Override
	public String getAddr(){
		String tmp = locateIP_str + ":" + String.valueOf(locatePort);
		if(DBG) System.out.println("addr: " + tmp);
		return tmp;
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
			System.out.println("mSocket close.");
	}

}
