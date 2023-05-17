package tp.dal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tp.logic.Employee;
import tp.logic.Employee.Role;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Data {
    private String employeesFileLocation = "src" + java.io.File.separator + "tp" + java.io.File.separator + "dal"
            + java.io.File.separator + "employeesList.json";
    private String conflictsFileLocation = "src" + java.io.File.separator + "tp" + java.io.File.separator + "dal"
            + java.io.File.separator + "conflictsList.json";

    public List<Employee> readEmployeesFromJSON() {
        List<Employee> employees = readEmployeesListFromJSON(employeesFileLocation);
        List<Map<String, List<String>>> conflictsList = readConflictsListFromJSON(conflictsFileLocation);

        if (employees != null && conflictsList != null) {
            for (Employee employee : employees) {
                for (Map<String, List<String>> conflict : conflictsList) {
                    String employeeIdStr = String.valueOf(employee.getId());
                    if (conflict.containsKey(employeeIdStr)) {
                        List<String> conflictedIds = conflict.get(employeeIdStr);
                        Set<String> conflictedIdsSet = new HashSet<>(conflictedIds);
                        employee.getConflicts().addAll(conflictedIdsSet);
                    }
                }
            }
        }

        return employees;
    }

    private List<Employee> readEmployeesListFromJSON(String fileLocation) {
        try (FileReader reader = new FileReader(fileLocation)) {
            Gson gson = new Gson();
            Type employeeListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            List<Map<String, Object>> employeeMapList = gson.fromJson(reader, employeeListType);

            List<Employee> employees = new ArrayList<>();

            for (Map<String, Object> employeeMap : employeeMapList) {
                String id = (String) employeeMap.get("id");
                String firstName = (String) employeeMap.get("firstName");
                String lastName = (String) employeeMap.get("lastName");
                int rating = ((Double) employeeMap.get("rating")).intValue();
                Role role = Role.valueOf((String) employeeMap.get("role"));
                String photo = (String) employeeMap.get("photo");
                Employee employee = new Employee(id, firstName, lastName, rating, new HashSet<>(), role, photo);
                employees.add(employee);
            }

            return employees;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Map<String, List<String>>> readConflictsListFromJSON(String fileLocation) {
        try (FileReader reader = new FileReader(fileLocation)) {
            Gson gson = new Gson();
            Type conflictsListType = new TypeToken<List<Map<String, List<String>>>>() {}.getType();
            return gson.fromJson(reader, conflictsListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

