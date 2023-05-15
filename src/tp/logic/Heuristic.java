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

	public Heuristic(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		this.employees = employees;
		this.projectLeaderCount = projectLeaderCount;
		this.architectCount = architectCount;
		this.programmerCount = programmerCount;
		this.testerCount = testerCount;
	}

	public List<Employee> findBestCombination(Comparator<Employee> comparator) {
		List<Employee> team = new ArrayList<>(employees);

		team.sort(comparator);

		List<Employee> finalTeam = new ArrayList<>();
		int projectLeadersAdded = 0;
		int architectsAdded = 0;
		int programmersAdded = 0;
		int testersAdded = 0;

		for (Employee employee : team) {
			if (isValidRole(employee, projectLeadersAdded, architectsAdded, programmersAdded, testersAdded)) {
				finalTeam.add(employee);
				incrementRoleCount(employee.getRole(), projectLeadersAdded, architectsAdded, programmersAdded,
						testersAdded);
			}
		}

		return finalTeam;
	}

	private void incrementRoleCount(Employee.Role role, int projectLeadersAdded, int architectsAdded,
			int programmersAdded, int testersAdded) {
		switch (role) {
		case Project_Leader:
			projectLeadersAdded++;
			break;
		case Architect:
			architectsAdded++;
			break;
		case Programmer:
			programmersAdded++;
			break;
		case Tester:
			testersAdded++;
			break;
		}
	}

	private boolean isValidRole(Employee employee, int projectLeadersAdded, int architectsAdded, int programmersAdded,
			int testersAdded) {
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

	
}
