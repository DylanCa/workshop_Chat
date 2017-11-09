package Client_Side;

public interface ModelListener {

	public void onUserConnected(String nickname, String ip_address, boolean newConnection);

	public void onUserDisconnected(String nickname, String ip_address);

	public void OnMessageReceived(String nickname, String ip_address, String message);
	
	public void onServerConnectionChanged(boolean isConnected);
}
