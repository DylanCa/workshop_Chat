package Client_Side;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;
import java.util.Collection;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class Controller implements ModelListener, ViewListener, Runnable {

	private Model model;
	private GUI view;
	private Socket s;
	private PrintWriter out;
	private BufferedReader in;
	private Thread receivingThread;
	private HashMap<String, String> connectedClients;
	private KeyGenerator keyGen;
	private Key key;
	private Cipher cipher;

	public Controller(Model model, GUI view) {
		this.model = model;
		this.view = view;
		this.connectedClients = new HashMap<>();

		view.addListener(this);
		model.addListener(this);

		try {
			this.keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			key = keyGen.generateKey();

			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			s = new Socket("127.0.0.1", 500);
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));

		} catch (Exception e) {

			System.out.println("Impossible de se connecter");
		}

	}

	public void start() {

		this.receivingThread = new Thread(this);
		this.receivingThread.start();
	}

	@Override // New Thread that seeks for any input from Server
	public void run() {
		byte[] messageBytes;
		String messageDecrypted = null;

		while (true) {

			try {

				if (in.readLine() != null) {

					messageBytes = in.readLine().getBytes();
					cipher.init(Cipher.DECRYPT_MODE, key, cipher.getParameters());
					messageDecrypted = new String(cipher.doFinal(messageBytes));

					System.out.println("test"); 

					String[] parts = messageDecrypted.split(";");

					switch (parts[0]) {
					case "MSG":
						OnMessageReceived(parts[1], parts[2], parts[4]);
						break;
					case "NCK":
						onNicknameChangeReceived(parts[1], parts[2], parts[4]);
						break;
					case "CON":
						onUserConnected(parts[1], parts[2], parts[3]);
						break;
					case "DIS":
						onUserDisconnected(parts[1], parts[2], parts[3]);
						break;
					default:
						break;
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void onNicknameChanged(String nick) {

		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			out.println(cipher.doFinal(("NCK;" + nick).getBytes()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public void onMessageSent(String message) {

		out.println("MSG;" + message);
	}

	@Override
	public void onCypherMethodChanged(String method) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserConnected(String nickname, String time, String ip_address) {

		synchronized (this.connectedClients) {
			connectedClients.put(ip_address, nickname);

			view.clientArea.setText("");

			Collection<String> nicks = connectedClients.values(); // get all nicknames
			for (String nick : nicks) {
				view.clientArea.setText(view.clientArea.getText() + "\n" + nick);
			}
		}

		view.messageArea
				.setText(view.messageArea.getText() + "\n" + "[" + time + "] " + nickname + " is now connected.");

	}

	@Override
	public void onUserDisconnected(String nickname, String time, String ip_address) {

		synchronized (this.connectedClients) {
			connectedClients.remove(ip_address);

			view.clientArea.setText("");

			Collection<String> nicks = connectedClients.values(); // get all nicknames
			for (String nick : nicks) {
				view.clientArea.setText(view.clientArea.getText() + "\n" + nick);
			}
		}

		view.messageArea
				.setText(view.messageArea.getText() + "\n" + "[" + time + "] " + nickname + " is now disconnected.");
	}

	@Override
	public void OnMessageReceived(String nickname, String time, String message) {

		view.messageArea.setText(view.messageArea.getText() + "\n" + "[" + time + "] " + nickname + ": " + message);

	}

	@Override
	public void onNicknameChangeReceived(String oldNickname, String time, String newNickname) {

		view.messageArea.setText(view.messageArea.getText() + "\n" + "[" + time + "] " + oldNickname
				+ " is now known as " + newNickname + ".");
	}

	@Override
	public void onServerConnectionChanged(boolean isConnected) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClientAppExit() {
		out.println("BYE;");
		receivingThread.interrupt();
	}

}
