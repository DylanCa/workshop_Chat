package Server_Side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Thread thread;
	private Server server;
	private String nickname = "Anonymous";

	public Client(Socket socket, Server server) throws IOException {
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

				if (message == null) {
					
					close();
					return;
				}
				
				this.server.onClientMessageReceived(this, message);
				
			} catch (IOException e) {

				System.err.println("[Server][" + socket.getInetAddress() + "] Error while receiving message");
			}
		}
	}

	public boolean write(String message) {

		try {
			this.out.println(message);
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	private boolean close() {

		try {
			this.server.onClientDisconnected(this);
			this.thread.interrupt();
			this.in.close();
			this.out.close();
			this.socket.close();
			

			return true;

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public String getNickname() {
		return this.nickname ;
	}

}
