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

		Comparator<Employee> customComparator = (e1, e2) -> {
			double coefficient1 = calculateCoefficient(e1);
			double coefficient2 = calculateCoefficient(e2);
			return Double.compare(coefficient2, coefficient1);
		};
		bruteForceComparative(projectLeaderCount, architectCount, programmerCount, testerCount, resultMap);
		backtrackingComparative(projectLeaderCount, architectCount, programmerCount, testerCount, resultMap);
		heuristicInComparative(projectLeaderCount, architectCount, programmerCount, testerCount, resultMap, customComparator);
		notifyComparativeGenerated(resultMap);

		return resultMap;
	}

	private void bruteForceComparative(int projectLeaderCount, int architectCount, int programmerCount, int testerCount,
			HashMap<String, Object[]> resultMap) {
		BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount);
		resultMap.put("Brute Force", new Object[] { bruteForce.findBestCombination(), bruteForce.getCombinationCount(),
				bruteForce.getExecutionTime(), bruteForce.getBestAverageRating() });
	}

	private void backtrackingComparative(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount, HashMap<String, Object[]> resultMap) {
		BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount);
		resultMap.put("Backtracking", new Object[] { backTracking.findBestCombination(), backTracking.getCombinationCount(),
				backTracking.getExecutionTime(), backTracking.getBestAverageRating() });
	}

	private void heuristicInComparative(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount, HashMap<String, Object[]> resultMap, Comparator<Employee> customComparator) {
		Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount, testerCount,
				customComparator);
		resultMap.put("Heuristic", new Object[] { heuristic.findBestCombination(), heuristic.getCombinationCount(),
				heuristic.getExecutionTime(), heuristic.getBestAverageRating() });
	}

	private void notifyTeamGenerated(List<Employee> team, double combinations, long time) {
	    if (observers != null) {
	        observers.stream()
	                .forEach(observer -> observer.onTeamGenerated(team, combinations, time));
	    }
	}

	private void notifyComparativeGenerated(HashMap<String, Object[]> resultMap) {
	    if (observers != null) {
	        observers.stream()
	                .forEach(observer -> observer.onConmparativeGenerated(resultMap));
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
	    return employees.stream()
	            .filter(employee -> employee.getDni().equals(conflictedId))
	            .findFirst()
	            .orElse(null);
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