package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;


import domain.Movement;
import domain.RegisteredUser;

import javax.swing.JScrollPane;

import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Color;


public class ShowMovementsGUI extends JFrame {

	private DefaultTableModel tableModelMovement;

	private String[] movementNames=new String[] { ResourceBundle.getBundle("Etiquetas").getString("Description")  ,ResourceBundle.getBundle("Etiquetas").getString("Quantity"),ResourceBundle.getBundle("Etiquetas").getString("Total")};;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowMovementsGUI frame = new ShowMovementsGUI(new RegisteredUser() , new JFrame());
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
	public ShowMovementsGUI(RegisteredUser u, final JFrame frame) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 736, 623);
		this.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 89, 694, 401);
		getContentPane().add(scrollPane);
		
		JTable tableMovements = new JTable();
		tableModelMovement = new DefaultTableModel(null, movementNames);
		tableMovements.setModel(tableModelMovement);
		tableMovements.setBackground(Color.WHITE);
		
		scrollPane.setViewportView(tableMovements);
		
		tableModelMovement.setDataVector(null, movementNames);
		tableModelMovement.setColumnCount(3);
		
		if (u.getMovements() == null) {
			u.setMovements(new Vector<domain.Movement>());
			
		}
		
		Vector<Movement> movements = u.getMovements();
		
		for (domain.Movement b: movements) {
			Vector<Object> row = new Vector<Object>();
			System.out.println("dentro");
			row.add(b.getDescription());
			row.add(b.getIncome());
			row.add(b.getQuantity());
			row.add(b);
			tableModelMovement.addRow(row);
			
		}
		tableMovements.getColumnModel().getColumn(0).setPreferredWidth(450);
		JLabel lblNewLabel = new JLabel (ResourceBundle.getBundle("Etiquetas").getString("AllMoves"));
		lblNewLabel.setBounds(221, 39, 300, 22);
		getContentPane().add(lblNewLabel);
	}
}

