package tp.logic;

import java.util.List;
import java.util.Set;
import java.util.Comparator;

public class IdealTeam {

	public void generateTeamByBruteForce(List<Employee> employees, int projectLeaderCount, int architectCount,
			int programmerCount, int testerCount) {
		runTask(() -> {
			BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount,
					testerCount);
			List<Employee> bestCombination = bruteForce.findBestCombination();
			printResult(bestCombination);
		}, "Fuerza Bruta");
	}

	public void generateTeamByBackTracking(List<Employee> employees, int projectLeaderCount, int architectCount,
			int programmerCount, int testerCount) {
		runTask(() -> {
			BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount, programmerCount,
					testerCount);
			List<Employee> bestCombination = backTracking.findBestCombination();
			printResult(bestCombination);
		}, "Back Tracking");
	}

	public void generateTeamByHeuristic(List<Employee> employees, int projectLeaderCount, int architectCount,
			int programmerCount, int testerCount) {
		runTask(() -> {
			Comparator<Employee> customComparator = (e1, e2) -> {
				double coefficient1 = calculateCoefficient(e1);
				double coefficient2 = calculateCoefficient(e2);
				return Double.compare(coefficient2, coefficient1);
			};

			Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount,
					testerCount);
			List<Employee> bestCombination = heuristic.findBestCombination(customComparator);
			printResult(bestCombination);
		},"Heuristica");
	}

	private void runTask(Runnable task, String algorithmName) {
		long startTime = System.currentTimeMillis();

		Thread thread = new Thread(task);
		thread.start();

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
		double executionTime = (endTime - startTime) / 1000.0;
		System.out.println("Tiempo de ejecución (" + algorithmName + "): " + executionTime + " segundos" + "\n");
	}

	
	private double calculateCoefficient(Employee employee) {
		int conflictCount = employee.getConflicts().size();
		double rating = employee.getRating();
		return rating / conflictCount;
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
			Set<Double> conflictedIds = employee.getConflicts();
			for (Double conflictedId : conflictedIds) {
				Employee conflictedEmployee = findEmployeeById(employees, conflictedId);
				if (conflictedEmployee != null) {
					System.out.println(
							"  - " + conflictedEmployee.getFirstName() + " " + conflictedEmployee.getLastName());
				}
			}
			System.out.println();
		}
	}

	private Employee findEmployeeById(List<Employee> employees, Double conflictedId) {
		for (Employee employee : employees) {
			if (employee.getId() == conflictedId) {
				return employee;
			}
		}
		return null;
	}
}
