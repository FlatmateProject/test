package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.apache.log4j.Logger;

import service.GuestBook;

public class GuestBookPanel extends JPanel {

	
	private static final Logger log = Logger.getLogger(GuestBookPanel.class);
	
	private static final long serialVersionUID = 1L;
	
	private GuestBook gue = new GuestBook();
	
	private JPanel guePanelCo;
	private JLabel gueClientLabel[] = new JLabel[12];
	private JTextField gueClientData[] = new JTextField[11];
	private JTextArea gueClientNotes = new JTextArea();
	private JTable gueTable[] = new JTable[3];
	private JScrollPane gueScrollPane[] = new JScrollPane[3];
	private JButton gueButton[] = new JButton[4];
	private Border border = BorderFactory.createLineBorder(new Color(60, 124, 142));
	private Color bgColor = new Color(224, 230, 233);
	private Color buttonColor = new Color(174, 205, 214);
	private MouseListener gueTableMLCl = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				gueTable[1] = gue.createTable(
						"rezerwacje",
						"where IDK_PESEL="
								+ gueTable[0].getValueAt(
										gueTable[0].getSelectedRow(), 0));
				gueTable[1].addMouseListener(gueTable2MLCl);
				gueScrollPane[1].setViewportView(gueTable[1]);
				add(gueScrollPane[1]);

				for (int i = 0; i < 11; i++) {
					if (i < 10)
						gueClientData[i].setText((String) gueTable[0]
								.getValueAt(gueTable[0].getSelectedRow(), i));
					else
						gueClientNotes.setText((String) gueTable[0].getValueAt(
								gueTable[0].getSelectedRow(), i));
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
	private MouseListener gueTableMLCo = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			gueTable[1] = gue.createTable("rezerwacje", "where IDF_KRS="
					+ gueTable[0].getValueAt(gueTable[0].getSelectedRow(), 0));
			gueTable[1].addMouseListener(gueTable2MLCo);
			gueScrollPane[1].setViewportView(gueTable[1]);
			guePanelCo.add(gueScrollPane[1]);

			for (int i = 0; i < 11; i++) {
				if (i < 10)
					gueClientData[i].setText((String) gueTable[0].getValueAt(
							gueTable[0].getSelectedRow(), i));
				else
					gueClientNotes.setText((String) gueTable[0].getValueAt(
							gueTable[0].getSelectedRow(), i));
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
	private MouseListener gueTable2MLCl = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			gueTable[2] = gue.createTable(
					"uslugi",
					", rekreacja where rekreacja.id_rez ="
							+ gueTable[1].getValueAt(
									gueTable[1].getSelectedRow(), 0)
							+ " and rekreacja.id_uslugi = uslugi.id_uslugi");
			gueScrollPane[2].setViewportView(gueTable[2]);
			add(gueScrollPane[2]);
			gueClientLabel[11] = new JLabel("US�UGI");
			gueClientLabel[11].setBounds(510, 21, 100, 20);
			add(gueClientLabel[11]);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
	};
	private MouseListener gueTable2MLCo = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			gueTable[2] = gue.createTable(
					"uslugi",
					", rekreacja where rekreacja.id_rez ="
							+ gueTable[1].getValueAt(
									gueTable[1].getSelectedRow(), 0)
							+ " and rekreacja.id_uslugi = uslugi.id_uslugi");
			gueScrollPane[2].setViewportView(gueTable[2]);
			guePanelCo.add(gueScrollPane[2]);
			gueClientLabel[11] = new JLabel("US�UGI");
			gueClientLabel[11].setBounds(510, 21, 100, 20);
			add(gueClientLabel[11]);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
	};
	public GuestBookPanel() {
		create();
		addEvents();
	}


