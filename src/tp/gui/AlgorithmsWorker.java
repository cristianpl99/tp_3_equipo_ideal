package tp.gui;

import javax.swing.SwingWorker;

import tp.logic.IdealTeam;

import tp.logic.Employee;

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

		long startTime = System.currentTimeMillis();
		List<Employee> bruteForceBestCombination = idealT.generateTeamByBruteForce(projectLeaderCount, architectCount,
				programmerCount, testerCount);
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		resultMap.put("Brute Force", new Object[] { bruteForceBestCombination, executionTime });

		startTime = System.currentTimeMillis();
		List<Employee> backTrackingBestCombination = idealT.generateTeamByBackTracking(projectLeaderCount,
				architectCount, programmerCount, testerCount);
		endTime = System.currentTimeMillis();
		executionTime = endTime - startTime;
		resultMap.put("Backtracking", new Object[] { backTrackingBestCombination, executionTime });

		startTime = System.currentTimeMillis();
		List<Employee> heuristicBestCombination = idealT.generateTeamByBruteForce(projectLeaderCount, architectCount,
				programmerCount, testerCount);
		endTime = System.currentTimeMillis();
		executionTime = endTime - startTime;
		resultMap.put("Heuristic", new Object[] { heuristicBestCombination, executionTime });

		int progress = 100;
		setProgress(progress);
		return resultMap;
	}

	@Override
	protected void done() {
		try {

			Map<String, Object[]> resultMap = get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
