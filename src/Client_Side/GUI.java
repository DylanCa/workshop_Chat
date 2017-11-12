package Client_Side;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField messageField;
	private JTextField fieldNickname;
	private List<ViewListener> listeners;

	public JTextArea messageArea;
	public JTextArea clientArea;

	/**
	 * Create the frame.
	 */

	public GUI() {

		this.listeners = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 817, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel mainPanel = new JPanel();

		JPanel textPanel = new JPanel();
		textPanel.setBackground(Color.PINK);

		JPanel clientPanel = new JPanel();

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
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textPanel, GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
								.addComponent(messageField, GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(clientPanel, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
						.addGap(17)));
		gl_mainPanel.setVerticalGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(clientPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_mainPanel.createSequentialGroup()
										.addComponent(textPanel, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(messageField,
												GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
						.addGap(9)));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				notifyEvent("onClientAppExit");
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		JComboBox dropdownMenu = new JComboBox();

		fieldNickname = new JTextField();
		fieldNickname.setFont(new Font("Tahoma", Font.PLAIN, 17));
		fieldNickname.setHorizontalAlignment(SwingConstants.CENTER);
		fieldNickname.setText("Anonymous");
		fieldNickname.setColumns(10);

		fieldNickname.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nick = fieldNickname.getText();
				notifyEvent("onNicknameChanged", nick);
				messageField.grabFocus();
			}
		});

		GroupLayout gl_clientPanel = new GroupLayout(clientPanel);
		gl_clientPanel.setHorizontalGroup(gl_clientPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_clientPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_clientPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addComponent(fieldNickname, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
								.addComponent(dropdownMenu, Alignment.TRAILING, 0, 195, Short.MAX_VALUE))));
		gl_clientPanel.setVerticalGroup(gl_clientPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_clientPanel.createSequentialGroup()
						.addComponent(fieldNickname, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(dropdownMenu,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		clientArea = new JTextArea();
		clientArea.setEditable(false);
		scrollPane.setViewportView(clientArea);
		clientPanel.setLayout(gl_clientPanel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		messageArea = new JTextArea();
		messageArea.setLineWrap(true);
		messageArea.setEditable(false);

		DefaultCaret caret = (DefaultCaret) messageArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

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
				.addGroup(gl_contentPane.createSequentialGroup().addGap(5)
						.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 398, Short.MAX_VALUE).addContainerGap()));
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
