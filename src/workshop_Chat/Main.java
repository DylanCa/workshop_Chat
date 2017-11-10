package workshop_Chat;

import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Client_Side.*;
import Server_Side.Server;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Server srv = new Server(500);
		srv.start();

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				
				GUI view = new GUI();
				Model model = new Model();
				Controller ctrl = new Controller(model, view);
				
				view.setVisible(true);

			}
		});

	}

}