package gui;


import java.awt.EventQueue;

import javax.swing.JFrame;



import businessLogic.BLFacade;

import domain.RegisteredUser;
import exceptions.IncorrectLogin;

import javax.swing.JPasswordField;
import java.awt.Color;

import java.awt.GridBagLayout;


import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class LoginGUI extends JFrame {
	private JPasswordField passwordTextField;
	private JTextField usernameTextField;
	private JLabel lblIncorrectUser;
	
	private JFrame current;
	private JLabel notResiteredLabel;
	private JButton btnSignUp;
	
	private static final String ETIQUETAS = "Etiquetas";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		current = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{};
		gridBagLayout.rowWeights = new double[]{};
		getContentPane().setLayout(null);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(161, 107, 206, 20);
		getContentPane().add(passwordTextField);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(161, 52, 206, 20);
		getContentPane().add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel usernameLabel = new JLabel(ResourceBundle.getBundle(ETIQUETAS).getString("Username"));
		usernameLabel.setBounds(71, 55, 83, 14);
		getContentPane().add(usernameLabel);
		
		JLabel passwordLabel = new JLabel(ResourceBundle.getBundle(ETIQUETAS).getString("Password"));
		passwordLabel.setBounds(71, 110, 83, 14);
		getContentPane().add(passwordLabel);
		
		//Code for log in button
		JButton loginButton = new JButton(ResourceBundle.getBundle(ETIQUETAS).getString("Login"));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameTextField.getText();
				String password = new String(passwordTextField.getPassword());
				
				 BLFacade implementation = MainGUI.getBusinessLogic();
				 
				 try {				 
					 if (implementation.login(username, password) == 0) { 		//Depending on the result of the method, show AdminGUI or UserGUI
						 usernameTextField.setText("");
						 passwordTextField.setText("");
						 System.out.println(username + " logged in succesfully.");
						 
						 current.setVisible(false);
						 AdminGUI a = new AdminGUI();
						 a.setVisible(true);
						 
						 
					 } else {
						 usernameTextField.setText("");
						 passwordTextField.setText("");
						 System.out.println(username + " logged in succesfully.");
						 
						 current.setVisible(false);
						 
						 RegisteredUser u = implementation.getUserByUsername(username);
						 UserGUI a = new UserGUI(u);
						 a.setVisible(true);
						 
					 }
				 
				 } catch (IncorrectLogin e1) {
					 	lblIncorrectUser.setVisible(true);
						usernameTextField.setText("");
						passwordTextField.setText("");
					 
				 }

			}
		});
		loginButton.setBounds(153, 143, 133, 49);
		getContentPane().add(loginButton);
		
		notResiteredLabel = new JLabel(ResourceBundle.getBundle(ETIQUETAS).getString("NotRegistered"));
		notResiteredLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notResiteredLabel.setForeground(SystemColor.textHighlight);
		notResiteredLabel.setBounds(37, 218, 266, 14);
		getContentPane().add(notResiteredLabel);
		
		//Button to open RegisterGUI
		btnSignUp = new JButton(ResourceBundle.getBundle(ETIQUETAS).getString("SignUp"));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterGUI a = new RegisterGUI();
				current.setVisible(false);
				a.setVisible(true);
				
			}
		});
		btnSignUp.setBounds(277, 214, 133, 23);
		getContentPane().add(btnSignUp);
		
		lblIncorrectUser = new JLabel(ResourceBundle.getBundle(ETIQUETAS).getString("IncorrectLogin"));
		lblIncorrectUser.setForeground(Color.RED);
		lblIncorrectUser.setBounds(138, 25, 296, 14);
		getContentPane().add(lblIncorrectUser);

		lblIncorrectUser.setVisible(false);
		
	}
}
