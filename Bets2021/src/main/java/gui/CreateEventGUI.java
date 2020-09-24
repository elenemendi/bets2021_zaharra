package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import exceptions.EmptyDescription;
import exceptions.ExistingEvent;
import exceptions.InvalidDate;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class CreateEventGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tfDescription;
	private JCalendar jCalendar;
	private JLabel lblDescription;
	private JLabel lblEventDate;
	private JButton btnCreateEvent;
	
	private Date selectedDate;
	private Calendar calendar = null;
	
	private DefaultTableModel eventsTableModel;
	private JTable eventsTable;
	private JScrollPane eventsScrollPane;
	
	private final String[] columnNames = {ResourceBundle.getBundle("Etiquetas").getString("EventN"), ResourceBundle.getBundle("Etiquetas").getString("Event")};
	private JLabel lblDateEvent;
	private JLabel lblError;
	
	private domain.Event selectedEvent = null;   //Atribute used for storing the selected event by the user

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateEventGUI frame = new CreateEventGUI();
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
	public CreateEventGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 716, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		jCalendar = new JCalendar();
		jCalendar.setBounds(37, 53, 261, 183);
		contentPane.add(jCalendar);
		//Code for jCalendar
		jCalendar.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				
				if (evt.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) evt.getNewValue());
					
				} else if (evt.getPropertyName().equals("calendar")) {
					calendar = (Calendar) evt.getNewValue();
					DateFormat dateFormat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					jCalendar.setCalendar(calendar);
					selectedDate = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					
					try {
						eventsTableModel.setDataVector(null, columnNames);
						eventsTableModel.setColumnCount(3);
						
						BLFacade facade=MainGUI.getBusinessLogic();

						List<domain.Event> events=facade.getEvents(selectedDate);
						
						if (events.isEmpty()) {
							lblDateEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+": "+ dateFormat1.format(calendar.getTime()));
							selectedEvent = null;			//If no events on the selected event disable delete button
							tfDescription.setText("");
							
						} else {
							lblDateEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+": "+ dateFormat1.format(calendar.getTime()));
							for (domain.Event ev: events) {
								Vector<Object> row = new Vector<Object>();
								row.add(ev.getEventNumber());				//Add events to table model by rows
								row.add(ev.getDescription());
								row.add(ev);
								
								eventsTableModel.addRow(row);
								
							}
							
						}
							
							eventsTable.getColumnModel().getColumn(0).setPreferredWidth(25);
							eventsTable.getColumnModel().getColumn(1).setPreferredWidth(268);
							eventsTable.getColumnModel().removeColumn(eventsTable.getColumnModel().getColumn(2)); // not shown in JTable

					} catch (Exception e1) {
						
						
					}
					
					paintDaysWithEvents(jCalendar);
				}
				
			}

		});
		
		lblEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate")+": ");
		lblEventDate.setBounds(37, 31, 224, 13);
		contentPane.add(lblEventDate);
		
		lblDescription = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Description") + ": ");
		lblDescription.setBounds(101, 359, 349, 13);
		contentPane.add(lblDescription);
		
		tfDescription = new JTextField();
		tfDescription.setBounds(101, 397, 485, 19);
		contentPane.add(tfDescription);
		tfDescription.setColumns(10);
		
		btnCreateEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		btnCreateEvent.setBounds(293, 442, 157, 21);
		contentPane.add(btnCreateEvent);
		//Button for the creation of events
		btnCreateEvent.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				BLFacade implementation = MainGUI.getBusinessLogic();
				String description = tfDescription.getText();
				
				Integer eventNumber = implementation.getLastEventNumber(); //Get biggest event number

				try {
					implementation.createEvent(eventNumber + 1, description, selectedDate); //Sum 1 to the event number to not repeat PK
					
				} catch (InvalidDate e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPassedDate"));
					lblError.setVisible(true);
					
					
				} catch (ExistingEvent e2) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventAleradyExists"));
					lblError.setVisible(true);
					
					
				} catch (EmptyDescription e3) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyFields"));
					lblError.setVisible(true);
					
				}

			}
			
		});
		
		eventsScrollPane = new JScrollPane();
		eventsScrollPane.setBounds(339, 53, 310, 183);
		contentPane.add(eventsScrollPane);
		
		eventsTable = new JTable();
		eventsScrollPane.setViewportView(eventsTable);
		
		
		eventsTableModel = new DefaultTableModel(null, columnNames);

		eventsTable.setModel(eventsTableModel);   //Show elements of the table model in the table
		
		eventsTable.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				int i=eventsTable.getSelectedRow();
				domain.Event ev=(domain.Event)eventsTableModel.getValueAt(i,2); //Get the event from the table
				
				BLFacade facade = MainGUI.getBusinessLogic();
				tfDescription.setText(ev.getDescription());
				
				selectedEvent = ev;				//There is a event selected, enable delete button
			}
			
		});
		
		lblDateEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
		lblDateEvent.setBounds(339, 31, 310, 13);
		contentPane.add(lblDateEvent);
		
		eventsTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		eventsTable.getColumnModel().getColumn(1).setPreferredWidth(268);
		
		
		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setBounds(107, 292, 468, 13);
		contentPane.add(lblError);
		
		lblError.setVisible(false);
		
	}
	
	public static void paintDaysWithEvents(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = MainGUI.getBusinessLogic();

		List<Date> dates=facade.getEventsMonth(jCalendar.getDate());
		
		System.out.println(dates);
			
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		//int today=calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:dates){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
	 		calendar.set(Calendar.DAY_OF_MONTH, 1);
	 		calendar.set(Calendar.MONTH, month);
	 	
	}
}
