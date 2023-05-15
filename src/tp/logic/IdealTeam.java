package tp.logic;

import java.util.List;
import java.util.Set;
import java.util.Comparator;

public class IdealTeam {

	public void generateTeamByBruteForce(List<Employee> employees, int projectLeaderCount, int architectCount,
			int programmerCount, int testerCount) {

		Runnable bruteForceTask = new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();

				BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount,
						testerCount);
				List<Employee> bestCombination = bruteForce.findBestCombination();

				long endTime = System.currentTimeMillis();
				double executionTime = (endTime - startTime) / 1000.0;

				System.out.println("Mejor combinación de empleados:");
				for (Employee employee : bestCombination) {
					System.out.println(employee);
				}
				System.out.println("Tiempo de ejecución: " + executionTime + " segundos");
			}
		};

		Thread thread = new Thread(bruteForceTask);
		thread.start();
	}

	public void generateTeamByBackTracking(List<Employee> employees, int projectLeaderCount, int architectCount,
			int programmerCount, int testerCount) {

		Runnable backTrackingTask = new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();

				BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount,
						programmerCount, testerCount);
				List<Employee> bestCombination = backTracking.findBestCombination();

				long endTime = System.currentTimeMillis();
				double executionTime = (endTime - startTime) / 1000.0;

				System.out.println("Mejor combinación de empleados:");
				System.out.println(bestCombination.size());
				int acum = 0;
				for (Employee employee : bestCombination) {
					System.out.println(employee);
					acum += employee.getRating();
				}
				System.out.println("Promedio de rating de empleados: " + acum / bestCombination.size());
				System.out.println("Tiempo de ejecución: " + executionTime + " segundos");
			}
		};

		Thread thread = new Thread(backTrackingTask);
		thread.start();
	}

	public void generateTeamByHeuristic(List<Employee> employees, int projectLeaderCount, int architectCount,
			int programmerCount, int testerCount) {

		Comparator<Employee> customComparator = new Comparator<Employee>() {
			@Override
			public int compare(Employee e1, Employee e2) {
				double coefficient1 = calculateCoefficient(e1);
				double coefficient2 = calculateCoefficient(e2);
				return Double.compare(coefficient2, coefficient1);
			}
		};

		Runnable heuristicTask = new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();

				Heuristic heuristic = new Heuristic(employees, projectLeaderCount, architectCount, programmerCount,
						testerCount);
				List<Employee> bestCombination = heuristic.findBestCombination(customComparator);

				long endTime = System.currentTimeMillis();
				double executionTime = (endTime - startTime) / 1000.0;

				System.out.println("Mejor combinación de empleados:");
				int acum = 0;
				for (Employee employee : bestCombination) {
					System.out.println(employee);
					acum += employee.getRating();
				}
				System.out.println("Promedio de rating de empleados: " + acum / bestCombination.size());
				System.out.println("Tiempo de ejecución: " + executionTime + " segundos");
			}
		};

		Thread thread = new Thread(heuristicTask);
		thread.start();
	}

	private double calculateCoefficient(Employee employee) {
		int conflictCount = employee.getConflicts().size();
		double rating = employee.getRating();
		double coefficient = rating / conflictCount;

		return coefficient;
	}

	// ------------------------------------------------------------------------------------------------//

	public void displayEmployees(List<Employee> employees) {
		for (Employee employee : employees) {
			employee.toString();
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
