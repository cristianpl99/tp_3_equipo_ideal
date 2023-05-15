package tp_3_equipo_ideal;

import java.util.List;
import java.util.Map;

import tp.logic.Employee;
import tp.logic.IdealTeam;
import tp.dal.Data;

public class Main {

	public static void main(String[] args) {
		Data data = new Data();
		IdealTeam idealTeam = new IdealTeam();
				
		List<Employee> employees = data.readEmployeesFromJSON();
		
		//idealTeam.displayEmployees(employees);
		
		//idealTeam.generateTeamByBruteForce(employees, 1, 1, 2, 3);
		idealTeam.generateTeamByBackTracking(employees, 1, 1, 3, 5);
		
		}

	}

		



	        
	    
		

	


