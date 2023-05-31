package tp.dal;

import tp.logic.Employee;

import java.util.List;

public interface IdataLoader {

	List<Employee> readEmployeesFromJSON();

}