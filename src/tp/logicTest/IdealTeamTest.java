package tp.logicTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tp.logic.Employee;
import tp.logic.IdealTeam;

import java.util.List;

public class IdealTeamTest {

    private IdealTeam idealTeam;
    private int projectLeaderCount;
    private int architectCount;
    private int programmerCount;
    private int testerCount;

    @Before
    public void setup() {
        idealTeam = IdealTeam.getIdealTeam();
        projectLeaderCount = 1;
        architectCount = 2;
        programmerCount = 3;
        testerCount = 4;
    }

    @Test
    public void testGenerateTeamByBruteForce() {
        List<Employee> team = idealTeam.generateTeamByBruteForce(projectLeaderCount, architectCount,
                programmerCount, testerCount);
        System.out.println("Generated Team:");
        
        Assert.assertEquals(projectLeaderCount, getEmployeeCountByRole(team, Employee.Role.Project_Leader));
        Assert.assertEquals(architectCount, getEmployeeCountByRole(team, Employee.Role.Architect));
        Assert.assertEquals(programmerCount, getEmployeeCountByRole(team, Employee.Role.Programmer));
        Assert.assertEquals(testerCount, getEmployeeCountByRole(team, Employee.Role.Tester));
    }

    @Test
    public void testGenerateTeamByBackTracking() {
        List<Employee> team = idealTeam.generateTeamByBackTracking(projectLeaderCount, architectCount,
                programmerCount, testerCount);

        Assert.assertEquals(projectLeaderCount, getEmployeeCountByRole(team, Employee.Role.Project_Leader));
        Assert.assertEquals(architectCount, getEmployeeCountByRole(team, Employee.Role.Architect));
        Assert.assertEquals(programmerCount, getEmployeeCountByRole(team, Employee.Role.Programmer));
        Assert.assertEquals(testerCount, getEmployeeCountByRole(team, Employee.Role.Tester));
    }

    @Test
    public void testGenerateTeamByHeuristic() {
        List<Employee> team = idealTeam.generateTeamByHeuristic(projectLeaderCount, architectCount,
                programmerCount, testerCount);

        Assert.assertEquals(projectLeaderCount, getEmployeeCountByRole(team, Employee.Role.Project_Leader));
        Assert.assertEquals(architectCount, getEmployeeCountByRole(team, Employee.Role.Architect));
        Assert.assertEquals(programmerCount, getEmployeeCountByRole(team, Employee.Role.Programmer));
        Assert.assertEquals(testerCount, getEmployeeCountByRole(team, Employee.Role.Tester));
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

           
