package tp.dal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tp.logic.Employee;

public class SaveData {

	public void createFile(List<Employee> team, int combinations, long time) {
		String fileName = generateFileName(team);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write("Equipo generado:\n");
			writer.write("Combinaciones generadas: " + combinations + "\n");
			writer.write("Tiempo del proceso: " + time + "\n");
			for (Employee employee : team) {
				writer.write(employee.toString() + "\n");
			}
			writer.flush();
			System.out.println("Equipo guardado en el archivo: " + fileName);
		} catch (IOException e) {
			System.out.println("Error al guardar el equipo en el archivo.");
			e.printStackTrace();
		}

	}

	private String generateFileName(List<Employee> team) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormat.format(new Date());

		for (Employee employee : team) {
			if (employee.getRole() == Employee.Role.Project_Leader) {
				return "team_Leader_" + employee.getLastName() + "_" + timestamp + ".txt";
			}
		}

		return "team_" + timestamp + ".txt";
	}

}
