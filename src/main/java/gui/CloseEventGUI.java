package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.AdminUser;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class CloseEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneForecasts = new JScrollPane();

	private ArrayList<Date> datesWithEventsCurrentMonth = new ArrayList<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableForecasts = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelForecasts;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	private String[] columnNamesForecasts = new String[] { ResourceBundle.getBundle("Etiquetas").getString("ForecastN"),
			ResourceBundle.getBundle("Etiquetas").getString("Forecast"), "Fee"

	};
	private Question que;
	private Forecast bet;
	private static BLFacade facade = LoginGUI.getBusinessLogic();

	private User userlog = null;

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public CloseEventGUI(User u) {

		userlog = u;

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainAdminGUI.buttonCloseEvent.text"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(10, 211, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(423, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = LoginGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = LoginGUI.getBusinessLogic();

						ArrayList<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev : events) {
							if (!ev.getClosed()) {
								Vector<Object> row = new Vector<Object>();

								System.out.println("Events " + ev);

								row.add(ev.getEventNumber());
								row.add(ev.getDescription());
								row.add(ev); // ev object added in order to obtain it with
												// tableModelEvents.getValueAt(i,2)
								tableModelEvents.addRow(row);
							}
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
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));

		scrollPaneQueries.setBounds(new Rectangle(10, 236, 372, 116));

		scrollPaneForecasts.setBounds(new Rectangle(410, 236, 204, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
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
					if(!q.getClosed()) {
						Vector<Object> row = new Vector<Object>();
						row.add(q.getQuestionNumber());
						row.add(q.getQuestion());
						row.add(q);
						tableModelQueries.addRow(row);
					}
				}
				tableQueries.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						tableModelForecasts.setRowCount(0);
						int i = tableQueries.getSelectedRow();
						if(!queries.isEmpty()) {
						que = queries.get(i);
						Vector<Forecast> forecasts = que.getForecasts();
						for (domain.Forecast f : forecasts) {
							
							Vector<Object> row = new Vector<Object>();
							row.add(f.getForecastNumber());
							row.add(f.getForecast());
							row.add(f.getFee());
							tableModelForecasts.addRow(row);
						}
						tableForecasts.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								int i = tableForecasts.getSelectedRow();
								if(forecasts.isEmpty()==false) {
									bet = forecasts.get(i);

								}
								System.out.println(bet.toString());
							}
						});
						}
					}
				});

				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); 
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneForecasts.setViewportView(tableForecasts);
		tableModelForecasts = new DefaultTableModel(null, columnNamesForecasts);

		tableForecasts.setModel(tableModelForecasts);
		tableForecasts.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableForecasts.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableForecasts.getColumnModel().getColumn(2).setPreferredWidth(25);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPaneForecasts);

		JButton btnCloseBet = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("CloseEventGUI.btnCloseBet.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCloseBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(que.toString());
				boolean inserted = facade.closeEvent(que.getEvent(), que, bet);
				if (inserted) {
					JOptionPane.showMessageDialog(getContentPane(), "Evento cerrado correctamente");
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "Error al cerrar evento");
				}
			}
		});
		btnCloseBet.setBounds(new Rectangle(100, 275, 130, 30));
		btnCloseBet.setBounds(120, 419, 130, 30);
		getContentPane().add(btnCloseBet);

		JLabel lblNewLabel = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("CloseBetGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(410, 211, 144, 14);
		getContentPane().add(lblNewLabel);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		Frame gui = new MainAdminGUI((AdminUser) userlog);
		gui.setAlwaysOnTop(true);
		gui.setVisible(true);
		dispose();
	}
}
