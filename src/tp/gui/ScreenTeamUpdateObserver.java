package tp.gui;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import tp.logic.Employee;
import tp.logic.IteamUpdateObserver;

public class ScreenTeamUpdateObserver implements IteamUpdateObserver {

	@Override
	public void onTeamGenerated(List<Employee> team, int combinations, long time) {
		double executionTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(time) + (double) (time % 1000) / 1000.0;
		int sum = 0;
		for (Employee employee : team) {
			sum += employee.getRating();
		}
		double average = (double) sum / team.size();
		MainScreen.combinations = combinations;
		MainScreen.time = executionTimeSeconds;
		MainScreen.averageRating = average;
	}

	@Override
	public void onConmparativeGenerated(HashMap<String, Object[]> resultMap) {
		MainScreen.resultMap = resultMap;
	}

}