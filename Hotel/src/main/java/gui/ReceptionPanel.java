package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import service.Reception;

public class ReceptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField recJta[] = new JTextField[3];
	private JTextField recCenaJta = new JTextField();
	private JLabel recGuestLabel[] = new JLabel[3];
	private JLabel recRezLabel = new JLabel();
	private JLabel recDateLabel = new JLabel();
	private JLabel recDayLabel = new JLabel();
	private JLabel recPrice = new JLabel();
	private JLabel recPay = new JLabel();
	private JLabel recBil = new JLabel();
	private ButtonGroup recPayGroup = new ButtonGroup();
	private ButtonGroup recBilGroup = new ButtonGroup();
	private JRadioButton recPayButton[] = new JRadioButton[2];
	private JRadioButton recBilButton[] = new JRadioButton[2];
	private JButton recButton[] = new JButton[5];
	private JTable recTable = new JTable();
	private JScrollPane recScrollPane;
	private Border border = BorderFactory.createLineBorder(new Color(60, 124, 142));
	private Color bgColor = new Color(224, 230, 233);
	private Color buttonColor = new Color(174, 205, 214);
	
	private int yJta, recYList, recEWidth, recJtaHeight, recBWidth, recBHeight;
	private int k = 50;
	
	private Reception recept = new Reception();
	
	public ReceptionPanel(){
		create();
		addEvents();
	}
	
	private void create() {
		setBounds(0, 0, getWidth(), getHeight());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//addComponentListener(this);
		setBackground(bgColor);
		recTable = new JTable();
		recGuestLabel[0] = new JLabel("ID Rezerwacji");
		recGuestLabel[1] = new JLabel("PESEL/KRS");
		recGuestLabel[2] = new JLabel("Data wymeldowania (RRRR-MM-DD)");

		for (int i = 0; i < 3; i++) {
			recJta[i] = new JTextField();
			recJta[i].setBorder(border);
			add(recGuestLabel[i]);
			add(recJta[i]);
		}
		recButton[0] = new JButton("Szukaj");
		recButton[1] = new JButton("Przelicz");
		recButton[2] = new JButton("Usu�");
		recButton[3] = new JButton("Wymelduj");
		recButton[4] = new JButton("Dokonaj wp�aty");
		for (int j = 0; j < 5; j++) {
			recButton[j].setBackground(buttonColor);
			add(recButton[j]);
		}
		recRezLabel = new JLabel("Lista rezerwacji");
		recCenaJta = new JTextField("");
		recCenaJta.setBorder(border);
		add(recPrice);
		add(recCenaJta);
		recPrice = new JLabel("Kwota do zap�aty:");
		recPay = new JLabel("Forma p�atno�ci");
		recBil = new JLabel("Rodzaj rachunku");
		add(recRezLabel);
		recCenaJta = new JTextField("");
		recCenaJta.setBorder(border);
		add(recTable);
		add(recPrice);
		add(recPay);
		add(recBil);
		add(recCenaJta);
		recDateLabel = new JLabel("Rozpocz�cie pobytu: ");
		recDayLabel = new JLabel("Doby: ");
		recPayButton[0] = new JRadioButton("Got�wka");
		recPayButton[1] = new JRadioButton("Karta p�atnicza");
		recBilButton[0] = new JRadioButton("Paragon");
		recBilButton[1] = new JRadioButton("Faktura");
		add(recPayButton[0]);
		add(recPayButton[1]);
		add(recBilButton[0]);
		add(recBilButton[1]);
		recPayGroup = new ButtonGroup();
		recBilGroup = new ButtonGroup();
		recPayGroup.add(recPayButton[0]);
		recPayGroup.add(recPayButton[1]);
		recBilGroup.add(recBilButton[0]);
		recBilGroup.add(recBilButton[1]);
		recTable = recept.createTable("");
		recScrollPane = new JScrollPane(recTable);
		recScrollPane.setBorder(border);
		recScrollPane.setViewportView(recTable);
		add(recScrollPane);
	}
	
	private void addEvents() {
		recButton[0].addMouseListener(new MouseListener() {
			boolean pes = false, krs = false, id = false, date = false;
			String sel = "";

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (recJta[0].getText().isEmpty()
						&& recJta[1].getText().isEmpty()
						&& recJta[2].getText().isEmpty())
					JOptionPane.showMessageDialog(getParent(), "Podaj parametry",
							"B��d", 0);
				else {
					if (!recept.isNumber(recJta[0].getText())) {
						id = false;
						JOptionPane.showMessageDialog(getParent(),
								"Nieprawidlowy numer rezerwacji",
								"Nieuznany parametr", 0);
					} else if (recept.isNumber(recJta[0].getText())
							&& !recJta[0].getText().isEmpty()) {
						id = true;
						sel = sel + " where ID_REZ=" + recJta[0].getText();
					}
					if (!recJta[1].getText().isEmpty()) {
						pes = recept.isPesel(recJta[1].getText());
						krs = recept.isKRS(recJta[1].getText());
						if (!pes && !krs)
							JOptionPane.showMessageDialog(getParent(),
									"Nieprawidlowy PESEL/KRS",
									"Nieuznany parametr", 0);
						else if (!pes && krs) {
							if (id) {
								sel = sel + " AND";
								sel = sel + " IDF_KRS=" + recJta[1];
							} else {
								sel = sel + " where IDF_KRS=" + recJta[1];
							}
						} else if (pes && !krs) {
							if (id) {
								sel = sel + " AND";
								sel = sel + " IDK_PESEL=" + recJta[1].getText();
							} else {
								sel = sel + " where IDK_PESEL="
										+ recJta[1].getText();
							}
						}
					}
					if (!recJta[2].getText().isEmpty()) {
						date = recept.isDate(recJta[2].getText());
						if (!date) {
							JOptionPane.showMessageDialog(getParent(),
									"Nieprawid�owa data", "Nieuznany parametr",
									0);
							return;

						} else if (id || pes || krs) {
							sel = sel + " AND";
							sel = sel + " DATA_W=" + "'" + recJta[2].getText()
									+ "'";
						} else {
							sel = sel + " where DATA_W=" + "'"
									+ recJta[2].getText() + "'";
						}
					}
				}
				recTable = recept.createTable(sel);
				recScrollPane.setViewportView(recTable);
				recScrollPane.repaint();
				id = false;
				pes = false;
				krs = false;
				date = false;
				sel = "";

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		recButton[4].addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				String form, name;
				float tax, much = 0;
				DateFormat dfY = new SimpleDateFormat("yyyy");
				DateFormat dfM = new SimpleDateFormat("MM");
				DateFormat dfD = new SimpleDateFormat("dd");
				Calendar calendar = Calendar.getInstance();
				String sYear = dfY.format(calendar.getTime());
				String sMonth = dfM.format(calendar.getTime());
				String sDay = dfD.format(calendar.getTime());
				String date1;

				date1 = sYear + "-" + sMonth + "-" + sDay;
				if (recTable.getSelectedColumnCount() < 1) {
					JOptionPane.showMessageDialog(getParent(),
							"Nie zaznaczono rezerwacji", "B��d", 0);
				} else if (recTable.getValueAt(0, 0) == "Brak danych") {
					JOptionPane.showMessageDialog(getParent(), "Brak danych",
							"B��d", 0);
				} else {
					if (recCenaJta.getText().isEmpty())
						JOptionPane.showMessageDialog(getParent(),
								"Nie przeliczono kwoty", "B��d", 0);
					else {
						if (recPayButton[0].isSelected())
							form = "Got�wka";
						else
							form = "Karta";
						if (recBilButton[0].isSelected())
							name = "Paragon";
						else
							name = "Faktura";

						much = Float.parseFloat(recCenaJta.getText());
						tax = (float) (much * 0.22);
						if (recept.pay(Integer.parseInt(recTable.getValueAt(
								recTable.getSelectedRow(), 0).toString()),
								date1, form, tax, much, name))
							JOptionPane.showMessageDialog(getParent(),
									"Dokonano zap�aty", "Informacja", 1);
						else
							JOptionPane.showMessageDialog(getParent(),
									"Zap�ata zosta�a dokonana wcze�niej",
									"B��d", 0);
					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		recButton[1].addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = -1;
				float cost;
				row = recTable.getSelectedRow();
				if (row < 0)
					JOptionPane.showMessageDialog(getParent(),
							"Nie zaznaczono zadnej kolumny",
							"Brak zaznaczenia", 1);
				else {
					if (recTable.getValueAt(0, 0) == "Brak danych") {
						JOptionPane.showMessageDialog(getParent(), "Brak danych!");
					} else {
						if ((cost = recept.calculate(Integer.parseInt(recTable
								.getValueAt(recTable.getSelectedRow(), 0)
								.toString()))) < 1)
							JOptionPane.showMessageDialog(getParent(),
									"Pobyt jeszcze si� nie rozpocz��");
						else {
							recCenaJta.setText(Float.toString(cost));
							row = -1;
						}
					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		recButton[2].addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = -1;
				row = recTable.getSelectedRow();
				if (row < 0)
					JOptionPane.showMessageDialog(getParent(),
							"Nie zaznaczono zadnej kolumny",
							"Brak zaznaczenia", 1);
				else {
					if (recTable.getValueAt(0, 0) == "Brak danych") {
						JOptionPane.showMessageDialog(getParent(), "Brak danych!");
					} else {
						recept.deleteRez(Integer.parseInt(recTable.getValueAt(
								recTable.getSelectedRow(), 0).toString()));
						JOptionPane.showMessageDialog(getParent(),
								"Usuni�to rezerwacj�");
						recTable = recept.createTable("");
						recScrollPane.setViewportView(recTable);
						recScrollPane.repaint();

					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}
		});
		recButton[3].addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = -1;
				boolean kind;
				long idK = 0;
				// float cost;
				row = recTable.getSelectedRow();
				if (row < 0)
					JOptionPane.showMessageDialog(getParent(),
							"Nie zaznaczono zadnej kolumny",
							"Brak zaznaczenia", 1);
				else {
					if (recTable.getValueAt(recTable.getSelectedRow(), 0)
							.toString() == "Brak danych")
						JOptionPane.showMessageDialog(getParent(), "Brak danych!",
								"B��d", 1);
					else if (recept.checkPay(Integer.parseInt(recTable
							.getValueAt(recTable.getSelectedRow(), 0)
							.toString()))) {
						if (recTable.getValueAt(recTable.getSelectedRow(), 2) == null) {
							kind = true;
							idK = Long.parseLong(recTable.getValueAt(
									recTable.getSelectedRow(), 1).toString());
						} else {
							kind = false;
							idK = Long.parseLong(recTable.getValueAt(
									recTable.getSelectedRow(), 2).toString());
						}
						recept.archivRez(
								Integer.parseInt(recTable.getValueAt(
										recTable.getSelectedRow(), 0)
										.toString()),
								kind,
								idK,
								Integer.parseInt(recTable.getValueAt(
										recTable.getSelectedRow(), 3)
										.toString()),
								Integer.parseInt(recTable.getValueAt(
										recTable.getSelectedRow(), 4)
										.toString()),
								recTable.getValueAt(recTable.getSelectedRow(),
										5).toString(),
								recTable.getValueAt(recTable.getSelectedRow(),
										6).toString());
						recept.deleteRez(Integer.parseInt(recTable.getValueAt(
								recTable.getSelectedRow(), 0).toString()));
						recTable = recept.createTable("");
						recScrollPane.setViewportView(recTable);
						recScrollPane.repaint();
					} else {
						JOptionPane.showMessageDialog(getParent(),
								"Nie dokonano wp�aty za pobyt", "Brak wp�aty",
								0);
					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
	}
	
	public void resizeReception(int width, int height) {

		recEWidth = (width - 200) / 3;
		recJtaHeight = 18;
		yJta = 30;
		recYList = 300;
		for (int i = 0; i < 3; i++) {
			if (i != 0 && i % 1 == 0) {
				yJta += 50;
			}
			recJta[i].setBounds(10, yJta, recEWidth - 10, recJtaHeight);
			recGuestLabel[i].setBounds(10, yJta - 18, recEWidth, recJtaHeight);
		}
		recScrollPane.setBounds(recJta[0].getX() + recJta[0].getWidth() + 20,
				recYList - 270, recEWidth + 200, 200);
		recRezLabel.setBounds(recTable.getX(), recTable.getY() - 20, recEWidth,
				18);
		recBWidth = 100;
		recBHeight = 25;
		recButton[0].setBounds(50, 200, recBWidth, recBHeight);
		recButton[1].setBounds(recScrollPane.getX() + recScrollPane.getWidth()
				+ 10, recScrollPane.getY(), recBWidth, recBHeight);
		recButton[2].setBounds(recButton[1].getX(), recButton[1].getY() + 100,
				recBWidth, recBHeight);
		recButton[3].setBounds(recButton[1].getX(), recButton[1].getY() + 170,
				recBWidth, recBHeight);
		recDateLabel.setBounds(k, recYList + 20, recEWidth, 18);
		recDayLabel.setBounds(recDateLabel.getX() + 390, recDateLabel.getY(),
				50, recJtaHeight);
		recPrice.setBounds(recJta[0].getX(), recDateLabel.getY(), 150,
				recJtaHeight);
		recCenaJta.setBounds(recJta[0].getX() + 110, recDateLabel.getY(), 50,
				recJtaHeight);
		recPay.setBounds(recPrice.getX(),
				recCenaJta.getY() + recCenaJta.getHeight() + 10, 150,
				recJtaHeight);
		recBil.setBounds(recPay.getX() + recPay.getWidth() + 30,
				recCenaJta.getY() + recCenaJta.getHeight() + 10, 150,
				recJtaHeight);
		recPayButton[0].setBounds(recPrice.getX(), recCenaJta.getY()
				+ recCenaJta.getHeight() + 40, 150, recJtaHeight);
		recPayButton[1].setBounds(recPrice.getX(), recCenaJta.getY()
				+ recCenaJta.getHeight() + 70, 150, recJtaHeight);
		recBilButton[0].setBounds(
				recPayButton[0].getX() + recPayButton[0].getWidth() + 20,
				recPayButton[0].getY(), 150, recJtaHeight);
		recBilButton[1].setBounds(
				recPayButton[1].getX() + recPayButton[1].getWidth() + 20,
				recPayButton[1].getY(), 150, recJtaHeight);
		recButton[4].setBounds(recPrice.getX(),
				recCenaJta.getY() + recCenaJta.getHeight() + 110,
				recBWidth + 30, recBHeight);
		recBilButton[0].setSelected(true);
		recPayButton[0].setSelected(true);
	}
	
	@Override
	public void setSize(int width, int height) {
		resizeReception(width, height);
		super.setSize(width, height);
	}
}
