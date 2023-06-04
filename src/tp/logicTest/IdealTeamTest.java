package tp.logicTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tp.logic.Employee;
import tp.logic.IdealTeam;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class IdealTeamTest {

	private IdealTeam idealTeam;
	private int projectLeaderCount;
	private int architectCount;
	private int programmerCount;
	private int testerCount;

	@Before
	public void setup() {
		idealTeam = IdealTeam.getIdealTeam();

		List<Employee> employees = new ArrayList<>();
		Set<String> idsConflicteds = new HashSet<>();
		idsConflicteds.add("0000");
		employees.add(new Employee("1111", "A", "A", 3, idsConflicteds, Employee.Role.Project_Leader, null));
		employees.add(new Employee("2222", "B", "B", 3, idsConflicteds, Employee.Role.Architect, null));
		employees.add(new Employee("3333", "C", "C", 3, idsConflicteds, Employee.Role.Tester, null));
		employees.add(new Employee("4444", "D", "C", 3, idsConflicteds, Employee.Role.Tester, null));
		employees.add(new Employee("5555", "E", "E", 3, idsConflicteds, Employee.Role.Programmer, null));
		employees.add(new Employee("6666", "F", "F", 3, idsConflicteds, Employee.Role.Programmer, null));
		employees.add(new Employee("7777", "G", "G", 3, idsConflicteds, Employee.Role.Tester, null));
		employees.add(new Employee("8888", "H", "H", 3, idsConflicteds, Employee.Role.Programmer, null));

		idealTeam.setEmployees(employees);

		projectLeaderCount = 1;
		architectCount = 1;
		programmerCount = 1;
		testerCount = 1;
	}

	@Test
	public void testGenerateTeamByBruteForce() {
		List<Employee> team = idealTeam.generateTeamByBruteForce(projectLeaderCount, architectCount, programmerCount,
				testerCount);

		Assert.assertEquals(projectLeaderCount, getEmployeeCountByRole(team, Employee.Role.Project_Leader));
		Assert.assertEquals(architectCount, getEmployeeCountByRole(team, Employee.Role.Architect));
		Assert.assertEquals(programmerCount, getEmployeeCountByRole(team, Employee.Role.Programmer));
		Assert.assertEquals(testerCount, getEmployeeCountByRole(team, Employee.Role.Tester));
	}

	@Test
	public void testGenerateTeamByBackTracking() {
		List<Employee> team = idealTeam.generateTeamByBackTracking(projectLeaderCount, architectCount, programmerCount,
				testerCount);

		Assert.assertEquals(projectLeaderCount, getEmployeeCountByRole(team, Employee.Role.Project_Leader));
		Assert.assertEquals(architectCount, getEmployeeCountByRole(team, Employee.Role.Architect));
		Assert.assertEquals(programmerCount, getEmployeeCountByRole(team, Employee.Role.Programmer));
		Assert.assertEquals(testerCount, getEmployeeCountByRole(team, Employee.Role.Tester));
	}

	@Test
	public void testGenerateTeamByHeuristic() {
		List<Employee> team = idealTeam.generateTeamByHeuristic(projectLeaderCount, architectCount, programmerCount,
				testerCount);

		Assert.assertEquals(projectLeaderCount, getEmployeeCountByRole(team, Employee.Role.Project_Leader));
		Assert.assertEquals(architectCount, getEmployeeCountByRole(team, Employee.Role.Architect));
		Assert.assertEquals(programmerCount, getEmployeeCountByRole(team, Employee.Role.Programmer));
		Assert.assertEquals(testerCount, getEmployeeCountByRole(team, Employee.Role.Tester));
	}

	@Test
	public void testFindEmployeeByDni() {

		Employee foundEmployee = idealTeam.findEmployeeByDni("1111");
		Assert.assertNotNull(foundEmployee);
		Assert.assertEquals("A", foundEmployee.getFirstName());
		Assert.assertEquals(Employee.Role.Project_Leader, foundEmployee.getRole());

		Employee notFoundEmployee = idealTeam.findEmployeeByDni("11112222");
		Assert.assertNull(notFoundEmployee);
	}

	@Test
	public void testGetEmployees() {
		List<Employee> fetchEmployees = idealTeam.getEmployees();

		Assert.assertNotNull(fetchEmployees);
		Assert.assertEquals(8, fetchEmployees.size());
		Assert.assertEquals("A", fetchEmployees.get(0).getFirstName());
		Assert.assertEquals("B", fetchEmployees.get(1).getFirstName());
	}

	@Test
	public void testAddEmployee() {

		Employee newEmployee = new Employee("9999", "I", "I", 3, null, Employee.Role.Programmer, null);

		idealTeam.addEmployee(newEmployee);

		List<Employee> employees = idealTeam.getEmployees();

		Assert.assertTrue(employees.contains(newEmployee));
	}

	private int getEmployeeCountByRole(List<Employee> employees, Employee.Role role) {
		int count = 0;
		for (Employee employee : employees) {
			if (employee.getRole() == role) {
				count++;
			}
		}
		return count;
	}

}