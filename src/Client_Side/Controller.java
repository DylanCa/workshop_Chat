package Client_Side;

public class Controller implements ModelListener, ViewListener {

	private Model model;
	private GUI view;

	public Controller(Model model, GUI view) {
		this.model = model;
		this.view = view;
		
		view.addListener(this);
		model.addListener(this);
	}

	@Override
	public void onNicknameChanged(String newNickname) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageSent(String message) {
System.out.println(message);		
	}

	@Override
	public void onCypherMethodChanged(String method) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserConnected(String nickname, String ip_address, boolean newConnection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserDisconnected(String nickname, String ip_address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnMessageReceived(String nickname, String ip_address, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServerConnectionChanged(boolean isConnected) {
		// TODO Auto-generated method stub
		
	}

}
