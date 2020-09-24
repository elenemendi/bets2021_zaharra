package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bet;
import domain.RegisteredUser;
import exceptions.InsufficientMoney;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ReplicateUserGUI extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	
	private DefaultTableModel tableModelBets;
	private JTable tableBets;
	private String[] topInfo = new String[] {ResourceBundle.getBundle("Etiquetas").getString("BetN"),ResourceBundle.getBundle("ETiquetas").getString("Description"), ResourceBundle.getBundle("Etiquetas").getString("Money2")};
	private JLabel lblUser;
	private JLabel lblUserMoney;
	private JLabel lblReplicatedUser;
	private JLabel lblReplicatedBetsMoney;
	private JButton btnReplicate;
	private double betMoney;
	private Vector<Bet> bets;
	
	private JFrame current;
	private JLabel lblError;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReplicateUserGUI frame = new ReplicateUserGUI(new RegisteredUser(), new RegisteredUser(), new JFrame());
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
	public ReplicateUserGUI(final RegisteredUser user, RegisteredUser replicatedUser, final JFrame frame) {
		this.current = this;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 905, 643);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 101, 694, 401);
		contentPane.add(scrollPane);
		
		tableBets = new JTable();
		tableModelBets = new DefaultTableModel(null, topInfo);
		
		tableModelBets.setDataVector(null, topInfo);
		tableModelBets.setColumnCount(3);
		
		tableBets.setModel(tableModelBets);
		tableBets.setBackground(Color.WHITE);
		
		scrollPane.setViewportView(tableBets);
		
		if (user.getBets() == null) {
			user.setBets(new Vector<Bet>());
			
		}
		
		if (user.getMovements() == null) {
			user.setMovements(new Vector<domain.Movement>());
			
		}
		
		
		
		if (replicatedUser.getBets() == null) {
			replicatedUser.setBets(new Vector<Bet>());
			
		}
		
		bets = replicatedUser.getBets();
		
		System.out.println("The replicated user has the following bets: " + bets);
		
		betMoney = 0.0;
		
		for (Bet b: bets) {
			Vector<Object> row = new Vector<Object>();
			row.add(b.getBetNumber());
			row.add(b.getDescription());
			row.add(b.getBetMoney());
			
			betMoney += b.getBetMoney();
			
			tableModelBets.addRow(row);
			
		}
		
		
		
		lblUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username")+":  "+ user.getUsername());
		lblUser.setBounds(72, 30, 317, 13);
		contentPane.add(lblUser);
		
		lblUserMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Money")+ user.getMoney());
		lblUserMoney.setBounds(72, 53, 317, 13);
		contentPane.add(lblUserMoney);
		
		lblReplicatedUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReplicatedUser")+ " "+ replicatedUser.getUsername());
		lblReplicatedUser.setBounds(446, 30, 429, 13);
		contentPane.add(lblReplicatedUser);
		
		lblReplicatedBetsMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BetMoney")+ " "+ betMoney);
		lblReplicatedBetsMoney.setBounds(446, 53, 340, 13);
		contentPane.add(lblReplicatedBetsMoney);
		
		btnReplicate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReplicateUser"));
		btnReplicate.setBounds(245, 548, 356, 32);
		btnReplicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					facade.replicateUser(user, bets, betMoney);
					RegisteredUser u = facade.getUserByUsername(user.getUsername());
					UserGUI a = new UserGUI(u);
					a.setVisible(true);
					current.setVisible(false);
					frame.setVisible(false);
					
				} catch (InsufficientMoney e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("NotMoney"));
					lblError.setVisible(true);
				}
				
			}
			
		});
		
		contentPane.add(btnReplicate);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(207, 512, 394, 13);
		lblError.setVisible(false);
		contentPane.add(lblError);
		
		
	}
}
