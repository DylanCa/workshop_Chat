package Server_Side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class Client implements Runnable {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Thread thread;
	private Server server;
	private KeyGenerator keyGen;
	private Key key;
	private Cipher cipher;
	private String nickname = "Anonymous";

	public Client(Socket socket, Server server) throws IOException {
		try {

			this.keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			key = keyGen.generateKey();
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.socket = socket;
		this.server = server;

		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	}

	public Socket getSocket() {
		return socket;
	}

	public void startPollingThread() {
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {

		String message;

		while (true) {

			try {
				message = this.in.readLine();

				cipher.init(Cipher.DECRYPT_MODE, key, cipher.getParameters());

				if (message == null) {

					close();
					return;
				}
				this.server.onClientMessageReceived(this, message);

			} catch (Exception e) {

				System.err.println("[Server][" + socket.getInetAddress() + "] Error while receiving message");
			}
		}
	}

	public boolean write(String message) {

		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			this.out.println(cipher.doFinal(message.getBytes()));
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean close() {
		try {

			this.server.onClientDisconnected(this);
			this.thread.interrupt();
			this.socket.close();
			this.in.close();
			this.out.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

}
