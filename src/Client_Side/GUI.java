package Client_Side;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField messageField;
	private JTextField txtBob;
	private List<String> connectedUsers;
	private List<ViewListener> listeners;

	/**
	 * Create the frame.
	 */

	public GUI() {

		this.connectedUsers = new ArrayList<>();
		this.listeners = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1402, 819);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel mainPanel = new JPanel();

		JPanel textPanel = new JPanel();
		textPanel.setBackground(Color.PINK);

		JPanel editPanel = new JPanel();
		editPanel.setBackground(Color.GRAY);

		JPanel clientPanel = new JPanel();
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_mainPanel.createSequentialGroup()
										.addComponent(textPanel, GroupLayout.DEFAULT_SIZE, 1138, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(clientPanel, GroupLayout.PREFERRED_SIZE, 205,
												GroupLayout.PREFERRED_SIZE)
										.addGap(11))
								.addGroup(gl_mainPanel.createSequentialGroup()
										.addComponent(editPanel, GroupLayout.DEFAULT_SIZE, 1350, Short.MAX_VALUE)
										.addContainerGap()))));
		gl_mainPanel.setVerticalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textPanel, GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
								.addComponent(clientPanel, GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE))
						.addGap(15).addComponent(editPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)));

		messageField = new JTextField();
		messageField.setColumns(10);

		messageField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = messageField.getText();
				notifyEvent("onMessageSent", message);
				messageField.setText("");
			}
		});

		GroupLayout gl_editPanel = new GroupLayout(editPanel);
		gl_editPanel.setHorizontalGroup(gl_editPanel.createParallelGroup(Alignment.LEADING).addComponent(messageField,
				GroupLayout.DEFAULT_SIZE, 1350, Short.MAX_VALUE));
		gl_editPanel.setVerticalGroup(gl_editPanel.createParallelGroup(Alignment.LEADING).addComponent(messageField,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE));
		editPanel.setLayout(gl_editPanel);

		JScrollPane scrollPane = new JScrollPane();

		JComboBox dropdownMenu = new JComboBox();

		txtBob = new JTextField();
		txtBob.setHorizontalAlignment(SwingConstants.CENTER);
		txtBob.setText("Bob");
		txtBob.setColumns(10);
		GroupLayout gl_clientPanel = new GroupLayout(clientPanel);
		gl_clientPanel.setHorizontalGroup(gl_clientPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_clientPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_clientPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtBob, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addComponent(dropdownMenu, 0, 195, Short.MAX_VALUE))));
		gl_clientPanel.setVerticalGroup(gl_clientPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_clientPanel.createSequentialGroup()
						.addComponent(txtBob, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 568, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE).addComponent(dropdownMenu,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		JTextArea clientArea = new JTextArea();
		clientArea.setEditable(false);
		scrollPane.setViewportView(clientArea);
		clientPanel.setLayout(gl_clientPanel);

		JScrollPane scrollPane_1 = new JScrollPane();

		JTextArea messageArea = new JTextArea();
		messageArea.setEditable(false);
		scrollPane_1.setViewportView(messageArea);
		GroupLayout gl_textPanel = new GroupLayout(textPanel);
		gl_textPanel.setHorizontalGroup(gl_textPanel.createParallelGroup(Alignment.LEADING).addComponent(scrollPane_1,
				GroupLayout.DEFAULT_SIZE, 1138, Short.MAX_VALUE));
		gl_textPanel.setVerticalGroup(gl_textPanel.createParallelGroup(Alignment.LEADING).addComponent(scrollPane_1,
				GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE));
		textPanel.setLayout(gl_textPanel);
		mainPanel.setLayout(gl_mainPanel);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(3)
						.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(3)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(mainPanel,
						GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

	public void addListener(ViewListener listener) {
		this.listeners.add(listener);

	}

	public void removeListener(ViewListener listener) {
		this.listeners.remove(listener);

	}

	public void notifyEvent(String methodName, Object... args) {

		Method methodCall = null;

		for (Method method : ViewListener.class.getMethods()) {
			if (methodName.equals(method.getName())) {
				methodCall = method;
				break;
			}
		}

		if (methodCall == null) {
			throw new IllegalArgumentException("Event " + methodName + " doesn't exist");

		}

		for (ViewListener listener : this.listeners) {
			try {
				methodCall.invoke(listener, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
