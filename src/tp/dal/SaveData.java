package tp.dal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tp.logic.Employee;

public class SaveData implements ISaveData {

	public void writeLogFile(List<Employee> team, double combinations, long time) {
		String fileName = "work_log.txt";
		String headliner = generateHeadLiner(team);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
			writer.write(headliner + "\n");
			writer.write("Generated team for this configuration: " + getEmployeeCountByRole(team) + "\n");
			writer.write("Combination generated: " + combinations + "\n");
			writer.write("Process time: " + time + "\n");
			for (Employee employee : team) {
				writer.write(employee.toString() + "\n");
			}
			writer.write("\n");
			writer.write("-------------------------------------------------------------------");
			writer.write("\n");
			writer.flush();
		} catch (IOException e) {
			System.out.println("Error al guardar el equipo en el archivo.");
			e.printStackTrace();
		}

	}

	public void createFile(HashMap<String, Object[]> resultMap) {
		String timestamp = generateTimestamp();
		String fileName = "algorithms_comparison_" + timestamp + ".txt";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (Map.Entry<String, Object[]> entry : resultMap.entrySet()) {
				String algorithm = entry.getKey();
				Object[] values = entry.getValue();
				@SuppressWarnings("unchecked")
				List<Employee> employees = (List<Employee>) values[0];
				double combinations = (double) values[1];
				long time = (long) values[2];
				double averageRating = (double) values[3];

				writer.write("Algorithm: " + algorithm + "\n");
				writer.write("Generated Combinations: " + combinations + "\n");
				writer.write("Process Time: " + time + "ms\n");
				writer.write("Average Rating: " + averageRating + "\n");
				writer.write("Generated team for this configuration: " + getEmployeeCountByRole(employees) + "\n");
				for (Employee employee : employees) {
					writer.write(employee.toString() + "\n");
				}

				writer.write("\n");
				writer.write("-------------------------------------------------------------");
				writer.write("\n");
			}
			writer.flush();
		} catch (IOException e) {
			System.out.println("Error al guardar la comparación de algoritmos en el archivo.");
			e.printStackTrace();
		}
	}

	private String generateTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		return dateFormat.format(new Date());
	}

	private String generateHeadLiner(List<Employee> team) {
		String timestamp = generateTimestamp();

		for (Employee employee : team) {
			if (employee.getRole() == Employee.Role.Project_Leader) {
				return "team_Leader_" + employee.getLastName() + "_" + timestamp;
			}
		}
		return "team_" + timestamp + ".txt";
	}

	private String getEmployeeCountByRole(List<Employee> team) {
		Map<Employee.Role, Integer> countByRole = new HashMap<>();

		for (Employee employee : team) {
			Employee.Role role = employee.getRole();
			countByRole.put(role, countByRole.getOrDefault(role, 0) + 1);
		}

		StringBuilder countBuilder = new StringBuilder();
		for (Map.Entry<Employee.Role, Integer> entry : countByRole.entrySet()) {
			Employee.Role role = entry.getKey();
			int count = entry.getValue();
			countBuilder.append(count).append(" ").append(role.toString().toLowerCase()).append("s, ");
		}
		if (countBuilder.length() > 0) {
			countBuilder.setLength(countBuilder.length() - 2);
		}

		return countBuilder.toString();
	}

}