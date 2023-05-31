package tp.logic;

import java.util.Objects;
import java.util.Set;

public class Employee {

	private String dni;
	private String firstName;
	private String lastName;
	private int rating;
	private Set<String> idsConflicteds;
	private Role role;
	private String photo;

	public Employee(String dni, String firstName, String lastName, int rating, Set<String> conflicts, Role role,
			String photo) {
		this.dni = dni;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
		this.idsConflicteds = conflicts;
		this.role = role;
		this.photo = photo;
	}

	public void addConflict(String dni) {
		idsConflicteds.add(dni);
	}

	public String getPhoto() {
		return photo;
	}

	public Set<String> getConflicts() {
		return idsConflicteds;
	}

	public String getDni() {
		return dni;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getRating() {
		return rating;
	}

	public Role getRole() {
		return role;
	}

	public enum Role {
		Project_Leader, Architect, Programmer, Tester
	}

	@Override
	public String toString() {
		return "Employee{" + "DNI='" + dni + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + ", rating=" + rating + ", role=" + role + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(dni, other.dni);
	}

}