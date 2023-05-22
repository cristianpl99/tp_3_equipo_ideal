package tp.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Heuristic extends Algorithm {
    private int projectLeadersAdded;
    private int architectsAdded;
    private int programmersAdded;
    private int testersAdded;
	private Comparator<Employee> comparator;

    public Heuristic(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
            int testerCount, Comparator<Employee> comparator) {
        super(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
        this.projectLeadersAdded = 0;
        this.architectsAdded = 0;
        this.programmersAdded = 0;
        this.testersAdded = 0;
        this.comparator = comparator;
    }
    
    @Override
    
    public List<Employee> findBestCombination() {
        long startTime = System.currentTimeMillis();
        List<Employee> team = new ArrayList<>(employees);

        team.sort(comparator);

        List<Employee> bestCombination = new ArrayList<>();

        for (Employee employee : team) {
            if (isValidRole(employee) && !hasConflicts(bestCombination, employee)) {
                incrementRoleCount(employee.getRole());
                bestCombination.add(employee);
            }
        }

        evaluateCombination(bestCombination);
        
        long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
        return bestCombination;
    }

    private void incrementRoleCount(Employee.Role role) {
        switch (role) {
            case Project_Leader:
                this.projectLeadersAdded++;
                break;
            case Architect:
                this.architectsAdded++;
                break;
            case Programmer:
                this.programmersAdded++;
                break;
            case Tester:
                this.testersAdded++;
                break;
        }
    }

    private boolean isValidRole(Employee employee) {
        switch (employee.getRole()) {
            case Project_Leader:
                return projectLeadersAdded < this.projectLeaderCount;
            case Architect:
                return architectsAdded < this.architectCount;
            case Programmer:
                return programmersAdded < this.programmerCount;
            case Tester:
                return testersAdded < this.testerCount;
            default:
                return false;
        }
    }

    public boolean hasConflicts(List<Employee> team, Employee employee) {
        for (Employee e : team) {
            if (e.getConflicts().contains(employee.getDni())) {
                return true;
            }
        }
        return false;
    }
}

