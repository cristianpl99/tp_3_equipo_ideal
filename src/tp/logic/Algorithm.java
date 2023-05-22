package tp.logic;

import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {
    protected List<Employee> employees;
    protected int projectLeaderCount;
    protected int architectCount;
    protected int programmerCount;
    protected int testerCount;
    protected List<Employee> bestCombination;
    protected double bestAverageRating;
    protected int combinationCount;
    protected long executionTime;

    public Algorithm(List<Employee> employees, int projectLeaderCount, int architectCount,
                             int programmerCount, int testerCount) {
        this.employees = employees;
        this.projectLeaderCount = projectLeaderCount;
        this.architectCount = architectCount;
        this.programmerCount = programmerCount;
        this.testerCount = testerCount;
        this.bestCombination = new ArrayList<>();
        this.bestAverageRating = 0;
        this.combinationCount = 0;
        this.executionTime = 0;
    }

    public abstract List<Employee> findBestCombination();

    public void evaluateCombination(List<Employee> combination) {
        if (isValidCombination(combination)) {
            double averageRating = calculateAverageRating(combination);
            if (averageRating > bestAverageRating && !hasConflictedEmployees(combination)) {
                bestCombination = new ArrayList<>(combination);
                bestAverageRating = averageRating;
            }
        }
    }

    public boolean hasConflictedEmployees(List<Employee> combination) {
        for (Employee employee : combination) {
            if (combinationContainsConflictedEmployee(combination, employee)) {
                return true;
            }
        }
        return false;
    }

    public boolean combinationContainsConflictedEmployee(List<Employee> combination, Employee employee) {
        for (Employee e : combination) {
            if (e.getConflicts().contains(employee.getDni())) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidCombination(List<Employee> combination) {
        int projectLeaderCount = 0;
        int architectCount = 0;
        int programmerCount = 0;
        int testerCount = 0;

        for (Employee employee : combination) {
            switch (employee.getRole()) {
                case Project_Leader:
                    projectLeaderCount++;
                    break;
                case Architect:
                    architectCount++;
                    break;
                case Programmer:
                    programmerCount++;
                    break;
                case Tester:
                    testerCount++;
                    break;
            }
        }

        return projectLeaderCount == this.projectLeaderCount && architectCount == this.architectCount
                && programmerCount == this.programmerCount && testerCount == this.testerCount;
    }

    protected double calculateAverageRating(List<Employee> combination) {
        int totalRating = 0;
        for (Employee employee : combination) {
            totalRating += employee.getRating();
        }
        return (double) totalRating / combination.size();
    }

    public int getCombinationCount() {
        return combinationCount;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public List<Employee> getBestCombination() {
        return bestCombination;
    }
}

