package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.AdminUser;
import domain.Event;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.beans.PropertyChangeEvent;

public class CreateEventGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textEvento;
	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;
	
	private AdminUser userlog = null;
	private JButton btnAtras;
	
	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public CreateEventGUI(AdminUser au) {
		
		userlog = au;
		
		setTitle("Create Event");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 585, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JCalendar jCalendar1 = new JCalendar();
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) evt.getNewValue());
				} else if (evt.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) evt.getOldValue();
					calendarAct = (Calendar) evt.getNewValue();

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

					}

				}
			}
		});
		jCalendar1.setBounds(163, 30, 266, 175);
		contentPane.add(jCalendar1);

		JLabel lblNewLabel = new JLabel("Date:");
		lblNewLabel.setBounds(41, 115, 103, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Event:");
		lblNewLabel_1.setBounds(41, 243, 113, 14);
		contentPane.add(lblNewLabel_1);

		textEvento = new JTextField();
		textEvento.setBounds(163, 240, 266, 20);
		contentPane.add(textEvento);
		textEvento.setColumns(10);

		JButton btnCrearEvento = new JButton("Create Event");
		btnCrearEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));
				
				if (textEvento.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "the event can not be void", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					
					Event ev = new Event(facade.getMaxIdInDB() + 1, textEvento.getText(), date);
					
					boolean exist = facade.existEvent(ev);
					
					if ( exist == false) {
						
						boolean ins = facade.insertEvent(ev);

						if (ins) {
							JOptionPane.showMessageDialog(contentPane, "Event added correctly");

						}
						else {
							JOptionPane.showMessageDialog(contentPane, "Could not add event");

						}

						Frame gui = new MainAdminGUI(userlog);
						gui.setAlwaysOnTop(true);
						gui.setVisible(true);
						close();
					} else {
						JOptionPane.showMessageDialog(contentPane, "Event already exists");
					}
				}
				

			}
		});
		btnCrearEvento.setBounds(220, 310, 113, 23);
		contentPane.add(btnCrearEvento);
		
		btnAtras = new JButton("Back");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Frame fr = new MainAdminGUI(userlog);
				fr.setAlwaysOnTop(true);
				fr.setVisible(true);
				dispose();
				
			}
		});
		btnAtras.setBounds(12, 310, 71, 23);
		contentPane.add(btnAtras);
	}

	public void close() {
		this.setVisible(false);
	}
}
