package tp.logic;

import java.util.List;

public class BruteForce extends Algorithm {
    public BruteForce(List<Employee> employees, int projectLeaderCount, int architectCount, int programmerCount,
                      int testerCount) {
        super(employees, projectLeaderCount, architectCount, programmerCount, testerCount);
    }

    @Override
	public void generateCombination(List<Employee> combination, int currentIndex) {
        if (currentIndex == employees.size()) {
            combinationCount++;
            evaluateCombination(combination);
            return;
        }

        Employee currentEmployee = employees.get(currentIndex);
        combination.add(currentEmployee);
        generateCombination(combination, currentIndex + 1);
        combination.remove(combination.size() - 1);
        generateCombination(combination, currentIndex + 1);
    }
}


