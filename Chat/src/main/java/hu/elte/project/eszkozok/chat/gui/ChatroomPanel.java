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
	private JButton btnAddUsers;
	private JButton btnSendMessage;
	private JTextPane textPaneMessages;
	private JList<?> listInChat;
	private JList<?> listOtherUsers;
	private JTextArea textAreaMessage;

	ChatroomPanel(String chatroomName) {
		setLayout(null);
		setSize(new Dimension(450, 450));
		lblChatroomName = new JLabel(chatroomName);
		initComponents();
	}

	private void initComponents() {
		lblChatroomName.setFont(new Font("Arial", Font.PLAIN, 32));
		lblChatroomName.setBounds(105, 13, 237, 47);
		add(lblChatroomName);

		lblUsersInChat = new JLabel("Users in chat");
		lblUsersInChat.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsersInChat.setBounds(334, 73, 82, 16);
		add(lblUsersInChat);

		textPaneMessages = new JTextPane();
		textPaneMessages.setFont(new Font("Arial", Font.PLAIN, 13));
		textPaneMessages.setBounds(12, 101, 234, 198);
		add(textPaneMessages);

		lblTypeYourMessage = new JLabel("Type your Message");
		lblTypeYourMessage.setFont(new Font("Arial", Font.PLAIN, 13));
		lblTypeYourMessage.setBounds(52, 316, 118, 16);
		add(lblTypeYourMessage);

		textAreaMessage = new JTextArea();
		textAreaMessage.setBounds(12, 343, 234, 47);
		add(textAreaMessage);

		btnSendMessage = new JButton("Send Message");
		btnSendMessage.setBounds(48, 395, 152, 25);
		add(btnSendMessage);

		lblChatMessages = new JLabel("Chat Messages");
		lblChatMessages.setFont(new Font("Arial", Font.PLAIN, 13));
		lblChatMessages.setBounds(52, 73, 104, 16);
		add(lblChatMessages);

		lblOtherUsers = new JLabel("Other Users");
		lblOtherUsers.setFont(new Font("Arial", Font.PLAIN, 13));
		lblOtherUsers.setBounds(339, 219, 82, 16);
		add(lblOtherUsers);

		listInChat = new JList<Object>();
		listInChat.setBounds(334, 102, 82, 104);
		add(listInChat);

		listOtherUsers = new JList<Object>();
		listOtherUsers.setBounds(339, 264, 82, 118);
		add(listOtherUsers);

		btnAddUsers = new JButton("Add Users");
		btnAddUsers.setBounds(334, 395, 97, 25);
		add(btnAddUsers);
	}

	public void setaddUserAction(ActionListener actionListener) {
		btnAddUsers.addActionListener(actionListener);
	}

	public void setSendMessageAction(ActionListener actionListener) {
		btnSendMessage.addActionListener(actionListener);
	}

}
