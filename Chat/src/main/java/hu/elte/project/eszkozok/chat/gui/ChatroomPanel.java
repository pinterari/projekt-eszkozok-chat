package hu.elte.project.eszkozok.chat.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class ChatroomPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblChatroomName;
	private JLabel lblUsersInChat;
	private JLabel lblChatMessages;
	private JLabel lblOtherUsers;
	private JLabel lblTypeYourMessage;
	private JButton btnCloseChatroom;
	private JButton btnAddUsers;
	private JButton btnSendMessage;
	private JTextPane textPaneMessages;
	private JList<?> listInChat;
	private JList<?> listOtherUsers;
	private JTextArea textAreaMessage;

	ChatroomPanel(String chatroomName) {
		setLayout(null);
		setSize(new Dimension(500, 450));
		lblChatroomName = new JLabel(chatroomName);
		fillTestData();
		initComponents();
	}

	private void fillTestData() {
		String[] testDataInChat = { "meszaros2008erno", "HUNtermester10" };

		listInChat = new JList<Object>(testDataInChat);

		String[] testDataOtherUsers = { "AnimeLover69", "BestViktorEU" };

		listOtherUsers = new JList<Object>(testDataOtherUsers);

	}

	private void initComponents() {
		lblChatroomName.setFont(new Font("Arial", Font.BOLD, 36));
		lblChatroomName.setBounds(10, 10, 250, 50);
		add(lblChatroomName);

		btnCloseChatroom = new JButton("Close");
		btnCloseChatroom.setBounds(352, 17, 100, 25);
		add(btnCloseChatroom);

		lblChatMessages = new JLabel("Chat Messages");
		lblChatMessages.setFont(new Font("Arial", Font.PLAIN, 16));
		lblChatMessages.setBounds(130, 65, 150, 25);
		add(lblChatMessages);

		textPaneMessages = new JTextPane();
		textPaneMessages.setFont(new Font("Arial", Font.PLAIN, 16));
		textPaneMessages.setBounds(35, 100, 300, 200);
		add(textPaneMessages);

		lblUsersInChat = new JLabel("Users in chat");
		lblUsersInChat.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsersInChat.setBounds(355, 65, 100, 25);
		add(lblUsersInChat);

		listInChat.setBounds(340, 100, 125, 100);
		add(listInChat);

		lblTypeYourMessage = new JLabel("Type your Message");
		lblTypeYourMessage.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTypeYourMessage.setBounds(120, 310, 150, 25);
		add(lblTypeYourMessage);

		textAreaMessage = new JTextArea();
		textAreaMessage.setBounds(35, 345, 300, 50);
		add(textAreaMessage);

		btnSendMessage = new JButton("Send Message");
		btnSendMessage.setBounds(115, 405, 150, 25);
		add(btnSendMessage);

		lblOtherUsers = new JLabel("Other Users");
		lblOtherUsers.setFont(new Font("Arial", Font.PLAIN, 16));
		lblOtherUsers.setBounds(360, 260, 100, 25);
		add(lblOtherUsers);

		listOtherUsers.setBounds(340, 295, 125, 100);
		add(listOtherUsers);

		btnAddUsers = new JButton("Add Users");
		btnAddUsers.setBounds(352, 405, 100, 25);
		add(btnAddUsers);
	}

	public void setCloseChatroomAction(ActionListener actionListener) {
		btnCloseChatroom.addActionListener(actionListener);
	}

	public void setaddUserAction(ActionListener actionListener) {
		btnAddUsers.addActionListener(actionListener);
	}

	public void setSendMessageAction(ActionListener actionListener) {
		btnSendMessage.addActionListener(actionListener);
	}
}
