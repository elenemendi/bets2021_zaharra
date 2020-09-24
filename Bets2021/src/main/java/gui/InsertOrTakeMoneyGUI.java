package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Movement;
import domain.Question;
import domain.RegisteredUser;
import exceptions.InsufficientMoney;
import exceptions.UnspecifiedMovement;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class InsertOrTakeMoneyGUI extends JFrame {
	private Calendar calendarMio = null;
	
	private String[] eventNames = new String[] {"Event#", "Event"};
	private String[] questionNames = new String[] {"Question#, Question"};
	private String[] feeNames = new String[] {"Prediction", "Factor" };
	
	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;

	private Question selectedQuestion = null;
	private JTextField textField;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private RegisteredUser selectedUser=null;
	
	private JFrame current;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertOrTakeMoneyGUI frame = new InsertOrTakeMoneyGUI(new RegisteredUser(), new JFrame());
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
	public InsertOrTakeMoneyGUI (final RegisteredUser u, final JFrame frame) {
		current= this;
		this.selectedUser = u;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 495, 290);
		this.getContentPane().setLayout(null);
		
		final JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Money") +u.getMoney());
		lblNewLabel.setFont(new Font("Verdana Pro", Font.PLAIN, 30));
		lblNewLabel.setBounds(81, 30, 384, 48);
		getContentPane().add(lblNewLabel);
		
		final JRadioButton insertB = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Insert"));
		insertB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(insertB);
		insertB.setBounds(144, 99, 97, 25);
		getContentPane().add(insertB);
		
		final JRadioButton takeB = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Take"));
		takeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonGroup.add(takeB);
		takeB.setBounds(243, 99, 127, 25);
		getContentPane().add(takeB);
		
		final JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setBounds(12, 214, 453, 16);
		getContentPane().add(lblError);
		tableModelEvents = new DefaultTableModel(null, eventNames);
		tableModelQueries = new DefaultTableModel(null, questionNames);
		
		textField = new JTextField();
		textField.setBounds(144, 150, 169, 22);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("€");
		lblNewLabel_1.setBounds(317, 153, 56, 16);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Accept"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					lblError.setText("");
					BLFacade facade = MainGUI.getBusinessLogic();
					RegisteredUser u1 = facade.getUserByUsername(selectedUser.getUsername());
					double quantity=0;
					boolean selected=false;
					String description= new String("");
					if (insertB.isSelected()) {
						quantity=u1.getMoney()+Float.parseFloat(textField.getText());
						selected=true;
						description="Money inserted";
					}
					else if(takeB.isSelected()) {
						quantity=u1.getMoney()-Float.parseFloat(textField.getText());
						selected=true;
						description="Money taken";
					}
					Movement mov=new Movement(Float.parseFloat(textField.getText()),quantity,description,u1);
					facade.createMovement(mov,u1);
					facade.updateMoney(u1, quantity, selected);
					u1.setMoney(quantity);
					lblNewLabel.setText("Your money: "+u1.getMoney());
					RegisteredUser u2 = facade.getUserByUsername(selectedUser.getUsername());
					UserGUI a = new UserGUI(u2);
					a.setVisible(true);
					frame.setVisible(false);
					current.setVisible(false);
				}catch(InsufficientMoney e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("NotMoney"));
				}
				catch(UnspecifiedMovement e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyFields"));
				}
				catch(NumberFormatException e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("WrongMoney"));
				}
			}
		});
		btnNewButton.setBounds(179, 185, 97, 25);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Quantity"));
		lblNewLabel_2.setBounds(144, 133, 56, 16);
		getContentPane().add(lblNewLabel_2);
	
	}
}
