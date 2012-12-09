package gui;

import dao.CantorDao;
import dao.CantorDaoImpl;
import dto.SimpleNameData;
import dto.cantor.CurrencyData;
import exception.DAOException;
import org.apache.log4j.Logger;
import service.cantor.CURRENCY;
import service.cantor.Cantor;
import service.cantor.CantorTableResult;
import service.cantor.ExchangeCalculation;
import validation.ValidationUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static validation.ValidationUtils.isKRS;
import static validation.ValidationUtils.isPesel;

public class CantorPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(CantorPanel.class);

    private ExchangeCalculation exchangeCalculation;

    private JTextField cantorAmountJta = new JTextField();
    private JLabel cantorBuyLabel = new JLabel();
    private JLabel cantorCurrL1 = new JLabel();
    private JLabel cantorCurrL2 = new JLabel();
    private JLabel cantorClientL = new JLabel();

    private JLabel cantorPrice = new JLabel();
    private JComboBox cantorCurrBox1 = new JComboBox();
    private JComboBox cantorCurrBox2 = new JComboBox();
    private JButton cantorResButton = new JButton();
    private JButton cantorPESButton = new JButton();
    private JButton cantorDoIt = new JButton();
    private JTextField cantorCostJta = new JTextField();
    private JTextField cantorPESJta = new JTextField();
    private JLabel cantorCostLabel = new JLabel();
    private JLabel cantorPay = new JLabel();
    private JTable cantorTable = new JTable();
    private JTable cantorClientTable = new JTable();
    private JScrollPane cantorScrollPane;
    private JScrollPane cantorScrollClientPane;

    private Color bgColor = new Color(224, 230, 233);
    private Border border = BorderFactory.createLineBorder(new Color(60, 124, 142));
    private Color buttonColor = new Color(174, 205, 214);

    //    private CantorDao cantorDao = createCantorDaoMock();
    private CantorDao cantorDao = new CantorDaoImpl();
    private Cantor cantor = new Cantor(cantorDao);

    public CantorPanel() {
        create();
        addEvents();
    }

    private void create() {
        setBounds(0, 0, getWidth(), getHeight());
        setLayout(null);
        setMinimumSize(new Dimension(800, 600));
        setBackground(bgColor);
        setVisible(true);

        cantorPESJta = new JTextField("");

        cantorTable = createTable(cantor.createCurrencyTable());
        cantorScrollPane = new JScrollPane();
        cantorScrollPane.setBorder(border);
        cantorScrollPane.setViewportView(cantorTable);

        cantorScrollClientPane = new JScrollPane();
        cantorScrollClientPane.setBorder(border);

        cantorTable = new JTable();
        cantorClientTable = new JTable();
        cantorBuyLabel = new JLabel("Dostepność oraz kursy walut");
        cantorCurrL1 = new JLabel("Waluta wymieniana");
        cantorCurrL2 = new JLabel("Waluta ");
        cantorClientL = new JLabel("PESEL/KRS Klienta");
        cantorAmountJta = new JTextField("");
        cantorCostJta = new JTextField("");

        cantorAmountJta.setBorder(border);
        cantorCostJta.setBorder(border);
        cantorPESJta.setBorder(border);

        cantorPrice = new JLabel("Kwota transakcji:");
        cantorCostLabel = new JLabel("Do zap�aty:");

        cantorCurrBox1 = new JComboBox(CURRENCY.values());
        cantorCurrBox2 = new JComboBox(CURRENCY.values());
        cantorResButton = new JButton("Przelicz");
        cantorPESButton = new JButton("Szukaj");
        cantorDoIt = new JButton("Dokonaj transakcji");
        cantorResButton.setBackground(buttonColor);
        cantorPESButton.setBackground(buttonColor);
        cantorDoIt.setBackground(buttonColor);

        resizeCantor(getWidth(), getHeight());

        add(cantorPESJta);
        add(cantorPrice);
        add(cantorAmountJta);
        add(cantorCostJta);
        add(cantorTable);
        add(cantorClientTable);
        add(cantorCostLabel);
        add(cantorBuyLabel);
        add(cantorCurrL1);
        add(cantorCurrL2);
        add(cantorClientL);
        add(cantorPrice);
        add(cantorPay);
        add(cantorCurrBox1);
        add(cantorCurrBox2);
        add(cantorResButton);
        add(cantorPESButton);
        add(cantorDoIt);
        add(cantorScrollPane);
        add(cantorScrollClientPane);
    }

    private void addEvents() {

        cantorPESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cantorPESJta.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getParent(), "Nie podano numeru PESEL/KRS");
                } else {
                    if (!isPesel(cantorPESJta.getText()) && !isKRS(cantorPESJta.getText())) {
                        JOptionPane.showMessageDialog(getParent(), "Nieprawidłowy PESEL/KRS");
                    } else if (ValidationUtils.isPesel(cantorPESJta.getText())) {
                        long pesel = Long.parseLong(cantorPESJta.getText());
                        cantorClientTable = createTable(cantor.createCustomerTable(pesel));
                        cantorScrollClientPane.setViewportView(cantorClientTable);
                        cantorScrollClientPane.repaint();
                    } else if (ValidationUtils.isKRS(cantorPESJta.getText())) {
                        cantorClientTable = createTable(cantor.createCompanyTable(cantorPESJta.getText()));
                        cantorScrollClientPane.setViewportView(cantorClientTable);
                        cantorScrollClientPane.repaint();

                    }
                }
            }
        });

        cantorResButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ifNumber = true;
                if (cantorAmountJta.getText().isEmpty())
                    JOptionPane.showMessageDialog(getParent(), "Nie podano kwoty");
                else {
                    for (int i = 0; i < cantorAmountJta.getText().length(); ++i) {
                        if (cantorAmountJta.getText().charAt(i) < '0'
                                || cantorAmountJta.getText().charAt(i) > '9') {
                            ifNumber = false;
                        }
                    }
                    if (!ifNumber)
                        JOptionPane.showMessageDialog(getParent(), "Podaj prawidlowa ilosc waluty");
                    else {
                        if (cantorCurrBox1.getSelectedItem().toString() == cantorCurrBox2
                                .getSelectedItem().toString())
                            JOptionPane.showMessageDialog(getParent(),
                                    "Nie mo�na wymienia� na t� sam� walut�");
                        else {
                            Float amount = Float.valueOf(cantorAmountJta.getText());
                            exchangeCalculation = cantor.changeCalc(
                                    (CURRENCY) cantorCurrBox1.getSelectedItem(),
                                    (CURRENCY)cantorCurrBox2.getSelectedItem(),
                                    amount);
                            cantorCostJta.setText(Double.toString(round(exchangeCalculation.getCost(), 2)));

                        }
                    }
                }
            }

        });

        cantorDoIt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cantorCostJta.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getParent(), "Brak nale�nej kwoty");
                } else if (cantorClientTable.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(getParent(), "Nie zaznaczono klienta");
                } else {
                    if (cantor.changeMoney(
                            ValidationUtils.isPesel(cantorPESJta.getText()),
                            cantorClientTable.getValueAt(cantorClientTable.getSelectedRow(), 0).toString(),
                            cantor.showCurrentDate(),
                            cantorCurrBox1.getSelectedItem().toString(),
                            cantorCurrBox2.getSelectedItem().toString(),
                            exchangeCalculation.getAmount(),
                            exchangeCalculation.getCost(),
                            exchangeCalculation.getGain())) {
                        JOptionPane.showMessageDialog(getParent(), "Dokonano transakcji");
                        cantorPESJta.setText("");
                        cantorCurrBox1.setSelectedIndex(0);
                        cantorCurrBox2.setSelectedIndex(0);
                        cantorCostJta.setText("");
                        cantorTable = createTable(cantor.createCurrencyTable());
                        cantorScrollPane.setViewportView(cantorTable);
                        cantorScrollPane.repaint();
                    } else
                        JOptionPane.showMessageDialog(getParent(),
                                "Nieprawid�owe dane");
                }
            }
        });
    }

    private JTable createTable(CantorTableResult result) {
        JTable cantorTable = new JTable(result.getRowsData(), result.getColumnNames());
        cantorTable.setFillsViewportHeight(true);
        return cantorTable;
    }

    public static double round(double d, int ic) {
        NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setMaximumFractionDigits(ic);
        nf.setMinimumFractionDigits(ic);
        return Double.parseDouble((nf.format(d)).replaceAll(",", ".").replaceAll("", ""));
    }

    private void resizeCantor(int width, int height) {
        int cantorEWidth = (width - 200) / 3;
        int cantorJtaHeight = 18;
        int cantorYList = 300;

        int cantorBWidth = 100;
        int cantorBHeight = 25;
        cantorPay.setBounds(10, 10, 150, cantorJtaHeight);
        cantorCurrL1.setBounds(10, 10, 150, 18);
        cantorScrollPane.setBounds(cantorCurrL1.getX() + cantorCurrL1.getWidth() + 40, cantorYList - 270, 500, 230);
        cantorBuyLabel.setBounds(cantorScrollPane.getX(), cantorScrollPane.getY() - 20, cantorEWidth, 18);
        cantorCurrBox1.setBounds(10, cantorCurrL1.getY() + 20, 100, 20);
        cantorCurrL2.setBounds(10, cantorCurrBox1.getY() + 20, cantorEWidth, 18);
        cantorCurrBox2.setBounds(cantorCurrBox1.getX(), cantorCurrL2.getY() + 20, 100, 20);
        cantorPrice.setBounds(cantorCurrBox2.getX(), cantorCurrBox2.getY() + 30, 150, cantorJtaHeight);
        cantorAmountJta.setBounds(cantorPrice.getX(), cantorPrice.getY() + 20, 100, cantorJtaHeight);
        cantorResButton.setBounds(cantorAmountJta.getX(), cantorAmountJta.getY() + 22, cantorBWidth, cantorBHeight);
        cantorCostLabel.setBounds(cantorResButton.getX(), cantorResButton.getY() + 28, 150, cantorJtaHeight);
        cantorCostJta.setBounds(cantorCostLabel.getX(), cantorCostLabel.getY() + 22, 100, cantorJtaHeight);
        cantorClientL.setBounds(10, cantorCostJta.getY() + 80, cantorEWidth, 18);
        cantorPESJta.setBounds(cantorClientL.getX(), cantorClientL.getY() + 22, 100, cantorJtaHeight);
        cantorPESButton.setBounds(cantorPESJta.getX(), cantorPESJta.getY() + 22, cantorBWidth, cantorBHeight);
        cantorScrollClientPane.setBounds(cantorScrollPane.getX(), cantorScrollPane.getY() + cantorScrollPane.getHeight() + 30, 550, 230);
        cantorDoIt.setBounds(cantorCostJta.getX(), cantorCostJta.getY() + 300, cantorBWidth + 50, cantorBHeight);
    }

    @Override
    public void setSize(int width, int height) {
        resizeCantor(width, height);
        super.setSize(width, height);
    }

    public static CantorDao createCantorDaoMock() {
        try {
            Object[] row = new Object[]{1, "USD", 3.2, 3.0, 100};
            String[] columnNames = new String[]{"Id", "Name", "Sale Price", "Buy Price", "Quantity"};

            CurrencyData currencyData = mock(CurrencyData.class);
            when(currencyData.getArray()).thenReturn(row);

            SimpleNameData simpleNameData = mock(SimpleNameData.class);
            when(simpleNameData.getName()).thenReturn(columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4]);

            CantorDao cantorDao = mock(CantorDao.class);
            when(cantorDao.showColumnsForCurrency()).thenReturn(Arrays.asList(simpleNameData, simpleNameData, simpleNameData, simpleNameData, simpleNameData));
            when(cantorDao.findAllCurrency()).thenReturn(Arrays.asList(currencyData));

            return cantorDao;
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
