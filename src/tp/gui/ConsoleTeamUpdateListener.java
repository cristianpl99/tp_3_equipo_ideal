package tp.gui;

import java.util.List;

import tp.logic.Employee;
import tp.logic.IteamUpdateListener;

public class ConsoleTeamUpdateListener implements IteamUpdateListener {

	@Override
	public void onTeamGenerated(List<Employee> team, int combination, long time) {
		System.out.println("Equipo generado:");
		for (Employee employee : team) {
			System.out.println(employee);
		}
		System.out.println("Combinaciones hechas "+ combination + " Tiempo del proceso: " + time);
		System.out.println();
	}
}
