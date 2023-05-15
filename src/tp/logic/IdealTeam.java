package tp.logic;

import java.util.List;
import java.util.Set;


public class IdealTeam {

	
	
	public void generateTeamByBruteForce(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount, int testerCount) {

	    Runnable bruteForceTask = new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();

                BruteForce bruteForce = new BruteForce(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
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

	
	public void generateTeamByBackTracking(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount, int testerCount) {

	    Runnable backTrackingTask = new Runnable() {
	        @Override
	        public void run() {
	        	long startTime = System.currentTimeMillis();
	        	
	            BackTracking backTracking = new BackTracking(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
	            List<Employee> bestCombination = backTracking.findBestCombination();
	            
	            long endTime = System.currentTimeMillis();
                double executionTime = (endTime - startTime) / 1000.0;
                
	            System.out.println("Mejor combinación de empleados:");
	            System.out.println(bestCombination.size());
	            for (Employee employee : bestCombination) {
	                System.out.println(employee);
	            }
	            System.out.println("Tiempo de ejecución: " + executionTime + " segundos");
	        }
	    };

	    Thread thread = new Thread(backTrackingTask);
	    thread.start();
	}
	
	//------------------------------------------------------------------------------------------------//
	
	public void displayEmployees(List<Employee> employees) {
	    for (Employee employee : employees) {
	        System.out.println("Employee ID: " + employee.getId());
	        System.out.println("First Name: " + employee.getFirstName());
	        System.out.println("Last Name: " + employee.getLastName());
	        System.out.println("Rating: " + employee.getRating());
	        System.out.println("Role: " + employee.getRole());
	        System.out.println("Photo: " + employee.getPhoto());

	        System.out.println("Conflicted Employees:");
	        Set<Double> conflictedIds = employee.getConflicts();
	        for (Double conflictedId : conflictedIds) {
	            Employee conflictedEmployee = findEmployeeById(employees, conflictedId);
	            if (conflictedEmployee != null) {
	                System.out.println("  - " + conflictedEmployee.getFirstName() + " " + conflictedEmployee.getLastName());
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


