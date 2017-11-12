package Server_Side;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogWriter {

	private Date date = new Date();
	private BufferedWriter writer;

	public LogWriter() {
		try {

			writer = new BufferedWriter(new FileWriter("./Logs/logs.txt", true));
			writer.write("\n\n **** New Logging session ( " + date + " ) initialized ****\n\n");
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMessageToWrite(String message) {

		String[] parts = message.split(";");

		try {

			writer = new BufferedWriter(new FileWriter("./Logs/logs.txt", true));

			switch (parts[0]) {
			case "MSG":

				writer.write("[" + parts[3] + "] [" + parts[2] + "] [" + parts[1] + "]: " + parts[4] + "\n");
				break;

			case "NCK":

				writer.write(
						"[" + parts[3] + "] [ " + parts[2] + "] " + parts[1] + " is now known as " + parts[4] + ".\n");
				break;

			case "CON":

				writer.write("[" + parts[3] + "] [ " + parts[2] + "] " + parts[1] + " is now connected.\n");
				break;

			case "DIS":

				writer.write("[" + parts[3] + "] [ " + parts[2] + "] " + parts[1] + " is now disconnected.\n");
				break;

			default:
				break;
			}

			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
