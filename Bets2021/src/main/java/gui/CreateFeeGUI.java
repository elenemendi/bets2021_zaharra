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
import domain.Fee;
import domain.Question;
import exceptions.EmptyDescription;
import exceptions.EventFinished;
import exceptions.ExistingFee;

import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JButton;
import javax.swing.JTextField;

public class CreateFeeGUI extends JFrame {

	private JCalendar calendar;
	private Calendar calendarMio = null;
	
	private JScrollPane scrollPaneEvents;
	private JScrollPane scrollPaneQuestions;
	
	private String[] eventNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("EventN"), ResourceBundle.getBundle("Etiquetas").getString("Event")};
	private String[] questionNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("QueryN"), ResourceBundle.getBundle("Etiquetas").getString("Queries")};
	private String[] feeNames = new String[] {ResourceBundle.getBundle("Etiquetas").getString("Prediction"), ResourceBundle.getBundle("Etiquetas").getString("Factor")};
	
	private JLabel jLabelEventDate;
	private JLabel jLabelEvents;
	private JLabel jLabelQuestions;
	
	private JTable tableEvents;
	private JTable tableQuestions;
	
	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;

	private Question selectedQuestion = null;
	private JButton btnCreateFee;
	private JTextField tfDescription;
	private JTextField tfFactor;
	private JLabel lblDescription;
	private JLabel lblFactor;
	private JLabel lblError;
	
	private JFrame current;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateFeeGUI frame = new CreateFeeGUI();
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
	public CreateFeeGUI() {
		current = this;
		
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
					Date date = UtilDate.trim(new Date(calendar.getCalendar().getTime().getTime()));
					
					try {
						tableModelEvents.setDataVector(null, eventNames);
						tableModelEvents.setColumnCount(3);
						
						BLFacade facade = MainGUI.getBusinessLogic();
						
						List<domain.Event> events = facade.getEvents(date);
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
				domain.Event ev = (domain.Event)tableModelEvents.getValueAt(i, 2);
				Vector<Question> questions = ev.getQuestions();
				
				tableModelQueries.setDataVector(null, questionNames);
				tableModelQueries.setColumnCount(3);
				
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
		scrollPaneQuestions.setBounds(191, 267, 320, 116);
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
				selectedQuestion = (Question)tableModelQueries.getValueAt(i,2); // obtain question object

			}
		});
		
		jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(54, 22, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
		jLabelEvents.setBounds(338, 28, 259, 16);
		getContentPane().add(jLabelEvents);
		
		jLabelQuestions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
		jLabelQuestions.setBounds(191, 243, 406, 14);
		getContentPane().add(jLabelQuestions);
		
		btnCreateFee = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateFee"));
		btnCreateFee.setBounds(269, 537, 124, 39);
		getContentPane().add(btnCreateFee);
		btnCreateFee.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lblError.setVisible(false);
				
				try {
					String description = tfDescription.getText();
					Float factor = Float.parseFloat(tfFactor.getText());
					int id = selectedQuestion.getLastFeeNumber();
					System.out.println(description + " " + factor);			
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.createFee(id + 1, selectedQuestion, description, factor);
					current.setVisible(false);
				} catch (EventFinished e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
					lblError.setVisible(true);
				} catch (ExistingFee e2) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorFeeAlreadyExists"));
					lblError.setVisible(true);
				} catch (NumberFormatException e3) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("WrongMoney"));
					lblError.setVisible(true);
					
				} catch (NullPointerException e4) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectQuestion"));
					lblError.setVisible(true);
					
				} catch (EmptyDescription e5) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyFields"));
					lblError.setVisible(true);
					
				}
			}
			
		});
		
		tfDescription = new JTextField();
		tfDescription.setBounds(54, 484, 287, 19);
		getContentPane().add(tfDescription);
		tfDescription.setColumns(10);
		
		tfFactor = new JTextField();
		tfFactor.setBounds(457, 484, 96, 19);
		getContentPane().add(tfFactor);
		tfFactor.setColumns(10);
		
		lblDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Description"));
		lblDescription.setBounds(new Rectangle(40, 15, 140, 25));
		lblDescription.setBounds(54, 449, 140, 25);
		getContentPane().add(lblDescription);
		
		lblFactor = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Factor"));
		lblFactor.setBounds(new Rectangle(40, 15, 140, 25));
		lblFactor.setBounds(457, 449, 140, 25);
		getContentPane().add(lblFactor);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(323, 417, 46, 13);
		getContentPane().add(lblError);
		lblError.setVisible(false);
	}
}
