package tp.gui.swingWorkers;

import javax.swing.SwingWorker;

import tp.logic.IdealTeam;

import tp.logic.Employee;

import java.util.List;

public class BruteForceWorker extends SwingWorker<List<Employee>, Void> {

	private int projectLeaderCount;
	private int architectCount;
	private int programmerCount;
	private int testerCount;
	

	public BruteForceWorker(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		this.projectLeaderCount = projectLeaderCount;
		this.architectCount = architectCount;
		this.programmerCount = programmerCount;
		this.testerCount = testerCount;
		
	}

	@Override
	protected List<Employee> doInBackground() throws Exception {
		IdealTeam idealTeam = IdealTeam.getIdealTeam();
		List<Employee> bestCombination = idealTeam.generateTeamByBruteForce(projectLeaderCount, architectCount,
				programmerCount, testerCount);
		int progress = 100;
		setProgress(progress);
		return bestCombination;
	}

}