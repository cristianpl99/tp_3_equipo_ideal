package tp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;

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

// faltaria hacer algo parecido a la seccion de agregar empleado pero con el tema de agregar conflictos (y de agregar foto tal vez???) en la otra parte libre que quedo
// ¡¡¡¡¡¡ NO USAR MAS ESPACIO DEL Q YA ESTA USADO PARA ABAJO QUE AHI SE PONDRAN LAS TABLAS DE ESTADISTICA OCULTANDO LOS BOTONES UNA VEZ CORRIDOS LOS ALGORITMOS !!!!!!

public class CreatorWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int cantProjectLeader;
	private int cantArchitect;
	private int cantProgrammer;
	private int cantTester;
	private JTextField textFirstName;
	private JTextField textLastName;
	private JTextField textRating;
	private JLabel lblAddEmployee;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblRating;
	private JLabel lblRole;
	private JLabel lblListOfEmployees;

	public CreatorWindow(String projectLeader, String architect, String programmer, String tester, IdealTeam idealTeam,
			List<Employee> employees) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<String> listOfEmployee = new JComboBox<String>();
		listOfEmployee.setBounds(17, 350, 330, 22);
		contentPane.add(listOfEmployee);

		for (Employee em : employees) {
			listOfEmployee.addItem(em.getFirstName() + " " + em.getLastName() + " - Role: " + em.getRole()
					+ ", Rating: " + em.getRating());
		}

		cantProjectLeader = Integer.parseInt(projectLeader);
		cantArchitect = Integer.parseInt(architect);
		cantProgrammer = Integer.parseInt(programmer);
		cantTester = Integer.parseInt(tester);

		lblAddEmployee = createLabel("DATA OF NEW EMPLOYEE", 18, 50, 11, 242, 45);
		lblFirstName = createLabel("First name", 14, 37, 67, 99, 29);
		lblLastName = createLabel("Last name", 14, 37, 107, 99, 29);
		lblRating = createLabel("Rating", 14, 37, 153, 99, 29);
		lblRole = createLabel("Role", 14, 37, 193, 99, 29);
		lblListOfEmployees = createLabel("LIST OF EMPLOYEES", 14, 107, 299, 159, 29);

		contentPane.add(lblAddEmployee);
		contentPane.add(lblFirstName);
		contentPane.add(lblLastName);
		contentPane.add(lblRating);
		contentPane.add(lblRole);
		contentPane.add(lblListOfEmployees);

		textFirstName = createTextField(166, 67, 148, 29, 10, JTextField.CENTER);
		textLastName = createTextField(166, 107, 148, 29, 10, JTextField.CENTER);
		textRating = createTextField(166, 153, 148, 29, 10, JTextField.CENTER);

		entryValidationForNames(textFirstName);
		entryValidationForNames(textLastName);
		entryValidationForRating(textRating);

		contentPane.add(textFirstName);
		contentPane.add(textLastName);
		contentPane.add(textRating);

		JComboBox<String> role = new JComboBox<String>();
		role.setBounds(166, 193, 148, 29);
		contentPane.add(role);
		role.addItem("Project_Leader");
		role.addItem("Architect");
		role.addItem("Programmer");
		role.addItem("Tester");

		// tema del id de la persona a agregar hay q ver q onda, y el tema de la foto
		// del tipo a agregar tambien (IDEA=poner una foto generica con un signo de
		// interrogacion

		JButton btnAddEmployee = new JButton("Add employee");
		btnAddEmployee.setBounds(99, 245, 159, 43);
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFirstName.getText().equals("") || textLastName.getText().equals("")
						|| textRating.getText().equals("")) {
					showMessageDialog("Missing to add data of the new employee");
				} else {
					Employee em = new Employee("", textFirstName.getText(), textLastName.getText(),
							Integer.parseInt(textRating.getText()), new HashSet<String>(),
							Role.valueOf((String) role.getSelectedItem()), "foto.jpg");
					if (employees.contains(em)) {
						showMessageDialog("Employee already exists");
					} else {
						employees.add(em);
						showMessageDialog("employee added successfully");
						listOfEmployee.addItem(em.getFirstName() + " " + em.getLastName() + " - Role: " + em.getRole()
								+ ", Rating: " + em.getRating());
					}
				}
			}

		});
		contentPane.add(btnAddEmployee);

		JButton btnBruteForce = new JButton("Run Brute Force");
		btnBruteForce.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        BruteForceWorker worker = new BruteForceWorker(employees, cantProjectLeader, cantArchitect, cantProgrammer, cantTester);
		        worker.execute(); 
		    }
		});
		btnBruteForce.setBounds(50, 600, 148, 122);
		contentPane.add(btnBruteForce);


		JButton btnBacktracking = new JButton("Run Backtracking");
		btnBacktracking.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
			        BackTrackingWorker worker = new BackTrackingWorker(employees, cantProjectLeader, cantArchitect, cantProgrammer, cantTester);
			        worker.execute(); 
			    }
			});
		btnBacktracking.setBounds(365, 600, 148, 122);
		contentPane.add(btnBacktracking);

		JButton btnHeuristics = new JButton("Run Heuristics");
		btnHeuristics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        HeuristicWorker worker = new HeuristicWorker(employees, cantProjectLeader, cantArchitect, cantProgrammer, cantTester);
		        worker.execute(); 
		    }
		});
		btnHeuristics.setBounds(680, 600, 148, 122);
		contentPane.add(btnHeuristics);

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
}
