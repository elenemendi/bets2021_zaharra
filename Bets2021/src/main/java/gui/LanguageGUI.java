package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Event;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LanguageGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel lblSelectLenguage;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblAukeratuHizkuntza;
	private JLabel lblSeleccionaUnIdioma;
	private JButton btnStart;
	
	/**
	 * This is the default constructor
	 */
	public LanguageGUI() {
		super();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(495, 290);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
			jContentPane.add(getLblAukeratuHizkuntza());
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getLblSeleccionaUnIdioma());
			jContentPane.add(getPanel());
		}
		return jContentPane;
	}
	


	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
							}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
								}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
			panel.add(getBtnStart());
		}
		return panel;
	}
	

	
	private JLabel getLblAukeratuHizkuntza() {
		if (lblAukeratuHizkuntza == null) {
			lblAukeratuHizkuntza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LenguageGUI.lblAukeratuHizkuntza.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblAukeratuHizkuntza.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblAukeratuHizkuntza;
	}
	private JLabel getLblSeleccionaUnIdioma() {
		if (lblSeleccionaUnIdioma == null) {
			lblSeleccionaUnIdioma = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LenguageGUI.lblSeleccionaUnIdioma.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblSeleccionaUnIdioma.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblSeleccionaUnIdioma;
	}
	
	private JLabel getLblNewLabel() {
		if (lblSelectLenguage == null) {
			lblSelectLenguage = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LenguageGUI.lblSelectLenguage.text"));
			lblSelectLenguage.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblSelectLenguage;
	}
	private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LenguageGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					LoginGUI a = new LoginGUI();
					a.setVisible(true);
				}
			});
		}
		return btnStart;
	}
} // @jve:decl-index=0:visual-constraint="0,0"

