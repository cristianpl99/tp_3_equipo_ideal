package tp.dal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tp.logic.Employee;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Data {
	private String fileLocation = "src" + java.io.File.separator + "tp" + java.io.File.separator + "dal"
			+ java.io.File.separator + "employeesList.json";
	private String fileConflicts = "src" + java.io.File.separator + "tp" + java.io.File.separator + "dal"
			+ java.io.File.separator + "conflicts.json";

    public List<Employee> readEmployeesFromJSON() {
        try (FileReader reader = new FileReader(fileLocation)) {
            Gson gson = new Gson();
            Type employeeListType = new TypeToken<List<Employee>>() {}.getType();
            return gson.fromJson(reader, employeeListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<List<Integer>> readTuplesFromJSON() {
        try (FileReader reader = new FileReader(fileConflicts)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<List<Integer>>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

