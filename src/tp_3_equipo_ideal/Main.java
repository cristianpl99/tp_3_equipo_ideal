package tp_3_equipo_ideal;

import javax.swing.UIManager;

import tp.gui.HomeScreen;
//import tp.logic.IdealTeam;
//import tp.gui.EmployeeScreen;

public class Main {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println(e);
		}

		// test de la ventana de ficha empleado
//		IdealTeam ideal = new IdealTeam();
//		EmployeeScreen launch = new EmployeeScreen((ideal.getEmployees()).get(0));
//		launch.setResizable(false);
//		launch.setVisible(true);
//		launch.setLocationRelativeTo(null);

		HomeScreen launch = new HomeScreen();
		launch.setResizable(false);
		launch.setVisible(true);
		launch.setLocationRelativeTo(null);
	}

}
