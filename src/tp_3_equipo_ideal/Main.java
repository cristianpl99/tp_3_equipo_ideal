package tp_3_equipo_ideal;

import javax.swing.UIManager;

import tp.dal.FileTeamUpdateObserver;
import tp.dal.IdataLoader;
import tp.dal.LoadData;
import tp.gui.HomeScreen;
import tp.gui.ScreenTeamUpdateObserver;
import tp.logic.IdealTeam;

public class Main {

	private static IdealTeam idealTeam;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println(e);
		}
		idealTeam = IdealTeam.getIdealTeam();
		FileTeamUpdateObserver fileObserver = new FileTeamUpdateObserver();
		ScreenTeamUpdateObserver screenObserver = new ScreenTeamUpdateObserver();
		idealTeam.addObserver(fileObserver);
		idealTeam.addObserver(screenObserver);
		IdataLoader data = new LoadData();
		idealTeam.setEmployees(data.readEmployeesFromJSON());
		HomeScreen launch = new HomeScreen();
		launch.initialize();
	}

}