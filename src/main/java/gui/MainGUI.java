package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Software Engineering teachers
 */
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.RegularUser;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;

	private static BLFacade appFacadeInterface = LoginGUI.getBusinessLogic();

	private RegularUser ru = null;
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnModificar = new JButton();
	private JLabel lblSaldo;
	private RegularUser userlog = null;
	private JButton btnBet;

	private JButton btnHistorial;
	private JButton btnAnularApuesta;
	private JButton btnChargeMoney;

	/**
	 * This is the default constructor
	 */
	public MainGUI(RegularUser ru) {
		super();

		userlog = ru;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				// System.exit(1);
			}
		});

		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setSize(549, 539);
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
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			btnModificar.setBounds(45, 99, 422, 71);
			// jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getBtnBet());
			jContentPane.add(getPanel());
			jContentPane.add(getlblSaldo());

			btnHistorial = new JButton();
			btnHistorial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					Frame reg = new BetHistoryGUI(userlog);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
					dispose();

				}
			});
			btnHistorial.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnHistorial.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnHistorial.setBounds(45, 239, 422, 71);
			jContentPane.add(btnHistorial);
			jContentPane.add(getBtnAnularApuesta());
			jContentPane.add(getBtnChargeMoney());
		}
		return jContentPane;
	}

	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {

		btnModificar.setText(ResourceBundle.getBundle("Etiquetas").getString("Show/EditProfile"));
		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Frame reg = new ModificarDatosGUI(userlog);
				reg.setAlwaysOnTop(true);
				reg.setVisible(true);
				dispose();
			}
		});

		return btnModificar;
	}

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
//	private JButton getBoton3() {
//		if (jButtonQueryQueries == null) {
//			jButtonQueryQueries = new JButton();
//			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
//			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
//				@Override
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					JFrame a = new FindQuestionsGUI();
//					a.setAlwaysOnTop(true);
//					a.setVisible(true);
//					a.setVisible(true);
//				}
//			});
//		}
//		return jButtonQueryQueries;
//	}

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 12, 536, 97);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}

	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
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
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
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
				@Override
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(0, 461, 536, 97);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}

	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		btnBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));
		btnModificar.setText(ResourceBundle.getBundle("Etiquetas").getString("Show/EditProfile"));
		btnHistorial.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnHistorial.text"));
		lblSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblSaldo") + " " + userlog.getBalance() +"€");
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		btnAnularApuesta.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnAnularApuesta.text"));

		
	}

	public void close() {
		this.setVisible(false);
	}

	private JButton getBtnBet() {
		if (btnBet == null) {
			btnBet = new JButton();
			btnBet.setBounds(45, 169, 422, 71);
			btnBet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Frame reg = new CreateBetGUI(userlog);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
					dispose();
				}
			});
			btnBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return btnBet;
	}
	
	
	private JLabel getlblSaldo() {
		if (lblSaldo == null) {
			lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblSaldo") + " " + userlog.getBalance() +"€");
			lblSaldo.setBounds(330, 12, 205, 19);
			lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSaldo.setForeground(Color.BLUE);
		}
		return lblSaldo;
	}

	
	
	
	private JButton getBtnAnularApuesta() {
		if (btnAnularApuesta == null) {
			btnAnularApuesta = new JButton();
			btnAnularApuesta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Frame reg = new AnularApuestaGUI(userlog);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
					dispose();
					
				}
			});
			btnAnularApuesta.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnAnularApuesta.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnAnularApuesta.setBounds(45, 309, 422, 71);
		}
		return btnAnularApuesta;
	}
	private JButton getBtnChargeMoney() {
		if (btnChargeMoney == null) {
			btnChargeMoney = new JButton();
			btnChargeMoney.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Frame reg = new ChargeMoneyGUI(userlog);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
				}
			});
			btnChargeMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnChargeMoney.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnChargeMoney.setBounds(45, 379, 422, 71);
		}
		return btnChargeMoney;
	}
}
// @jve:decl-index=0:visual-constraint="0,0"
