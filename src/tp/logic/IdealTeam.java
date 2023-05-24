package tp.logic;

import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashMap;

public class IdealTeam {

	private List<IteamUpdateListener> listeners;
	private List<Employee> employees;

	public IdealTeam() {	
		employees = new ArrayList<>();
		listeners = new ArrayList<>();
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
	
	// todo esto se puede refactorizar pasando directamente el objeto algorithm class
	public HashMap<String, Object[]> generateComparative(int projectLeaderCount, int architectCount, int programmerCount, int testerCount) {
	    HashMap<String, Object[]> resultMap = new HashMap<>();
	    
	    BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
	    List<Employee> bruteForceBestCombination = bruteForce.findBestCombination();
	    int bruteForceCombinationCount = bruteForce.getCombinationCount();
	    long bruteForceExecutionTime = bruteForce.getExecutionTime();
	    resultMap.put("Brute Force", new Object[]{ bruteForceBestCombination, bruteForceCombinationCount, bruteForceExecutionTime });

	    BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
	    List<Employee> backTrackingBestCombination = backTracking.findBestCombination();
	    int backTrackingCombinationCount = backTracking.getCombinationCount();
	    long backTrackingExecutionTime = backTracking.getExecutionTime();
	    resultMap.put("Backtracking", new Object[]{ backTrackingBestCombination, backTrackingCombinationCount, backTrackingExecutionTime });

	    Comparator<Employee> customComparator = (e1, e2) -> {
	        double coefficient1 = calculateCoefficient(e1);
	        double coefficient2 = calculateCoefficient(e2);
	        return Double.compare(coefficient2, coefficient1);
	    };
	    Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount, testerCount, customComparator);
	    List<Employee> heuristicBestCombination = heuristic.findBestCombination();
	    int heuristicCombinationCount = heuristic.getCombinationCount();
	    long heuristicExecutionTime = heuristic.getExecutionTime();
	    resultMap.put("Heuristic", new Object[] { heuristicBestCombination, heuristicCombinationCount, heuristicExecutionTime });

	    return resultMap;
	}


	
	

	public void addListener(IteamUpdateListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(listener);
	}

	public void removeListener(IteamUpdateListener listener) {
		if (listeners != null) {
			listeners.remove(listener);
		}
	}

	private void notifyTeamGenerated(List<Employee> team, int combinations, long time) {
		if (listeners != null) {
			for (IteamUpdateListener listener : listeners) {
				listener.onTeamGenerated(team, combinations, time);
			}
		}
	}

	private double calculateCoefficient(Employee employee) {
		int conflictCount = employee.getConflicts().size();
		double rating = employee.getRating();
		return rating - conflictCount;
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
	
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
