package tp.gui;

import javax.swing.SwingWorker;

import tp.logic.IdealTeam;

import tp.logic.Employee;

import java.util.List;

public class HeuristicWorker extends SwingWorker<List<Employee>, Void> {
    private List<Employee> employees;
    private int projectLeaderCount;
    private int architectCount;
    private int programmerCount;
    private int testerCount;

    public HeuristicWorker(List<Employee> employees, int projectLeaderCount, int architectCount,
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
        List<Employee> bestCombination = idealTeam.generateTeamByHeuristic(employees, projectLeaderCount, architectCount, programmerCount, testerCount);	
        return bestCombination;
    }

    @Override
    protected void done() {
        try {
            // Este método se ejecuta en el hilo de la interfaz de usuario cuando la tarea en segundo plano ha finalizado
            List<Employee> bestCombination = get(); // Obtiene el resultado de la tarea en segundo plano
            // Actualiza la interfaz de usuario con el resultado obtenido
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}