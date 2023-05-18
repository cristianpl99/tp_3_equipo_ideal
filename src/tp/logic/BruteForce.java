package tp.logic;

import java.util.ArrayList;
import java.util.List;

public class BruteForce {
	private List<Employee> employees;
	private int projectLeaderCount;
	private int architectCount;
	private int programmerCount;
	private int testerCount;
	private List<Employee> bestCombination;

	private double bestAverageRating;
	private int combinationCount;

	public BruteForce(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
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
		System.out.println("En BruteForce, cantidad de combinaciones generadas: " + combinationCount);
		return bestCombination;
	}

	public void generateCombination(List<Employee> combination, int currentIndex) {
		if (currentIndex == employees.size()) {
			combinationCount++;
			evaluateCombination(combination);
			return;
		}

		Employee currentEmployee = employees.get(currentIndex);
		combination.add(currentEmployee);
		generateCombination(combination, currentIndex + 1);
		combination.remove(combination.size() - 1);
		generateCombination(combination, currentIndex + 1);
	}

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

	private boolean combinationContainsConflictedEmployee(List<Employee> combination, Employee employee) {
		for (Employee e : combination) {
			if (e.getConflicts().contains(employee.getId())) {
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

	private double calculateAverageRating(List<Employee> combination) {
		int totalRating = 0;
		for (Employee employee : combination) {
			totalRating += employee.getRating();
		}
		return (double) totalRating / combination.size();
	}

	public int getCombinationCount() {
		return combinationCount;
	}

	public List<Employee> getBestCombination() {
		return bestCombination;
	}
}
