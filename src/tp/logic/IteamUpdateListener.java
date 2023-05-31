package tp.logic;

import java.util.HashMap;
import java.util.List;

public interface IteamUpdateListener {

	void onTeamGenerated(List<Employee> team, int combinations, long time);

	void onConmparativeGenerated(HashMap<String, Object[]> resultMap);

}