package hu.elte.project.eszkozok.chat.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblLogin;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField tfUsername;
	private JPasswordField passwordField;
	private JButton btnRegister;
	private JButton btnLogin;

	LoginPanel() {
		setLayout(null);
		setSize(new Dimension(400, 400));
		initComponents();
	}

	private void initComponents() {

		lblLogin = new JLabel("Login");
		lblLogin.setBounds(176, 13, 87, 38);
		lblLogin.setFont(new Font("Arial", Font.BOLD, 32));
		add(lblLogin);

		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		lblUsername.setBounds(115, 64, 67, 16);
		add(lblUsername);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 13));
		lblPassword.setBounds(115, 111, 67, 16);
		add(lblPassword);

		tfUsername = new JTextField();
		tfUsername.setBounds(213, 61, 116, 22);
		tfUsername.setColumns(10);
		add(tfUsername);

		passwordField = new JPasswordField();
		passwordField.setBounds(213, 108, 116, 22);
		add(passwordField);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		btnLogin.setBounds(142, 140, 141, 25);
		add(btnLogin);

		btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Arial", Font.PLAIN, 13));
		btnRegister.setBounds(166, 178, 97, 25);
		add(btnRegister);

	}

	public void setLoginAction(ActionListener actionListener) {
		btnLogin.addActionListener(actionListener);
	}

	public void setRegisterAction(ActionListener actionListener) {
		btnRegister.addActionListener(actionListener);
	}
}
