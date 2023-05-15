package tp.dal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tp.logic.Employee;
import tp.logic.Employee.Role;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Data {
	private String fileLocation = "src" + java.io.File.separator + "tp" + java.io.File.separator + "dal"
			+ java.io.File.separator + "employeesList.json";

	public List<Employee> readEmployeesFromJSON() {
		try (FileReader reader = new FileReader(fileLocation)) {
			Gson gson = new Gson();
			Type employeeListType = new TypeToken<List<Map<String, Object>>>() {
			}.getType();
			List<Map<String, Object>> employeeMapList = gson.fromJson(reader, employeeListType);

			List<Employee> employees = new ArrayList<>();

			for (Map<String, Object> employeeMap : employeeMapList) {
				int id = ((Double) employeeMap.get("id")).intValue();
				String firstName = (String) employeeMap.get("firstName");
				String lastName = (String) employeeMap.get("lastName");
				int rating = ((Double) employeeMap.get("rating")).intValue();
				@SuppressWarnings("unchecked")
				List<Double> idsConflicteds = (List<Double>) employeeMap.get("IdsConflicted");
				Role role = Role.valueOf((String) employeeMap.get("role"));
				String photo = (String) employeeMap.get("photo");

				Set<Double> idsConflictedsSet = new HashSet<>(idsConflicteds);

				Employee employee = new Employee(id, firstName, lastName, rating, idsConflictedsSet, role, photo);
				employees.add(employee);
			}

			return employees;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
