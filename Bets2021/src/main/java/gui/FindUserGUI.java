package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.RegisteredUser;
import exceptions.EmptyField;
import exceptions.NotExistingUser;
import exceptions.SameUser;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;

public class FindUserGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsername;
	private JLabel lblText;
	private JLabel lblError;
	private JButton btnFind;
	
	private JFrame current;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindUserGUI frame = new FindUserGUI(new RegisteredUser(), new JFrame());
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
	public FindUserGUI(final RegisteredUser user, final JFrame frame) {
		this.current = this;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 533, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		btnFind = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Find"));
		btnFind.setBounds(150, 273, 220, 31);
		contentPane.add(btnFind);
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String username = tfUsername.getText();
				try {
					RegisteredUser replicatedUser = facade.getUserToReplicate(username, user.getUsername());
					ReplicateUserGUI a = new ReplicateUserGUI(user, replicatedUser, frame);
					a.setVisible(true);
					current.setVisible(false);
					
				} catch (EmptyField e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("PlsUsername"));
					lblError.setVisible(true);
					
				} catch (NotExistingUser e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("NotExistingUsername"));
					lblError.setVisible(true);
					
				} catch (SameUser e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("SameUser"));
					lblError.setVisible(true);
					
				}
				
			}
			
		});
		
		tfUsername = new JTextField();
		tfUsername.setBounds(121, 190, 264, 19);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		
		lblText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("InsertUsername"));
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		lblText.setBounds(12, 77, 505, 13);
		contentPane.add(lblText);
		
		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setBounds(12, 140, 497, 13);
		lblError.setVisible(false);
		contentPane.add(lblError);
	}
}
