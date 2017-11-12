package Client_Side;

public interface ViewListener {

	public void onNicknameChanged(String newNickname);

	public void onMessageSent(String message);
	
	public void onCypherMethodChanged(String method);
	
	public void onClientAppExit();
	
}
