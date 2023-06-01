package tp.gui;

import javax.swing.SwingWorker;

import tp.logic.IdealTeam;

import java.util.Map;
import java.util.HashMap;

public class AlgorithmsWorker extends SwingWorker<Map<String, Object[]>, Void> {

	private int projectLeaderCount;
	private int architectCount;
	private int programmerCount;
	private int testerCount;
	

	public AlgorithmsWorker(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		this.projectLeaderCount = projectLeaderCount;
		this.architectCount = architectCount;
		this.programmerCount = programmerCount;
		this.testerCount = testerCount;
		
	}

	@Override
	protected Map<String, Object[]> doInBackground() throws Exception {
		Map<String, Object[]> resultMap = new HashMap<>();
		IdealTeam idealTeam = IdealTeam.getIdealTeam();
		return idealTeam.generateComparative(projectLeaderCount, architectCount, programmerCount, testerCount);
		
	}

}