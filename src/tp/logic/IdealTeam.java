package tp.logic;

import java.util.List;
import java.util.Set;
import java.util.Comparator;

public class IdealTeam {

	public List<Employee> generateTeamByBruteForce(List<Employee> employees, int projectLeaderCount, int architectCount,
	        int programmerCount, int testerCount) {
	    BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
	    List<Employee> bestCombination = bruteForce.findBestCombination();
	    printResult(bestCombination);
	    return bestCombination;
	}

	public List<Employee> generateTeamByBackTracking(List<Employee> employees, int projectLeaderCount, int architectCount,
	        int programmerCount, int testerCount) {
	    BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
	    List<Employee> bestCombination = backTracking.findBestCombination();
	    printResult(bestCombination);
	    return bestCombination;
	}

	public List<Employee> generateTeamByHeuristic(List<Employee> employees, int projectLeaderCount, int architectCount,
	        int programmerCount, int testerCount) {
	    Comparator<Employee> customComparator = (e1, e2) -> {
	        double coefficient1 = calculateCoefficient(e1);
	        double coefficient2 = calculateCoefficient(e2);
	        return Double.compare(coefficient2, coefficient1);
	    };

	    Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
	    List<Employee> bestCombination = heuristic.findBestCombination(customComparator);
	    printResult(bestCombination);
	    return bestCombination;
	}

	private double calculateCoefficient(Employee employee) {
		int conflictCount = employee.getConflicts().size();
		double rating = employee.getRating();
		return rating - conflictCount;
	}

	// ------------------------------------------------------------------------------------------------//

	private void printResult(List<Employee> bestCombination) {
		System.out.println("Mejor combinación de empleados:" + "\n");
		int totalRating = 0;
		for (Employee employee : bestCombination) {
			System.out.println(employee);
			totalRating += employee.getRating();
		}
		int employeeCount = bestCombination.size();
		double averageRating = (double) totalRating / employeeCount;
		System.out.println("Promedio de rating de empleados: " + averageRating + "\n");
	}

	public void displayEmployees(List<Employee> employees) {
		for (Employee employee : employees) {
			System.out.println(employee.toString());
			System.out.println("Conflicted Employees:");
			Set<String> conflictedIds = employee.getConflicts();
			for (String conflictedId : conflictedIds) {
				Employee conflictedEmployee = findEmployeeById(employees, conflictedId);
				if (conflictedEmployee != null) {
					System.out.println(
							" - " + conflictedEmployee.getFirstName() + " " + conflictedEmployee.getLastName());
				}
			}
			System.out.println();
		}
	}

	private Employee findEmployeeById(List<Employee> employees, String conflictedId) {
		for (Employee employee : employees) {
			if (employee.getId().equals(conflictedId)) {
				return employee;
			}
		}
		return null;
	}
}
