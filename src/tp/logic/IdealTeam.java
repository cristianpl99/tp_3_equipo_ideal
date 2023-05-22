package tp.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import tp.dal.LoadData;

import java.util.Comparator;

public class IdealTeam {

	private List<TeamUpdateListener> listeners;
	private ConsoleTeamUpdateListener consoleListener;
	private FileTeamUpdateListener fileListener;
	private ScreenTeamUpdateListener screenListener;
	
	private LoadData data;
	private List<Employee> employees;

	public IdealTeam() {
		data = new LoadData();
		employees = data.readEmployeesFromJSON();
		listeners = new ArrayList<>();
		consoleListener = new ConsoleTeamUpdateListener();
		fileListener = new FileTeamUpdateListener();
		screenListener = new ScreenTeamUpdateListener();
		addListener(consoleListener);
		addListener(fileListener);
		addListener(screenListener);
	}

	public List<Employee> generateTeamByBruteForce(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount);
		List<Employee> bestCombination = bruteForce.findBestCombination();
		notifyTeamGenerated(bestCombination, bruteForce.getCombinationCount(), bruteForce.getExecutionTime());
		return bestCombination;
	}

	public List<Employee> generateTeamByBackTracking(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount);
		List<Employee> bestCombination = backTracking.findBestCombination();
		notifyTeamGenerated(bestCombination, backTracking.getCombinationCount(), backTracking.getExecutionTime());
		return bestCombination;
	}

	public List<Employee> generateTeamByHeuristic(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		Comparator<Employee> customComparator = (e1, e2) -> {
			double coefficient1 = calculateCoefficient(e1);
			double coefficient2 = calculateCoefficient(e2);
			return Double.compare(coefficient2, coefficient1);
		};

		Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount, customComparator);
		List<Employee> bestCombination = heuristic.findBestCombination();
		notifyTeamGenerated(bestCombination, heuristic.getCombinationCount(), heuristic.getExecutionTime());
		return bestCombination;
	}

	public void addListener(TeamUpdateListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(listener);
	}

	public void removeListener(TeamUpdateListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	private void notifyTeamGenerated(List<Employee> team, int combinations, long time) {
		if (listeners != null) {
			for (TeamUpdateListener listener : listeners) {
				listener.onTeamGenerated(team, combinations, time);
			}
		}
	}

	private double calculateCoefficient(Employee employee) {
		int conflictCount = employee.getConflicts().size();
		double rating = employee.getRating();
		return rating - conflictCount;
	}

	public void displayEmployees() {
		for (Employee employee : employees) {
			System.out.println(employee.toString());
			System.out.println("Conflicted Employees:");
			Set<String> conflictedIds = employee.getConflicts();
			for (String conflictedId : conflictedIds) {
				Employee conflictedEmployee = findEmployeeByDni(conflictedId);
				if (conflictedEmployee != null) {
					System.out.println(
							" - " + conflictedEmployee.getFirstName() + " " + conflictedEmployee.getLastName());
				}
			}
			System.out.println();
		}
	}

	public Employee findEmployeeByDni(String conflictedId) {
		for (Employee employee : employees) {
			if (employee.getDni().equals(conflictedId)) {
				return employee;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Employee> getEmployees() {
		return (List<Employee>) ((ArrayList<Employee>) employees).clone();
	}

	public void addEmployee(Employee em) {
		employees.add(em);
	}

}
