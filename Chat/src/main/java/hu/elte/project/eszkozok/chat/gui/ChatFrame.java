package hu.elte.project.eszkozok.chat.gui;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

public class ChatFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginPanel loginPanel;
	private RegistrationPanel registrationPanel;
	private MenuPanel menuPanel;

	public ChatFrame() {
		setLocation(200,200);
		setTitle("Chat Client\r\n");
		setSize(new Dimension(500, 500));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		initComponents();
	}

	private void initComponents() {
		loginPanel = new LoginPanel();
		registrationPanel = new RegistrationPanel();
		menuPanel = new MenuPanel();

		loginPanel.setVisible(true);
		registrationPanel.setVisible(false);
		menuPanel.setVisible(false);
		
		getContentPane().setLayout(null);
		loginPanel.setBounds(50, 0, 400, 400);
		registrationPanel.setBounds(50, 0, 400, 400);
		menuPanel.setBounds(0, 0, 500, 500);

		getContentPane().add(loginPanel);
		getContentPane().add(registrationPanel);
		getContentPane().add(menuPanel);

		loginPanel.setRegisterAction(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				loginPanel.setVisible(false);
				registrationPanel.setVisible(true);

			}
		});

		loginPanel.setLoginAction(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				loginPanel.setVisible(false);
				menuPanel.setVisible(true);

			}
		});

		registrationPanel.setRegisterAction(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO regisztráció
				registrationPanel.setVisible(false);
				loginPanel.setVisible(true);

			}
		});

		registrationPanel.setBackAction(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				registrationPanel.setVisible(false);
				loginPanel.setVisible(true);

			}
		});

		menuPanel.setOpenChatroomAction(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO letezo chat szoba megnyitasa
				menuPanel.addTab("Harambe fans", new ChatroomPanel("Harambe fans"));
			}
		});

		menuPanel.setCreateChatroomAction(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO uj chat szoba letrehozasa
			}
		});
	}
}
