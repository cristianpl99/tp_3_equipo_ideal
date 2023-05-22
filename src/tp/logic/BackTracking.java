package tp.logic;

import java.util.ArrayList;
import java.util.List;

public class BackTracking extends Algorithm {

    public BackTracking(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
                        int testerCount) {
        super(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
    }

    @Override
    public List<Employee> findBestCombination() {
        long startTime = System.currentTimeMillis();
        List<Employee> combination = new ArrayList<>();
        generateCombination(combination, 0);
        long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
        return bestCombination;
    }

    public void generateCombination(List<Employee> combination, int currentIndex) {
        if (currentIndex == employees.size()) {
            if (isValidCombination(combination)) {
                double averageRating = calculateAverageRating(combination);
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
            combination.remove(combination.size() - 1);
        }
        combinationCount++;
        generateCombination(combination, currentIndex + 1);
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
}
