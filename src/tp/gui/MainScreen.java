package tp.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JProgressBar;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import tp.logic.Employee;
import tp.logic.IdealTeam;
import tp.logic.Employee.Role;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;

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
	private JLabel lblCombinations;
	private JLabel lblTime;
	private JLabel lblAverageRating;
	private ArrayList<Employee> employees;
	private List<Employee> bestCombination;
	private IdealTeam idealTeam;
	private JLabel lblAlgorithmtitle;

	public static double combinations;
	public static double time;
	public static double averageRating;
	public static HashMap<String, Object[]> resultMap;

	public MainScreen(String projectLeader, String architect, String programmer, String tester) {

		this.cantProjectLeader = Integer.parseInt(projectLeader);
		this.cantArchitect = Integer.parseInt(architect);
		this.cantProgrammer = Integer.parseInt(programmer);
		this.cantTester = Integer.parseInt(tester);
		
		idealTeam = IdealTeam.getIdealTeam();

		setTitle("Programacion III - Team Builder");
		ImageIcon icon = new ImageIcon("src/tp/dal/images/icon.png");
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 818);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		lblAddEmployee = createLabel("DATA OF NEW EMPLOYEE", 18, 70, 11, 242, 45);
		lblDni = createLabel("DNI", 14, 47, 67, 99, 29);
		lblFirstName = createLabel("First name", 14, 47, 107, 99, 29);
		lblLastName = createLabel("Last name", 14, 47, 147, 99, 29);
		lblRating = createLabel("Rating", 14, 47, 193, 99, 29);
		lblRole = createLabel("Role", 14, 47, 233, 99, 29);
		lblListOfEmployees = createLabel("LIST OF EMPLOYEES", 14, 117, 319, 159, 29);
		lblConflicts = createLabel("CONFLICTS", 18, 612, 11, 130, 45);
		lblListOfConflicts = createLabel("LIST OF CONFLICTS", 14, 592, 321, 152, 22);
		lblCombinations = createLabel("", 14, 284, 715, 452, 22);
		lblCombinations.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime = createLabel("", 14, 284, 735, 452, 22);
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblAverageRating = createLabel("", 14, 284, 755, 452, 22);
		lblAverageRating.setHorizontalAlignment(SwingConstants.CENTER);	
		lblAlgorithmtitle = createLabel("", 15, 414, 409, 193, 22);
		lblAlgorithmtitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		contentPane.add(lblAddEmployee);
		contentPane.add(lblDni);
		contentPane.add(lblFirstName);
		contentPane.add(lblLastName);
		contentPane.add(lblRating);
		contentPane.add(lblRole);
		contentPane.add(lblListOfEmployees);
		contentPane.add(lblConflicts);
		contentPane.add(lblListOfConflicts);
		contentPane.add(lblCombinations);
		contentPane.add(lblTime);
		contentPane.add(lblCombinations);
		contentPane.add(lblAverageRating);
		contentPane.add(lblAlgorithmtitle);

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

		String[] columnNames = { "DNI", "Rol", "Nombre", "Apellido", "Rating" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

		JTable table = new JTable(tableModel);
		table.setBounds(344, 447, 392, 267);
		table.setEnabled(true);

		table.setDefaultEditor(Object.class, null);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {
						showEmployee(selectedRow);
					}
				}
			}
		});
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		TableColumnModel columnModel = table.getColumnModel();
		int columnCount = columnModel.getColumnCount();
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			TableColumn column = columnModel.getColumn(columnIndex);
			column.setResizable(false);
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(284, 447, 452, 267);
		contentPane.add(scrollPane);
		populateTable(table, employees);

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
					Employee employee = new Employee(textDni.getText(), textFirstName.getText(), textLastName.getText(),
							Integer.parseInt(textRating.getText()), new HashSet<String>(),
							Role.valueOf((String) role.getSelectedItem()), "src/tp/dal/images/random.png");
					if (employees.contains(employee)) {
						showMessageDialog("Employee already exists");
					} else {
						employees.add(employee);
						idealTeam.addEmployee(employee);
						showMessageDialog("Employee added successfully");
						listOfEmployee.addItem(employee.getFirstName() + " " + employee.getLastName() + " - Role: "
								+ employee.getRole() + ", Rating: " + employee.getRating());
						conflict_1.addItem(employee.getDni() + " - " + employee.getLastName());
						conflict_2.addItem(employee.getDni() + " - " + employee.getLastName());
						populateTable(table, employees);
					}
				}
			}

		});
		contentPane.add(btnAddEmployee);

		JProgressBar progressBarBruteForce = new JProgressBar();
		progressBarBruteForce.setBounds(17, 497, 148, 45);
		progressBarBruteForce.setVisible(false);
		progressBarBruteForce.setForeground(Color.GREEN);
		contentPane.add(progressBarBruteForce);

		JButton btnBruteForce = new JButton("Run Brute Force");
		btnBruteForce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configureButtonWithProgressBar(btnBruteForce, progressBarBruteForce,
						new BruteForceWorker(cantProjectLeader, cantArchitect, cantProgrammer, cantTester),
						table);
						lblAlgorithmtitle.setText("BRUTE FORCE SEARCH");
			}
		});
		btnBruteForce.setBounds(17, 498, 148, 44);
		contentPane.add(btnBruteForce);

		JProgressBar progressBarBacktracking = new JProgressBar();
		progressBarBacktracking.setBounds(17, 576, 148, 44);
		progressBarBacktracking.setVisible(false);
		progressBarBacktracking.setForeground(Color.RED);
		contentPane.add(progressBarBacktracking);

		JButton btnBackTracking = new JButton("Run BackTracking");
		btnBackTracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configureButtonWithProgressBar(btnBackTracking, progressBarBacktracking,
						new BackTrackingWorker(cantProjectLeader, cantArchitect, cantProgrammer, cantTester),
						table);
						lblAlgorithmtitle.setText("BACKTRACKING SEARCH");
			}
		});
		btnBackTracking.setBounds(17, 576, 148, 44);
		contentPane.add(btnBackTracking);

		JProgressBar progressBarHeuristics = new JProgressBar();
		progressBarHeuristics.setBounds(17, 654, 148, 43);
		progressBarHeuristics.setVisible(false);
		progressBarHeuristics.setForeground(Color.BLUE);
		contentPane.add(progressBarHeuristics);

		JButton btnHeuristics = new JButton("Run Heuristic");
		btnHeuristics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configureButtonWithProgressBar(btnHeuristics, progressBarHeuristics,
						new HeuristicWorker(cantProjectLeader, cantArchitect, cantProgrammer, cantTester),
						table);
				lblAlgorithmtitle.setText("HEURISTIC SEARCH");
			}
		});
		btnHeuristics.setBounds(17, 654, 148, 43);
		contentPane.add(btnHeuristics);

		JProgressBar progressBarAlgorithms = new JProgressBar();
		progressBarAlgorithms.setBounds(17, 417, 148, 45);
		progressBarAlgorithms.setVisible(false);
		progressBarAlgorithms.setForeground(Color.YELLOW);
		contentPane.add(progressBarAlgorithms);

		JButton btnAlgorithms = new JButton("Run Comparative");
		btnAlgorithms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBarAlgorithms.setVisible(true);
				progressBarAlgorithms.setIndeterminate(true);
				AlgorithmsWorker worker = new AlgorithmsWorker(cantProjectLeader, cantArchitect,
						cantProgrammer, cantTester);
				worker.execute();
				worker.addPropertyChangeListener(new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if ("state".equals(evt.getPropertyName()) && SwingWorker.StateValue.DONE == evt.getNewValue()) {
							btnAlgorithms.setVisible(true);
							progressBarAlgorithms.setVisible(false);
							ComparisonScreen launch = new ComparisonScreen(resultMap);
							launch.initialize();
						}
					}
				});
			}
		});
		btnAlgorithms.setBounds(17, 417, 148, 43);
		contentPane.add(btnAlgorithms);

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


	public void initialize() {
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void showEmployee(int selectedRow) {
		EmployeeScreen launch;
		launch = new EmployeeScreen(this.bestCombination.get(selectedRow));
		launch.setResizable(false);
		launch.setVisible(true);
		launch.setLocationRelativeTo(null);
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

	private void configureButtonWithProgressBar(JButton button, JProgressBar progressBar,
			SwingWorker<List<Employee>, Void> worker, JTable table) {
		button.setVisible(false);
		progressBar.setVisible(true);
		progressBar.setIndeterminate(true);

		worker.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("progress".equals(evt.getPropertyName())) {
					int progress = (int) evt.getNewValue();
					progressBar.setIndeterminate(false);
					progressBar.setValue(progress);
				}
			}
		});

		worker.execute();
		worker.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("state".equals(evt.getPropertyName()) && SwingWorker.StateValue.DONE == evt.getNewValue()) {
					try {
						bestCombination = worker.get();
						if (bestCombination.size() != cantProjectLeader + cantArchitect + cantProgrammer + cantTester) {
							showMessageDialog("There is no possible combination due to the employees "
									+ "conflict configuration.");
						} else {
							populateTable(table, bestCombination);
							lblCombinations.setText("Combinations " + String.valueOf(combinations));
							String executionTime = String.format("%.4fs", time);
							String rating = String.format("%.4f", averageRating);
							lblTime.setText("Time " + executionTime + " seconds");
							lblAverageRating.setText("Average Team Rating " + rating);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					button.setVisible(true);
					progressBar.setVisible(false);
				}
			}
		});
	}

	private void populateTable(JTable table, List<Employee> employees) {
		this.bestCombination = employees;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Employee employee : employees) {
			model.addRow(new Object[] { employee.getDni(), employee.getRole(), employee.getFirstName(),
					employee.getLastName(), employee.getRating() });
		}
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