package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Event;
import domain.Question;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class AdminGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblAdminOptions;
	private JButton btnQueryQuestions;
	private JButton btnCreateQuestion;
	private JButton btnCreateEvent;
	
	private JFrame current;
	private JButton btnLogOut;
	private JButton btnEnterresult;
	private JButton btnTopUsers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI();
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
	public AdminGUI() {
		current = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		lblAdminOptions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Menu"));
		lblAdminOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminOptions.setBounds(206, 0, 105, 30);
		contentPane.add(lblAdminOptions);
		
		//First button of the menu
		btnQueryQuestions = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		btnQueryQuestions.setBounds(117, 31, 297, 37);
		contentPane.add(btnQueryQuestions);
		btnQueryQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindQuestionsGUI a = new FindQuestionsGUI();
				a.setVisible(true);
				
			}
			
		});
		
		//Second button of the menu
		btnCreateQuestion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		btnCreateQuestion.setBounds(117, 124, 297, 37);
		contentPane.add(btnCreateQuestion);
		btnCreateQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateQuestionGUI a = new CreateQuestionGUI(new Vector<Event>());
				a.setVisible(true);
				
			}
			
		});
		
		//Third button of the menu
		btnCreateEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		btnCreateEvent.setBounds(117, 77, 297, 37);
		contentPane.add(btnCreateEvent);
		btnCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateEventGUI a = new CreateEventGUI();
				a.setVisible(true);
				
			}
			
		});
		
		
		
		
		JButton btnCreateFee = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateFee"));
		btnCreateFee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateFeeGUI a = new CreateFeeGUI();
				a.setVisible(true);
				
				
			}
		});
		btnCreateFee.setBounds(117, 171, 297, 38);
		contentPane.add(btnCreateFee);
		
		//Fourth button of the menu
		btnLogOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		btnLogOut.setBounds(200, 367, 127, 37);
		contentPane.add(btnLogOut);
		btnLogOut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				LoginGUI a = new LoginGUI();
				current.setVisible(false);
				a.setVisible(true);
				
			}
			
			
		});
		
		btnEnterresult = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EnterResult"));
		btnEnterresult.setBounds(117, 313, 297, 37);
		contentPane.add(btnEnterresult);
		btnEnterresult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			EnterResultGUI a = new EnterResultGUI();
			a.setVisible(true);
			}
			});
		
		JButton btnRemoveEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RemoveEvent"));
		btnRemoveEvent.setBounds(117, 219, 297, 37);
		contentPane.add(btnRemoveEvent);
		btnRemoveEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveEventGUI a = new RemoveEventGUI();
				a.setVisible(true);
				
			}

			
			
		});
		
		
		btnTopUsers = new JButton(ResourceBundle.getBundle("Etiquetas").getString("TopUsers"));
		btnTopUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TopUsersGUI a = new TopUsersGUI(null, current);
				a.setVisible(true);
				
			}
		});
		btnTopUsers.setBounds(117, 266, 297, 37);
		contentPane.add(btnTopUsers);
}
}
