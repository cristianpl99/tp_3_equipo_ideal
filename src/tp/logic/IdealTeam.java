package tp.logic;

import java.util.List;


public class IdealTeam {


	public void showEmployees(List<List<Integer>> tuples, List<Employee> employees) {
		for (List<Integer> tuple : tuples) {
			if (tuple.size() == 2) {
				int id1 = tuple.get(0);
				int id2 = tuple.get(1);

				Employee employee1 = searchEmployeeById(id1, employees);
				Employee employee2 = searchEmployeeById(id2, employees);

				if (employee1 != null && employee2 != null) {
					System.out.println("Empleado 1: " + employee1.getLastName());
					System.out.println("Empleado 2: " + employee2.getLastName());
					System.out.println();
				}
			}
		}
	}

	private Employee searchEmployeeById(int id, List<Employee> employees) {
		for (Employee employee : employees) {
			if (employee.getId() == id) {
				return employee;
			}
		}
		return null;
	}

}


