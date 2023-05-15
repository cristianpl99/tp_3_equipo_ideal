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
	private int combinationCount;

	public Heuristic(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
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

	}

	public List<Employee> findBestCombination(Comparator<Employee> comparator) {
		List<Employee> team = new ArrayList<>(employees);

		team.sort(comparator);

		List<Employee> finalTeam = new ArrayList<>();

		for (Employee employee : team) {
			if (isValidRole(employee, projectLeadersAdded, architectsAdded, programmersAdded, testersAdded)) {
				incrementRoleCount(employee.getRole(), projectLeadersAdded, architectsAdded, programmersAdded,
						testersAdded);
				finalTeam.add(employee);
				combinationCount++;
			}
		}
		System.out.println("En Heuristic, cantidad de combinaciones generadas: " + combinationCount);
		return finalTeam;
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
}
