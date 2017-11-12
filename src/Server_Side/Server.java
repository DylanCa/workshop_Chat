package Server_Side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server implements Runnable {
	private int port;
	private ServerSocket socket;
	private Thread acceptThread;
	private LogWriter logwriter;
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

		this.logwriter = new LogWriter();
		this.connectedClients = new ArrayList<>();

	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket s = socket.accept();

				Client c = new Client(s, this);

				c.startPollingThread();

				synchronized (this.connectedClients) {
					this.connectedClients.add(c);
				}

				broadcastNewClient(c);

			} catch (IOException e) {
				System.out.println("Connection error");
				e.printStackTrace();
			}
		}
	}

	public void onClientMessageReceived(Client c, String message) {

		if (message.length() < 3) {
			System.out.println("[ERROR] Invalid raw data");
			return;
		}

		String opcode = message.substring(0, 4);

		switch (opcode) {
		case "MSG;":

			broadcastMessage(c, message.substring(4));
			break;

		case "NCK;":

			broadcastNickname(c, message.substring(4));
			c.setNickname(message.substring(4));
			break;

		case "BYE;":
			c.close();
			break;

		default:
			System.out.println("[ERROR] Invalid raw data");
			break;
		}
	}

	public void onClientDisconnected(Client c) {

		synchronized (this.connectedClients) {
			this.connectedClients.remove(c);
		}

		broadcastClientDisconnected(c);
	}

	private void broadcastMessage(Client c, String message) {
		String data = "MSG;";
		data += c.getNickname();
		data += ";";
		data += new Date(System.currentTimeMillis());
		data += ";";
		data += c.getSocket().getInetAddress();
		data += ";";
		data += message;

		broadcast(data);

		logwriter.setMessageToWrite(data);
	}

	private void broadcastNickname(Client c, String nick) {
		String data = "NCK;";
		data += c.getNickname();
		data += ";";
		data += new Date(System.currentTimeMillis());
		data += ";";
		data += c.getSocket().getInetAddress();
		data += ";";
		data += nick;

		broadcast(data);

		logwriter.setMessageToWrite(data);
	}

	private void broadcastNewClient(Client c) {
		String data = "CON;";
		data += c.getNickname();
		data += ";";
		data += new Date(System.currentTimeMillis());
		data += ";";
		data += c.getSocket().getInetAddress();

		broadcast(data);

		logwriter.setMessageToWrite(data);

	}

	private void broadcastClientDisconnected(Client c) {
		String data = "DIS;";
		data += c.getNickname();
		data += ";";
		data += new Date(System.currentTimeMillis());
		data += ";";
		data += c.getSocket().getInetAddress();

		broadcast(data);

		logwriter.setMessageToWrite(data);

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

}
