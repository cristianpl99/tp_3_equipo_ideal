package tp.gui;

import javax.swing.SwingWorker;

import tp.logic.IdealTeam;

import tp.logic.Employee;

import java.util.List;

public class BruteForceWorker extends SwingWorker<List<Employee>, Void> {
    private List<Employee> employees;
    private int projectLeaderCount;
    private int architectCount;
    private int programmerCount;
    private int testerCount;

    public BruteForceWorker(List<Employee> employees, int projectLeaderCount, int architectCount,
                            int programmerCount, int testerCount) {
        this.employees = employees;
        this.projectLeaderCount = projectLeaderCount;
        this.architectCount = architectCount;
        this.programmerCount = programmerCount;
        this.testerCount = testerCount;
    }

    @Override
    protected List<Employee> doInBackground() throws Exception {
        IdealTeam idealTeam = new IdealTeam();
        List<Employee> bestCombination = idealTeam.generateTeamByBruteForce(employees, projectLeaderCount, architectCount, programmerCount, testerCount);	
        return bestCombination;
    }

    @Override
    protected void done() {
        try {
            List<Employee> bestCombination = get(); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
