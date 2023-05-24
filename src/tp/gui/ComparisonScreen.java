package tp.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import tp.logic.Employee;

import java.util.List;

public class ComparisonScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Map<String, Object[]> resultMap;

	public ComparisonScreen(Map<String, Object[]> resultMap) {
		this.resultMap = resultMap;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 802, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] columnNames = { "DNI", "Rol", "Nombre", "Apellido", "Rating" };
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

		for (int i = 0; i < 3; i++) {
			JTable table = new JTable(tableModel);
			table.setEnabled(true);
			table.setDefaultEditor(Object.class, null);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) {
							// showEmployee(selectedRow);
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
			populateTable(table, i);
			scrollPane.setBounds(10 + i * 263, 11, 248, 292);
			contentPane.add(scrollPane);
		}
	
	
	String[] resultcolumnNames = { "Algorithm", "Combinations", "Excecution Time"};
	DefaultTableModel tableModelResult = new DefaultTableModel(resultcolumnNames, 0);

	JTable table = new JTable(tableModelResult);
	table.setBounds(344, 447, 392, 267);
	table.setEnabled(true);

	table.setDefaultEditor(Object.class, null);
	table.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					//showEmployee(selectedRow);
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
	scrollPane.setBounds(326, 370, 452, 89);
	contentPane.add(scrollPane);
	populateResultsTable(table);
	contentPane.setLayout(null);
	}
	
	

	private void populateTable(JTable table, int col) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        String[] keys = {"Brute Force", "Backtracking", "Heuristic"};
        String key = keys[col];
        
        List<Employee> employees = (List<Employee>) resultMap.get(key)[0];
        for (Employee employee : employees) {
            model.addRow(new Object[] { employee.getDni(), employee.getRole(), employee.getFirstName(),
                    employee.getLastName(), employee.getRating() });
        }
    }
	
	private void populateResultsTable(JTable table) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);
	    
	    for (Map.Entry<String, Object[]> entry : resultMap.entrySet()) {
	        String algorithm = entry.getKey();
	        Object[] values = entry.getValue();
	        String combinations = String.valueOf(values[1]);
	        String executionTime = String.valueOf(values[2]);
	        
	        model.addRow(new Object[] { algorithm, combinations, executionTime });
	    }
	}



}
