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
		lblLogin.setBounds(150, 50, 100, 50);
		lblLogin.setFont(new Font("Arial", Font.BOLD, 36));
		add(lblLogin);

		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUsername.setBounds(75, 140, 75, 25);
		add(lblUsername);

		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPassword.setBounds(75, 205, 75, 25);
		add(lblPassword);

		tfUsername = new JTextField();
		tfUsername.setBounds(205, 140, 120, 25);
		tfUsername.setColumns(10);
		add(tfUsername);

		passwordField = new JPasswordField();
		passwordField.setBounds(205, 205, 120, 25);
		add(passwordField);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		btnLogin.setBounds(100, 270, 200, 25);
		add(btnLogin);

		btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Arial", Font.PLAIN, 13));
		btnRegister.setBounds(150, 325, 100, 25);
		add(btnRegister);

	}

	public void setLoginAction(ActionListener actionListener) {
		btnLogin.addActionListener(actionListener);
	}

	public void setRegisterAction(ActionListener actionListener) {
		btnRegister.addActionListener(actionListener);
	}
}
