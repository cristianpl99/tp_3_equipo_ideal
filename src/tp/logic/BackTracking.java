package tp.logic;

import java.util.ArrayList;
import java.util.List;

public class BackTracking {
    private List<Employee> employees;
    private int projectLeaderCount;
    private int architectCount;
    private int programmerCount;
    private int testerCount;
    private List<Employee> bestCombination;
    private double bestAverageRating;
    private int combinationCount;

    public BackTracking(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
                      int testerCount) {
        this.employees = employees;
        this.projectLeaderCount = projectLeaderCount;
        this.architectCount = architectCount;
        this.programmerCount = programmerCount;
        this.testerCount = testerCount;
        this.bestCombination = new ArrayList<>();
        this.bestAverageRating = 0;
        this.combinationCount = 0;
    }

    public List<Employee> findBestCombination() {
        List<Employee> combination = new ArrayList<>();
        generateCombination(combination, 0);
        System.out.println("En BackTracking, cantidad de combinaciones generadas: " + combinationCount);
        return bestCombination;
    }
    private void generateCombination(List<Employee> combination, int currentIndex) {
        if (currentIndex == employees.size()) {
            if (isValidCombination(combination)) {
                double averageRating = calculateAverageRating(combination);
                //System.out.println(averageRating);
                if (averageRating > bestAverageRating) {
                    bestCombination = new ArrayList<>(combination);
                    bestAverageRating = averageRating;
                }
            }
            return;
        }

        Employee currentEmployee = employees.get(currentIndex);
        if (!combinationContainsConflictedEmployee(combination, currentEmployee)
                && !combinationContainsExceedingRole(combination, currentEmployee.getRole())) {
            combination.add(currentEmployee);
            combinationCount++;
            generateCombination(combination, currentIndex + 1);
            combination.remove(combination.size() - 1); // Aca se corta la rama de la recursion
        }
        combinationCount++;
        generateCombination(combination, currentIndex + 1);
    }

    private boolean combinationContainsConflictedEmployee(List<Employee> combination, Employee employee) {
        for (Employee e : combination) {
            if (e.getConflicts().contains((double) employee.getId())) {
                return true;
            }
        }
        return false;
    }
    private boolean combinationContainsExceedingRole(List<Employee> combination, Employee.Role role) {
        int count = 0;
        for (Employee e : combination) {
            if (e.getRole() == role) {
                count++;
            }
        }
        switch (role) {
            case Project_Leader:
                return count >= projectLeaderCount;
            case Architect:
                return count >= architectCount;
            case Programmer:
                return count >= programmerCount;
            case Tester:
                return count >= testerCount;
            default:
                return false;
        }
    }

    private boolean isValidCombination(List<Employee> combination) {
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

    private double calculateAverageRating(List<Employee> combination) {
        int totalRating = 0;
        for (Employee employee : combination) {
            totalRating += employee.getRating();
		}
		return (double) totalRating / combination.size();
	}
}

