package tp.logic;

import java.util.List;

public interface IteamUpdateListener {
	void onTeamGenerated(List<Employee> team, int combinations, long time);
}