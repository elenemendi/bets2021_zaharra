package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.RegisteredUser;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class UserGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JButton btnQueryQuestions;
	private JButton btnLogOut;
	
	private JFrame current;
	private JButton btnBet;
	
	private RegisteredUser selectedUser = null;
	private JLabel lblMoney;
	private JButton btnRemoveBet;
	private JButton btnTakeOrInsert;
	private JButton queryMovements;
	private JButton btnTopUsers;
	private JButton btnReplicateUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserGUI frame = new UserGUI(new RegisteredUser());
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
	public UserGUI(RegisteredUser u) {
		current = this;
		
		this.selectedUser = u;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Menu"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 0, 597, 38);
		contentPane.add(lblNewLabel);
		
		//Code for first menu button
		btnQueryQuestions = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		btnQueryQuestions.setBounds(128, 69, 320, 38);
		contentPane.add(btnQueryQuestions);
		btnQueryQuestions.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				FindQuestionsGUI a = new FindQuestionsGUI();
				a.setVisible(true);
				
			}

		});
		//Code for second menu button
		btnLogOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		btnLogOut.setBounds(199, 406, 171, 37);
		contentPane.add(btnLogOut);
		btnLogOut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				LoginGUI a = new LoginGUI();
				current.setVisible(false);
				a.setVisible(true);
				
			}
			
			
		});
		
		btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		btnBet.setBounds(128, 119, 320, 38);
		contentPane.add(btnBet);
		btnBet.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CreateBetGUI a = new CreateBetGUI(selectedUser, current);
				a.setVisible(true);
				
			}
			
			
		});

		
		lblMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username")+": " + selectedUser.getUsername() + "    " + ResourceBundle.getBundle("Etiquetas").getString("Money")+ selectedUser.getMoney()+"€");
		lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoney.setBounds(12, 44, 597, 13);
		contentPane.add(lblMoney);
		
		btnRemoveBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RemoveBet"));
		btnRemoveBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveBetGUI a = new RemoveBetGUI(selectedUser, current);
				a.setVisible(true);
			}
		});
		btnRemoveBet.setBounds(128, 169, 320, 37);
		contentPane.add(btnRemoveBet);
		
		btnTakeOrInsert = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TakeInsert"));
		btnTakeOrInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertOrTakeMoneyGUI a = new InsertOrTakeMoneyGUI(selectedUser, current);
				a.setVisible(true);
			}
		});
		btnTakeOrInsert.setBounds(128, 218, 320, 37);
		contentPane.add(btnTakeOrInsert);
		
		queryMovements = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryMoves"));
		queryMovements.setBounds(128, 267, 320, 37);
		contentPane.add(queryMovements);
		queryMovements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowMovementsGUI a = new ShowMovementsGUI(selectedUser, current);
				a.setVisible(true);
				
				
			}
			
			
		});
		
		btnTopUsers = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TopUsers"));
		btnTopUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TopUsersGUI a = new TopUsersGUI(selectedUser, current);
				a.setVisible(true);
				
			}
		});
		btnTopUsers.setBounds(128, 316, 320, 38);
		contentPane.add(btnTopUsers);
		
		btnReplicateUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReplicateUser"));
		btnReplicateUser.setBounds(128, 364, 320, 37);
		btnReplicateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindUserGUI a = new FindUserGUI(selectedUser, current);
				a.setVisible(true);
				
			}
			
			
		});
		contentPane.add(btnReplicateUser);


	}
}
