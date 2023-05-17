package tp_3_equipo_ideal;

import java.util.List;

import javax.swing.UIManager;

import tp.logic.Employee;
import tp.logic.IdealTeam;
import tp.dal.Data;
import tp.gui.MainWindow;

public class Main {

	public static void main(String[] args) {

		Data data = new Data();
		IdealTeam idealTeam = new IdealTeam();
		List<Employee> employees = data.readEmployeesFromJSON();

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println(e);
		}
		MainWindow launch = new MainWindow(idealTeam, employees);
		launch.setResizable(false);
		launch.setVisible(true);
		launch.setLocationRelativeTo(null);
	}

}
