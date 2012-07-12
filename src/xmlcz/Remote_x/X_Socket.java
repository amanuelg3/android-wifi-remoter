package xmlcz.Remote_x;

public interface X_Socket {
	
	public void PacketSetup(String ip,int port);
	
	public void Connect();
	
	public void Disconnect();
	
	public void sendData(String Data);
	
	public String receiveData();
	
	public String getAddr();
	
}
