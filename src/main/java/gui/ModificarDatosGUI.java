package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.RegularUser;

public class ModificarDatosGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fieldUsername;
	private JPasswordField fieldContraseña;
	private JPasswordField fieldContraseña2;
	private JPasswordField fieldContraseña3;
	private JCheckBox checkContraseña;
	private JTextField fieldNombre;
	private JTextField fieldApellido;
	private JTextField fieldEmail;
	private JTextField fieldCuentaBancaria;
	private JLabel lblUsername;
	private JLabel lblContraseña;
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JLabel lblEmail;
	private JLabel lblCuentaBancaria;
	private JButton buttonCancelar;
	private JButton buttonModificar;
	private JButton buttonGuardar;
	private static BLFacade facade = LoginGUI.getBusinessLogic();

	private RegularUser userlog = null;
	private JTextField textSaldo;
	private JLabel lblSaldo;

	public ModificarDatosGUI(RegularUser ru) {

		userlog = ru;

		setTitle("Modificar datos de usuario");
		getContentPane().setLayout(null);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 812, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		fieldUsername = new JTextField();
		fieldUsername.setBounds(249, 66, 262, 29);
		contentPane.add(fieldUsername);
		fieldUsername.setColumns(10);
		fieldUsername.setEditable(false);
		fieldUsername.setText(userlog.getUserName());

		fieldContraseña = new JPasswordField();
		fieldContraseña.setBounds(249, 105, 262, 29);
		contentPane.add(fieldContraseña);
		fieldContraseña.setColumns(10);
		fieldContraseña.setEditable(false);
		fieldContraseña.setText(userlog.getUserPass());

		checkContraseña = new JCheckBox("Mostrar contraseña");
		checkContraseña.setVisible(false);
		checkContraseña.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (checkContraseña.isSelected()) {

					fieldContraseña2.setEchoChar((char) 0);
					fieldContraseña3.setEchoChar((char) 0);

				} else {

					fieldContraseña2.setEchoChar('•');
					fieldContraseña3.setEchoChar('•');

				}

			}
		});
		checkContraseña.setBounds(519, 168, 179, 21);
		contentPane.add(checkContraseña);

		fieldNombre = new JTextField();
		fieldNombre.setText((String) null);
		fieldNombre.setEditable(false);
		fieldNombre.setColumns(10);
		fieldNombre.setBounds(249, 144, 262, 29);
		contentPane.add(fieldNombre);
		fieldNombre.setText(userlog.getFirstName());

		fieldApellido = new JTextField();
		fieldApellido.setText((String) null);
		fieldApellido.setEditable(false);
		fieldApellido.setColumns(10);
		fieldApellido.setBounds(249, 183, 262, 29);
		contentPane.add(fieldApellido);
		fieldApellido.setText(userlog.getLastName());

		fieldEmail = new JTextField();
		fieldEmail.setText((String) null);
		fieldEmail.setEditable(false);
		fieldEmail.setColumns(10);
		fieldEmail.setBounds(249, 222, 262, 29);
		contentPane.add(fieldEmail);
		fieldEmail.setText(userlog.getEmail());

		fieldCuentaBancaria = new JTextField();
		fieldCuentaBancaria.setText((String) null);
		fieldCuentaBancaria.setEditable(false);
		fieldCuentaBancaria.setColumns(10);
		fieldCuentaBancaria.setBounds(249, 261, 262, 29);
		contentPane.add(fieldCuentaBancaria);
		fieldCuentaBancaria.setText(userlog.getBankAccount());

		buttonModificar = new JButton("Modificar");
		buttonModificar.addActionListener(new ActionListener() {
			private JLabel lblContraseña2;
			private JLabel lblContraseña3;

			@Override
			public void actionPerformed(ActionEvent e) {

				checkContraseña.setVisible(true);

				fieldContraseña2 = new JPasswordField();
				fieldContraseña2.setBounds(249, 144, 262, 29);
				contentPane.add(fieldContraseña2);
				fieldContraseña2.setColumns(10);
				fieldContraseña2.setEditable(true);

				fieldContraseña3 = new JPasswordField();
				fieldContraseña3.setBounds(249, 183, 262, 29);
				contentPane.add(fieldContraseña3);
				fieldContraseña3.setColumns(10);
				fieldContraseña3.setEditable(true);

				fieldNombre.setBounds(249, 222, 262, 29);
				fieldApellido.setBounds(249, 261, 262, 29);
				fieldEmail.setBounds(249, 300, 262, 29);
				fieldCuentaBancaria.setBounds(249, 339, 262, 29);
				textSaldo.setBounds(249, 384, 262, 29);

				
				fieldNombre.setEditable(true);
				fieldApellido.setEditable(true);
				fieldEmail.setEditable(true);
				fieldCuentaBancaria.setEditable(true);
				
				
				lblContraseña2 = new JLabel("Contraseña nueva:");
				lblContraseña2.setBounds(10, 150, 133, 13);
				contentPane.add(lblContraseña2);

				lblContraseña3 = new JLabel("Repite contraseña nueva:");
				lblContraseña3.setBounds(10, 189, 133, 13);
				contentPane.add(lblContraseña3);

				lblNombre.setBounds(10, 228, 133, 13);
				lblApellido.setBounds(10, 267, 133, 13);
				lblEmail.setBounds(10, 306, 133, 13);
				lblCuentaBancaria.setBounds(10, 345, 133, 13);
				lblSaldo.setBounds(10, 384, 133, 13);

				
				buttonModificar.setEnabled(false);
				buttonModificar.setVisible(false);
				buttonGuardar.setVisible(true);
				buttonGuardar.setEnabled(true);
			}
		});
		buttonModificar.setBounds(648, 429, 127, 42);
		contentPane.add(buttonModificar);

		lblUsername = new JLabel("Nombre de usuario:");
		lblUsername.setBounds(10, 74, 133, 13);
		contentPane.add(lblUsername);

		lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(10, 111, 133, 13);
		contentPane.add(lblContraseña);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 150, 133, 13);
		contentPane.add(lblNombre);

		lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(10, 189, 133, 13);
		contentPane.add(lblApellido);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 228, 133, 13);
		contentPane.add(lblEmail);

		lblCuentaBancaria = new JLabel("Cuenta bancaria:");
		lblCuentaBancaria.setBounds(10, 267, 133, 13);
		contentPane.add(lblCuentaBancaria);

		buttonCancelar = new JButton("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame reg = new MainGUI(userlog);
				reg.setAlwaysOnTop(true);
				reg.setVisible(true);
				dispose();
				dispose();
			}
		});
		buttonCancelar.setBounds(27, 429, 127, 42);
		contentPane.add(buttonCancelar);

		buttonGuardar = new JButton("Guardar");
		buttonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean resul = false;
				boolean resul2 = false;
				if (fieldContraseña2.getText().equals("")) {

					resul2 = facade.editarPerfilUsuarioSinPass(fieldUsername.getText(), fieldNombre.getText(),
							fieldApellido.getText(), fieldEmail.getText(), fieldCuentaBancaria.getText());

				} else {

					if (fieldContraseña2.getText().equals(fieldContraseña3.getText())) {

						if (RegisterGUI.validoContraseña(fieldContraseña2.getText())) {

							resul = facade.editarPerfilUsuario(fieldContraseña2.getText(), fieldUsername.getText(),
									fieldNombre.getText(), fieldApellido.getText(), fieldEmail.getText(),
									fieldCuentaBancaria.getText());

						} else {
							JOptionPane.showMessageDialog(getContentPane(), "Contraseña no válida!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(getContentPane(), "Las contraseñas no coinciden!", "Error",
								JOptionPane.ERROR_MESSAGE);

					}

				}

				if (resul || resul2) {
					JOptionPane.showMessageDialog(getContentPane(), "Cambios guardados correctamente");
					RegularUser usuarioactualizado = facade.getRegularUserByUsername(fieldUsername.getText());
					Frame reg = new MainGUI(usuarioactualizado);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
					dispose();

				}

			}
		});
		buttonGuardar.setBounds(648, 429, 127, 42);
		contentPane.add(buttonGuardar);
		buttonGuardar.setVisible(false);
		buttonGuardar.setEnabled(false);
		
		textSaldo = new JTextField();
		textSaldo.setText((String) null);
		textSaldo.setEditable(false);
		textSaldo.setColumns(10);
		textSaldo.setBounds(249, 306, 262, 29);
		contentPane.add(textSaldo);
		textSaldo.setText(Float.toString(userlog.getBalance()) + " €");
		
		
		lblSaldo = new JLabel("Saldo:");
		lblSaldo.setBounds(10, 306, 133, 13);
		contentPane.add(lblSaldo);

	}
}
