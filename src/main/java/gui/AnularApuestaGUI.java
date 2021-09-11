package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import businessLogic.BLFacade;
import domain.Bet;
import domain.RegularUser;

import javax.swing.Action;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AnularApuestaGUI extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPaneApuestas = new JScrollPane();
	private JTable tabla;
	private String[] nombresColumnas = { "Fecha", "Evento", "Pregunta", "Apuesta a ", "Cant. apostada", "Cuota", "" };
	private DefaultTableModel tableModelApuestas = new DefaultTableModel(null, nombresColumnas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private static BLFacade facade = LoginGUI.getBusinessLogic();

	private RegularUser userlog;
	private JButton btnAtras;

	public AnularApuestaGUI(RegularUser ru) {
		setTitle("Cancel Bet");
		userlog = ru;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1300, 720);
		getContentPane().setLayout(null);

		JButton btnEliminar = new JButton("Cancel");
		btnEliminar.setName("Cancel");
		Vector<Bet> apuestasUsuario = userlog.getBets();
		Vector<Bet> apuestasAbiertas = new Vector<Bet>();
		for (Bet bet : apuestasUsuario) {
			if (bet.getEstadoApuesta().equals("Awaiting")) {
				Vector<Object> row = new Vector<Object>();
				row.add(bet.getForecast().getQuestion().getEvent().getEventDate().toString().substring(0, 11));
				row.add(bet.getForecast().getQuestion().getEvent().getDescription());
				row.add(bet.getForecast().getQuestion().getQuestion());
				row.add(bet.getForecast().getForecast());
				row.add(bet.getAmount());
				row.add(bet.getForecast().getFee());
				row.addElement(btnEliminar);
				apuestasAbiertas.addElement(bet);
				tableModelApuestas.addRow(row);
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
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {

				int column = tabla.getColumnModel().getColumnIndexAtX(ev.getX());
				int row = ev.getY() / tabla.getRowHeight();

				if (row < tabla.getRowCount() && row >= 0 && column < tabla.getColumnCount() && column >= 0) {
					Object value = tabla.getValueAt(row, column);
					if (value instanceof JButton) {
						((JButton) value).doClick();
						JButton boton = (JButton) value;
						try {
							int i = tabla.getSelectedRow();
							Bet apu = apuestasAbiertas.get(i);
							System.out.println("Canceling bet: " + apu.getForecast().getForecast() + " / "
									+ apu.getForecast().getQuestion() + " / "
									+ apu.getForecast().getQuestion().getEvent().getDescription() + "from table");
							tableModelApuestas.removeRow(row);
							apuestasAbiertas.remove(i);

							if (facade.anularApuesta(apu)) {
								System.out.println("Bet canceled");
							}

						} catch (Exception e) {
						}

					}
				}

			}
		});

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

		tabla.setDefaultRenderer(Object.class, new Render());

		scrollPaneApuestas.setBounds(new Rectangle(40, 34, 774, 119));
		scrollPaneApuestas.setViewportView(tabla);
		this.getContentPane().add(scrollPaneApuestas);

		btnAtras = new JButton("Back");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				RegularUser usuarioActualizado = facade.getRegularUserByUsername(userlog.getUserName());

				JFrame a = new MainGUI(usuarioActualizado);
				a.setAlwaysOnTop(true);
				a.setVisible(true);
				dispose();

			}
		});
		btnAtras.setBounds(12, 613, 99, 26);
		getContentPane().add(btnAtras);

	}
}
