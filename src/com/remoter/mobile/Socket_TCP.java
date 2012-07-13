package com.remoter.mobile;


public class Socket_TCP extends Thread implements X_Socket{

//	private boolean DBG = true;
//	
//	private Socket mSocket_UDP;
//
//	
//	private String targetIP_str;
//	private InetAddress targetIP;
//	private int targetPort;
//	private String locateIP_str;
//	private int locatePort;
//	
//	private String Socketopen = "open";
//	private String Socketclose = "close";
 
	public Socket_TCP() {
		//-------------------default values-----------------------
//		targetIP_str = "192.168.1.5";
//		targetPort = 40001;
//		locateIP_str = "127.0.0.1";
//		locatePort = 40002;
	}

	public Socket_TCP(String targetip,int targetport,String locateip,int locateport) {
		super();
		//-------------------setup addr---------------------------
//		targetIP_str = targetip;
//		targetPort = targetport;
//		locateIP_str = locateip;
//		locatePort = locateport;
	}
	
	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	@Override
	public void PacketSetup(String ip, int port) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendData(String Data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String receiveData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

}
