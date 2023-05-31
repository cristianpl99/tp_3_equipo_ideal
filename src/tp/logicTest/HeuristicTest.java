package tp.logicTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tp.logic.Employee;
import tp.logic.Heuristic;
import tp.logic.Employee.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class HeuristicTest {

	private Heuristic heuristic;
	private List<Employee> employees;
	private Comparator<Employee> customComparator;

	@Before
	public void setUp() {
		employees = new ArrayList<>();
		employees.add(new Employee("1", "Linus", "Torvalds", 5, new HashSet<>(), Role.Project_Leader, "photo1.jpg"));
		employees.add(new Employee("2", "Elon", "Musk", 4, new HashSet<>(), Role.Architect, "photo2.jpg"));
		employees.add(new Employee("3", "Raul", "Capablanca", 3, new HashSet<>(), Role.Programmer, "photo3.jpg"));
		employees.add(new Employee("4", "Magnus", "Carlsen", 2, new HashSet<>(), Role.Tester, "photo4.jpg"));
		employees.add(new Employee("5", "Judith", "Polgar", 2, new HashSet<>(Arrays.asList("1", "2", "3")), Role.Tester,
				"photo4.jpg"));

		customComparator = (e1, e2) -> {
			double coefficient1 = e1.getRating() - e1.getConflicts().size();
			double coefficient2 = e2.getRating() - e2.getConflicts().size();
			return Double.compare(coefficient2, coefficient1);
		};
		heuristic = new Heuristic(employees, 1, 1, 1, 1, customComparator);
	}

	@Test
	public void testFindBestCombination() {
		List<Employee> bestCombination = heuristic.findBestCombination();
		Assert.assertNotNull(bestCombination);
		Assert.assertFalse(bestCombination.isEmpty());
	}

	@Test
	public void testCombinationContainsConflictedEmployee() {
		List<Employee> combination = new ArrayList<>();
		combination.add(employees.get(4));
		combination.add(employees.get(1));
		combination.add(employees.get(2));
		combination.add(employees.get(3));

		boolean containsConflicted = heuristic.hasConflicts(combination, employees.get(0));
		Assert.assertTrue(containsConflicted);
	}

}