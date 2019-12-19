package courses;

import java.time.LocalDate;

public class Instructor extends Person {
	private String title;
	
	public Instructor(String firstName, String lastName, LocalDate dateOfBirth, String title) {
		super(firstName, lastName, dateOfBirth);
		// TODO Auto-generated constructor stub
		this.setTitle(title);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
}
