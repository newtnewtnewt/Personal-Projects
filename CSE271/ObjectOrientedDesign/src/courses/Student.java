package courses;

import java.time.LocalDate;

public class Student extends Person {
	private double GPA;

	/**
	 * @return the gPA
	 */
	public double getGPA() {
		return GPA;
	}

	/**
	 * @param gPA the gPA to set
	 */
	public void setGPA(double gPA) {
		GPA = gPA;
	}
	
	public Student(String firstName, String lastName, LocalDate dateOfBirth, double GPA) {
		super(firstName, lastName, dateOfBirth);
		this.GPA=GPA;
	}
}
