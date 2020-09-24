package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Movement;
import domain.RegisteredUser;

import javax.swing.JScrollPane;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;


public class TopUsersGUI extends JFrame {

	private DefaultTableModel tableModelTop;

	private String[] topInfo=new String[] {"Top", ResourceBundle.getBundle("Etiquetas").getString("Username")  ,ResourceBundle.getBundle("Etiquetas").getString("WonMoney")} ;

	private JLabel lblTopUsers;

	private JTable tableUsers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopUsersGUI frame = new TopUsersGUI(new RegisteredUser() , new JFrame());
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
	public TopUsersGUI(RegisteredUser u, final JFrame frame) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 736, 623);
		this.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 89, 694, 401);
		getContentPane().add(scrollPane);
		
		tableUsers = new JTable();
		tableModelTop = new DefaultTableModel(null, topInfo);
		tableUsers.setModel(tableModelTop);
		tableUsers.setBackground(Color.WHITE);
		
		scrollPane.setViewportView(tableUsers);
		
		tableModelTop.setDataVector(null, topInfo);
		tableModelTop.setColumnCount(3);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		int j =1;
		List<RegisteredUser> top3 = facade.getTopUsers();
		for (int i =0; i<=2; i++) {
			Vector<Object> row = new Vector<Object>();
			row.add("Top "+j);
			row.add(top3.get(i).getUsername());
			row.add(top3.get(i).getWonMoney()+"€");
			tableModelTop.addRow(row);
			j++;
		}
		lblTopUsers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TopUsers"));
		lblTopUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopUsers.setBounds(12, 42, 694, 22);
		getContentPane().add(lblTopUsers);
		
	}
}

