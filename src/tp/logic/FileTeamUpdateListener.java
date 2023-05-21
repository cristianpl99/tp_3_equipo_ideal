package tp.logic;

import java.util.List;
import tp.dal.SaveData;

public class FileTeamUpdateListener implements TeamUpdateListener {
	@Override
	public void onTeamGenerated(List<Employee> team, int combinations, long time) {
		SaveData saveData = new SaveData();
		saveData.createFile(team, combinations, time);
	}
}
