package tp_3_equipo_ideal;

import java.util.List;

import javax.swing.UIManager;

import tp.logic.Employee;
import tp.logic.IdealTeam;
import tp.dal.LoadData;
import tp.gui.HomeScreen;
import tp.gui.EmployeeScreen;

public class Main {

	public static void main(String[] args) {

		LoadData data = new LoadData();
		IdealTeam idealTeam = new IdealTeam();
		
		
		List<Employee> employees = data.readEmployeesFromJSON();

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//test de la ventana de ficha empleado
		//EmployeeScreen launch = new EmployeeScreen(employees.get(0));
		//launch.setResizable(false);
		//launch.setVisible(true);
		//launch.setLocationRelativeTo(null);
		HomeScreen launch = new HomeScreen(idealTeam, employees);
		launch.setResizable(false);
		launch.setVisible(true);
		launch.setLocationRelativeTo(null);
	}

}
