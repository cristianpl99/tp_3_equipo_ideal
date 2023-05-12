package tp.logic;

public class Employee {
	private int id;
    private String firstName;
    private String lastName;
    private int rating;
    private Role role;

    public Employee(int id, String firstName, String lastName, int rating, Role role) {
    	this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.role = role;
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
