package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;

	private JPanel cantorPanel;

	private JPanel schegulerPanel;

	private JPanel guessPanel;

	private JPanel receptionPanel;

	private JPanel rezervationPanel;

	private JPanel statisticPanel;

	private JPanel managerPanel;

	private JPanel employeeManagerPanel;

	public GUI() {
		super();
		setupLookAndFeel();
		initializeWindow();
		createGUI();
		setVisible(true);
	}
	
	private void setupLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.err.println("Błąd :: LOOK AND FEEL");
		}
	}

	private void initializeWindow() {
		setTitle("Hotel");
		setBounds(0, 0, 1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(1024, 768));
	}
	
	private void createGUI() {
		tabbedPane = new JTabbedPane();
		
		cantorPanel = new CantorPanel();
		add(cantorPanel);
		tabbedPane.addTab("Cantor", cantorPanel);
		
		schegulerPanel = new SchedulerPanel();
		add(schegulerPanel);
		tabbedPane.addTab("Grafik", schegulerPanel);
		
		receptionPanel = new ReceptionPanel();
		add(receptionPanel);
		tabbedPane.addTab("Recepcja", receptionPanel);
		
		rezervationPanel = new RezervationPanel();
		add(rezervationPanel);
		tabbedPane.addTab("Rezerwacje", rezervationPanel);
		
		
		managerPanel = new ManagerPanel();
		add(managerPanel);
		tabbedPane.addTab("Manager", managerPanel);
		
		guessPanel = new GuestBookPanel();
		add(guessPanel);
		tabbedPane.addTab("Ksi�ga go�ci", guessPanel);
		
		statisticPanel = new StatisticPanel();
		add(statisticPanel);
		tabbedPane.addTab("Statystyka", statisticPanel);
		
		employeeManagerPanel = new EmployeeManagerPanel();
		add(employeeManagerPanel);
		tabbedPane.addTab("Menad�er personelu", employeeManagerPanel);
		
		tabbedPane.setBounds(0, 0, getWidth(), getHeight());
		add(tabbedPane);
	}


	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});
	}
}