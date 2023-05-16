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
        employees.add(new Employee(1, "Linus", "Torvalds", 5, new HashSet<>(), Role.Project_Leader, "photo1.jpg"));
        employees.add(new Employee(2, "Elon", "Musk", 4, new HashSet<>(), Role.Architect, "photo2.jpg"));
        employees.add(new Employee(3, "Raul", "Capablanca", 3, new HashSet<>(), Role.Programmer, "photo3.jpg"));
        employees.add(new Employee(4, "Magnus", "Carlsen", 2, new HashSet<>(), Role.Tester, "photo4.jpg"));
        employees.add(new Employee(5, "Judith", "Polgar", 2, new HashSet<>(Arrays.asList(1.0, 2.0, 3.0)), Role.Tester, "photo4.jpg"));

        bruteForce = new BruteForce(employees, 1, 1, 1, 1);
    }
    
    @Test
    public void testFindBestCombination() {
        // Crear una instancia de la clase BruteForce
        BruteForce bruteForce = new BruteForce(employees, 1, 1, 1, 1);

        // Obtener la mejor combinación
        List<Employee> bestCombination = bruteForce.findBestCombination();

        // Verificar que la mejor combinación no sea nula y contenga empleados válidos
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
    public void testEvaluateCombination() {
        List<Employee> validCombination = new ArrayList<>();
        validCombination.add(employees.get(0));
        validCombination.add(employees.get(1));
        validCombination.add(employees.get(2));
        validCombination.add(employees.get(3));

        List<Employee> invalidCombination = new ArrayList<>();
        invalidCombination.add(employees.get(1));
        invalidCombination.add(employees.get(2));
        invalidCombination.add(employees.get(3));
        invalidCombination.add(employees.get(4));

        bruteForce.evaluateCombination(validCombination);
        bruteForce.evaluateCombination(invalidCombination);

        Assert.assertTrue(bruteForce.getBestCombination().size() > 0);
        
        // Por los conflictos de Polgar, no hay combinacion posible
        
        Assert.assertFalse(bruteForce.getBestCombination().size() == 0);
    }
}


