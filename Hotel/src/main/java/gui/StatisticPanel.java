package gui;

import dao.StatisticDao;
import dao.StatisticDaoImpl;
import dto.SimpleNameData;
import exception.DAOException;
import service.GraphDraw;
import service.dictionary.MONTH;
import service.statictic.REPORT_KIND;
import service.statictic.Statistic;
import service.statictic.StatisticReport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StatisticPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JButton executeButton;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JLabel monthLabelFrom;
    private JLabel yearLabelFrom;
    private JLabel monthLabelTo;
    private JLabel yearLabelTo;
    private JLabel classLabel;
    private JLabel serveLabel;
    private JComboBox<String> chooseType;
    private JComboBox<REPORT_KIND> chooseSubFinance;
    private JComboBox<REPORT_KIND> chooseSubHotel;
    private JComboBox<MONTH> chooseMonth;
    private JComboBox<String> chooseYear;
    private JComboBox<MONTH> chooseMonth2;
    private JComboBox<String> chooseYear2;
    private JComboBox<String> roomTypeChoose;
    private JComboBox<String> serviceTypeChoose;
    private JScrollPane reportScroll;
    private JTextPane reportText;
    private GraphDraw graphDraw;

    private StatisticDao statisticDao;

    private Statistic statistic;

    public StatisticPanel() {
        statisticDao = new StatisticDaoImpl();
        statistic = new Statistic(statisticDao);
        create();
        addEvents();
    }

    private void create() {
        Color color = new Color(224, 230, 233);
        Font font = new Font("arial", Font.ROMAN_BASELINE, 15);

        graphDraw = new GraphDraw();

        setBounds(0, 0, 900, 650);
        setLayout(null);
        setBackground(color);
        setVisible(true);

        executeButton = new JButton("Wykonaj");
        executeButton.setBounds(380, 60, 100, 60);
        executeButton.setFont(font);
        executeButton.setVisible(true);
        add(executeButton);

        chooseType = new JComboBox<String>();
        chooseType.setBounds(20, 60, 230, 20);
        chooseType.addItem("hotelowe");
        chooseType.addItem("finansowe");
        chooseType.setSelectedIndex(0);
        add(chooseType);

        chooseSubHotel = new JComboBox<REPORT_KIND>(REPORT_KIND.hotel());
        chooseSubHotel.setBounds(20, 100, 230, 20);
        chooseSubHotel.setSelectedIndex(0);
        add(chooseSubHotel);

        chooseMonth = new JComboBox<MONTH>(MONTH.values());
        chooseMonth.setBounds(260, 100, 100, 20);
        chooseMonth.setSelectedIndex(0);
        chooseMonth.setVisible(true);
        add(chooseMonth);

        chooseMonth2 = new JComboBox<MONTH>(MONTH.values());
        chooseMonth2.setBounds(260, 60, 100, 20);
        chooseMonth2.setSelectedIndex(0);
        chooseMonth2.setVisible(false);
        add(chooseMonth2);

        chooseSubFinance = new JComboBox<REPORT_KIND>(REPORT_KIND.finance());
        chooseSubFinance.setBounds(20, 100, 230, 20);
        chooseSubFinance.setSelectedIndex(0);
        chooseSubFinance.setVisible(false);
        add(chooseSubFinance);

        chooseYear = new JComboBox<String>();
        chooseYear.setBounds(260, 60, 100, 20);
        chooseYear.addItem("2010");
        chooseYear.addItem("2011");
        chooseYear.addItem("2012");
        chooseYear.addItem("2013");
        chooseYear.addItem("2014");
        chooseYear.addItem("2015");
        chooseYear.addItem("2016");
        chooseYear.setSelectedIndex(0);
        add(chooseYear);

        chooseYear2 = new JComboBox<String>();
        chooseYear2.setBounds(260, 100, 100, 20);
        chooseYear2.addItem("2010");
        chooseYear2.addItem("2011");
        chooseYear2.addItem("2012");
        chooseYear2.addItem("2013");
        chooseYear2.addItem("2014");
        chooseYear2.addItem("2015");
        chooseYear2.addItem("2016");
        chooseYear2.setSelectedIndex(0);
        chooseYear2.setVisible(false);
        add(chooseYear2);

        createServiceTypesComboBoxAndRoomTypesComboBox();

        font = new Font("arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Statystyki");
        titleLabel.setBounds(20, 5, 100, 20);
        titleLabel.setFont(font);
        add(titleLabel);

        font = new Font("arial", Font.ROMAN_BASELINE, 15);
        JLabel typeLabel = new JLabel("Wybierz rodzaj statystyk");
        typeLabel.setBounds(20, 45, 200, 15);
        typeLabel.setFont(font);
        add(typeLabel);

        JLabel subLabel = new JLabel("Wybierz jedna");
        subLabel.setBounds(20, 85, 100, 15);
        subLabel.setFont(font);
        add(subLabel);

        monthLabel = new JLabel("Wybierz miesiąc");
        monthLabel.setBounds(260, 85, 200, 15);
        monthLabel.setFont(font);
        add(monthLabel);

        yearLabel = new JLabel("Wybierz rok");
        yearLabel.setBounds(260, 45, 100, 15);
        yearLabel.setFont(font);
        add(yearLabel);

        classLabel = new JLabel("Wybierz klasę pokuju");
        classLabel.setBounds(500, 45, 200, 15);
        classLabel.setFont(font);
        classLabel.setVisible(false);
        add(classLabel);

        serveLabel = new JLabel("Wybierz typ usługi");
        serveLabel.setBounds(500, 45, 200, 15);
        serveLabel.setFont(font);
        serveLabel.setVisible(false);
        add(serveLabel);

        monthLabelFrom = new JLabel("Miesiący od");
        monthLabelFrom.setBounds(260, 45, 200, 15);
        monthLabelFrom.setFont(font);
        monthLabelFrom.setVisible(false);
        add(monthLabelFrom);

        yearLabelFrom = new JLabel("Lata od");
        yearLabelFrom.setBounds(260, 45, 200, 15);
        yearLabelFrom.setFont(font);
        yearLabelFrom.setVisible(false);
        add(yearLabelFrom);

        monthLabelTo = new JLabel("do");
        monthLabelTo.setBounds(260, 85, 200, 15);
        monthLabelTo.setFont(font);
        monthLabelTo.setVisible(false);
        add(monthLabelTo);

        yearLabelTo = new JLabel("do");
        yearLabelTo.setBounds(260, 85, 200, 15);
        yearLabelTo.setFont(font);
        yearLabelTo.setVisible(false);
        add(yearLabelTo);

        reportText = new JTextPane();
        reportText.setBounds(20, 130, 360, 444);
        reportText.setFont(font);

        reportScroll = new JScrollPane(reportText);
        reportScroll.setVisible(false);


        graphDraw.setBackground(Color.WHITE);
        graphDraw.setVisible(false);

        resizeStatistic(getWidth(), getHeight());

        add(reportScroll);
        add(graphDraw);
    }

    private void createServiceTypesComboBoxAndRoomTypesComboBox() {
        try {
            createServiceTypesComboBoxAndRoomTypesComboBox2();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void createServiceTypesComboBoxAndRoomTypesComboBox2() throws DAOException {
        List<SimpleNameData> serviceTypes = statisticDao.findAllServiceTypes();
        serviceTypeChoose = createComboBoxForData(serviceTypes);

        List<SimpleNameData> roomTypes = statisticDao.findAllRoomTypes();
        roomTypeChoose = createComboBoxForData(roomTypes);
    }

    private JComboBox<String> createComboBoxForData(List<SimpleNameData> types) {
        JComboBox<String> choose = new JComboBox<String>();
        setup(choose);
        fillComboBox(choose, types);
        add(choose);
        return choose;
    }

    private void setup(JComboBox<String> choose) {
        choose.setBounds(500, 60, 230, 20);
        choose.setVisible(false);
    }

    private void fillComboBox(JComboBox<String> choose, List<SimpleNameData> types) {
        for (SimpleNameData type : types) {
            choose.addItem(type.getName());
        }
    }

    private void addEvents() {
        executeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                graphDraw.setVisible(false);
                StatisticReport report;
                if (chooseType.getSelectedIndex() == 0) {
                    report = statistic.hotel(//
                            (REPORT_KIND) chooseSubHotel.getSelectedItem(),
                            (MONTH) chooseMonth.getSelectedItem(),
                            chooseYear.getSelectedIndex() + 2010,
                            (String) roomTypeChoose.getSelectedItem(),
                            (String) serviceTypeChoose.getSelectedItem());
                } else {
                    report = statistic.finance(//
                            (REPORT_KIND) chooseSubFinance.getSelectedItem(),
                            (MONTH) chooseMonth2.getSelectedItem(),
                            (MONTH) chooseMonth.getSelectedItem(),
                            chooseYear.getSelectedIndex() + 2010,
                            chooseYear2.getSelectedIndex() + 2010);
                }
                graphDraw.setArray(report.getArrayResult());
                reportText.setText(report.getTextResult());
                graphDraw.setVisible(true);
                reportScroll.setVisible(true);
            }
        });
        chooseType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                if (chooseType.getSelectedIndex() == 0) {
                    yearLabel.setLocation(260, 45);
                    yearLabel.setVisible(true);
                    chooseYear.setLocation(260, 60);
                    chooseYear.setVisible(true);
                    chooseYear2.setVisible(false);
                    chooseSubHotel.setVisible(true);
                    chooseSubFinance.setVisible(false);
                    chooseMonth.setVisible(true);
                    chooseMonth2.setVisible(false);
                    monthLabel.setVisible(true);
                    monthLabelFrom.setVisible(false);
                    yearLabelFrom.setVisible(false);
                    monthLabelTo.setVisible(false);
                    yearLabelTo.setVisible(false);
                    chooseSubHotel.setSelectedIndex(0);
                } else {
                    roomTypeChoose.setVisible(false);
                    serviceTypeChoose.setVisible(false);
                    serveLabel.setVisible(false);
                    classLabel.setVisible(false);
                    chooseSubHotel.setVisible(false);
                    chooseSubFinance.setVisible(true);
                    yearLabel.setLocation(500, 45);
                    chooseYear.setLocation(500, 60);
                    chooseMonth.setVisible(true);
                    chooseMonth2.setVisible(true);
                    chooseYear.setVisible(true);
                    chooseYear2.setVisible(false);
                    monthLabel.setVisible(false);
                    monthLabelFrom.setVisible(true);
                    monthLabelTo.setVisible(true);
                    chooseSubFinance.setSelectedIndex(0);
                }
            }
        });
        chooseSubHotel.addItemListener(new ItemListener() {
            int i = 0;

            public void itemStateChanged(ItemEvent arg0) {
                if ((i = chooseSubHotel.getSelectedIndex()) == 1) {
                    roomTypeChoose.setVisible(true);
                    classLabel.setVisible(true);
                    serviceTypeChoose.setVisible(false);
                    serveLabel.setVisible(false);
                } else if (i == 3) {
                    roomTypeChoose.setVisible(false);
                    classLabel.setVisible(false);
                    serviceTypeChoose.setVisible(true);
                    serveLabel.setVisible(true);
                } else {
                    roomTypeChoose.setVisible(false);
                    classLabel.setVisible(false);
                    serviceTypeChoose.setVisible(false);
                    serveLabel.setVisible(false);
                }
            }
        });
        chooseSubFinance.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                if (chooseSubFinance.getSelectedIndex() == 0) {
                    chooseYear.setLocation(500, 60);
                    yearLabel.setVisible(true);
                    chooseMonth.setVisible(true);
                    chooseMonth2.setVisible(true);
                    chooseYear.setVisible(true);
                    chooseYear2.setVisible(false);
                    monthLabelFrom.setVisible(true);
                    monthLabelTo.setVisible(true);
                    yearLabelFrom.setVisible(false);
                    yearLabelTo.setVisible(false);
                } else {
                    yearLabel.setVisible(false);
                    chooseYear.setLocation(260, 60);
                    chooseMonth.setVisible(false);
                    chooseMonth2.setVisible(false);
                    chooseYear.setVisible(true);
                    chooseYear2.setVisible(true);
                    monthLabelFrom.setVisible(false);
                    monthLabelTo.setVisible(false);
                    yearLabelFrom.setVisible(true);
                    yearLabelTo.setVisible(true);
                }
            }
        });
    }

    public void resizeStatistic(int width, int height) {
        int x = 20;
        int y = 130;
        int w = width - x * 2;
        int hh = height - y - x;
        int ww = (int) (0.4 * w);
        reportScroll.setBounds(x, y, ww, hh);
        x += ww;
        ww = (int) (0.59 * w);
        graphDraw.setBounds(x, y, ww, hh);
    }

    @Override
    public void setSize(int width, int height) {
        resizeStatistic(width, height);
        super.setSize(width, height);
    }
}
