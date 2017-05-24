package hu.elte.project.eszkozok.chat.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
	private JScrollPane tableScroll;
	private JList<?> listUsers;

	MenuPanel() {
		fillTestData();
		initComponents();
	}

	private void fillTestData() {
		String[] columnNames = { "ID", "Chat name", "Participants" };
		Object[][] testDataTable = { { 1, "Csopika", 5 }, { 2, "Hurutkák", 7 }, { 3, "Harambe Fans", 3 },
				{ 4, "Dat Boiz", 4 }, };

		DefaultTableModel model = new DefaultTableModel(testDataTable, columnNames);
		tableChatrooms = new JTable(model);

		String[] testDataList = { "meszaros2008erno", "HUNtermester10", "AnimeLover69", "BestViktorEU" };

		listUsers = new JList<Object>(testDataList);

	}

	private void initComponents() {
		mainPanel = new JPanel();
		mainPanel.setSize(new Dimension(400, 400));
		mainPanel.setLayout(null);

		lblChatClient = new JLabel("Chat Client");
		lblChatClient.setFont(new Font("Arial", Font.BOLD, 36));
		lblChatClient.setBounds(160, 10, 200, 50);
		mainPanel.add(lblChatClient);

		lblAvaibleChatrooms = new JLabel("Avaible Chatrooms");
		lblAvaibleChatrooms.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAvaibleChatrooms.setBounds(90, 75, 150, 25);
		mainPanel.add(lblAvaibleChatrooms);

		tableChatrooms.setFont(new Font("Arial", Font.PLAIN, 16));
		tableChatrooms.setBounds(35, 110, 250, 150);
		tableScroll = new JScrollPane(tableChatrooms);
		tableScroll.setBounds(35, 110, 250, 150);
		mainPanel.add(tableScroll);

		btnOpenChatroom = new JButton("Open Chatroom");
		btnOpenChatroom.setFont(new Font("Arial", Font.PLAIN, 13));
		btnOpenChatroom.setBounds(90, 285, 150, 25);
		mainPanel.add(btnOpenChatroom);

		lblUsers = new JLabel("Users");
		lblUsers.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsers.setBounds(380, 75, 50, 25);
		mainPanel.add(lblUsers);

		listUsers.setBounds(340, 110, 125, 150);
		mainPanel.add(listUsers);

		btnCreateChatroom = new JButton("Create Chatroom");
		btnCreateChatroom.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCreateChatroom.setBounds(335, 285, 135, 25);
		mainPanel.add(btnCreateChatroom);

		addTab("MainTab", mainPanel);
	}

	public void setOpenChatroomAction(ActionListener actionListener) {
		btnOpenChatroom.addActionListener(actionListener);
	}

	public void setCreateChatroomAction(ActionListener actionListener) {
		btnCreateChatroom.addActionListener(actionListener);
	}

	public String getSelectedChatroomname() {
		try {
			return (String) tableChatrooms.getValueAt(tableChatrooms.getSelectedRow(), 1);
		} catch (ArrayIndexOutOfBoundsException ex) {
			return "";
		}
	}
}
