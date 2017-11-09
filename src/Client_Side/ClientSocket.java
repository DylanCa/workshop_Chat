package Client_Side;

public class ClientSocket {

	private String clientName;

	public ClientSocket(String clientName) {
		this.clientName = clientName;
	}

	private void onMessageSent(String message) {
		// Captures sent message, encrypts & sends it to server
		encode(message);
	}

	private void onMessageReceived(String message) {
		// Event on Message Received from server
		decode(message);

		System.out.println(message);
	}

	private String encode(String message) {
		// Final ?
		String encodedMessage = null;

		// use Cypher to encode message
		return encodedMessage;
	}

	private String decode(String message) {
		// Final ?
		String decodedMessage = null;

		// use Cypher to decode message
		return decodedMessage;
	}
}
