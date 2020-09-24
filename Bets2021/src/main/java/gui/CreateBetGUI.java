package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Fee;
import domain.Movement;
import domain.Question;
import domain.RegisteredUser;
import exceptions.EmptyDescription;
import exceptions.EventFinished;
import exceptions.ExistingBet;
import exceptions.InsufficientMoney;
import exceptions.LessThanTheMinimumMoney;
import exceptions.NotSelectedFee;

import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;

public class CreateBetGUI extends JFrame {

	private JCalendar calendar;
	private Calendar calendarMio = null;
	
	private JScrollPane scrollPaneEvents;
	private JScrollPane scrollPaneQuestions;
	private JScrollPane scrollPaneFees;
	
	private String[] eventNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("EventN"), ResourceBundle.getBundle("Etiquetas").getString("Event")};
	private String[] questionNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("QueryN"), ResourceBundle.getBundle("Etiquetas").getString("Queries")};
	private String[] feeNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("Prediction"), ResourceBundle.getBundle("Etiquetas").getString("Factor")};
	
	private JLabel jLabelEventDate;
	private JLabel jLabelEvents;
	private JLabel jLabelQuestions;
	private JLabel jLabelFees;
	
	private JTable tableEvents;
	private JTable tableQuestions;
	private JTable tableFees;
	
	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelFees;
	private JTextField tfBetMoney;
	private JLabel lblMoney;
	private JButton btnBet;
	
	private Date selectedDate = null;
	private Fee selectedFee = null;
	private static RegisteredUser selectedUser = null;
	private Event selectedEvent = null;
	
	private JLabel lblError;
	
	private JFrame current;
	private Question que;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateBetGUI frame = new CreateBetGUI(new RegisteredUser(), new JFrame());
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
	public CreateBetGUI(RegisteredUser u, final JFrame frame) {
		current = this;
		this.selectedUser = u;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 736, 623);
		this.getContentPane().setLayout(null);
		
		calendar = new JCalendar();
		calendar.setBounds(54, 57, 225, 150);
		getContentPane().add(calendar);
		
		this.calendar.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				
				if (evt.getPropertyName().equals("locale")) {
					calendar.setLocale((Locale) evt.getNewValue());
					
				} 
				else if (evt.getPropertyName().equals("calendar"))
				{
					calendarMio = (Calendar) evt.getNewValue();
					DateFormat dateFormat = DateFormat.getDateInstance(1, calendar.getLocale());
					calendar.setCalendar(calendarMio);
					selectedDate = UtilDate.trim(new Date(calendar.getCalendar().getTime().getTime()));
					
					try {
						tableModelEvents.setDataVector(null, eventNames);
						tableModelEvents.setColumnCount(3);
						
						BLFacade facade = MainGUI.getBusinessLogic();
						List<domain.Event> events = facade.getEvents(selectedDate);
						if (events.isEmpty()) System.out.println("No events on this date.");
						else {
							for (domain.Event ev: events) {
								Vector<Object> row = new Vector<Object>();
								
								row.add(ev.getEventNumber());
								row.add(ev.getDescription());
								row.add(ev);
								tableModelEvents.addRow(row);
								
							}
							tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
							tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
							tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2));
							
							
						}
						
					} catch (Exception e1) {
						System.out.println("Something ocurred.");
					}
					
				}
				CreateQuestionGUI.paintDaysWithEvents(calendar);
				
			}
			
		});
		
		scrollPaneEvents = new JScrollPane();
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneEvents.setBounds(338, 57, 346, 150);
		getContentPane().add(scrollPaneEvents);
		
		tableEvents = new JTable();
		tableModelEvents = new DefaultTableModel(null, eventNames);
		tableEvents.setModel(tableModelEvents);
		tableEvents.setBackground(Color.WHITE);
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				selectedEvent = (domain.Event)tableModelEvents.getValueAt(i, 2);
				Vector<Question> questions = selectedEvent.getQuestions();
				
				tableModelQueries.setDataVector(null, questionNames);
				
				if (questions.isEmpty()) {
					System.out.println("There are no questions for this event.");
					
				} else {
					System.out.println(questions.toString());
					tableModelQueries.setColumnCount(3);
					
					for (domain.Question q: questions) {
						Vector<Object> row = new Vector<Object>();
						row.add(q.getQuestionNumber());
						row.add(q.getQuestion());
						row.add(q);
						tableModelQueries.addRow(row);
						
					}
					
					tableQuestions.getColumnModel().getColumn(0).setPreferredWidth(25);
					tableQuestions.getColumnModel().getColumn(1).setPreferredWidth(268);
					tableQuestions.getColumnModel().removeColumn(tableQuestions.getColumnModel().getColumn(2));
				}
				
			}
		});
		
		scrollPaneEvents.setViewportView(tableEvents);
		
		scrollPaneQuestions = new JScrollPane();
		scrollPaneQuestions.setBounds(new Rectangle(40, 281, 320, 116));
		scrollPaneQuestions.setBounds(54, 292, 320, 116);
		getContentPane().add(scrollPaneQuestions);
		
		tableQuestions = new JTable();
		tableModelQueries = new DefaultTableModel(null, questionNames);
		tableQuestions.setModel(tableModelQueries);
		tableQuestions.setBackground(Color.WHITE);
		scrollPaneQuestions.setViewportView(tableQuestions);
		
		tableQuestions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableQuestions.getSelectedRow();
				que=(Question)tableModelQueries.getValueAt(i,2); // obtain question object
				Vector<Fee> fees=que.getFees();

				tableModelFees.setDataVector(null, feeNames);
				tableModelFees.setColumnCount(3);

				if (fees.isEmpty())
					System.out.println("There are no fees for this question.");
				else 
					System.out.println(fees.toString());

				for (Fee f:fees){
					Vector<Object> row = new Vector<Object>();
					//row.add(f.getFeeNum());
					row.add(f.getPrediction());
					row.add(f.getFactor());
					row.add(f);
					tableModelFees.addRow(row);
				}
				tableFees.getColumnModel().getColumn(0).setPreferredWidth(240);
				tableFees.getColumnModel().getColumn(1).setPreferredWidth(45);
				tableFees.getColumnModel().removeColumn(tableFees.getColumnModel().getColumn(2));
			}
		});
		
		scrollPaneFees = new JScrollPane();
		scrollPaneFees.setBounds(418, 292, 266, 116);
		getContentPane().add(scrollPaneFees);
		
		tableFees = new JTable();
		tableModelFees = new DefaultTableModel(null, feeNames);
		tableFees.setModel(tableModelFees);
		tableFees.setBackground(Color.WHITE);
		tableFees.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableFees.getSelectedRow();
				selectedFee=(Fee)tableModelFees.getValueAt(i,2); // obtain question object
				System.out.println(selectedFee);
				
			}
		});
		scrollPaneFees.setViewportView(tableFees);
		
		jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(54, 22, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
		jLabelEvents.setBounds(338, 28, 259, 16);
		getContentPane().add(jLabelEvents);
		
		jLabelQuestions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
		jLabelQuestions.setBounds(54, 268, 406, 14);
		getContentPane().add(jLabelQuestions);
		
		jLabelFees = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FindQuestionsGUI.lblFees.text"));
		jLabelFees.setBounds(418, 267, 287, 16);
		getContentPane().add(jLabelFees);
		
		tfBetMoney = new JTextField();
		tfBetMoney.setBounds(54, 473, 96, 19);
		getContentPane().add(tfBetMoney);
		tfBetMoney.setColumns(10);
		
		btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		btnBet.setBounds(288, 506, 198, 30);
		btnBet.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			
				try {
					lblError.setText("");
					lblError.setVisible(false);
					System.out.println(selectedFee);
					Float betMoney = Float.parseFloat(tfBetMoney.getText());
					BLFacade facade = MainGUI.getBusinessLogic();
					RegisteredUser u1 = facade.getUserByUsername(selectedUser.getUsername());
					facade.createBet(betMoney, u1, selectedDate, selectedFee, selectedEvent.getDescription(), que);
					Movement mov=new Movement(betMoney,u1.getMoney()-betMoney,"Bet for: "+selectedFee.getPrediction()+", "+selectedFee.getQuestion().getQuestion()+", "+selectedEvent.getDescription(),u1);
					facade.createMovement(mov,u1);
					RegisteredUser u2 = facade.getUserByUsername(selectedUser.getUsername());
					UserGUI a = new UserGUI(u2);
					a.setVisible(true);
					frame.setVisible(false);
					current.setVisible(false);
					
				} catch (EventFinished e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
					lblError.setVisible(true);
				} catch (ExistingBet e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("BetAlreadyExists"));
					lblError.setVisible(true);
				} catch (EmptyDescription e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyFields"));
					lblError.setVisible(true);
					
				} catch (InsufficientMoney e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("NotMoney"));
					lblError.setVisible(true);
				} catch (NotSelectedFee e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectFee"));
					lblError.setVisible(true);	
									
				} catch (NumberFormatException e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("WrongMoney"));
					lblError.setVisible(true);
					
				
					
				} catch (LessThanTheMinimumMoney e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("GreaterQuantity"));
					lblError.setVisible(true);
					
				}
				
			}
			
			
		});
		getContentPane().add(btnBet);
		
		lblMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyToBet"));
		lblMoney.setBounds(54, 450, 327, 13);
		getContentPane().add(lblMoney);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(197, 435, 327, 13);
		lblError.setVisible(false);
		getContentPane().add(lblError);
	}
}
