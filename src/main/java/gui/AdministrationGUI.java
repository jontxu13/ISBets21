package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import businessLogic.BLFacade;
import domain.AdminUser;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;

import javax.swing.JTextArea;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class AdministrationGUI extends JFrame {

	private DefaultListModel modeloeventos = new DefaultListModel();
	private JList listEventos;
	private JButton mostrarEventos;
	private ArrayList<Event> eventos = new ArrayList<Event>();
	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private JButton mostrarPreguntas;
	private DefaultListModel modelopreguntas = new DefaultListModel();
	private ArrayList<Question> preguntas = new ArrayList<Question>();
	private DefaultListModel modeloPronosticos = new DefaultListModel();
	private Event eventoSeleccionado;
	private Question preguntaSeleccionada;
	private DefaultListModel modelousuarios = new DefaultListModel();
	private ArrayList<User> usuarios = new ArrayList<User>();
	private AdminUser userlog = null;
	private JButton btnAtras;
	
	private String[] nombresColumnasPreguntas = { "Id", "Pregunta", "Evento"};
	private JScrollPane scrollPanePreguntas = new JScrollPane();
	private JTable tablaPreguntas;
	private DefaultTableModel tableModelPreguntas = new DefaultTableModel(null, nombresColumnasPreguntas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	
	
	private String[] nombresColumnas = { "Username", "UserPass", "FirstName", "LastName ", "BirthDate", "Email",
			"BankAccount", "PhoneNumber", "Address", "Balance" };
	private JScrollPane scrollPaneUsuarios = new JScrollPane();
	private JTable tabla;
	private DefaultTableModel tableModelUsuarios = new DefaultTableModel(null, nombresColumnas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public AdministrationGUI(AdminUser u) {

		userlog = u;

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainAdminGUI.buttonAdmin.text"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1300, 720);
		getContentPane().setLayout(null);
		mostrarEventos = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdministrationGUI.mostrarEventos.text")); //$NON-NLS-1$ //$NON-NLS-2$
		mostrarEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Event e : eventos) {
					if(!e.getClosed()) {
						modeloeventos.addElement(e);
					}
				}
				listEventos.setModel(modeloeventos);
				if (listEventos.isVisible()) {
					listEventos.setVisible(false);
				} else {
					listEventos.setVisible(true);

				}

			}
		});
		mostrarEventos.setBounds(165, 47, 140, 26);
		getContentPane().add(mostrarEventos);
		eventos = facade.getAllEvents();

		listEventos = new JList();
		listEventos.setLayoutOrientation(JList.VERTICAL_WRAP);
		listEventos.setVisible(false);
		listEventos.setModel(modeloeventos);

		for (Event e : eventos) {
			if(!e.getClosed()) {
				modeloeventos.addElement(e);
			}
		}
		listEventos.setSelectedValue(modeloeventos.getElementAt(0), true);

		listEventos.setBounds(12, 85, 513, 209);
		getContentPane().add(listEventos);
		preguntas = facade.getAllQuestions();
		for (Question q : preguntas) {
			modelopreguntas.addElement(q);
		}

		mostrarPreguntas = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdministrationGUI.mostrarPreguntas.text")); //$NON-NLS-1$ //$NON-NLS-2$
		mostrarPreguntas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (scrollPanePreguntas.isVisible()) {
					scrollPanePreguntas.setVisible(false);
				} else {
					scrollPanePreguntas.setVisible(true);

				}

			}
		});
		mostrarPreguntas.setBounds(873, 47, 140, 26);
		getContentPane().add(mostrarPreguntas);

		btnAtras = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdministrationGUI.btnAtras.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFrame a = new MainAdminGUI(userlog);
				a.setAlwaysOnTop(true);
				a.setVisible(true);
				dispose();

			}
		});
		btnAtras.setBounds(1161, 618, 99, 26);
		getContentPane().add(btnAtras);
		modelousuarios.addElement("Usuarios:\n");
		modelousuarios.addElement("\n");
		usuarios = facade.getAllUsers();
		for (User e : usuarios) {
			modelousuarios.addElement(e.toString());
		}

		ArrayList<User> usuarios = facade.getAllUsers();

		for (User us : usuarios) {

			Vector<Object> row = new Vector<Object>();

			if (us instanceof AdminUser) {

				row.add(us.getUserName());
				row.add(us.getUserPass());
				row.add(us.getFirstName());
				row.add(us.getLastName());
				tableModelUsuarios.addRow(row);

			} else if (us instanceof RegularUser) {
				row.add(us.getUserName());
				row.add(us.getUserPass());
				row.add(us.getFirstName());
				row.add(us.getLastName());
				row.add(((RegularUser) us).getBirthDate());
				row.add(((RegularUser) us).getEmail());
				row.add(((RegularUser) us).getBankAccount());
				row.add(((RegularUser) us).getPhoneNumb());
				row.add(((RegularUser) us).getAddress());
				row.add(((RegularUser) us).getBalance() + "€");

				tableModelUsuarios.addRow(row);
			}

		}
		tabla = new JTable(tableModelUsuarios) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}

		};
		
		
		
		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 == 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});
		
		
		

		scrollPaneUsuarios.setBounds(new Rectangle(51, 446, 774, 119));
		scrollPaneUsuarios.setViewportView(tabla);
		scrollPaneUsuarios.setVisible(false);

		this.getContentPane().add(scrollPaneUsuarios);

		JButton btnMostrarUsuarios = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("AdministrationGUI.btnMostrarUsuarios.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnMostrarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (scrollPaneUsuarios.isVisible()) {
					scrollPaneUsuarios.setVisible(false);
				} else {
					scrollPaneUsuarios.setVisible(true);

				}

			}
		});
		btnMostrarUsuarios.setBounds(147, 393, 140, 26);
		getContentPane().add(btnMostrarUsuarios);

		
		
		
		ArrayList<Question> preguntas = facade.getAllQuestions();
		

		for (Question q: preguntas) {
			
			Vector<Object> row = new Vector<Object>();
			row.add(q.getQuestionNumber());
			row.add(q.getQuestion());
			row.add(q.getEvent().getDescription());

			tableModelPreguntas.addRow(row);

		}
		
		tablaPreguntas = new JTable(tableModelPreguntas) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}

		};
		
		
		
		tablaPreguntas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 == 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});
		
		
		

		scrollPanePreguntas.setBounds(new Rectangle(571, 103, 689, 113));
		scrollPanePreguntas.setViewportView(tablaPreguntas);
		scrollPanePreguntas.setVisible(false);
		this.getContentPane().add(scrollPanePreguntas);
		
		JButton cancelEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdministrationGUI.cancelEvent.text")); //$NON-NLS-1$ //$NON-NLS-2$
		cancelEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int i = listEventos.getSelectedIndex();
				
				Event e = eventos.get(i);
				
				boolean inserted = facade.cancelEvent(e);
				if (inserted) {
					JOptionPane.showMessageDialog(getContentPane(), "Event closed correctly");
				} else {
					JOptionPane.showMessageDialog(getContentPane(), "Error closing event");
				}
				
				
				
			}
		});
		cancelEvent.setBounds(365, 305, 140, 26);
		getContentPane().add(cancelEvent);

		
		
		
		
	}
}
