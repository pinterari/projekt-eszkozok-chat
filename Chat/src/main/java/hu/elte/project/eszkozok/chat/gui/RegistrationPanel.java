package hu.elte.project.eszkozok.chat.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistrationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel lblRegistration;
	private JLabel lblUsername;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblEmailAddress;
	private JLabel lblPassword;
	private JButton btnRegister;
	private JButton btnBack;
	private JTextField tfUsername;
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JTextField tfEmail;
	private JPasswordField passwordField;

	RegistrationPanel() {
		setLayout(null);
		setSize(new Dimension(400, 400));
		initComponents();
	}

	private void initComponents() {
		lblRegistration = new JLabel("Registration");
		lblRegistration.setBounds(90, 25, 220, 50);
		lblRegistration.setFont(new Font("Arial", Font.BOLD, 36));
		add(lblRegistration);

		lblUsername = new JLabel("Username");
		lblUsername.setBounds(65, 90, 100, 25);
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		add(lblUsername);

		lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(65, 130, 100, 25);
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 16));
		add(lblFirstName);

		lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(65, 170, 100, 25);
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 16));
		add(lblLastName);

		lblEmailAddress = new JLabel("Email Address");
		lblEmailAddress.setBounds(65, 210, 110, 25);
		lblEmailAddress.setFont(new Font("Arial", Font.PLAIN, 16));
		add(lblEmailAddress);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(65, 250, 100, 25);
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		add(lblPassword);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(100, 300, 200, 25);
		btnRegister.setFont(new Font("Arial", Font.PLAIN, 16));
		add(btnRegister);

		btnBack = new JButton("Back");
		btnBack.setBounds(150, 350, 100, 25);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 13));
		add(btnBack);

		tfUsername = new JTextField();
		tfUsername.setBounds(215, 90, 120, 25);
		tfUsername.setColumns(10);
		add(tfUsername);

		tfFirstName = new JTextField();
		tfFirstName.setBounds(215, 130, 120, 25);
		tfFirstName.setColumns(10);
		add(tfFirstName);

		tfLastName = new JTextField();
		tfLastName.setBounds(215, 170, 120, 25);
		tfLastName.setColumns(10);
		add(tfLastName);

		tfEmail = new JTextField();
		tfEmail.setBounds(215, 210, 120, 25);
		tfEmail.setColumns(10);
		add(tfEmail);

		passwordField = new JPasswordField();
		passwordField.setBounds(215, 250, 120, 25);
		add(passwordField);
	}

	public void setRegisterAction(ActionListener actionListener) {
		btnRegister.addActionListener(actionListener);
	}

	public void setBackAction(ActionListener actionListener) {
		btnBack.addActionListener(actionListener);
	}

}
