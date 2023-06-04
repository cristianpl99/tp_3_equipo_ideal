package tp.logic;

import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashMap;

public class IdealTeam {

	private List<IteamUpdateObserver> observers;
	private List<Employee> employees;
	private static IdealTeam idealTeam;

	public static synchronized IdealTeam getIdealTeam() {
		if (idealTeam == null) {
			idealTeam = new IdealTeam();
		}
		return idealTeam;
	}

	private IdealTeam() {
		employees = new ArrayList<>();
		observers = new ArrayList<>();
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

		Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount, testerCount,
				customComparator);
		List<Employee> bestCombination = heuristic.findBestCombination();
		notifyTeamGenerated(bestCombination, heuristic.getCombinationCount(), heuristic.getExecutionTime());
		return bestCombination;
	}

	public HashMap<String, Object[]> generateComparative(int projectLeaderCount, int architectCount,
			int programmerCount, int testerCount) {

		HashMap<String, Object[]> resultMap = new HashMap<>();

		bruteForceComparative(projectLeaderCount, architectCount, programmerCount, testerCount, resultMap);

		backtrackingComparative(projectLeaderCount, architectCount, programmerCount, testerCount, resultMap);

		Comparator<Employee> customComparator = (e1, e2) -> {
			double coefficient1 = calculateCoefficient(e1);
			double coefficient2 = calculateCoefficient(e2);
			return Double.compare(coefficient2, coefficient1);
		};

		heuristicInComparative(projectLeaderCount, architectCount, programmerCount, testerCount, resultMap,
				customComparator);

		notifyComparativeGenerated(resultMap);

		return resultMap;
	}

	private void bruteForceComparative(int projectLeaderCount, int architectCount, int programmerCount, int testerCount,
			HashMap<String, Object[]> resultMap) {
		BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount);
		List<Employee> bruteForceBestCombination = bruteForce.findBestCombination();
		double bruteForceCombinationCount = bruteForce.getCombinationCount();
		long bruteForceExecutionTime = bruteForce.getExecutionTime();
		double bruteForceBestAverageRating = bruteForce.getBestAverageRating();
		resultMap.put("Brute Force", new Object[] { bruteForceBestCombination, bruteForceCombinationCount,
				bruteForceExecutionTime, bruteForceBestAverageRating });
	}

	private void backtrackingComparative(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount, HashMap<String, Object[]> resultMap) {
		BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount);
		List<Employee> backTrackingBestCombination = backTracking.findBestCombination();
		double backTrackingCombinationCount = backTracking.getCombinationCount();
		long backTrackingExecutionTime = backTracking.getExecutionTime();
		double backTrackingBestAverageRating = backTracking.getBestAverageRating();
		resultMap.put("Backtracking", new Object[] { backTrackingBestCombination, backTrackingCombinationCount,
				backTrackingExecutionTime, backTrackingBestAverageRating });
	}

	private void heuristicInComparative(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount, HashMap<String, Object[]> resultMap, Comparator<Employee> customComparator) {
		Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount, testerCount,
				customComparator);
		List<Employee> heuristicBestCombination = heuristic.findBestCombination();
		double heuristicCombinationCount = heuristic.getCombinationCount();
		long heuristicExecutionTime = heuristic.getExecutionTime();
		double heuristicBestAverageRating = heuristic.getBestAverageRating();
		resultMap.put("Heuristic", new Object[] { heuristicBestCombination, heuristicCombinationCount,
				heuristicExecutionTime, heuristicBestAverageRating });
	}

	private void notifyTeamGenerated(List<Employee> team, double combinations, long time) {
		if (observers != null) {
			for (IteamUpdateObserver observer : observers) {
				observer.onTeamGenerated(team, combinations, time);
			}
		}
	}

	private void notifyComparativeGenerated(HashMap<String, Object[]> resultMap) {
		if (observers != null) {
			for (IteamUpdateObserver listener : observers) {
				listener.onConmparativeGenerated(resultMap);
			}
		}
	}

	public void addObserver(IteamUpdateObserver listener) {
		if (observers == null) {
			observers = new ArrayList<>();
		}
		observers.add(listener);
	}

	public void removeObserver(IteamUpdateObserver listener) {
		if (observers != null) {
			observers.remove(listener);
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

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	private double calculateCoefficient(Employee employee) {
		int conflictCount = employee.getConflicts().size();
		double rating = employee.getRating();
		return rating - conflictCount;
	}

}