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
	private List<Employee> finalTeam;

    public Heuristic(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
                     int testerCount, Comparator<Employee> comparator) {
        super(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
        this.projectLeadersAdded = 0;
        this.architectsAdded = 0;
        this.programmersAdded = 0;
        this.testersAdded = 0;
        this.comparator = comparator;
        this.finalTeam = new ArrayList<>();
    }

    @Override
    public List<Employee> findBestCombination() {
        long startTime = System.currentTimeMillis();
        List<Employee> combination = new ArrayList<>(employees);
        combination.sort(comparator);    
        generateCombination(combination, 0);     
        long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
        bestAverageRating = calculateAverageRating(finalTeam);
        return this.finalTeam;
    }
    
    @Override
	protected void generateCombination(List<Employee> combination, int currentIndex) {
		for (Employee employee : combination) {
            if (isValidRole(employee) && !hasConflictedEmployees(finalTeam, employee)) {
                incrementRoleCount(employee.getRole());
                finalTeam.add(employee);
            }
        }
		combinationCount++;
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
                return projectLeadersAdded < projectLeaderCount;
            case Architect:
                return architectsAdded < architectCount;
            case Programmer:
                return programmersAdded < programmerCount;
            case Tester:
                return testersAdded < testerCount;
            default:
                return false;
        }
    }

    public boolean hasConflictedEmployees(List<Employee> team, Employee employee) {
        for (Employee e : team) {
            if (e.getConflicts().contains(employee.getDni())) {
                return true;
            }
        }
        return false;
    }
}

