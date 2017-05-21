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
		lblRegistration.setBounds(111, 3, 188, 38);
		lblRegistration.setFont(new Font("Arial", Font.BOLD, 32));
		add(lblRegistration);

		lblUsername = new JLabel("Username");
		lblUsername.setBounds(69, 54, 59, 15);
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 13));
		add(lblUsername);

		lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(69, 82, 64, 15);
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 13));
		add(lblFirstName);

		lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(69, 110, 63, 15);
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 13));
		add(lblLastName);

		lblEmailAddress = new JLabel("Email Address");
		lblEmailAddress.setBounds(69, 138, 85, 15);
		lblEmailAddress.setFont(new Font("Arial", Font.PLAIN, 13));
		add(lblEmailAddress);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(69, 166, 57, 15);
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 13));
		add(lblPassword);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(121, 197, 141, 25);
		btnRegister.setFont(new Font("Arial", Font.PLAIN, 16));
		add(btnRegister);

		btnBack = new JButton("Back");
		btnBack.setBounds(154, 235, 71, 23);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 13));
		add(btnBack);

		tfUsername = new JTextField();
		tfUsername.setBounds(225, 50, 116, 22);
		tfUsername.setColumns(10);
		add(tfUsername);

		tfFirstName = new JTextField();
		tfFirstName.setBounds(225, 78, 116, 22);
		tfFirstName.setColumns(10);
		add(tfFirstName);

		tfLastName = new JTextField();
		tfLastName.setBounds(226, 106, 116, 22);
		tfLastName.setColumns(10);
		add(tfLastName);

		tfEmail = new JTextField();
		tfEmail.setBounds(226, 134, 116, 22);
		tfEmail.setColumns(10);
		add(tfEmail);

		passwordField = new JPasswordField();
		passwordField.setBounds(226, 162, 116, 22);
		add(passwordField);
	}

	public void setRegisterAction(ActionListener actionListener) {
		btnRegister.addActionListener(actionListener);
	}

	public void setBackAction(ActionListener actionListener) {
		btnBack.addActionListener(actionListener);
	}

}
