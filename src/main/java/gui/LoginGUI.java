package gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.AdminUser;
import domain.RegularUser;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textUser;
	private JPasswordField textPass;
	private static BLFacade facade;
	private static RegularUser userlog;

	private JLabel lblUser = new JLabel("Usuario:");
	private JLabel lblPass = new JLabel("Contraseña:");

	private JButton buttonLogin = new JButton("Login");
	private JButton buttonRegister = new JButton("Registrarse");

	public LoginGUI() {
		super();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initialize() {
		setTitle("Login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setBounds(39, 47, 100, 14);
		contentPane.add(lblUser);

		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setBounds(63, 87, 76, 14);
		contentPane.add(lblPass);

		textUser = new JTextField("usuario");
		textUser.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textUser.setBounds(149, 45, 131, 20);
		contentPane.add(textUser);
		textUser.setColumns(10);

		textPass = new JPasswordField("usuario");
		textPass.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textPass.setBounds(149, 85, 131, 20);
		contentPane.add(textPass);
		textPass.setColumns(10);

		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pass = String.valueOf(textPass.getPassword());
				String userName = textUser.getText();
				if (pass.equals("") || userName.equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Usuario o contraseña no introducida", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					boolean b = facade.doLogin(userName, pass);
					if (b) {

						// Check user permissions
						if (facade.isAdmin(userName, pass)) {

							AdminUser au = facade.getAdminUserByUsername(userName);
							JFrame a = new MainAdminGUI(au);
							a.setLocationRelativeTo(null);
							a.setVisible(true);
							textUser.setText("");
							textPass.setText("");
							dispose();

						} else {

							RegularUser ru = facade.getRegularUserByUsername(userName);
							JFrame a = new MainGUI(ru);
							a.setLocationRelativeTo(null);
							a.setVisible(true);
							textUser.setText("");
							textPass.setText("");
							dispose();
						}
					} else {

						JOptionPane.showMessageDialog(null, "Usuario y contraseña no coinciden", "LOGIN INFO",
								JOptionPane.ERROR_MESSAGE, null);
					}
				}
			}
		});

		buttonLogin.setBounds(97, 134, 89, 23);
		contentPane.add(buttonLogin);

		buttonRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Frame reg = new RegisterGUI();
				reg.setAlwaysOnTop(true);
				reg.setVisible(true);
				// dispose();
			}
		});

		buttonRegister.setBounds(220, 134, 135, 23);
		contentPane.add(buttonRegister);

	}

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public void setRegularUserLog(RegularUser user) {
		LoginGUI.userlog = user;
	}

	public static RegularUser getUserLog() {
		return userlog;
	}

}
