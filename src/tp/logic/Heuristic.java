package tp.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Heuristic {

	private List<Employee> employees;
	private int projectLeaderCount;
	private int architectCount;
	private int programmerCount;
	private int testerCount;
	private int projectLeadersAdded;
	private int architectsAdded;
	private int programmersAdded;
	private int testersAdded;
	private double combinationCount;
	private long executionTime;
	private double bestAverageRating;
	private Comparator<Employee> comparator;

	public Heuristic(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount, Comparator<Employee> comparator) {

		this.employees = employees;
		this.projectLeaderCount = projectLeaderCount;
		this.architectCount = architectCount;
		this.programmerCount = programmerCount;
		this.testerCount = testerCount;
		this.projectLeadersAdded = 0;
		this.architectsAdded = 0;
		this.programmersAdded = 0;
		this.testersAdded = 0;
		this.combinationCount = 0;
		this.executionTime = 0;
		this.bestAverageRating = 0;
		this.comparator = comparator;

	}

	public List<Employee> findBestCombination() {
		long startTime = System.currentTimeMillis();
		List<Employee> team = new ArrayList<>(employees);

		team.sort(comparator);

		List<Employee> finalTeam = new ArrayList<>();

		for (Employee employee : team) {
			if (isValidRole(employee, projectLeadersAdded, architectsAdded, programmersAdded, testersAdded)
					&& !hasConflicts(finalTeam, employee)) {
				incrementRoleCount(employee.getRole(), projectLeadersAdded, architectsAdded, programmersAdded,
						testersAdded);
				finalTeam.add(employee);
			}
		}
		combinationCount++;
		long endTime = System.currentTimeMillis();
		executionTime = endTime - startTime;
		bestAverageRating = calculateAverageRating(finalTeam);
		return finalTeam;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public double getCombinationCount() {
		return combinationCount;
	}

	private void incrementRoleCount(Employee.Role role, int projectLeadersAdded, int architectsAdded,
			int programmersAdded, int testersAdded) {
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

	private boolean isValidRole(Employee employee, int projectLeadersAdded, int architectsAdded, int programmersAdded,
			int testersAdded) {
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

	private double calculateAverageRating(List<Employee> combination) {
		int totalRating = 0;
		for (Employee employee : combination) {
			totalRating += employee.getRating();
		}
		return (double) totalRating / combination.size();
	}

	public double getBestAverageRating() {
		return this.bestAverageRating;
	}

}