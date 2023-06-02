package tp.dal;

import java.util.HashMap;
import java.util.List;

import tp.logic.Employee;
import tp.logic.IteamUpdateObserver;

public class FileTeamUpdateObserver implements IteamUpdateObserver {

	@Override
	public void onTeamGenerated(List<Employee> team, int combinations, long time) {
		SaveData saveData = new SaveData();
		saveData.writeLogFile(team, combinations, time);
	}

	@Override
	public void onConmparativeGenerated(HashMap<String, Object[]> resultMap) {
		SaveData saveData = new SaveData();
		saveData.createFile(resultMap);
	}

}