package tp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tp.logic.Employee;
import tp.logic.IdealTeam;
import tp.logic.Employee.Role;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int cantProjectLeader;
	private int cantArchitect;
	private int cantProgrammer;
	private int cantTester;
	private JTextField textDni;
	private JTextField textFirstName;
	private JTextField textLastName;
	private JTextField textRating;
	private JLabel lblAddEmployee;
	private JLabel lblDni;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblRating;
	private JLabel lblRole;
	private JLabel lblListOfEmployees;
	private JLabel lblConflicts;
	private JLabel lblListOfConflicts;
	private IdealTeam idealTeam;
	private ArrayList<Employee> employees;

	public MainScreen(String projectLeader, String architect, String programmer, String tester) {
		setTitle("Programacion III - Equipo ideal - Constructor");
		ImageIcon icon = new ImageIcon("src/tp/dal/images/icon.png");
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		idealTeam = new IdealTeam();
		employees = (ArrayList<Employee>) idealTeam.getEmployees();

		JComboBox<String> listOfEmployee = new JComboBox<String>();
		listOfEmployee.setBounds(17, 350, 350, 22);
		contentPane.add(listOfEmployee);

		JComboBox<String> conflict_1 = new JComboBox<String>();
		conflict_1.setBounds(466, 70, 175, 22);
		contentPane.add(conflict_1);

		JComboBox<String> conflict_2 = new JComboBox<String>();
		conflict_2.setBounds(680, 70, 175, 22);
		contentPane.add(conflict_2);

		for (Employee em : employees) {
			listOfEmployee.addItem(em.getFirstName() + " " + em.getLastName() + " - Role: " + em.getRole()
					+ ", Rating: " + em.getRating());
			conflict_1.addItem(em.getDni() + " - " + em.getLastName());
			conflict_2.addItem(em.getDni() + " - " + em.getLastName());
		}

		JComboBox<String> listOfConflicts = new JComboBox<String>();
		listOfConflicts.setBounds(493, 350, 335, 22);
		contentPane.add(listOfConflicts);

		for (Employee em : employees) {
			if (!em.getConflicts().isEmpty()) {
				for (String dniConflict : em.getConflicts()) {
					listOfConflicts.addItem(em.getDni() + " - " + em.getLastName() + " ---> " + dniConflict + " - "
							+ idealTeam.findEmployeeByDni(dniConflict).getLastName());
				}
			}
		}

		cantProjectLeader = Integer.parseInt(projectLeader);
		cantArchitect = Integer.parseInt(architect);
		cantProgrammer = Integer.parseInt(programmer);
		cantTester = Integer.parseInt(tester);

		lblAddEmployee = createLabel("DATA OF NEW EMPLOYEE", 18, 70, 11, 242, 45);
		lblDni = createLabel("DNI", 14, 47, 67, 99, 29);
		lblFirstName = createLabel("First name", 14, 47, 107, 99, 29);
		lblLastName = createLabel("Last name", 14, 47, 147, 99, 29);
		lblRating = createLabel("Rating", 14, 47, 193, 99, 29);
		lblRole = createLabel("Role", 14, 47, 233, 99, 29);
		lblListOfEmployees = createLabel("LIST OF EMPLOYEES", 14, 117, 319, 159, 29);
		lblConflicts = createLabel("CONFLICTS", 18, 612, 11, 130, 45);
		lblListOfConflicts = createLabel("LIST OF CONFLICTS", 14, 592, 321, 152, 22);

		contentPane.add(lblAddEmployee);
		contentPane.add(lblDni);
		contentPane.add(lblFirstName);
		contentPane.add(lblLastName);
		contentPane.add(lblRating);
		contentPane.add(lblRole);
		contentPane.add(lblListOfEmployees);
		contentPane.add(lblConflicts);
		contentPane.add(lblListOfConflicts);

		textDni = createTextField(176, 67, 148, 29, 10, JTextField.CENTER);
		textFirstName = createTextField(176, 107, 148, 29, 10, JTextField.CENTER);
		textLastName = createTextField(176, 147, 148, 29, 10, JTextField.CENTER);
		textRating = createTextField(176, 193, 148, 29, 10, JTextField.CENTER);

		entryValidationForNames(textFirstName);
		entryValidationForDni(textDni);
		entryValidationForNames(textLastName);
		entryValidationForRating(textRating);

		contentPane.add(textFirstName);
		contentPane.add(textDni);
		contentPane.add(textLastName);
		contentPane.add(textRating);

		JComboBox<String> role = new JComboBox<String>();
		role.setBounds(176, 233, 148, 29);
		contentPane.add(role);
		role.addItem("Project_Leader");
		role.addItem("Architect");
		role.addItem("Programmer");
		role.addItem("Tester");

		JButton btnAddEmployee = new JButton("Add employee");
		btnAddEmployee.setBounds(109, 275, 159, 43);
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFirstName.getText().equals("") || textLastName.getText().equals("")
						|| textRating.getText().equals("") || textDni.getText().equals("")) {
					showMessageDialog("Missing to add data of the new employee");
				} else {
					Employee em = new Employee(textDni.getText(), textFirstName.getText(), textLastName.getText(),
							Integer.parseInt(textRating.getText()), new HashSet<String>(),
							Role.valueOf((String) role.getSelectedItem()), "foto.png");
					if (employees.contains(em)) {
						showMessageDialog("Employee already exists");
					} else {
						employees.add(em);
						showMessageDialog("Employee added successfully");
						listOfEmployee.addItem(em.getFirstName() + " " + em.getLastName() + " - Role: " + em.getRole()
								+ ", Rating: " + em.getRating());
						conflict_1.addItem(em.getDni() + " - " + em.getLastName());
						conflict_2.addItem(em.getDni() + " - " + em.getLastName());
					}
				}
			}

		});
		contentPane.add(btnAddEmployee);

		JButton btnBruteForce = new JButton("Run Brute Force");
		btnBruteForce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BruteForceWorker worker = new BruteForceWorker(idealTeam, cantProjectLeader, cantArchitect,
						cantProgrammer, cantTester);
				worker.execute();
			}
		});
		btnBruteForce.setBounds(50, 600, 148, 122);
		contentPane.add(btnBruteForce);

		JButton btnBacktracking = new JButton("Run Backtracking");
		btnBacktracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BackTrackingWorker worker = new BackTrackingWorker(idealTeam, cantProjectLeader, cantArchitect,
						cantProgrammer, cantTester);
				worker.execute();
			}
		});
		btnBacktracking.setBounds(365, 600, 148, 122);
		contentPane.add(btnBacktracking);

		JButton btnHeuristics = new JButton("Run Heuristics");
		btnHeuristics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HeuristicWorker worker = new HeuristicWorker(idealTeam, cantProjectLeader, cantArchitect,
						cantProgrammer, cantTester);
				worker.execute();
			}
		});
		btnHeuristics.setBounds(680, 600, 148, 122);
		contentPane.add(btnHeuristics);

		JButton btnAddConflict = new JButton("Add conflict");
		btnAddConflict.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conflict_1.getSelectedItem().equals(conflict_2.getSelectedItem())) {
					showMessageDialog("Selected the same employee");
				} else {
					String[] conflict1 = conflict_1.getSelectedItem().toString().split(" - ");
					String[] conflict2 = conflict_2.getSelectedItem().toString().split(" - ");
					String dni1 = conflict1[0];
					String dni2 = conflict2[0];
					if (idealTeam.findEmployeeByDni(dni1).getConflicts().contains(dni2)) {
						showMessageDialog("Conflict already exists");
					} else {
						Employee em1 = idealTeam.findEmployeeByDni(dni1);
						em1.addConflict(dni2);
						Employee em2 = idealTeam.findEmployeeByDni(dni2);
						em2.addConflict(dni1);
						showMessageDialog("Conflict added successfully");
						listOfConflicts.addItem(em1.getDni() + " - " + em1.getLastName() + " ---> " + em2.getDni()
								+ " - " + em2.getLastName());
						listOfConflicts.addItem(em2.getDni() + " - " + em2.getLastName() + " ---> " + em1.getDni()
								+ " - " + em1.getLastName());
					}
				}
			}

		});
		btnAddConflict.setBounds(588, 275, 148, 43);
		contentPane.add(btnAddConflict);

	}

	private JLabel createLabel(String text, int fontSize, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(x, y, width, height);
		return label;
	}

	private JTextField createTextField(int x, int y, int width, int height, int columns, int horizontalAlignment) {
		JTextField textField = new JTextField();
		textField.setHorizontalAlignment(horizontalAlignment);
		textField.setForeground(Color.BLACK);
		textField.setColumns(columns);
		textField.setBounds(x, y, width, height);
		contentPane.add(textField);
		return textField;
	}

	private void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
	}

	private void entryValidationForNames(JTextField jText) {
		jText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int key = e.getKeyChar();
				// valida que la entrada sean letras y no mas de 9
				boolean letras = ((key >= 97 && key <= 122) || (key >= 65 && key <= 90));
				if ((!letras || jText.getText().trim().length() == 9)) {
					e.consume();
				}
			}
		});
	}

	private void entryValidationForRating(JTextField jText) {
		jText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int key = e.getKeyChar();
				// valida que la entrada sea un numero del 1 al 5, solo 1
				boolean numeros = (key >= 49 && key <= 53);
				if ((!numeros || jText.getText().trim().length() == 1)) {
					e.consume();
				}
			}
		});
	}

	private void entryValidationForDni(JTextField jText) {
		jText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int key = e.getKeyChar();
				// valida que la entrada sea un numero del 0 al 9, y de largo de 8 digitos
				boolean numeros = (key >= 48 && key <= 58);
				if ((!numeros || jText.getText().trim().length() == 8)) {
					e.consume();
				}
			}
		});
	}
}
