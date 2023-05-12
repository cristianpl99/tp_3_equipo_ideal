package tp.logic;

import java.util.Set;

public class Employee {
	private int id;
    private String firstName;
    private String lastName;
    private int rating;
    private Set<Double> idsConflicteds;
    private Role role;
    private String photo;

    public Employee(int id, String firstName, String lastName, int rating, Set<Double> conflicts, Role role, String photo) {
    	this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.idsConflicteds = conflicts;
        this.role = role;
        this.photo = photo;
    }
    
    public String getPhoto() {
		return photo;
	}

	public Set<Double> getConflicts() {
		return idsConflicteds;
	}

	public int getId() {
        return id;
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
    	Project_Leader,
        Architect,
        Programmer,
        Tester
    }


    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rating=" + rating +
                ", role=" + role +
                '}';
    }
}
