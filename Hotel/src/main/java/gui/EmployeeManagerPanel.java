package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import dao.Singleton;

import service.EmployeeManager;
import service.dictionary.MONTH;
import validation.ValidationUtils;

public class EmployeeManagerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(EmployeeManagerPanel.class);
	
	private JComboBox mgpChooseType;
	private JComboBox mgpChooseAdd;
	private JComboBox mgpChooseDel;
	private JComboBox mgpChooseMonth;
	private JComboBox mgpChooseEmployee;
	private JLabel mgpTitle;
	private JLabel mgpTypeLabel;
	private JLabel mgpMonthLabel;
	private JLabel mgpCalMonthLabel;
	private JLabel mgpSubTitle[];
	private JLabel mgpAddLabel[];
	private JLabel mgpDelLabel[];
	private JLabel mgpDayLabel[];
	private JTextField mgpAddEmploy[];
	private JTextField mgpDelEmploy[];
	private JButton mgpAdd;
	private JButton mgpFind;
	private JButton mgpDel;
	private JButton mgpCreat;
	private JButton mgpDays[];
	private JButton mgpPrev;
	private JButton mgpNext;
	private JTable mgpTable;
	private JScrollPane mgpSchedScroll;
	private JScrollPane mgpRaportScroll;
	private JScrollPane mgpTableScroll;
	private JList mgpSchedText;
	private JTextPane mgpRaportText;
	private JPanel mgpServe[];
	
	private EmployeeManager mgp = new EmployeeManager();

	private String mgpColsName[];
	private String mgpMatrix[][];
	private boolean mgpIndel = false;
	private final String[] dayOfWeek = { "Pn", "Wt", "�r", "Cz", "Pt", "So",
			"Nd" };
	
	private final int[] dayInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };
	private Calendar schCalendar = GregorianCalendar.getInstance();
	private int mgpDay = schCalendar.get(Calendar.DAY_OF_MONTH);
	private int mgpMonth = schCalendar.get(Calendar.MONTH);
	private int mgpYear = schCalendar.get(Calendar.YEAR);
	private int mgpClickCount = 0;

	private Color bgColor = new Color(224, 230, 233);
	
	private Singleton db = Singleton.getInstance();
	
	public EmployeeManagerPanel() {
		try {
			create();
			addEvents();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void create() throws SQLException{
		int i = 0;
		ResultSet rset = null;
		Font font = null;

		
		setBounds(0, 0, this.getWidth(), this.getHeight());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(bgColor);
		setVisible(true);

		font = new Font("arial", Font.BOLD, 20);
		mgpTitle = new JLabel("Manager personalny");
		mgpTitle.setBounds(20, 5, 300, 20);
		mgpTitle.setFont(font);
		add(mgpTitle);

		mgpServe = new JPanel[4];
		mgpSubTitle = new JLabel[4];
		mgpSubTitle[0] = new JLabel("Grafik misi�czny");
		mgpSubTitle[1] = new JLabel("Dodaj pracownika");
		mgpSubTitle[2] = new JLabel("Usu� pracownika");
		mgpSubTitle[3] = new JLabel("Raport p�ac");
		font = new Font("arial", Font.BOLD, 15);
		for (i = 0; i < mgpServe.length; i++) {
			mgpServe[i] = new JPanel();
			mgpServe[i].setLayout(null);
			mgpServe[i].setBackground(bgColor);
			mgpServe[i].setVisible(false);
			mgpSubTitle[i].setBounds(20, 5, 300, 20);
			mgpSubTitle[i].setFont(font);
			mgpServe[i].add(mgpSubTitle[i]);
			add(mgpServe[i]);
		}
		mgpServe[0].setVisible(true);

		mgpAddEmploy = new JTextField[8];
		mgpDelEmploy = new JTextField[4];
		mgpAddLabel = new JLabel[9];
		mgpDelLabel = new JLabel[4];
		mgpColsName = new String[4];
		rset = db.query("SHOW COLUMNS FROM pracownicy");
		log.info("SHOW COLUMNS FROM pracownicy");
		if (rset != null) {
			for (i = 0; i < 8 && rset.next(); i++) {
				mgpAddEmploy[i] = new JTextField();
				mgpAddLabel[i] = new JLabel(rset.getString(1));
				mgpAddLabel[i].setBounds(20, 40 + i * 25, 100, 20);
				mgpAddEmploy[i].setBounds(130, 40 + i * 25, 200, 20);
				mgpServe[1].add(mgpAddEmploy[i]);
				mgpServe[1].add(mgpAddLabel[i]);
			}
			rset.beforeFirst();
			for (i = 0; i < 3 && rset.next(); i++) {
				mgpDelEmploy[i] = new JTextField();
				mgpDelLabel[i] = new JLabel(rset.getString(1));
				mgpColsName[i] = rset.getString(1);
				mgpDelEmploy[i].setBounds(130, 40 + i * 25, 200, 20);
				mgpDelLabel[i].setBounds(20, 40 + i * 25, 100, 20);
				mgpServe[2].add(mgpDelLabel[i]);
				mgpServe[2].add(mgpDelEmploy[i]);
			}
		}
		mgpAddLabel[8] = new JLabel("STANOWISKO");
		mgpAddLabel[8].setBounds(20, 40 + 8 * 25, 100, 20);
		mgpDelLabel[3] = new JLabel("STANOWISKO");
		mgpDelLabel[3].setBounds(20, 40 + 3 * 25, 100, 20);
		mgpServe[1].add(mgpAddLabel[8]);
		mgpServe[2].add(mgpDelLabel[3]);
		mgpColsName[3] = "STANOWISKO";
		mgpChooseAdd = new JComboBox();
		mgpChooseAdd.setBounds(130, 40 + 8 * 25, 200, 20);
		mgpChooseDel = new JComboBox();
		mgpChooseDel.setBounds(130, 40 + 3 * 25, 200, 20);
		rset = db.query("SELECT nazwa FROM stanowiska");
		if (rset != null) {
			mgpChooseDel.addItem("");
			while (rset.next()) {
				mgpChooseAdd.addItem(rset.getString(1));
				mgpChooseDel.addItem(rset.getString(1));
			}
			mgpChooseAdd.setSelectedIndex(-1);//should be 0
			mgpChooseDel.setSelectedIndex(-1);//should be 0
			mgpServe[1].add(mgpChooseAdd);
			mgpServe[2].add(mgpChooseDel);
		}

		mgpSchedText = new JList();
		mgpSchedScroll = new JScrollPane(mgpSchedText);
		mgpServe[0].add(mgpSchedScroll);

		rset = db
				.query("SELECT imie, nazwisko, idp_pesel FROM pracownicy WHERE id_stanowiska=1");
		System.out
				.println("SELECT imie, nazwisko, idp_pesel FROM pracownicy WHERE id_stanowiska=1");
		mgpChooseEmployee = new JComboBox();
		if (rset != null) {
			log.info("in costam lipa");
			mgpChooseEmployee.setBounds(0, 0, 120, 20);
			while (rset.next()) {
				mgpChooseEmployee.addItem(rset.getString(1) + " "
						+ rset.getString(2) + " " + rset.getString(3));
				log.info(rset.getString(1) + " " + rset.getString(2));
			}
			mgpChooseEmployee.setVisible(false);
			mgpSchedText.add(mgpChooseEmployee);
		}
		font = new Font("arial", Font.ROMAN_BASELINE, 15);
		mgpAdd = new JButton("Dodaj");
		mgpAdd.setBounds(340, 40, 100, 40);
		mgpAdd.setFont(font);
		mgpServe[1].add(mgpAdd);

		mgpFind = new JButton("Znajdz");
		mgpFind.setBounds(340, 40, 100, 40);
		mgpFind.setFont(font);
		mgpServe[2].add(mgpFind);

		mgpCreat = new JButton("Wykonaj");
		mgpCreat.setBounds(160, 40, 100, 40);
		mgpCreat.setFont(font);
		mgpServe[3].add(mgpCreat);

		mgpDel = new JButton("Usu�");
		mgpDel.setFont(font);

		// mgpDel.setBounds(505,165,107,40);
		mgpDel.setVisible(false);
		mgpServe[2].add(mgpDel);

		mgpPrev = new JButton("Poprzedni");
		mgpServe[0].add(mgpPrev);

		mgpNext = new JButton("Nast�pny");
		mgpServe[0].add(mgpNext);

		mgpTableScroll = new JScrollPane();
		// mgpTableScroll.setBounds(20,165,473,382);
		mgpTableScroll.setVisible(false);
		mgpServe[2].add(mgpTableScroll);

		mgpTypeLabel = new JLabel("Wybirz czynno��");
		mgpTypeLabel.setBounds(20, 40, 300, 20);
		mgpTypeLabel.setFont(font);
		mgpChooseType = new JComboBox();
		mgpChooseType.setBounds(20, 60, 230, 20);
		// ///////////////////------------b
		mgpChooseType.addItem("Tw�rz grafik");
		mgpChooseType.addItem("Usu� grafik");
		mgpChooseType.addItem("Pokaz grafik");
		mgpChooseType.addItem("Dodaj pracownika");
		mgpChooseType.addItem("Usu� pracownika");
		mgpChooseType.addItem("Raport p�ac");
		mgpChooseType.setSelectedIndex(2);
		// ///////////////////------------e
		add(mgpChooseType);
		add(mgpTypeLabel);

		mgpMonthLabel = new JLabel("Wybirz miesi�c");
		mgpMonthLabel.setBounds(20, 40, 300, 20);
		mgpMonthLabel.setFont(font);
		mgpChooseMonth = new JComboBox();
		mgpChooseMonth.setBounds(20, 60, 130, 20);
		mgpChooseMonth.addItem(MONTH.January);
		mgpChooseMonth.addItem(MONTH.February);
		mgpChooseMonth.addItem(MONTH.March);
		mgpChooseMonth.addItem(MONTH.April);
		mgpChooseMonth.addItem(MONTH.May);
		mgpChooseMonth.addItem(MONTH.June);
		mgpChooseMonth.addItem(MONTH.July);
		mgpChooseMonth.addItem(MONTH.August);
		mgpChooseMonth.addItem(MONTH.September);
		mgpChooseMonth.addItem(MONTH.October);
		mgpChooseMonth.addItem(MONTH.November);
		mgpChooseMonth.addItem(MONTH.December);
		mgpChooseMonth.setSelectedIndex(0);
		mgpServe[3].add(mgpChooseMonth);
		mgpServe[3].add(mgpMonthLabel);

		mgpRaportText = new JTextPane();
		mgpRaportScroll = new JScrollPane(mgpRaportText);
		mgpServe[3].add(mgpRaportScroll);
		mgpRaportScroll.setBounds(20, 90, 592, 457);
		mgpRaportText.setSize(592, 457);

		mgpCalMonthLabel = new JLabel();
		mgpServe[0].add(mgpCalMonthLabel);
		mgpDays = new JButton[31];
		for (i = 0; i < mgpDays.length; i++) {
			mgpDays[i] = new JButton(String.valueOf(i + 1));
			mgpDays[i].setBackground(Color.WHITE);
			mgpServe[0].add(mgpDays[i]);
		}
		mgpDayLabel = new JLabel[7];
		for (i = 0; i < mgpDayLabel.length; i++) {
			mgpDayLabel[i] = new JLabel(dayOfWeek[i]);
			mgpServe[0].add(mgpDayLabel[i]);
		}

		// add(mgpPanel);
	}

	private void addEvents() {
		mgpSchedText.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int a = (e.getY() / 18) * 18;
				int i = mgpSchedText.getSelectedIndex();
				String s = (String) mgpSchedText.getSelectedValue();
				if (i > 0 && s.charAt(0) == '[') {
					mgpChooseEmployee.setBounds(0, a, 250, 18);
					log.info(e.getX() + " " + a);
					mgpChooseEmployee.setVisible(true);
				} else
					mgpChooseEmployee.setVisible(false);
			}
		});

		// ///////////////-------------------b
		mgpChooseType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				boolean ret = false;
				int i = 0;
				mgpChooseEmployee.setVisible(false);
				if ((i = mgpChooseType.getSelectedIndex()) == 0) {
					if (++mgpClickCount == 2) {
						mgpClickCount = 0;
						ret = mgp.createSchedule(mgpMonth + 1);
						log.info((mgpMonth + 1));
						if (ret)
							JOptionPane.showMessageDialog(getParent(),
									"Stworzono grafik");
						else
							JOptionPane.showMessageDialog(getParent(),
									"Grafik na ten miesiac juz istnieje");
						mgpChooseType.setSelectedIndex(2);
					}
				} else if (i == 1) {
					if (++mgpClickCount == 2) {
						mgpClickCount = 0;
						ret = mgp.delSchedule(mgpMonth + 1);
						if (ret)
							JOptionPane.showMessageDialog(getParent(),
									"Usunieto grafik");
						else
							JOptionPane.showMessageDialog(getParent(),
									"Grafik na ten miesiac nie istnieje");
						mgpChooseType.setSelectedIndex(2);
					}
				} else {
					for (i = 0; i < mgpServe.length; i++)
						if (mgpChooseType.getSelectedIndex() == (i + 2))
							mgpServe[i].setVisible(true);
						else
							mgpServe[i].setVisible(false);
				}
			}
		});

		// ////////////////////////-------------------e
		for (int i = 0; i < 31; i++)
			mgpDays[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					JButton tmp = (JButton) e.getSource();
					log.info("dzien: " + tmp.getText());
					mgpDay = Integer.valueOf(tmp.getText());
					mgpSchedText.setListData(mgp.getDaySchedule(mgpYear + "/"
							+ (mgpMonth + 1) + "/" + mgpDay));
					mgpChooseEmployee.setVisible(false);
				}
			});
		mgpNext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (++mgpMonth > 11) {
					mgpYear++;
					mgpMonth = 0;
				}
				resizePersonelMenager(getWidth(), getHeight());
			}
		});
		mgpPrev.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (--mgpMonth < 0) {
					mgpYear--;
					mgpMonth = 11;
				}
				resizePersonelMenager(getWidth(), getHeight());
			}
		});
		// ///////////////////-------------b
		mgpAdd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				boolean ret = false;
				int i = 0;
				String msg = "";
				if (!ValidationUtils.isPesel(mgpAddEmploy[0].getText()))
					msg += "Pole PESEL ma nieprawid�ow� warto��\n";
				for (i = 1; i < 6; i++)
					if (mgpAddEmploy[i].getText().equals(""))
						msg += "Nie wype�ni�es pola "
								+ mgpAddLabel[i].getText() + "\n";
				if (!ValidationUtils.isNumber(mgpAddEmploy[7].getText()))
					msg += "Pole NR_LOKALU ma nieprawid�ow� warto��\n";
				if (msg == "") {
					ret = mgp.addEmployee(mgpAddEmploy[0].getText(),
							mgpAddEmploy[1].getText(),
							mgpAddEmploy[2].getText(),
							mgpAddEmploy[3].getText(),
							mgpAddEmploy[4].getText(),
							mgpAddEmploy[5].getText(),
							mgpAddEmploy[6].getText(),
							mgpAddEmploy[7].getText(),
							(String) mgpChooseAdd.getSelectedItem());
					if (ret) {
						if (mgpChooseAdd.getSelectedIndex() == 0) {
							mgpChooseEmployee.addItem(mgpAddEmploy[1].getText()
									+ " " + mgpAddEmploy[2].getText() + " "
									+ mgpAddEmploy[0].getText());

							log.info("ok dziala: "
									+ mgpAddEmploy[1].getText() + " "
									+ mgpAddEmploy[2].getText() + " "
									+ mgpAddEmploy[0].getText());
						} else
							log.info("lipa "
									+ mgpChooseAdd.getSelectedIndex());
					} else
						JOptionPane.showMessageDialog(mgpServe[1],
								"Osoba o podanych danych jest ju� w systemie");

				} else
					JOptionPane.showMessageDialog(mgpServe[1], "B��d!\n" + msg);
			}
		});
		// /////////////////////////////////////----e
		// //////////////-----------------------b
		mgpChooseEmployee.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (++mgpClickCount == 2) {
					if (!mgpIndel) {
						mgpClickCount = 0;
						String s = (String) mgpSchedText.getSelectedValue();
						String z = (String) mgpChooseEmployee.getSelectedItem();
						int ind = z.lastIndexOf(" ");
						log.info("index: "
								+ mgpSchedText.getSelectedIndex());
						mgpChooseEmployee.setVisible(false);
						log.info("pesel: "
								+ z.substring(ind, z.length()));
						log.info("dane: " + z.substring(0, ind));
						mgpSchedText.setListData(mgp.updateSchedule(
								mgpSchedText.getSelectedIndex(),
								z.substring(0, ind), mgpYear + "/"
										+ (mgpMonth + 1) + "/" + mgpDay,
								z.substring(ind + 1, z.length()),
								s.substring(1, s.indexOf(" "))));

						mgpSchedText.repaint();
					} else
						mgpIndel = false;
				}
			}
		});
		// //////////////////////////////----------------e
		// ///////////////////------------b
		mgpDel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = mgpTable.getSelectedRow();

				boolean ret = false;
				String s = "";
				if (i != -1) {
					log.info("table index: " + i);
					s = (String) mgpTable.getValueAt(i, 1) + " "
							+ (String) mgpTable.getValueAt(i, 2) + " "
							+ (String) mgpTable.getValueAt(i, 0);
					log.info("table selected item: " + s);
					ret = mgp.delEmployee((String) mgpTable.getValueAt(i, 0));
					mgpIndel = true;
					if (ret) {
						// mgpTable.removeRowSelectionInterval(i,i);
						// mgpServe[2].remove(mgpTableScroll);

					}
					for (i = 0; i < mgpChooseEmployee.getItemCount(); i++) {
						if (mgpChooseEmployee.getItemAt(i).equals(s)) {
							log.info("is equla: " + i);
							mgpChooseEmployee.removeItemAt(i);
						}

					}
					mgpTableScroll.repaint();
				}
			}
		});
		// ///////////////////////////-----------------e
		mgpFind.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = (mgpServe[2].getWidth() - 40);
				mgpMatrix = mgp.findEmployee(mgpDelEmploy[0].getText(),
						mgpDelEmploy[1].getText(), mgpDelEmploy[2].getText(),
						(String) mgpChooseDel.getSelectedItem());
				mgpServe[2].remove(mgpTableScroll);
				mgpTable = new JTable(mgpMatrix, mgpColsName);
				mgpTable.setFillsViewportHeight(true);
				mgpTableScroll = new JScrollPane(mgpTable);
				mgpTableScroll.setBounds(20, 165, (int) (i * 0.8),
						mgpServe[2].getHeight() - 175);
				mgpTable.setSize((int) (i * 0.8), mgpServe[2].getHeight() - 175);
				mgpServe[2].add(mgpTableScroll);
				mgpServe[2].repaint();
				if (mgp.getRowCount() > 0)
					mgpDel.setVisible(true);
				if (!mgpDelEmploy[0].getText().equals("")
						&& !ValidationUtils.isPesel(mgpDelEmploy[0].getText()))
					JOptionPane.showMessageDialog(mgpServe[1],
							"Pole PESEL ma nieprawid�ow� warto��\n");
			}
		});
		mgpCreat.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mgpRaportText.setText("Raport z zarobk�w za miesi�c "
						+ mgpChooseMonth.getSelectedItem()
						+ "\n"
						+ mgp.getPaysRaport(mgpChooseMonth.getSelectedIndex() + 1));
				mgpRaportText.setVisible(true);

			}
		});
	}

	public void resizePersonelMenager(int width, int height) {
		int i = 0, k = 0, j = 0, dayNum = 0;
		int tmp = 0, dayTmp = 0, dX = 0;
		int w = width - 8;
		int h = height - 33;
		int ww = 0, hh = 0;
		int m = 0, y = 0;
		int index = mgpChooseType.getSelectedIndex();
		setBounds(0, 0, w, h);
		ww = w - 260;
		hh = h - 60;
		for (i = 0; i < mgpServe.length; i++)
			mgpServe[i].setBounds(260, 60, ww, hh);
		// log.info("ww: "+ww);////////////
		// log.info("hh: "+hh); ////////////
		i = (ww - 40);
		mgpDel.setBounds(20 + (int) (i * 0.82), 165, (int) (i * 0.18), 40);
		if (index <= 2) {
			hh = 80;
			y = schCalendar.get(Calendar.YEAR);
			m = schCalendar.get(Calendar.MONTH);
			schCalendar.set(Calendar.YEAR, mgpYear);
			schCalendar.set(Calendar.MONTH, mgpMonth);
			schCalendar.set(Calendar.DAY_OF_MONTH, mgpDay);
			dX = ww / 2 - 182;
			dayTmp = schCalendar.get(Calendar.DAY_OF_MONTH);
			schCalendar.set(Calendar.DAY_OF_MONTH, 0);
			tmp = schCalendar.get(Calendar.DAY_OF_WEEK) - 1;
			dX += tmp * 51;
			dayNum = dayInMonth[mgpMonth];
			if (mgpMonth == 1 && mgpYear % 4 == 0)
				dayNum++;
			for (i = 0, k = 51; i < 31; i++, dX += k, tmp++) {
				if (i != 0 && tmp % 7 == 0) {
					hh += 31;
					dX = ww / 2 - 182;
				}
				mgpDays[i].setBounds(dX, hh, 50, 30);
				if (i < dayNum) {
					mgpDays[i].setVisible(true);
					j = i;
				} else
					mgpDays[i].setVisible(false);
			}
			dX = ww / 2 - 175;
			k = mgpDays[0].getY() - 18;
			for (i = 0; i < mgpDayLabel.length; i++, dX += 51)
				mgpDayLabel[i].setBounds(dX + 10, k, 50, 18);
			mgpCalMonthLabel.setText(MONTH.getMonthName(mgpMonth) + " " + mgpYear);// <----
			mgpCalMonthLabel.setHorizontalAlignment(SwingConstants.CENTER);
			mgpCalMonthLabel.setBounds((ww - 100) / 2,
					mgpDayLabel[0].getY() - 30, 100, 20);
			dX = ww / 2 - 75;
			mgpPrev.setBounds(mgpDayLabel[0].getX() - 18,
					mgpCalMonthLabel.getY(), 100, 20);
			mgpNext.setBounds(mgpDayLabel[6].getX() - 68,
					mgpCalMonthLabel.getY(), 100, 20);
			schCalendar.set(Calendar.DAY_OF_MONTH, dayTmp);
			schCalendar.set(Calendar.YEAR, y);
			schCalendar.set(Calendar.MONTH, m);
			mgpDays[dayTmp - 1].requestFocus();
			i = (mgpServe[2].getWidth() - 40);
			hh = h - mgpDays[j].getY() - 120;
			mgpSchedScroll.setBounds(20, mgpDays[j].getY() + 50, i, hh);
			mgpSchedText.setSize(i, hh);
		} else if (index == 4) {
			// i=(mgpServe[2].getWidth()-40);
			hh = hh - 175;
			mgpTableScroll.setBounds(20, 165, (int) (i * 0.8), hh);
			if (mgpTable != null)
				mgpTable.setSize((int) (i * 0.8), hh);
		} else if (index == 5) {
			// i=(mgpServe[3].getWidth()-40);
			hh = hh - 100;
			mgpRaportScroll.setBounds(20, 90, i, hh);
			mgpRaportText.setSize(i, hh);
		}
	}
	
	@Override
	public void setSize(int width, int height) {
		resizePersonelMenager(width, height);
		super.setSize(width, height);
	}
}
