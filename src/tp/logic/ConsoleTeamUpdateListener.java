package tp.logic;

import java.util.List;

public class ConsoleTeamUpdateListener implements TeamUpdateListener {
    
	@Override
    public void onTeamGenerated(List<Employee> team) {
        System.out.println("Equipo generado:");
        for (Employee employee : team) {
            System.out.println(employee);
        }
        System.out.println();
    }
}

