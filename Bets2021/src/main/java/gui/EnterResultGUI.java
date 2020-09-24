package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Fee;
import domain.Movement;
import domain.Question;
import domain.RegisteredUser;
import exceptions.EventUnfinished;
import exceptions.InsufficientMoney;
import exceptions.NotSelectedFee;
import exceptions.UnspecifiedMovement;
import exceptions.WinnerAlreadyExist;

public class EnterResultGUI extends JFrame {

	static class DecimalFormatRenderer extends DefaultTableCellRenderer {
		private static final DecimalFormat formatter = new DecimalFormat("#.00");

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			// First format the cell value as required

			value = formatter.format(value);

			// And pass it on to parent class

			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}

	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private JLabel lblFees;

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneFees = new JScrollPane();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableFees = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelFees;

	private Fee selectedFee = null;
	private Question selectedQuestion = null;
	private Event ev = null;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	private String[] columnNamesFees = new String[] { ResourceBundle.getBundle("Etiquetas").getString("Prediction"),
			ResourceBundle.getBundle("Etiquetas").getString("Factor")

	};
	/*private final JButton btnSetWinner = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("EnterResultGUI.btnNewButton.text"));*///$NON-NLS-1$ //$NON-NLS-2$
	
	private final JButton btnSetWinner = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Accept"));
	
	private final JLabel lblError = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	
	private JFrame current;
	
	private Vector feeNames;

	public EnterResultGUI() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		current = this;
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 253, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jCalendar1.setCalendar(calendarMio);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = MainGUI.getBusinessLogic();

						List<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						for (domain.Event ev : events) {
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
																												// in
																												// JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
				CreateQuestionGUI.paintDaysWithEvents(jCalendar1);
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 269, 320, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				ev = (Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3);
				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q); // q object added in order to obtain it with tableModelQueries.getValueAt(i,2)
					tableModelQueries.addRow(row);
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); // not shown in
																										// JTable
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		tableModelFees = new DefaultTableModel(null, columnNamesFees);
		tableFees.setModel(tableModelFees);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPaneFees, null);
		scrollPaneQueries.setViewportView(tableQueries);

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				selectedQuestion = (Question) tableModelQueries.getValueAt(i, 2); // obtain question object
				Vector<Fee> fees = selectedQuestion.getFees();

				tableModelFees.setDataVector(null, columnNamesFees);
				tableModelFees.setColumnCount(3);
				if (fees.isEmpty())
					lblFees.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoFees") + ": " + selectedQuestion.getQuestion());
				else
					lblFees.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedQuestion") + " "
							+ selectedQuestion.getQuestion());

				for (Fee f : fees) {
					Vector<Object> row = new Vector<Object>();
					// row.add(f.getFeeNum());
					row.add(f.getPrediction());
					row.add(f.getFactor());
					row.add(f);
					tableModelFees.addRow(row);
				}
				tableFees.getColumnModel().getColumn(0).setPreferredWidth(240);
				tableFees.getColumnModel().getColumn(1).setPreferredWidth(45);
				tableFees.getColumnModel().getColumn(1).setCellRenderer(new DecimalFormatRenderer());
				tableFees.getColumnModel().removeColumn(tableFees.getColumnModel().getColumn(2));
			}
		});

		lblFees = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FindQuestionsGUI.lblFees.text"));
		lblFees.setBounds(383, 252, 287, 16);
		getContentPane().add(lblFees);

		scrollPaneFees.setBounds(372, 269, 266, 116);
		getContentPane().add(scrollPaneFees);

		tableFees = new JTable();
		tableModelFees = new DefaultTableModel(null, feeNames);
		tableFees.setModel(tableModelFees);
		tableFees.setBackground(Color.WHITE);
		tableFees.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableFees.getSelectedRow();
				selectedFee = (Fee) tableModelFees.getValueAt(i, 2); // obtain question object

			}
		});
		scrollPaneFees.setViewportView(tableFees);
		tableFees.setBackground(Color.WHITE);

		scrollPaneFees.setViewportView(tableFees);
		btnSetWinner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblError.setVisible(false);
					System.out.println(ev);
					System.out.println(selectedQuestion);
					System.out.println(selectedFee);
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.enterResult(selectedFee, selectedQuestion, ev.getEventDate());
					
					double wonMoney = 0.0;
					if (selectedFee.getBets() != null) {
						for (Bet b : selectedFee.getBets()) {
							
							RegisteredUser u1 = facade.getUserByUsername(b.getBetUserUsername());
							double won = b.getBetMoney() * selectedFee.getFactor();
							wonMoney = wonMoney + won;
							Movement mov = new Movement(won, won + u1.getMoney(), "Won bet: " + b.getFee().getPrediction()+", "+b.getFee().getQuestion().getQuestion()+", "+b.getFee().getQuestion().getEvent().getDescription(), u1);
							facade.createMovement(mov, u1);
							facade.updateMoney(u1, won + u1.getMoney(), true);
							facade.updateWonMoney(u1, won + u1.getWonMoney());
							u1.setMoney(won + u1.getMoney());
						}
					}
					
					double totalProfit = facade.subtractProfitsToQuestion(wonMoney, selectedQuestion);
					facade.removeBetsFromUser(selectedQuestion);
					ProfitGUI a = new ProfitGUI(totalProfit, current);
					a.setVisible(true);
					
				} catch (NotSelectedFee e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectFee"));
					lblError.setVisible(true);
				} catch (WinnerAlreadyExist e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorWiningFee"));
					lblError.setVisible(true);
				} 
				catch (EventUnfinished e1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasNotFinished"));
					lblError.setVisible(true);
				}
				catch (InsufficientMoney e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnspecifiedMovement e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			}
		});
		btnSetWinner.setBounds(308, 415, 123, 25);

		getContentPane().add(btnSetWinner);
		lblError.setForeground(Color.RED);
		lblError.setBounds(276, 397, 320, 16);

		getContentPane().add(lblError);

	}
}
