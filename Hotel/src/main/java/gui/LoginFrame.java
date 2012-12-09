package gui;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import service.Login;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame logFrame;//= new LoginFrame();
	private JPanel logPanel;
	private JLabel logLabel[] = new JLabel[3];
	private JTextField logLogin;
	private JPasswordField logPass;
	private JButton logSubmit;
	private Color bgColor2 = new Color(227, 239, 243);
	private Border border = BorderFactory.createLineBorder(new Color(60, 124, 142));
	private Color buttonColor = new Color(174, 205, 214);
	
	private Login log = new Login();
	
	public LoginFrame() throws HeadlessException {
		createLogin();
	}
	
	private void createLogin() {
		
		logFrame = new JFrame("Logowanie");
		logFrame.setLayout(null);
		logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logFrame.setBounds(0, 0, 320, 180);
		logFrame.setLocationRelativeTo(null);

		logPanel = new JPanel();
		logPanel.setLayout(null);
		logPanel.setBounds(0, 0, logFrame.getWidth(), logFrame.getHeight());
		logPanel.setBackground(bgColor2);

		logLabel[0] = new JLabel("LOGIN:");
		logLabel[0].setBounds(20, 50, 50, 20);

		logLabel[1] = new JLabel("HAS�O:");
		logLabel[1].setBounds(logLabel[0].getX(), logLabel[0].getY() + 21, 50,
				20);

		logLogin = new JTextField();
		logLogin.setBorder(border);
		logLogin.setBounds(logLabel[0].getX() + 55, logLabel[0].getY() + 2,
				200, 18);
		logPanel.add(logLogin);

		logPass = new JPasswordField();
		logPass.setBorder(border);
		logPass.setBounds(logLabel[0].getX() + 55, logLabel[1].getY() + 2, 200,
				18);
		logPanel.add(logPass);

		logSubmit = new JButton("Zaloguj");
		logSubmit.setBackground(buttonColor);
		logSubmit.setBorder(border);
		logSubmit.setBounds(logPass.getX() + logPass.getWidth() - 100,
				logPass.getY() + 25, 100, 25);
		logSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (log.check(logLogin.getText(), logPass.getPassword())) {
					logLabel[2].setVisible(false);
					createGUI();
					logFrame.dispose();
				} else {
					logLabel[2].setVisible(true);
				}
			}

		});
		logPanel.add(logSubmit);

		logLabel[2] = new JLabel("B��dny login lub has�o!");
		logLabel[2].setForeground(Color.red);
		logLabel[2].setBounds(logLabel[0].getX(), logLabel[0].getY() - 25, 200,
				20);
		logLabel[2].setVisible(false);

		for (int i = 0; i < 3; i++) {
			logLabel[i].setHorizontalAlignment(SwingConstants.RIGHT);
			logPanel.add(logLabel[i]);
		}

		logFrame.add(logPanel);
		logFrame.setVisible(true);
	}

	public void createGUI() {
		// TODO Auto-generated method stub
		
	}
}
