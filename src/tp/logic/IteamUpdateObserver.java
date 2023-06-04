package tp.logic;

import java.util.HashMap;
import java.util.List;

public interface IteamUpdateObserver {

	void onTeamGenerated(List<Employee> team, double combinations, long time);

	void onConmparativeGenerated(HashMap<String, Object[]> resultMap);

}