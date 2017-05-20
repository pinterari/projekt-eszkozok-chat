package hu.elte.project.eszkozok.chat.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class ChatFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_Username;
	private JPasswordField passwordField;
	private JTable table;

	public ChatFrame() {
		setTitle("Chat Application\r\n");
		setSize(new Dimension(500, 500));

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		final JPanel registerPanel = new JPanel();
		registerPanel.setVisible(false);
		final JTabbedPane mainMenu = new JTabbedPane();
		mainMenu.setVisible(false);
		JPanel mainPanel=new JPanel();
		mainPanel.setFont(new Font("Arial", Font.PLAIN, 13));
		mainMenu.addTab("MainTab", mainPanel);
		
		mainPanel.setLayout(null);
		
		JLabel lblChatClient = new JLabel("Chat Client\r\n");
		lblChatClient.setFont(new Font("Arial", Font.PLAIN, 32));
		lblChatClient.setBounds(139, 5, 171, 38);
		mainPanel.add(lblChatClient);
		
		JLabel lblAvaibleChatrooms = new JLabel("Avaible Chatrooms");
		lblAvaibleChatrooms.setFont(new Font("Arial", Font.PLAIN, 13));
		lblAvaibleChatrooms.setBounds(63, 72, 116, 16);
		mainPanel.add(lblAvaibleChatrooms);
		
		table = new JTable();
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.setBounds(12, 102, 229, 155);
		mainPanel.add(table);
		
		JButton btnOpenChatroom = new JButton("Open Chatroom");
		final JPanel panel = new JPanel();
		btnOpenChatroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO tobb chatablak megnyitasa funkcio
				panel.setFont(new Font("Arial", Font.PLAIN, 13));
				panel.setLayout(null);
				mainMenu.addTab("Chatroom", null, panel, null);
			}
		});
		btnOpenChatroom.setFont(new Font("Arial", Font.PLAIN, 13));
		btnOpenChatroom.setBounds(63, 297, 123, 25);
		mainPanel.add(btnOpenChatroom);
		
		JList<?> list = new JList<Object>();
		list.setBounds(325, 102, 89, 155);
		mainPanel.add(list);
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsers.setBounds(349, 72, 41, 16);
		mainPanel.add(lblUsers);
		
		JButton btnCreateChatroom = new JButton("Create Chatroom");
		btnCreateChatroom.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCreateChatroom.setBounds(303, 297, 137, 25);
		mainPanel.add(btnCreateChatroom);
		mainMenu.setBounds(0, 0, 482, 450);
		getContentPane().add(mainMenu);
		
		JLabel lblChatroomName = new JLabel("Chatroom Name");
		lblChatroomName.setFont(new Font("Arial", Font.PLAIN, 32));
		lblChatroomName.setBounds(105, 13, 237, 47);
		panel.add(lblChatroomName);
		
		JLabel lblUsersInChat = new JLabel("Users in chat");
		lblUsersInChat.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsersInChat.setBounds(334, 73, 82, 16);
		panel.add(lblUsersInChat);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textPane.setBounds(12, 101, 234, 198);
		panel.add(textPane);
		
		JLabel lblChatMessages = new JLabel("Chat Messages");
		lblChatMessages.setFont(new Font("Arial", Font.PLAIN, 13));
		lblChatMessages.setBounds(52, 73, 104, 16);
		panel.add(lblChatMessages);
		
		JList<?> list_1 = new JList<Object>();
		list_1.setBounds(334, 102, 82, 104);
		panel.add(list_1);
		
		JLabel lblOtherUsers = new JLabel("Other Users");
		lblOtherUsers.setFont(new Font("Arial", Font.PLAIN, 13));
		lblOtherUsers.setBounds(339, 219, 82, 16);
		panel.add(lblOtherUsers);
		
		JList<?> list_2 = new JList<Object>();
		list_2.setBounds(339, 264, 82, 118);
		panel.add(list_2);
		
		JButton btnAddUsers = new JButton("Add Users");
		btnAddUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO user hozzaadasa a chatszobahoz funkcio
			}
		});
		btnAddUsers.setBounds(334, 395, 97, 25);
		panel.add(btnAddUsers);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 343, 234, 47);
		panel.add(textArea);
		
		JLabel lblTypeYourMessage = new JLabel("Type your Message");
		lblTypeYourMessage.setFont(new Font("Arial", Font.PLAIN, 13));
		lblTypeYourMessage.setBounds(52, 316, 118, 16);
		panel.add(lblTypeYourMessage);
		
		JButton btnSendMessage = new JButton("Send Message");
		btnSendMessage.setBounds(48, 395, 152, 25);
		panel.add(btnSendMessage);
		registerPanel.setSize(400, 400);
		registerPanel.setLocation(43, 13);
		getContentPane().add(registerPanel);
		registerPanel.setLayout(null);
		registerPanel.setVisible(false);

		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setBounds(111, 3, 188, 38);
		registerPanel.add(lblRegistration);
		lblRegistration.setFont(new Font("Arial", Font.BOLD, 32));

		JLabel lblUsername_1 = new JLabel("Username");
		lblUsername_1.setBounds(69, 54, 59, 15);
		registerPanel.add(lblUsername_1);
		lblUsername_1.setFont(new Font("Arial", Font.PLAIN, 13));

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(69, 82, 64, 15);
		registerPanel.add(lblFirstName);
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 13));

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(69, 110, 63, 15);
		registerPanel.add(lblLastName);
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 13));

		JLabel lblEmailAddress = new JLabel("Email Address");
		lblEmailAddress.setBounds(69, 138, 85, 15);
		registerPanel.add(lblEmailAddress);
		lblEmailAddress.setFont(new Font("Arial", Font.PLAIN, 13));

		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(69, 166, 57, 15);
		registerPanel.add(lblPassword_1);
		lblPassword_1.setFont(new Font("Arial", Font.PLAIN, 13));

		JButton btnRegister_1 = new JButton("Register");
		btnRegister_1.setBounds(121, 197, 141, 25);
		registerPanel.add(btnRegister_1);
		btnRegister_1.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(154, 235, 71, 23);
		registerPanel.add(btnBack);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 13));

		JTextField textField_ = new JTextField();
		textField_.setBounds(225, 50, 116, 22);
		registerPanel.add(textField_);
		textField_.setColumns(10);

		JTextField textField = new JTextField();
		textField.setBounds(225, 78, 116, 22);
		registerPanel.add(textField);
		textField.setColumns(10);

		JTextField textField_1 = new JTextField();
		textField_1.setBounds(226, 106, 116, 22);
		registerPanel.add(textField_1);
		textField_1.setColumns(10);

		JTextField textField_2 = new JTextField();
		textField_2.setBounds(226, 134, 116, 22);
		registerPanel.add(textField_2);
		textField_2.setColumns(10);

		JPasswordField passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(226, 162, 116, 22);
		registerPanel.add(passwordField_1);
		final JPanel loginPanel = new JPanel();
		loginPanel.setBounds(23, 79, 432, 249);
		getContentPane().add(loginPanel);
		loginPanel.setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsername.setBounds(115, 64, 67, 16);
		loginPanel.add(lblUsername);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(176, 13, 87, 38);
		lblLogin.setFont(new Font("Arial", Font.BOLD, 32));
		loginPanel.add(lblLogin);

		textField_Username = new JTextField();
		textField_Username.setBounds(213, 61, 116, 22);
		loginPanel.add(textField_Username);
		textField_Username.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 13));
		lblPassword.setBounds(115, 111, 67, 16);
		loginPanel.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(213, 108, 116, 22);
		loginPanel.add(passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		btnLogin.setBounds(142, 140, 141, 25);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginPanel.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		loginPanel.add(btnLogin);

		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Arial", Font.PLAIN, 13));
		btnRegister.setBounds(166, 178, 97, 25);
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginPanel.setVisible(false);
				registerPanel.setVisible(true);
			}
		});
		loginPanel.add(btnRegister);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registerPanel.setVisible(false);
				loginPanel.setVisible(true);

			}
		});
		btnRegister_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO regisztrációs funkció
				registerPanel.setVisible(false);
				loginPanel.setVisible(true);

			}
		});
	}
}
