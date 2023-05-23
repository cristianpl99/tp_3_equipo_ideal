package tp_3_equipo_ideal;

import javax.swing.UIManager;

import tp.dal.FileTeamUpdateListener;
import tp.dal.IdataLoader;
import tp.dal.LoadData;
import tp.gui.ConsoleTeamUpdateListener;
import tp.gui.HomeScreen;
import tp.gui.ScreenTeamUpdateListener;
import tp.logic.IdealTeam;

public class Main {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println(e);
		}
		IdealTeam idealTeam = new IdealTeam();
		ConsoleTeamUpdateListener consoleListener = new ConsoleTeamUpdateListener();
		FileTeamUpdateListener fileListener = new FileTeamUpdateListener();
		ScreenTeamUpdateListener screenListener = new ScreenTeamUpdateListener();
		idealTeam.addListener(consoleListener);
		idealTeam.addListener(fileListener);
		idealTeam.addListener(screenListener);
		IdataLoader data = new LoadData();
		idealTeam.setEmployees(data.readEmployeesFromJSON());
			
		HomeScreen launch = new HomeScreen(idealTeam);
		launch.setResizable(false);
		launch.setVisible(true);
		launch.setLocationRelativeTo(null);
	}

}
