package tp.gui;

import javax.swing.SwingWorker;

import tp.logic.IdealTeam;

import tp.logic.Employee;

import tp.gui.ComparisonScreen;

import java.util.List;

import java.util.Map;
import java.util.HashMap;

public class AlgorithmsWorker extends SwingWorker<Map<String, Object[]>, Void> {
	private int projectLeaderCount;
	private int architectCount;
	private int programmerCount;
	private int testerCount;
	private IdealTeam idealT;

	public AlgorithmsWorker(IdealTeam idealTeam, int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		this.projectLeaderCount = projectLeaderCount;
		this.architectCount = architectCount;
		this.programmerCount = programmerCount;
		this.testerCount = testerCount;
		this.idealT = idealTeam;
	}

	@Override
	protected Map<String, Object[]> doInBackground() throws Exception {
		Map<String, Object[]> resultMap = new HashMap<>();
		resultMap = idealT.generateComparative(projectLeaderCount, architectCount,
				programmerCount, testerCount);
		return resultMap;
	}

	@Override
	protected void done() {
		try {	
			Map<String, Object[]> resultMap = get();
			ComparisonScreen launch = new ComparisonScreen(resultMap);
			launch.setResizable(false);
			launch.setVisible(true);
			launch.setLocationRelativeTo(null);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
