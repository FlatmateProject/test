package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import service.GuestBook;
import service.Manager;
import validation.ValidationUtils;

public class ManagerPanel extends JPanel {

	
	private static final Logger log = Logger.getLogger(ManagerPanel.class);
	
	private Calendar schCalendar = GregorianCalendar.getInstance();

	private static final long serialVersionUID = 1L;
	private JPanel manDataPan;
	private JButton manButton[];
	private JButton manButton2[];
	private JList manNews;
	private JScrollPane manScrollPane = new JScrollPane();
	private JTable manTable;
	private String manName = "klienci";
	private JLabel manLabel[];
	private JTextField manData[];

	private Color bgColor = new Color(224, 230, 233);
	private MouseListener manTableML = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				for (int i = 0; i < manData.length; i++) {
					manData[i].setText((String) manTable.getValueAt(
							manTable.getSelectedRow(), i));
				}
			} catch (Exception e) {
				log.info("Brak danych!");
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	};
	private Color buttonColor = new Color(174, 205, 214);
	private Manager man = new Manager();
	private GuestBook gue = new GuestBook();

	public ManagerPanel() {
		create();
		addEvents();
	}

	private void create() {

		setBackground(bgColor);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		manButton = new JButton[10];
		manButton2 = new JButton[5];

		manButton2[0] = new JButton("Dodaj");
		manButton2[1] = new JButton("Usu�");
		manButton2[2] = new JButton("Edytuj");
		manButton2[3] = new JButton("Szukaj");
		manButton2[4] = new JButton("Wyczy��");

		manButton[0] = new JButton("Klienci");
		manButton[1] = new JButton("Firmy");
		manButton[2] = new JButton("Us�ugi");
		manButton[3] = new JButton("Pokoje");
		manButton[4] = new JButton("Stanowiska");
		manButton[5] = new JButton("Waluty");
		manButton[6] = new JButton("Pracownicy");
		manButton[7] = new JButton("Klasy");
		manButton[8] = new JButton("Archiwum");
		manButton[9] = new JButton("Rachunki");

		manButton2[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String l[] = new String[manData.length];
				String d[] = new String[manData.length];
				for (int i = 0; i < manData.length; i++) {
					l[i] = manLabel[i].getText();
					d[i] = manData[i].getText();
					if (!d[i].isEmpty()) {
						if ((l[i].equals("IDK_PESEL") || l[i]
								.equals("IDP_PESEL"))
								&& !ValidationUtils.isPesel(d[i])) {
							JOptionPane.showMessageDialog(null,
									"B��dny PESEL!", "UWAGA!",
									JOptionPane.ERROR_MESSAGE);
							break;
						} else if (l[i].equals("IDF_KRS")
								&& !ValidationUtils.isKRS(d[i])) {
							JOptionPane.showMessageDialog(null, "B��dny KRS!",
									"UWAGA!", JOptionPane.ERROR_MESSAGE);
							break;
						} else if ((l[i].equals("DATA_Z")
								|| l[i].equals("DATA_W") || l[i].equals("DATA"))
								&& !ValidationUtils.isDate(d[i])) {
							JOptionPane.showMessageDialog(null, "B��dna data!",
									"UWAGA!", JOptionPane.ERROR_MESSAGE);
							break;
						} else if ((l[i].equals("CENA_SP")
								|| l[i].equals("CENA_KU")
								|| l[i].equals("ILOSC")
								|| l[i].equals("WARTOSC")
								|| l[i].equals("PODATEK")
								|| l[i].equals("IL_OSOB")
								|| l[i].equals("ID_STANOWISKA")
								|| l[i].equals("TELEFON") || l[i].equals("NIP")
								|| l[i].equals("NR_LOKALU")
								|| l[i].equals("REGON") || l[i].equals("CENA")
								|| l[i].equals("PODSTAWA")
								|| l[i].equals("PREMIA")
								|| l[i].equals("ID_POKOJU")
								|| l[i].equals("ID_KLASY")
								|| l[i].equals("ID_REZ") || l[i]
									.equals("ID_USLUGI"))
								&& !ValidationUtils.isNumber(d[i])) {
							JOptionPane.showMessageDialog(null,
									"B��dna liczba!", "UWAGA!",
									JOptionPane.ERROR_MESSAGE);
							break;
						}
					}
					if (i == manData.length - 1) {
						if (!man.insertData(manName, l, d, manData.length)) {
							JOptionPane.showMessageDialog(null,
									"B��dne ID lub taki klient ju� istnieje!",
									"UWAGA!", JOptionPane.ERROR_MESSAGE);
							break;
						}
						manTable = manGenTable(manName);
						manTable.addMouseListener(manTableML);
						manScrollPane.setViewportView(manTable);
					}
				}
			}
		});
		manButton2[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!manData[0].getText().isEmpty()) {
					String l = new String(manLabel[0].getText());
					String d = new String(manData[0].getText());
					if (!man.deleteData(manName, l, d)) {
						JOptionPane.showMessageDialog(null,
								"Nie mo�na usun�� tego wiersza!", "UWAGA!",
								JOptionPane.ERROR_MESSAGE);
					} else {
						manTable = manGenTable(manName);
						manTable.addMouseListener(manTableML);
						manScrollPane.setViewportView(manTable);
					}
				}
			}
		});
		manButton2[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String l[] = new String[manData.length];
				String d[] = new String[manData.length];
				for (int i = 0; i < manData.length; i++) {
					l[i] = manLabel[i].getText();
					d[i] = manData[i].getText();
				}
				man.updateData(manName, l, d, manData.length);
				manTable = manGenTable(manName);
				manTable.addMouseListener(manTableML);
				manScrollPane.setViewportView(manTable);
			}
		});
		manButton2[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s2 = "";
				for (int i = 0; i < manData.length; i++) {
					if (!manData[i].getText().isEmpty()) {
						if (!s2.isEmpty()) {
							s2 = s2 + " and ";
						}
						s2 = s2 + manLabel[i].getText() + "=" + "\""
								+ manData[i].getText() + "\"";
					}
				}
				if (!s2.isEmpty()) {
					manTable = gue.createTable(manName, " where " + s2);
					manTable.addMouseListener(manTableML);
					manScrollPane.setViewportView(manTable);
				}
			}
		});
		manButton2[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < manData.length; i++) {
					manData[i].setText("");
					manTable = gue.createTable(manName, "");
					manTable.addMouseListener(manTableML);
					manScrollPane.setViewportView(manTable);
				}
			}
		});

		manButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "klienci";
				manAction(manName);
			}
		});
		manButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "firmy";
				manAction(manName);
			}
		});
		manButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "uslugi";
				manAction(manName);
			}
		});
		manButton[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "pokoje";
				manAction(manName);
			}
		});
		manButton[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "stanowiska";
				manAction(manName);
			}
		});
		manButton[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "waluty";
				manAction(manName);
			}
		});
		manButton[6].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "pracownicy";
				manAction(manName);
			}
		});
		manButton[7].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "klasy";
				manAction(manName);
			}
		});
		manButton[8].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "archiwum";
				manAction(manName);
			}
		});
		manButton[9].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				manName = "rachunki";
				manAction(manName);
			}
		});

		String news[] = {
				"Og�lna ilo�� rezerwacji: " + man.getCount("rezerwacje"),
				"Ilo�� zarejestrowanych go�ci: " + man.getCount("klienci"),
				man.getCount("pokoje") + " pokoi, z czego "
//						+ man.getCount("pokoje where id_rez is null")
						+ " wolnych i "
//						+ man.getCount("pokoje where id_rez is not null")
						+ " zaj�tych.",
				"Ilo�� dost�pnych us�ug: " + man.getCount("uslugi"),
				"W tym miesi�cu oczekujemy na "
						+ man.getCount("rezerwacje where month(data_z) = "
								+ (schCalendar.get(Calendar.MONTH) + 1))
						+ ", a �egnamy "
						+ man.getCount("rezerwacje where month(data_w) = "
								+ (schCalendar.get(Calendar.MONTH) + 1))
						+ " go�ci" };
		manNews = new JList(news);
		manNews.setBackground(bgColor);

		manTable = manGenTable("klienci");
		manTable.addMouseListener(manTableML);
		manScrollPane = new JScrollPane(manTable);

		createDataPanel("klienci");
		add(manDataPan);

		add(manScrollPane);
		add(manNews);

		for (int i = 0; i < manButton.length; i++) {
			manButton[i].setBackground(buttonColor);
			add(manButton[i]);
		}
		for (int i = 0; i < manButton2.length; i++) {
			manButton2[i].setBackground(buttonColor);
			add(manButton2[i]);
		}
	}

	private JTable manGenTable(String name) {
		String manData[][] = man.createTable(name);
		String columnNames[] = new String[manData[0].length];
		Object rowData[][] = new Object[manData.length - 1][manData[0].length];

		for (int i = 0; i < manData.length; i++) {
			for (int j = 0; j < manData[i].length; j++) {
				if (i == 0) {
					columnNames[j] = manData[i][j];
				} else {
					rowData[i - 1][j] = manData[i][j];
				}
			}
		}
		return new JTable(rowData, columnNames);
	}

	private JPanel createDataPanel(String name) {
		String cols[] = man.getColumns(name);
		int colCount = (cols != null ? cols.length : 0);
		manLabel = new JLabel[colCount];
		manData = new JTextField[colCount];

		manDataPan = new JPanel();
		manDataPan.setLayout(null);
		manDataPan.setBounds(0, 0, 340, (colCount + 1) * 20);
		manDataPan.setBackground(bgColor);

		int manX = 30, manY = 20;
		if (cols == null) {
			return manDataPan;
		}
		for (int i = 0; i < cols.length; i++) {
			manLabel[i] = new JLabel(cols[i]);
			if (i == 0)
				manY = 20;
			else
				manY = 20 * (i + 1);
			manLabel[i].setBounds(manX, manY, 150, 20);

			manData[i] = new JTextField();
			manData[i].setBounds(manLabel[i].getX() + 150, manLabel[i].getY(),
					150, 19);
			// manData[i].setBorder(border);

			manDataPan.add(manLabel[i]);
			manDataPan.add(manData[i]);
		}
		return manDataPan;
	}

	private void manAction(String manName) {
		manTable = manGenTable(manName);
		manTable.addMouseListener(manTableML);
		manScrollPane.setViewportView(manTable);
		remove(manDataPan);
		add(createDataPanel(manName));
		repaint();
		revalidate();
	}

	private void addEvents() {
		// TODO Auto-generated method stub

	}

	public void resizeManager(int width, int height) {
		manScrollPane.setBounds(20, 300, width - 50, height - 470);

		manButton2[0].setBounds(manScrollPane.getX(),
				manScrollPane.getY() - 35, 150, 25);
		for (int i = 1; i < manButton2.length; i++) {
			manButton2[i].setBounds(manButton2[i - 1].getX() + 160,
					manScrollPane.getY() - 35, 150, 25);
		}
		manButton2[1].setBounds(manButton2[0].getX() + 160,
				manScrollPane.getY() - 35, 150, 25);
		manButton2[2].setBounds(manButton2[1].getX() + 160,
				manScrollPane.getY() - 35, 150, 25);
		manButton2[3].setBounds(manButton2[2].getX() + 160,
				manScrollPane.getY() - 35, 150, 25);

		int tmp, manX = manButton2[2].getX(), manY = 20;
		tmp = manX;
		manButton[0].setBounds(manX, manY, 150, 30);
		for (int i = 1; i < manButton.length; i++) {
			manX += 160;
			if (i % 2 == 0) {
				manX = tmp;
				manY += 31;
			}
			manButton[i].setBounds(manX, manY, 150, 30);
		}

		manNews.setBounds(20, manScrollPane.getY() + manScrollPane.getHeight()
				+ 10, width - 50, 100);
	}

	@Override
	public void setSize(int width, int height) {
		resizeManager(width, height);
		super.setSize(width, height);
	}
}
