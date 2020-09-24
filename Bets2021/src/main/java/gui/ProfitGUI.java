package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ProfitGUI extends JFrame {

	private JPanel contentPane;
	private JButton btnClose;
	private JLabel lblProfits;
	
	private JFrame current;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfitGUI frame = new ProfitGUI(0, new JFrame());
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
	public ProfitGUI(double profit, final JFrame frame) {
		current = this;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 419, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close2"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				current.setVisible(false);
				
			}
		});
		
		btnClose.setBounds(148, 150, 103, 32);
		contentPane.add(btnClose);
		
		lblProfits = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Profit")+ profit);
		lblProfits.setHorizontalAlignment(SwingConstants.CENTER);
		lblProfits.setBounds(77, 76, 247, 13);
		contentPane.add(lblProfits);
	}
}
