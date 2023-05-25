package tp.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

	@Override
	public void onConmparativeGenerated(HashMap<String, Object[]> resultMap) {
	    for (Map.Entry<String, Object[]> entry : resultMap.entrySet()) {
	        String algorithmName = entry.getKey();
	        Object[] values = entry.getValue();

	        System.out.println("Algorithm: " + algorithmName);

	        List<Employee> bestCombination = (List<Employee>) values[0];
	        System.out.println("Best Combination:");
	        for (Employee employee : bestCombination) {
	            System.out.println(employee);
	        }

	        int combinationCount = (int) values[1];
	        System.out.println("Combination Count: " + combinationCount);
	           
	        long time = (long) values[2];
	        double executionTimeSeconds = (TimeUnit.MILLISECONDS.toSeconds(time)
	                + (time % 1000) / 1000.0);
	        String executionTime = String.format("%.3fs", executionTimeSeconds);
	        
	        System.out.println("Execution Time: " + executionTime + " seconds");
	        System.out.println("--------------------------");
	    }
	}

}
