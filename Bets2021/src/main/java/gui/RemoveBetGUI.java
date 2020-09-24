package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bet;
import domain.BetContainer;
import domain.FeeContainer;
import domain.Movement;
import domain.QuestionContainer;
import domain.RegisteredUser;
import exceptions.NotSelectedBet;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class RemoveBetGUI extends JFrame {

	private JPanel contentPane;
	
	private static RegisteredUser selectedUser = null;
	private Bet selectedBet = null;

	private JButton btnRemove;

	private JScrollPane scrollPaneBets;
	private JTable tableBets;
	private DefaultTableModel tableModelBets;
	
	//private String[] betNames = new String[] {"Bet#", "Event", "Question", "Option", "Money"};
	private String[] betNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("BetN"),ResourceBundle.getBundle("ETiquetas").getString("Description"), ResourceBundle.getBundle("Etiquetas").getString("Money2")};
	private JLabel lblTitle;
	
	private JFrame current;

	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoveBetGUI frame = new RemoveBetGUI(new RegisteredUser() , new JFrame());
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
	public RemoveBetGUI(RegisteredUser u, final JFrame frame) {
		this.selectedUser = u;
		current = this;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		lblTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("YourBets"));
		lblTitle.setBounds(259, 47, 158, 13);
		contentPane.add(lblTitle);
		
		scrollPaneBets = new JScrollPane();
		scrollPaneBets.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneBets.setBounds(166, 122, 346, 150);
		
		contentPane.add(scrollPaneBets);
		
		tableBets = new JTable();
		tableModelBets = new DefaultTableModel(null, betNames);
		tableBets.setModel(tableModelBets);
		tableBets.setBackground(Color.WHITE);
		
		scrollPaneBets.setViewportView(tableBets);
		tableBets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableBets.getSelectedRow();
				selectedBet = (Bet)tableModelBets.getValueAt(i, 3);
				
			}
		
		});
		
		tableModelBets.setDataVector(null, betNames);
		tableModelBets.setColumnCount(4);
		
		if (selectedUser.getBets() == null) {
			selectedUser.setBets(new Vector<Bet>());
			
		}
		
		Vector<Bet> bets = selectedUser.getBets();
		
		for (Bet b: bets) {
			Vector<Object> row = new Vector<Object>();
			
			row.add(b.getBetNumber());
			row.add(b.getDescription());
			//row.add(b.getFee().getQuestion().getQuestion());
			//row.add(b.getFee().getPrediction());
			row.add(b.getBetMoney());
			row.add(b);
			tableModelBets.addRow(row);
			
		}
		
		tableBets.getColumnModel().removeColumn(tableBets.getColumnModel().getColumn(3));
		
		
		btnRemove = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RemoveBet"));
		btnRemove.setBounds(221, 364, 238, 35);
		btnRemove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					BetContainer bc = facade.getBetContainer(selectedBet, selectedUser);
					FeeContainer fc = facade.getFeeContainer(bc.getFee());
					QuestionContainer qc = facade.getQuestionContainer(fc.getQuestion());
					facade.removeBet(selectedUser, selectedBet, fc.getFee(), qc.getQuestion());
					System.out.println(bc);
					Movement mov=new Movement(selectedBet.getBetMoney(),selectedUser.getMoney()+selectedBet.getBetMoney(),"Bet for: "+bc.getFee().getPrediction()+", "+fc.getQuestion().getQuestion()+", "+bc.getFee().getPrediction()+qc.getEvent().getDescription()+" REMOVED",selectedUser);
					facade.createMovement(mov,selectedUser);
					
					RegisteredUser u = facade.getUserByUsername(selectedUser.getUsername());
					UserGUI a = new UserGUI(u);
					a.setVisible(true);
					frame.setVisible(false);
					current.setVisible(false);
				
				} catch (NotSelectedBet e1) {
					lblError.setVisible(true);
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("RemoveBet"));
					
				}
				
				
			}
			
			
		});
		contentPane.add(btnRemove);
		
		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setBounds(247, 316, 212, 13);
		contentPane.add(lblError);
		lblError.setVisible(false);
	}
}
