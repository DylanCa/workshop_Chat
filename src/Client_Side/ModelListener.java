package Client_Side;

public interface ModelListener {

	public void onUserConnected(String nickname, String time, String ip_address);

	public void onUserDisconnected(String nickname, String time, String ip_address);

	public void OnMessageReceived(String nickname, String time, String message);

	public void onNicknameChangeReceived(String oldNickname, String time, String newNickname);

	public void onServerConnectionChanged(boolean isConnected);
}
