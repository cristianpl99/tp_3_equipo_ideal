package tp.gui;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;

import tp.logic.Employee;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

public class ComparisonScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Map<String, Object[]> resultMap;
	private JLabel lblBruteForce;
	private JLabel lblBacktracking;
	private JLabel lblHeuristic;

	public ComparisonScreen(Map<String, Object[]> resultMap) {

		this.resultMap = resultMap;
		setTitle("Programacion III - Team Comparison");
		ImageIcon icon = new ImageIcon("src/tp/dal/images/icon.png");
		setIconImage(icon.getImage());
		
		Icon backgroundIcon = new ImageIcon("src/tp/dal/images/fondo4.png");
		JLabel backgroundLabel = new JLabel(backgroundIcon);
		backgroundLabel.setBounds(0, 0, 1000, 600);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblBruteForce = createLabel("Brute Force", 14, 76, 11, 123, 22);
		lblBacktracking = createLabel("Backtracking", 14, 389, 11, 123, 22);
		lblHeuristic = createLabel("Heuristic", 14, 663, 11, 123, 22);

		contentPane.add(lblBruteForce);
		contentPane.add(lblBacktracking);
		contentPane.add(lblHeuristic);

		String[] columnNames = { "DNI", "Role", "Lastname", "Rating" };

		for (int i = 0; i < 3; i++) {
			DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
			JTable table = new JTable(tableModel);
			table.setEnabled(true);
			table.setDefaultEditor(Object.class, null);
			populateTable(table, i);
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

			scrollPane.setBounds(15 + i * 290, 35, 280, 260);
			contentPane.add(scrollPane);
		}

		String[] resultcolumnNames = { "Algorithm", "Combinations", "Execution Time", "Average Rating" };
		DefaultTableModel tableModelResult = new DefaultTableModel(resultcolumnNames, 0);

		JTable table = new JTable(tableModelResult);
		table.setBounds(344, 447, 392, 267);
		table.setEnabled(true);

		table.setDefaultEditor(Object.class, null);
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
		scrollPane.setBounds(422, 367, 452, 89);
		contentPane.add(scrollPane);
		populateResultsTable(table);
		contentPane.setLayout(null);

		JPanel graphPanel = new JPanel();
		graphPanel.setBounds(20, 319, 387, 137);
		contentPane.add(graphPanel);
		createBarChart(resultMap, graphPanel);
		
		contentPane.add(backgroundLabel);
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

	private void populateTable(JTable table, int tab) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		String[] keys = { "Brute Force", "Backtracking", "Heuristic" };
		String key = keys[tab];

		@SuppressWarnings("unchecked")
		List<Employee> employees = (List<Employee>) resultMap.get(key)[0];
		for (Employee employee : employees) {
			model.addRow(new Object[] { employee.getDni(), employee.getRole(), employee.getLastName(),
					employee.getRating() });
		}
	}

	private void populateResultsTable(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (Map.Entry<String, Object[]> entry : resultMap.entrySet()) {
			String algorithm = entry.getKey();
			Object[] values = entry.getValue();
			String combinations = String.valueOf(values[1]);
			long time = (long) values[2];
			double executionTimeSeconds = (TimeUnit.MILLISECONDS.toSeconds(time) + (time % 1000) / 1000.0);
			String executionTime = String.format("%.4fs", executionTimeSeconds);
			String averageRating = String.format("%.4f", values[3]);
			model.addRow(new Object[] { algorithm, combinations, executionTime, averageRating });
		}
	}

	private void createBarChart(Map<String, Object[]> resultMap, JPanel graphPanel) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Map.Entry<String, Object[]> entry : resultMap.entrySet()) {
			String algorithm = entry.getKey();
			double averageRating = (double) entry.getValue()[3];
			dataset.addValue(averageRating, "Average Rating", algorithm);
		}

		JFreeChart chart = ChartFactory.createBarChart("Team Rating Comparison", "Algorithm", "Average Rating", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(java.awt.Color.GRAY);
		plot.getRangeAxis().setRange(3, 5);

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, java.awt.Color.BLUE);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(graphPanel.getSize());
		graphPanel.removeAll();
		graphPanel.setLayout(new java.awt.FlowLayout());
		graphPanel.add(chartPanel);
		graphPanel.revalidate();
		graphPanel.repaint();
	}

}