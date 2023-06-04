package tp.dal;

import tp.logic.Employee;

import java.util.HashMap;
import java.util.List;

public interface ISaveData {

    void writeLogFile(List<Employee> team, double combinations, long time);

    void createFile(HashMap<String, Object[]> resultMap);
}

