package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import domain.CreditCard;
import domain.RegularUser;


public class CreditCardPayGUI extends JPanel implements ActionListener
{

	private final int WIDTH = 400;
	private final int HEIGHT = 100;
	
	private JFrame frame;
	private JPanel panel;
	private JLabel inputLabel, resultLabel;
	private JTextField cardNum;
	private JComboBox cardList;
	private JButton btnValidate = new JButton("Confirm");
	private RegularUser userlog = null;
	private JTextField expiryDate;
	private JTextField securityCode;
	private JTextField textField;
	
	
	//Constructor
	public CreditCardPayGUI(RegularUser ru)
	{
		super();
		userlog = ru;
		//Make the dropdown menu
		String[] cardTypes = {"Visa","MasterCard","Discover","American Express"};
		cardList = new JComboBox(cardTypes);
		cardList.setBounds(236, 5, 111, 20);
		cardList.setSelectedIndex(0);
		
		inputLabel = new JLabel("Enter a credit card type and number:");
		inputLabel.setBounds(53, 8, 178, 14);
		resultLabel = new JLabel("---"); //Changes with validation
		resultLabel.setBounds(303, 34, 12, 14);
		
		cardNum = new JTextField(16);
		cardNum.setBounds(84, 31, 134, 20);
		
		//Set the panel
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		panel.add(inputLabel);
		panel.add(cardList);
		panel.add(cardNum);
		btnValidate.setBounds(223, 30, 75, 23);
		panel.add(btnValidate);
		panel.add(resultLabel);
		btnValidate.addActionListener(this);
		
		frame = new JFrame("Credit Card Validator");
		frame.setAlwaysOnTop(true);
		
		//Set the frame up.
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane();
		frame.getContentPane().add(panel);
		
		expiryDate = new JTextField();
		expiryDate.setBounds(304, 53, 86, 20);
		panel.add(expiryDate);
		expiryDate.setColumns(10);
		
		securityCode = new JTextField();
		securityCode.setColumns(10);
		securityCode.setBounds(304, 77, 86, 20);
		panel.add(securityCode);
		
		JLabel lblNewLabel = new JLabel("Expiry date:");
		lblNewLabel.setBounds(233, 56, 62, 14);
		panel.add(lblNewLabel);
		
		JLabel lblSecurityCode = new JLabel("Security code:");
		lblSecurityCode.setBounds(223, 80, 71, 14);
		panel.add(lblSecurityCode);
		
		textField = new JTextField(16);
		textField.setBounds(84, 62, 134, 20);
		panel.add(textField);
		
		JLabel lblNameOnCard = new JLabel("Name on card:");
		lblNameOnCard.setBounds(10, 65, 74, 14);
		panel.add(lblNameOnCard);
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String input1 = cardNum.getText();
		long number = Long.parseLong(input1);
		String type = (String)cardList.getSelectedItem();
		int sCode = Integer.parseInt(securityCode.getText());	
		
		CreditCard cc = new CreditCard(type,number);
		if(cc.validate())
		{
			resultLabel.setText("Card is valid.");
		}
		else
		{
			resultLabel.setText("Card is invalid.");
		}
	}
}