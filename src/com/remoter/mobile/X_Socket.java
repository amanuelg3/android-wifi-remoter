package com.remoter.mobile;

public interface X_Socket {
	
	public void PacketSetup(String addr);
	
	public void connect();
	
	public void disconnect();
	
	public void sendData(String Data);
	
	public String receiveData();
	
	public String getAddr();
	
}