	private void create() {

		setBounds(0, 0, getWidth(), getHeight());
		setBackground(bgColor);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		for (int i = 0; i < 11; i++) {
			if (i < 10) {
				gueClientLabel[i] = new JLabel(gue.getLabel("klienci")[i]);
				gueClientLabel[i].setBounds(30, (i + 1) * 21, 150, 20);

				gueClientData[i] = new JTextField();
				gueClientData[i].setBounds(140, (i + 1) * 21, 150, 18);
				gueClientData[i].setBorder(border);

				add(gueClientData[i]);
			} else {
				gueClientLabel[i] = new JLabel(gue.getLabel("klienci")[i]);
				gueClientLabel[i].setBounds(300, 21, 70, 20);
			}
			add(gueClientLabel[i]);
		}

		gueClientNotes.setBorder(border);
		gueClientNotes.setBounds(gueClientLabel[10].getX(), 41, 200, 187);

		add(gueClientNotes);

		for (int i = 0; i < 4; i++) {
			gueButton[i] = new JButton();
			if (i == 0)
				gueButton[i].setBounds(20, 250, 100, 25);
			else
				gueButton[i].setBounds(gueButton[i - 1].getX() + 110, 250, 100,
						25);
			gueButton[i].setBackground(buttonColor);
			add(gueButton[i]);
		}
		gueButton[0].setText("Szukaj");
		gueButton[1].setText("Aktualizuj");
		gueButton[2].setText("Firmy");
		gueButton[3].setText("Wyczy��");

		gueTable[0] = gue.createTable("klienci", "");
		gueTable[0].addMouseListener(gueTableMLCl);

		gueScrollPane[0] = new JScrollPane(gueTable[0]);
		gueScrollPane[0].setBorder(border);

		gueScrollPane[1] = new JScrollPane();
		gueScrollPane[1].setBorder(border);
		gueScrollPane[2] = new JScrollPane();
		gueScrollPane[2].setBorder(border);

		gueButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s2 = "";
				for (int i = 0; i < 11; i++) {
					if (i < 10 && !gueClientData[i].getText().isEmpty()) {
						if (!s2.isEmpty()) {
							s2 = s2 + " and ";
						}
						s2 = s2 + gueClientLabel[i].getText() + "=" + "\""
								+ gueClientData[i].getText() + "\"";
					} else if (i >= 10 && !gueClientNotes.getText().isEmpty()) {
						if (!s2.isEmpty()) {
							s2 = s2 + " and ";
						}
						s2 = s2 + gueClientLabel[i].getText() + "=" + "\""
								+ gueClientNotes.getText() + "\"";
					}
				}
				if (!s2.isEmpty()) {
					gueTable[0] = gue.createTable("klienci", " where " + s2);
					gueTable[0].addMouseListener(gueTableMLCl);
					gueScrollPane[0].setViewportView(gueTable[0]);
				}
			}
		});
		gueButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String l[] = new String[10];
				String d[] = new String[10];
				for (int i = 0; i < 10; i++) {
					l[i] = gueClientLabel[i].getText();
					d[i] = gueClientData[i].getText();
					// log.info(gueClientLabel[i].getText() + " " +
					// gueClientData[i].getText());
				}
				if (gue.updateClientData(l, d)) {
					gueTable[0] = gue.createTable("klienci", "");
					gueTable[0].addMouseListener(gueTableMLCl);
					gueScrollPane[0].setViewportView(gueTable[0]);
				} else {
					JOptionPane.showMessageDialog(null,
							"B��d aktualizacji! Sprawd� dane!", "UWAGA!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		gueButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				jtp.remove(guePanelCl);
				createGuestBookCo();
//				jtp.addTab("Ksi�ga Go�ci", guePanelCo);
//				jtp.setSelectedIndex(jtp.getComponentCount() - 1);
				resizeGuestBook(getWidth(), getHeight());
			}
		});
		gueButton[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 10; i++) {
					gueClientData[i].setText("");
					gueClientNotes.setText("");
					gueTable[0] = gue.createTable("klienci", "");
					gueTable[0].addMouseListener(gueTableMLCl);
					gueScrollPane[0].setViewportView(gueTable[0]);
				}
			}
		});
		add(gueScrollPane[0]);
	}

	private void createGuestBookCo() {
		guePanelCo = new JPanel();
		guePanelCo.setBounds(0, 0, getWidth(), getHeight());
		guePanelCo.setBackground(bgColor);
		guePanelCo.setLayout(null);

		for (int i = 0; i < 11; i++) {
			if (i < 10) {
				gueClientLabel[i] = new JLabel(gue.getLabel("firmy")[i]);
				gueClientLabel[i].setBounds(30, (i + 1) * 21, 150, 20);

				gueClientData[i] = new JTextField();
				gueClientData[i].setBounds(140, (i + 1) * 21, 150, 18);
				gueClientData[i].setBorder(border);

				guePanelCo.add(gueClientData[i]);
			} else {
				gueClientLabel[i] = new JLabel(gue.getLabel("firmy")[i]);
				gueClientLabel[i].setBounds(300, 21, 70, 20);
			}
			guePanelCo.add(gueClientLabel[i]);
		}

		gueClientNotes.setBorder(border);
		gueClientNotes.setBounds(gueClientLabel[10].getX(), 41, 200, 187);

		guePanelCo.add(gueClientNotes);

		for (int i = 0; i < 4; i++) {
			gueButton[i] = new JButton();
			if (i == 0)
				gueButton[i].setBounds(20, 250, 100, 25);
			else
				gueButton[i].setBounds(gueButton[i - 1].getX() + 110, 250, 100,
						25);
			gueButton[i].setBackground(buttonColor);
			guePanelCo.add(gueButton[i]);
		}
		gueButton[0].setText("Szukaj");
		gueButton[1].setText("Aktualizuj");
		gueButton[2].setText("Klienci");
		gueButton[3].setText("Wyczy��");

		gueTable[0] = gue.createTable("firmy", "");
		gueTable[0].addMouseListener(gueTableMLCo);

		gueScrollPane[0] = new JScrollPane(gueTable[0]);
		gueScrollPane[0].setBorder(border);

		gueScrollPane[1] = new JScrollPane();
		gueScrollPane[1].setBorder(border);
		gueScrollPane[2] = new JScrollPane();
		gueScrollPane[2].setBorder(border);

		guePanelCo.add(gueScrollPane[0]);

		gueButton[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s2 = "";
				for (int i = 0; i < 11; i++) {
					if (i < 10 && !gueClientData[i].getText().isEmpty()) {
						if (!s2.isEmpty()) {
							s2 = s2 + " and ";
						}
						s2 = s2 + gueClientLabel[i].getText() + "=" + "\""
								+ gueClientData[i].getText() + "\"";
					} else if (i >= 10 && !gueClientNotes.getText().isEmpty()) {
						if (!s2.isEmpty()) {
							s2 = s2 + " and ";
						}
						s2 = s2 + gueClientLabel[i].getText() + "=" + "\""
								+ gueClientNotes.getText() + "\"";
					}
				}
				if (!s2.isEmpty()) {
					gueTable[0] = gue.createTable("firmy", " where " + s2);
					gueTable[0].addMouseListener(gueTableMLCo);
					gueScrollPane[0].setViewportView(gueTable[0]);
				}
			}
		});
		gueButton[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String l[] = new String[10];
				String d[] = new String[10];
				for (int i = 0; i < 10; i++) {
					l[i] = gueClientLabel[i].getText();
					d[i] = gueClientData[i].getText();
					// log.info(gueClientLabel[i].getText() + " " +
					// gueClientData[i].getText());
				}
				if (gue.updateClientData(l, d)) {
					gueTable[0] = gue.createTable("firmy", "");
					gueTable[0].addMouseListener(gueTableMLCo);
					gueScrollPane[0].setViewportView(gueTable[0]);
				} else {
					JOptionPane.showMessageDialog(null,
							"B��d aktualizacji! Sprawd� dane!", "UWAGA!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		gueButton[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				jtp.remove(guePanelCo);
//				createGuestBook();
//				jtp.addTab("Ksi�ga Go�ci", this);
//				jtp.setSelectedIndex(jtp.getComponentCount() - 1);
				resizeGuestBook(getWidth(), getHeight());
			}
		});
		gueButton[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 10; i++) {
					gueClientData[i].setText("");
					gueClientNotes.setText("");
					gueTable[0] = gue.createTable("firmy", "");
					gueTable[0].addMouseListener(gueTableMLCo);
					gueScrollPane[0].setViewportView(gueTable[0]);
				}
			}
		});
	}


	private void addEvents() {
		// TODO Auto-generated method stub
		
	}
	
	public void resizeGuestBook(int width, int height) {
		gueScrollPane[0].setBounds(20, 300, width - 50,
				height / 2 - 190);
		gueScrollPane[1].setBounds(20, gueScrollPane[0].getY()
				+ gueScrollPane[0].getHeight() + 5, width - 50,
				height / 2 - 190);
		gueScrollPane[2].setBounds(510, 41, width - 540, 187);
	}
	
	@Override
	public void setSize(int width, int height) {
		resizeGuestBook(width, height);
		super.setSize(width, height);
	}
}
