package com.remoter.mobile;

public interface X_Socket {
	
	public void PacketSetup(String addr); //数据包地址设置
	
	public void connect(); //链接
	
	public void disconnect(); //断开
	
	public void sendData(String Data); //发送数据
	
	public String receiveData(); //接收数据
	
	public String getAddr(); //获取本地地址
	
}
