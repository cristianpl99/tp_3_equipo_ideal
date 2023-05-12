package tp_3_equipo_ideal;

import java.util.List;

import tp.logic.Employee;
import tp.logic.IdealTeam;
import tp.dal.Data;

public class Main {

	public static void main(String[] args) {
		Data data = new Data();
		IdealTeam idealTeam = new IdealTeam();
		List<Employee> employees = data.readEmployeesFromJSON();
		for (Employee employee : employees) {
			System.out.println(employee);
		}

		List<List<Integer>> tuples = data.readTuplesFromJSON();
		for (List<Integer> tuple : tuples) {
			if (tuple.size() == 2) {
				int firstValue = tuple.get(0);
				int secondValue = tuple.get(1);
				System.out.println("Tuple: " + firstValue + ", " + secondValue);
			}
		}
		
		idealTeam.showEmployees(tuples, employees);
		}

	}

		



	        
	    
		

	


