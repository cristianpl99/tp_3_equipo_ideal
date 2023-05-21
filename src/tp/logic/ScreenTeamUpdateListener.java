package tp.logic;

import java.util.List;
import java.util.concurrent.TimeUnit;

import tp.gui.MainScreen;

public class ScreenTeamUpdateListener implements TeamUpdateListener {

	@Override
	public void onTeamGenerated(List<Employee> team, int combinations, long time) {
		double executionTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(time)
		        + (double) (time % 1000) / 1000.0;	
		int sum = 0;
        for (Employee employee : team) {
            sum += employee.getRating();
        }   
        double average = (double) sum / team.size();
		MainScreen.combinations = combinations;
		MainScreen.time = executionTimeSeconds;
		MainScreen.averageRating = average;
	}
}