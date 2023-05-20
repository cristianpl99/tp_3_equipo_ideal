package tp.gui;

import javax.swing.SwingWorker;

import tp.logic.IdealTeam;

import tp.logic.Employee;

import java.util.List;

public class BruteForceWorker extends SwingWorker<List<Employee>, Void> {
	private int projectLeaderCount;
	private int architectCount;
	private int programmerCount;
	private int testerCount;
	private IdealTeam idealT;

	public BruteForceWorker(IdealTeam idealTeam, int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		this.projectLeaderCount = projectLeaderCount;
		this.architectCount = architectCount;
		this.programmerCount = programmerCount;
		this.testerCount = testerCount;
		this.idealT = idealTeam;
	}

	@Override
	protected List<Employee> doInBackground() throws Exception {
	    List<Employee> bestCombination = idealT.generateTeamByBruteForce(projectLeaderCount, architectCount,
	            programmerCount, testerCount);
	    int progress = 100;
	    setProgress(progress);	    
	    return bestCombination;
	}


	@Override
	protected void done() {
		try {
			@SuppressWarnings("unused")
			List<Employee> bestCombination = get();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
