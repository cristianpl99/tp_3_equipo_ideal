package tp.logic;

import java.util.List;

public class ConsoleTeamUpdateListener implements TeamUpdateListener {

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
