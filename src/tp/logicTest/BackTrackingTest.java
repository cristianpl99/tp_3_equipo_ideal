package tp.logicTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tp.logic.BackTracking;
import tp.logic.Employee;
import tp.logic.Employee.Role;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BackTrackingTest {

	private BackTracking backTracking;
	private List<Employee> employees;

	@Before
	public void setUp() {
		employees = new ArrayList<>();
		employees.add(new Employee("1", "Linus", "Torvalds", 5, new HashSet<>(), Role.Project_Leader, "photo1.jpg"));
		employees.add(new Employee("2", "Elon", "Musk", 4, new HashSet<>(), Role.Architect, "photo2.jpg"));
		employees.add(new Employee("3", "Raul", "Capablanca", 3, new HashSet<>(), Role.Programmer, "photo3.jpg"));
		employees.add(new Employee("4", "Magnus", "Carlsen", 2, new HashSet<>(), Role.Tester, "photo4.jpg"));
		employees.add(new Employee("5", "Judith", "Polgar", 2, new HashSet<>(Arrays.asList("0", "1", "2")), Role.Tester,
				"photo4.jpg"));

		backTracking = new BackTracking(employees, 1, 1, 1, 1);
	}

	@Test
	public void testFindBestCombination() {
		List<Employee> bestCombination = backTracking.findBestCombination();
		assertNotNull(bestCombination);
		assertFalse(bestCombination.isEmpty());
		assertTrue(backTracking.isValidCombination(bestCombination));
	}

	@Test
	public void testGenerateCombination() {
		List<Employee> combinations = new ArrayList<>();
		backTracking.generateCombination(combinations, 0);
		Assert.assertEquals(54, backTracking.getCombinationCount());
	}

	@Test
	public void testCombinationContainsConflictedEmployee() {
		List<Employee> combination = new ArrayList<>();
		combination.add(employees.get(4));
		combination.add(employees.get(1));
		combination.add(employees.get(2));
		combination.add(employees.get(3));

		boolean containsConflicted = backTracking.combinationContainsConflictedEmployee(combination, employees.get(0));
		Assert.assertTrue(containsConflicted);
	}

	@Test
	public void testEvaluateValidCombination() {
		List<Employee> validCombination = new ArrayList<>();
		validCombination.add(employees.get(0));
		validCombination.add(employees.get(1));
		validCombination.add(employees.get(2));
		validCombination.add(employees.get(3));

		Assert.assertTrue(backTracking.isValidCombination(validCombination));
	}

	@Test
	public void testEvaluateInvalidCombination() {

		List<Employee> invalidCombination = new ArrayList<>();
		invalidCombination.add(employees.get(0));
		invalidCombination.add(employees.get(1));
		invalidCombination.add(employees.get(2));
		invalidCombination.add(employees.get(3));
		invalidCombination.add(employees.get(4));

		Assert.assertFalse(backTracking.isValidCombination(invalidCombination));
	}

}