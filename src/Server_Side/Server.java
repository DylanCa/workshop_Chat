package Server_Side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
	private int port;
	private ServerSocket socket;
	private Thread acceptThread;
	private List<Client> connectedClients;

	public Server(int port) {
		if (port < 1 || port > 65535) {
			throw new IllegalArgumentException("Invalid port");
		}

		this.port = port;
	}

	public void start() throws IOException {
		this.socket = new ServerSocket(this.port);

		this.acceptThread = new Thread(this);
		this.acceptThread.start();

		this.connectedClients = new ArrayList<>();

	}

	public void onClientMessageReceived(Client c, String message) {

		if (message.length() < 3) {
			System.out.println("[ERROR] Invalid raw data");
			return;
		}

		String opcode = message.substring(0, 3);

		switch (opcode) {
		case "MSG;":

			System.out.println("[Server][" + c.getSocket().getInetAddress() + "] Received message: " + message);
			broadcastMessage(c, message);
			break;

		case "NCK;":
			c.setNickname(message.substring(4));
			System.out.println("Nickname changed: " + message.substring(4) );
			break;

		default:
			System.out.println("[ERROR] Invalid raw data");
			break;
		}
	}

	public void onClientDisconnected(Client c) {
		System.out.println("[Server][" + c.getSocket().getInetAddress() + "] is now disconnected.");

		synchronized (this.connectedClients) {
			this.connectedClients.remove(c);
		}
	}

	private void broadcastMessage(Client c, String message) {
		String data = "MSG;";
		data += c.getNickname();
		data += ";";
		data += (long) (System.currentTimeMillis() / 1000);
		data += ";";
		data += c.getSocket().getInetAddress();
		data += ";";
		data += message;
		broadcast(data);
	}

	private void broadcast(String data) {
		ArrayList<Client> copyConnectedClients;

		synchronized (this.connectedClients) {

			copyConnectedClients = new ArrayList<>(this.connectedClients);
		}
		for (Client client : copyConnectedClients) {
			client.write(data);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket s = socket.accept();

				System.out.println("Connection received from " + s.getInetAddress());

				Client c = new Client(s, this);

				c.startPollingThread();

				synchronized (this.connectedClients) {
					this.connectedClients.add(c);
				}

			} catch (IOException e) {
				System.out.println("Connection error");
				e.printStackTrace();
			}
		}
	}
}
