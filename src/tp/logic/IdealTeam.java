package tp.logic;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashMap;

public class IdealTeam {

	private List<IteamUpdateObserver> observers;
	private List<Employee> employees;
	private static IdealTeam idealTeam;
	private HashMap<String, Object[]> resultMap;
	
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

	private void notifyTeamGenerated(List<Employee> team, double combinations, long time) {
		if (observers != null) {
			for (IteamUpdateObserver observer : observers) {
				observer.onTeamGenerated(team, combinations, time);
			}
		}
	}

	public Map<String, Object[]> generateComparative(int projectLeaderCount, int architectCount,
			int programmerCount, int testerCount) {
		
		this.resultMap = new HashMap<>();

		bruteForceInComparator(projectLeaderCount, architectCount, programmerCount, testerCount);

		backtrackingInComparator(projectLeaderCount, architectCount, programmerCount, testerCount);

		Comparator<Employee> customComparator = (e1, e2) -> {
			double coefficient1 = calculateCoefficient(e1);
			double coefficient2 = calculateCoefficient(e2);
			return Double.compare(coefficient2, coefficient1);
		};

		heuristicInComparator(projectLeaderCount, architectCount, programmerCount, testerCount,
				customComparator);
		
		notifyComparativeGenerated(this.resultMap);
		return this.resultMap;
	}

	private void notifyComparativeGenerated(HashMap<String, Object[]> resultMap) {
		if (observers != null) {
			for (IteamUpdateObserver observer : observers) {
				observer.onConmparativeGenerated(resultMap);
			}
		}
	}

	private void bruteForceInComparator(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount);
		List<Employee> bruteForceBestCombination = bruteForce.findBestCombination();
		double bruteForceCombinationCount = bruteForce.getCombinationCount();
		long bruteForceExecutionTime = bruteForce.getExecutionTime();
		double bruteForceBestAverageRating = bruteForce.getBestAverageRating();
		
		this.resultMap.put("Brute Force", new Object[] { bruteForceBestCombination, bruteForceCombinationCount,
				bruteForceExecutionTime, bruteForceBestAverageRating });
	}

	private void backtrackingInComparator(int projectLeaderCount, int architectCount, int programmerCount,
			int testerCount) {
		BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount, programmerCount,
				testerCount);
	
		List<Employee> backTrackingBestCombination = backTracking.findBestCombination();
		double backTrackingCombinationCount = backTracking.getCombinationCount();
		long backTrackingExecutionTime = backTracking.getExecutionTime();
		double backTrackingBestAverageRating = backTracking.getBestAverageRating();
	
		this.resultMap.put("Backtracking", new Object[] { backTrackingBestCombination, backTrackingCombinationCount,
				backTrackingExecutionTime, backTrackingBestAverageRating });
	}

	private void heuristicInComparator(int projectLeaderCount, int architectCount, int programmerCount, int testerCount, 
			Comparator<Employee> customComparator) {
		Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount, testerCount,
				customComparator);
	
		List<Employee> heuristicBestCombination = heuristic.findBestCombination();
		double heuristicCombinationCount = heuristic.getCombinationCount();
		long heuristicExecutionTime = heuristic.getExecutionTime();
		double heuristicBestAverageRating = heuristic.getBestAverageRating();
	
		this.resultMap.put("Heuristic", new Object[] { heuristicBestCombination, heuristicCombinationCount,
				heuristicExecutionTime, heuristicBestAverageRating });
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

	public void addEmployee(Employee em) {
		employees.add(em);
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