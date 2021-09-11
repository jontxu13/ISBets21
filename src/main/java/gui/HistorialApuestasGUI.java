package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import domain.Bet;
import domain.RegularUser;

public class HistorialApuestasGUI extends JFrame {

	private JList listApuestas;

	private DefaultListModel modeloApuestas = new DefaultListModel();

	private DefaultListModel modeloApuestas2 = new DefaultListModel();

	private RegularUser userlog = null;

	private JButton btnAtras;

	private JList listApuestas2;

	public HistorialApuestasGUI(RegularUser ru) {

		userlog = ru;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setSize(1388, 541);
		getContentPane().setLayout(null);

		listApuestas = new JList();
		listApuestas.setBounds(45, 47, 1102, 169);
		getContentPane().add(listApuestas);
		listApuestas.setModel(modeloApuestas);
		modeloApuestas.add(0,
				"   FECHA                                               EVENTO                                               PREGUNTA                                             PRONÓSTICO                                                 CANT. APOSTADA                                            CUOTA"); // 30

		btnAtras = new JButton("Atrás");
		btnAtras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JFrame a = new MainGUI(userlog);
				a.setAlwaysOnTop(true);
				a.setVisible(true);
				dispose();

			}
		});
		btnAtras.setBounds(12, 440, 99, 26);
		getContentPane().add(btnAtras);

		listApuestas2 = new JList();
		listApuestas2.setBounds(45, 251, 1102, 98);
		getContentPane().add(listApuestas2);
		listApuestas2.setModel(modeloApuestas2);
		modeloApuestas2.add(0,
				"   FECHA                       EVENTO                                       PREGUNTA                                                PRONÓSTICO                                  ESTADO                     APUESTA                                CANT                        CUOTA    ");

		JLabel lblNewLabel = new JLabel("Apuestas abiertas");
		lblNewLabel.setBounds(81, 22, 123, 13);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Apuestas cerradas");
		lblNewLabel_1.setBounds(81, 228, 123, 13);
		getContentPane().add(lblNewLabel_1);

		Vector<Bet> apuestasUsuario = userlog.getBets();

		for (Bet b : apuestasUsuario) {

			if (b.getForecast().getQuestion().getResult() == null) {
				modeloApuestas.addElement(b.toString3());
			} else {

				modeloApuestas2.addElement(b.toString2());
			}
		}

	}

}
