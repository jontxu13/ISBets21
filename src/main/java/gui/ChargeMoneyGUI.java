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
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class ChargeMoneyGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;

	private static BLFacade facade = LoginGUI.getBusinessLogic();

	private JRadioButton rdbtnCreditCard;
	private JRadioButton rdbtnBankAccount;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private RegularUser userlog = null;
	private JButton btnCharge;
	private JLabel lblNewLabel;
	private JTextPane textPaneMoney;
	private JLabel lblNewLabel_1;
	private JTextField textMoneyToCharge;
	private JLabel lblNewLabel_2;
	
	/**
	 * This is the default constructor
	 */
	public ChargeMoneyGUI(RegularUser ru) {
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
				//System.exit(1);
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

		this.setSize(406, 339);
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
			jContentPane.add(getBtnCharge());
			jContentPane.add(getPanel());
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getTextPaneMoney());
			jContentPane.add(getLblNewLabel_1());
			jContentPane.add(getTextMoneyToCharge());
			jContentPane.add(getLblNewLabel_2());
		}
		return jContentPane;
	}

	private JRadioButton getRdbtnCreditCard() {
		if (rdbtnCreditCard == null) {
			rdbtnCreditCard = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ChargeMoneyGUI.rdbtnNewRadioButton_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
			rdbtnCreditCard.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("CreditCard");
				}
			});
			buttonGroup.add(rdbtnCreditCard);
		}
		return rdbtnCreditCard;
	}

	private JRadioButton getRdbtnBankAccount() {
		if (rdbtnBankAccount == null) {
			rdbtnBankAccount = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("ChargeMoneyGUI.rdbtnNewRadioButton_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
			rdbtnBankAccount.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("BankAccount");
				}
			});
			buttonGroup.add(rdbtnBankAccount);
		}
		return rdbtnBankAccount;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(84, 186, 229, 58);
			panel.add(getRdbtnCreditCard());
			panel.add(getRdbtnBankAccount());
		}
		return panel;
	}

	public void close() {
		this.setVisible(false);
	}
	private JButton getBtnCharge() {
		if (btnCharge == null) {
			btnCharge = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ChargeMoneyGUI.btnNewButton.text"));
			btnCharge.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//TODO implementar pago bien
					
					if(rdbtnCreditCard.isSelected()) {
					CreditCardPayGUI reg = new CreditCardPayGUI(userlog);
					reg.setVisible(true);
					}
					
					facade.addMoney(userlog, Float.parseFloat(textMoneyToCharge.getText()));
				}
			});
			btnCharge.setBounds(148, 255, 90, 23);
		}
		return btnCharge;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ChargeMoneyGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblNewLabel.setBounds(49, 57, 123, 14);
		}
		return lblNewLabel;
	}
	private JTextPane getTextPaneMoney() {
		if (textPaneMoney == null) {
			textPaneMoney = new JTextPane();
			textPaneMoney.setText(userlog.getBalance() +"€"); //$NON-NLS-1$ //$NON-NLS-2$
			textPaneMoney.setBounds(162, 47, 90, 36);
		}
		return textPaneMoney;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ChargeMoneyGUI.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblNewLabel_1.setBounds(84, 136, 73, 14);
		}
		return lblNewLabel_1;
	}
	private JTextField getTextMoneyToCharge() {
		if (textMoneyToCharge == null) {
			textMoneyToCharge = new JTextField();
			textMoneyToCharge.setBounds(162, 133, 86, 20);
			textMoneyToCharge.setColumns(10);
		}
		return textMoneyToCharge;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ChargeMoneyGUI.lblNewLabel_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblNewLabel_2.setBounds(251, 136, 46, 14);
		}
		return lblNewLabel_2;
	}
}
// @jve:decl-index=0:visual-constraint="0,0"
