package hu.elte.project.eszkozok.chat.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class MenuPanel extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel mainPanel;
	private JLabel lblChatClient;
	private JLabel lblAvaibleChatrooms;
	private JLabel lblUsers;
	private JButton btnOpenChatroom;
	private JButton btnCreateChatroom;
	private JTable tableChatrooms;
	private JList<?> listUsers;

	MenuPanel() {
		initComponents();
	}

	private void initComponents() {
		mainPanel = new JPanel();
		mainPanel.setSize(new Dimension(400, 400));
		mainPanel.setLayout(null);

		lblChatClient = new JLabel("Chat Client");
		lblChatClient.setFont(new Font("Arial", Font.PLAIN, 32));
		lblChatClient.setBounds(139, 5, 171, 38);
		mainPanel.add(lblChatClient);

		lblAvaibleChatrooms = new JLabel("Avaible Chatrooms");
		lblAvaibleChatrooms.setFont(new Font("Arial", Font.PLAIN, 13));
		lblAvaibleChatrooms.setBounds(63, 72, 116, 16);
		mainPanel.add(lblAvaibleChatrooms);

		tableChatrooms = new JTable();
		tableChatrooms.setFont(new Font("Arial", Font.PLAIN, 13));
		tableChatrooms.setBounds(12, 102, 229, 155);
		mainPanel.add(tableChatrooms);

		btnOpenChatroom = new JButton("Open Chatroom");
		btnOpenChatroom.setFont(new Font("Arial", Font.PLAIN, 13));
		btnOpenChatroom.setBounds(63, 297, 148, 25);
		mainPanel.add(btnOpenChatroom);

		lblUsers = new JLabel("Users");
		lblUsers.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsers.setBounds(349, 72, 41, 16);
		mainPanel.add(lblUsers);

		listUsers = new JList<Object>();
		listUsers.setBounds(325, 102, 89, 155);
		mainPanel.add(listUsers);

		btnCreateChatroom = new JButton("Create Chatroom");
		btnCreateChatroom.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCreateChatroom.setBounds(303, 297, 137, 25);
		mainPanel.add(btnCreateChatroom);

		addTab("MainTab", mainPanel);
	}

	public void setOpenChatroomAction(ActionListener actionListener) {
		btnOpenChatroom.addActionListener(actionListener);
	}

	public void setCreateChatroomAction(ActionListener actionListener) {
		btnCreateChatroom.addActionListener(actionListener);
	}
}
