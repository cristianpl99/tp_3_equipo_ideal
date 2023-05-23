package tp.dal;

import java.util.List;

import tp.logic.Employee;
import tp.logic.IteamUpdateListener;

public class FileTeamUpdateListener implements IteamUpdateListener {
	@Override
	public void onTeamGenerated(List<Employee> team, int combinations, long time) {
		SaveData saveData = new SaveData();
		saveData.createFile(team, combinations, time);
	}

}
