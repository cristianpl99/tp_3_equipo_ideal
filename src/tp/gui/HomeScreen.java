package tp.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tp.logic.IdealTeam;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HomeScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField project_leader;
	private JTextField architect;
	private JTextField programmer;
	private JTextField tester;
	private JLabel lblTitulo;
	private JLabel lblLeader;
	private JLabel lblArch;
	private JLabel lblProgrammer;
	private JLabel lblTester;

	public HomeScreen(IdealTeam idealTeam) {

		setTitle("Programacion III - Ideal Team");
		ImageIcon icon = new ImageIcon("src/tp/dal/images/icon.png");
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		project_leader = createTextField(313, 103, 107, 20, 10, JTextField.CENTER);
		architect = createTextField(313, 134, 107, 20, 10, JTextField.CENTER);
		programmer = createTextField(313, 165, 107, 20, 10, JTextField.CENTER);
		tester = createTextField(313, 196, 107, 20, 10, JTextField.CENTER);

		entryValidationForCant(project_leader);
		entryValidationForCant(architect);
		entryValidationForCant(programmer);
		entryValidationForCant(tester);

		contentPane.add(project_leader);
		contentPane.add(architect);
		contentPane.add(programmer);
		contentPane.add(tester);

		lblTitulo = createLabel("TEAM REQUIREMENTS", 20, 39, 23, 411, 50);
		lblLeader = createLabel("Project Leaders", 14, 53, 103, 191, 20);
		lblArch = createLabel("Architects", 14, 53, 134, 191, 20);
		lblProgrammer = createLabel("Programmers", 14, 53, 165, 191, 20);
		lblTester = createLabel("Testers", 14, 53, 196, 191, 20);

		contentPane.add(lblTitulo);
		contentPane.add(lblLeader);
		contentPane.add(lblArch);
		contentPane.add(lblProgrammer);
		contentPane.add(lblTester);

		JButton btnAvanzar = new JButton("Go to creator");
		btnAvanzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainScreen launch;
				if (project_leader.getText().equals("") || architect.getText().equals("")
						|| programmer.getText().equals("") || tester.getText().equals("")) {
					showMessageDialog("Must complete all team requirements to advance");
				} else {
					dispose();
					launch = new MainScreen(idealTeam, project_leader.getText(), architect.getText(),
							programmer.getText(), tester.getText());
					launch.initialize();
				}
			}

		});
		btnAvanzar.setBounds(283, 227, 143, 50);
		contentPane.add(btnAvanzar);
	}

	public void initialize() {
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private JLabel createLabel(String text, int fontSize, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(x, y, width, height);
		return label;
	}

	private JTextField createTextField(int x, int y, int width, int height, int columns, int horizontalAlignment) {
		JTextField textField = new JTextField();
		textField.setHorizontalAlignment(horizontalAlignment);
		textField.setColumns(columns);
		textField.setBounds(x, y, width, height);
		contentPane.add(textField);
		return textField;
	}

	private void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
	}

	private void entryValidationForCant(JTextField jText) {
		jText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int key = e.getKeyChar();
				// valida que la entrada sea un numero, no 0, y solo 1
				boolean numeros = (key >= 49 && key <= 57);
				if ((!numeros || jText.getText().trim().length() == 1)) {
					e.consume();
				}
			}
		});
	}

}