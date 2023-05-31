package tp.logicTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tp.logic.BruteForce;
import tp.logic.Employee;
import tp.logic.Employee.Role;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BruteForceTest {

	private BruteForce bruteForce;
	private List<Employee> employees;

	@Before
	public void setUp() {
		employees = new ArrayList<>();
		employees.add(new Employee("1", "Linus", "Torvalds", 5, new HashSet<>(), Role.Project_Leader, "photo1.jpg"));
		employees.add(new Employee("2", "Elon", "Musk", 4, new HashSet<>(), Role.Architect, "photo2.jpg"));
		employees.add(new Employee("3", "Raul", "Capablanca", 3, new HashSet<>(), Role.Programmer, "photo3.jpg"));
		employees.add(new Employee("4", "Magnus", "Carlsen", 2, new HashSet<>(), Role.Tester, "photo4.jpg"));
		employees.add(new Employee("5", "Judith", "Polgar", 2, new HashSet<>(Arrays.asList("1", "2", "3")), Role.Tester,
				"photo4.jpg"));

		bruteForce = new BruteForce(employees, 1, 1, 1, 1);
	}

	@Test
	public void testFindBestCombination() {
		List<Employee> bestCombination = bruteForce.findBestCombination();
		assertNotNull(bestCombination);
		assertFalse(bestCombination.isEmpty());
		assertTrue(bruteForce.isValidCombination(bestCombination));
	}

	@Test
	public void testGenerateCombination() {
		List<Employee> combinations = new ArrayList<>();
		bruteForce.generateCombination(combinations, 0);
		// Las combinaciones posibles no contemplan a Judith Polgar por conflictos
		Assert.assertEquals(32, bruteForce.getCombinationCount());
	}

	@Test
	public void testEvaluateValidCombination() {
		List<Employee> validCombination = new ArrayList<>();
		validCombination.add(employees.get(0));
		validCombination.add(employees.get(1));
		validCombination.add(employees.get(2));
		validCombination.add(employees.get(3));

		bruteForce.evaluateCombination(validCombination);
		// Es posible generar combinacion
		boolean sameSizeLists = bruteForce.getBestCombination().size() == validCombination.size();
		;
		Assert.assertTrue(sameSizeLists);

	}

	@Test
	public void testEvaluateInvalidCombination() {
		List<Employee> invalidCombination = new ArrayList<>();
		invalidCombination.add(employees.get(1));
		invalidCombination.add(employees.get(2));
		invalidCombination.add(employees.get(3));
		invalidCombination.add(employees.get(4));

		bruteForce.evaluateCombination(invalidCombination);
		// Por los conflictos de Polgar, no hay combinacion posible
		boolean emptyBestCombinationList = bruteForce.getBestCombination().isEmpty();
		Assert.assertTrue(emptyBestCombinationList);

	}

	@Test
	public void testHasConflictedEmployees() {
		List<Employee> combinationWithConflicts = new ArrayList<>();
		combinationWithConflicts.add(employees.get(0));
		combinationWithConflicts.add(employees.get(1));
		combinationWithConflicts.add(employees.get(2));
		combinationWithConflicts.add(employees.get(4));

		List<Employee> combinationWithoutConflicts = new ArrayList<>();
		combinationWithoutConflicts.add(employees.get(0));
		combinationWithoutConflicts.add(employees.get(1));
		combinationWithoutConflicts.add(employees.get(2));
		combinationWithoutConflicts.add(employees.get(3));

		boolean hasConflicts1 = bruteForce.hasConflictedEmployees(combinationWithConflicts);
		boolean hasConflicts2 = bruteForce.hasConflictedEmployees(combinationWithoutConflicts);

		assertTrue(hasConflicts1);
		assertFalse(hasConflicts2);
	}

}