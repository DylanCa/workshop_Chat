package workshop_Chat;

import java.awt.EventQueue;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Client_Side.*;

public class Main {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
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