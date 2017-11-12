package workshop_Chat;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Client_Side.Controller;
import Client_Side.GUI;
import Client_Side.Model;
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
				
				ctrl.start();
				view.setVisible(true);
				view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
												
//				GUI view2 = new GUI();
//				Model model2 = new Model();
//				Controller ctrl2 = new Controller(model2, view2);
//				
//				ctrl2.start();
//				view2.setVisible(true);
//				view2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		});

	}

}