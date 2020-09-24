package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import exceptions.ExistingUsername;
import exceptions.InvalidDate;
import exceptions.InvalidEmail;
import exceptions.InvalidID;
import exceptions.NotMatchingPasswords;
import exceptions.Underage;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfSurname;
	private JTextField tfUsername;
	private JPasswordField tfPassword;
	private JPasswordField tfPassword2;
	private JTextField tfEmail;
	private JTextField tfBirthDate;
	private JTextField tfId;
	private JTextField tfCCard;

	private JLabel lblInvalidDate;
	private JLabel lblInvalidId;
	private JLabel lblPasswordsDo;
	private JLabel lblTheUsername;
	private JLabel lblYou;
	private JLabel lblInvalidEmail;
	private JLabel lblYouCan;
	
	private JFrame current;
	private JLabel lblNewLabel;
	private JLabel lblName;
	private JLabel lblSurname;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblRepeatPassword;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblIdNumber;
	private JLabel lblCreditCardNumber;
	private JButton btnRegister;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		current = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 690);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(0, 0, 0, 0);
		contentPane.add(lblNewLabel);

		lblName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Name"));
		lblName.setBounds(37, 31, 46, 14);
		contentPane.add(lblName);

		lblSurname = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surname"));
		lblSurname.setBounds(37, 95, 73, 14);
		contentPane.add(lblSurname);

		lblUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username")+":");
		lblUsername.setBounds(37, 156, 73, 14);
		contentPane.add(lblUsername);

		lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password")+":");
		lblPassword.setBounds(37, 221, 73, 14);
		contentPane.add(lblPassword);

		lblRepeatPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RPassword"));
		lblRepeatPassword.setBounds(37, 285, 115, 14);
		contentPane.add(lblRepeatPassword);

		lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("E-mail"));
		lblNewLabel_1.setBounds(37, 349, 46, 14);
		contentPane.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BirthDate"));
		lblNewLabel_2.setBounds(37, 408, 73, 14);
		contentPane.add(lblNewLabel_2);

		lblIdNumber = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IDnumber"));
		lblIdNumber.setBounds(37, 468, 73, 14);
		contentPane.add(lblIdNumber);

		lblCreditCardNumber = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CCNumber"));
		lblCreditCardNumber.setBounds(37, 533, 127, 14);
		contentPane.add(lblCreditCardNumber);

		btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Accept"));
		btnRegister.setBounds(164, 596, 108, 34);
		contentPane.add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Erabiltzailearen atributu bakoitza aldagai batean gorde, eta ondoren
				 * try-catch egiturarekin registerUser() metodoari deitu.
				 */
				lblInvalidDate.setVisible(false);
				lblInvalidId.setVisible(false);
				lblPasswordsDo.setVisible(false);
				lblTheUsername.setVisible(false);
				lblYou.setVisible(false);
				lblInvalidEmail.setVisible(false);
				lblInvalidId.setVisible(false);
				lblYouCan.setVisible(false);
				
				
				
				if( tfName.getText().equals("") || tfSurname.getText().equals("") || tfUsername.getText().equals("")||
						Arrays.toString(tfPassword.getPassword()).equals("") || Arrays.toString(tfPassword2.getPassword()).equals("") ||
						tfEmail.getText().equals("")|| tfBirthDate.getText().equals("")|| tfId.getText().equals("")||
						tfCCard.getText().equals("")) {
																			//Some of the fields are empty
					lblYouCan.setVisible(true);
					

				} else if(!tfBirthDate.getText().equals("")) {

					String[] date = tfBirthDate.getText().split("/");		//Inserted date is incorrect
					int lagDay =  Integer.parseInt(date[0]);
					int lagMonth = Integer.parseInt(date[1]);
					int lagYear= Integer.parseInt(date[2]);

					if (lagMonth == 1 || lagMonth == 3 || lagMonth == 5 || lagMonth == 7 || lagMonth == 8 || lagMonth == 10
							|| lagMonth == 11) {
						if (lagDay > 31 || lagDay <= 0) {
							lblInvalidDate.setVisible(true);
							
						}
					} else if (lagMonth == 2) {
						if (lagYear % 4 == 0) { // bisurtea
							if (lagDay > 29 || lagDay <= 0)
								lblInvalidDate.setVisible(true);
								
								
						} else {
							if (lagDay > 28 || lagDay <= 0)
								lblInvalidDate.setVisible(true);
								
							
						}
					} else {
						if (lagDay > 30 || lagDay <= 0)
							lblInvalidDate.setVisible(true);
							
						
					}

				 
				
				try {

					String name = tfName.getText();
					String surname = tfSurname.getText();
					String username = tfUsername.getText();
					String password = new String(tfPassword.getPassword());
					String password2 = new String(tfPassword2.getPassword());
					String email = tfEmail.getText();
					String birthDate = tfBirthDate.getText();
					String idNumber = tfId.getText();
					String cCard = tfCCard.getText();

					Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);

					BLFacade implementation = MainGUI.getBusinessLogic();

					implementation.registerUser(name, surname, idNumber, email, username, password, date2, "user", cCard,
							password2);
					
					current.setVisible(false);
					LoginGUI a = new LoginGUI();
					a.setVisible(true);
					

				} catch (NotMatchingPasswords e) {
					lblPasswordsDo.setVisible(true);		
					tfPassword.setText("");
					tfPassword2.setText("");

				} catch (ExistingUsername e) {
					lblTheUsername.setVisible(true);
					tfUsername.setText("");

				} catch (ParseException e) {
					lblInvalidDate.setVisible(true);
					tfBirthDate.setText("");

				} catch (Underage e) {
					lblYou.setVisible(true);
					tfBirthDate.setText("");

				} catch (InvalidEmail e) {
					lblInvalidEmail.setVisible(true);
					tfEmail.setText("");

				} catch (InvalidID e) {
					lblInvalidId.setVisible(true);
					tfId.setText("");

				} catch (NumberFormatException e) {
					lblInvalidId.setVisible(true);
					tfId.setText("");
					
				}
				
			}

		}
	});

		tfName = new JTextField();
		tfName.setBounds(164, 28, 166, 20);
		contentPane.add(tfName);
		tfName.setColumns(10);

		tfSurname = new JTextField();
		tfSurname.setBounds(164, 93, 166, 20);
		contentPane.add(tfSurname);
		tfSurname.setColumns(10);

		tfUsername = new JTextField();
		tfUsername.setBounds(163, 154, 167, 20);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);

		tfPassword = new JPasswordField();
		tfPassword.setBounds(164, 219, 167, 20);
		contentPane.add(tfPassword);

		tfPassword2 = new JPasswordField();
		tfPassword2.setBounds(162, 283, 167, 20);
		contentPane.add(tfPassword2);

		tfEmail = new JTextField();
		tfEmail.setBounds(164, 347, 166, 20);
		contentPane.add(tfEmail);
		tfEmail.setColumns(10);

		tfBirthDate = new JTextField();
		tfBirthDate.setText("dd/mm/yyyy");
		tfBirthDate.setBounds(163, 406, 167, 20);
		contentPane.add(tfBirthDate);
		tfBirthDate.setColumns(10);
		tfBirthDate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				tfBirthDate.setText("");
				
			}

		});

		tfId = new JTextField();
		tfId.setBounds(164, 466, 167, 20);
		contentPane.add(tfId);
		tfId.setColumns(10);

		tfCCard = new JTextField();
		tfCCard.setBounds(164, 531, 167, 20);
		contentPane.add(tfCCard);
		tfCCard.setColumns(10);


		lblPasswordsDo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NotMatchingPasswords"));
		lblPasswordsDo.setForeground(Color.RED);
		lblPasswordsDo.setBounds(186, 315, 208, 16);
		contentPane.add(lblPasswordsDo);

		lblInvalidId = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("InvalidID"));
		lblInvalidId.setForeground(Color.RED);
		lblInvalidId.setBounds(186, 498, 208, 16);
		contentPane.add(lblInvalidId);

		lblInvalidDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("WrongMoney"));
		lblInvalidDate.setForeground(Color.RED);
		lblInvalidDate.setBounds(186, 438, 144, 16);
		contentPane.add(lblInvalidDate);

		lblTheUsername = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ExistingUsername"));
		lblTheUsername.setForeground(Color.RED);
		lblTheUsername.setBounds(186, 178, 226, 16);
		contentPane.add(lblTheUsername);

		lblYou = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Underage"));
		lblYou.setForeground(Color.RED);
		lblYou.setBounds(186, 427, 175, 26);
		contentPane.add(lblYou);

		lblInvalidEmail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("InvalidEmail"));
		lblInvalidEmail.setForeground(Color.RED);
		lblInvalidEmail.setBounds(186, 368, 175, 16);
		contentPane.add(lblInvalidEmail);
		
		lblYouCan = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EmptyFields"));
		lblYouCan.setForeground(Color.RED);
		lblYouCan.setBounds(142, 567, 219, 16);
		contentPane.add(lblYouCan);

		lblInvalidDate.setVisible(false);
		lblInvalidId.setVisible(false);
		lblPasswordsDo.setVisible(false);
		lblTheUsername.setVisible(false);
		lblYou.setVisible(false);
		lblInvalidEmail.setVisible(false);
		lblYouCan.setVisible(false);
	}
}
