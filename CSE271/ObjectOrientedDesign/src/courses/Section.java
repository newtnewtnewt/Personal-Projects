package courses;

import java.util.ArrayList;

public class Section {
	private char sectionLetter;
	private Course course;
	private Instructor instructor;
	private ArrayList<Student> students;
	private Location location;
	
	public Section(char sectionLetter, Course course, Instructor instructor, ArrayList<Student> students, Location location) {
		this.setSectionLetter(sectionLetter);
		this.setCourse(course);
		this.setInstructor(instructor);
		this.setStudents(students);
		this.setLocation(location);
	}

	/**
	 * @return the sectionLetter
	 */
	public char getSectionLetter() {
		return sectionLetter;
	}

	/**
	 * @param sectionLetter the sectionLetter to set
	 */
	public void setSectionLetter(char sectionLetter) {
		this.sectionLetter = sectionLetter;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return the instructor
	 */
	public Instructor getInstructor() {
		return instructor;
	}

	/**
	 * @param instructor the instructor to set
	 */
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	/**
	 * @return the students
	 */
	public ArrayList<Student> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
}
