package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import businessLogic.BLFacade;
import domain.Bet;
import domain.RegularUser;

import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class BetHistoryGUI extends JFrame {

	private JScrollPane scrollPaneApuestas = new JScrollPane();
	private JScrollPane scrollPaneApuestas2 = new JScrollPane();

	private JTable tabla;
	private JTable tabla2;

	private String[] nombresColumnas = { "Date", "Event", "Question", "Bet for", "Amount", "Fee", "State"};
	private String[] nombresColumnas2 = { "Date", "Event", "Question", "Bet for", "State", "Winner",
			"Amount", "Fee", "Win (Benef.)" };

	private DefaultTableModel tableModelApuestas = new DefaultTableModel(null, nombresColumnas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private DefaultTableModel tableModelApuestas2 = new DefaultTableModel(null, nombresColumnas2) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private RegularUser userlog;
	
	private static BLFacade facade = LoginGUI.getBusinessLogic();


	public BetHistoryGUI(RegularUser ru) {
		setTitle("History");

		userlog = ru;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().setLayout(null);
		this.setSize(1288, 641);

		Vector<Bet> apuestasUsuario = userlog.getBets();

		/* TABLA 1 APUESTAS ABIERTAS */

		// Introducir datos de las dos tablas a los tablemodel, dependiendo de si la
		// apuesta esta cerrada o no
		for (Bet b : apuestasUsuario) {

			if (b.getForecast().getQuestion().getResult() == null && b.getEstadoApuesta().equals("Canceled") == false) { // PRIMERA TABLA APUESTAS ABIERTAS
				Vector<Object> row = new Vector<Object>();
				row.add(b.getForecast().getQuestion().getEvent().getEventDate().toString().substring(0, 11));
				row.add(b.getForecast().getQuestion().getEvent().getDescription());
				row.add(b.getForecast().getQuestion().getQuestion());
				row.add(b.getForecast().getForecast());
				row.add(b.getAmount() + "€");
				row.add(b.getForecast().getFee());
				row.add(b.getEstadoApuesta());

				tableModelApuestas.addRow(row);
			} else { // SEGUNDA TABLA APUESTAS CERRADAS
				Vector<Object> row = new Vector<Object>();
				row.add(b.getForecast().getQuestion().getEvent().getEventDate().toString().substring(0, 11));
				row.add(b.getForecast().getQuestion().getEvent().getDescription());
				row.add(b.getForecast().getQuestion().getQuestion());
				row.add(b.getForecast().getForecast());
				row.add(b.getEstadoApuesta());
				row.add(b.getForecast().getQuestion().getResult());
				row.add(b.getAmount() + "€");
				row.add(b.getForecast().getFee());
				
					if (b.getEstadoApuesta().equals("Lose")) {
						row.add("");

					}
					else if(b.getEstadoApuesta().equals("Canceled")) {
						
					}
					else {
				
					if(Float.toString((b.getAmount()*b.getForecast().getFee()-b.getAmount())).equals("0.0")) {
						row.add(b.getAmount()*b.getForecast().getFee());

					}
					else {
						row.add(b.getAmount()*b.getForecast().getFee() + " (" + Float.toString((b.getAmount()*b.getForecast().getFee()-b.getAmount()))+ ")€");

					}
					}
				tableModelApuestas2.addRow(row);
			}

		}

		// OVERRIDE DE METODO PARA QUE SE AJUSTEN AUTOMATICAMENTE LAS COLUMNAS DE LA
		// TABLA SEGUN EL CONTENIDO
		tabla = new JTable(tableModelApuestas) {
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

		// COLOR DE LAS FILAS DE LA TABLA GRIS Y BLANCO
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

		scrollPaneApuestas.setBounds(new Rectangle(35, 60, 774, 119));
		scrollPaneApuestas.setViewportView(tabla);
		this.getContentPane().add(scrollPaneApuestas);

		/* TABLA 2 APUESTAS CERRADAS */

		// OVERRIDE DE METODO PARA QUE SE AJUSTEN AUTOMATICAMENTE LAS COLUMNAS DE LA
		// TABLA SEGUN EL CONTENIDO
		tabla2 = new JTable(tableModelApuestas2) {
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

		// COLOR DE LAS FILAS DE LA TABLA GRIS Y BLANCO
		tabla2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 == 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});

		scrollPaneApuestas2.setBounds(new Rectangle(35, 243, 1089, 119));
		scrollPaneApuestas2.setViewportView(tabla2);

		getContentPane().add(scrollPaneApuestas2);

		/* BOTON ATRAS, BOTONES DESCARGAR TABLA Y LABELS */

		JButton btnAtras = new JButton("Back");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFrame a = new MainGUI(userlog);
				a.setAlwaysOnTop(true);
				a.setVisible(true);
				dispose();
			}
		});
		btnAtras.setBounds(35, 449, 99, 26);
		getContentPane().add(btnAtras);

		JLabel lblApuestasAbiertas = new JLabel("Bets open");
		lblApuestasAbiertas.setBounds(49, 25, 147, 16);
		getContentPane().add(lblApuestasAbiertas);

		JLabel lblApuestasCerradas = new JLabel("Bets closed");
		lblApuestasCerradas.setBounds(49, 208, 147, 16);
		getContentPane().add(lblApuestasCerradas);
		
		
		
		
		
		JLabel lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateBetGUI.lblSaldo_2.text") + " " + userlog.getBalance() +"€"); //$NON-NLS-1$ //$NON-NLS-2$
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaldo.setForeground(Color.BLUE);
		lblSaldo.setBounds(1069, 23, 197, 19);
		getContentPane().add(lblSaldo);
		
		

	}
}
