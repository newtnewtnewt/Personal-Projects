using System;
using System.Reflection;

namespace HW5 {			// Don't change namespace
	public class Reflection {   // Don't change name of class
		/*
		 * Should search the public data members to see if searchString occurs anywhere
		 * in the structure. What this method does for CSE 465 students:
		 *     Search publicly declare string data members for the target string
		 *     Ignores non-string data members
		 *     Returns false if the passed object is null
		 *     Assumes that the object is a user-defined class
		 * 565 student versions must also do the following
		 *     Search publicly declared string Properties
		 *     Search publicly declared objects that are nested within the object
		 * This code will not crash
		 */
		public static bool SearchStructure(Object obj, String searchString) {
			if(obj == null){
				return false;
			}
			Type type = obj.GetType();
			foreach (FieldInfo info in type.GetFields()) {
				if (info.MemberType == MemberTypes.Field && info.FieldType.ToString() == "System.String") {
					if(info.GetValue(obj).ToString().Contains(searchString)){
						return true;
					}
				}
			}
			return false;
		}
		static void Main(string[] args) {
			Console.WriteLine("==== Student ====");
			Student student1 = new Student("John", "Doe", 3.5);
			Student student2 = new Student("Tommy", "John", 3.9);
			Console.WriteLine(SearchStructure(student1, "John"));   // true
			Console.WriteLine(SearchStructure(student2, "John"));   // true
			Console.WriteLine(SearchStructure(student1, "Juan"));   // false

			Console.WriteLine("==== Album ====");
			Album album1 = new Album("Beatles", "Meet the Beetles", "George Martin");
			Album album2 = new Album("The Who", "Who Are You?", "Glyn Johns, John Astley");
			Console.WriteLine(SearchStructure(album1, "Martin"));   // true
			Console.WriteLine(SearchStructure(album1, "Who"));      // false
			Console.WriteLine(SearchStructure(album2, "Martin"));   // false
			Console.WriteLine(SearchStructure(album2, "Who"));      // true

			/* The following tests apply only the CSE 565 students */

			Console.WriteLine("==== Grad #1 ====");
			GradStudentClass1 gs1 = new GradStudentClass1();
			Console.WriteLine(SearchStructure(gs1, "John"));   // true
			Console.WriteLine(SearchStructure(gs1, "Juan"));   // false

			Console.WriteLine("==== Grad #2 ====");
			GradStudentClass2 gs2 = new GradStudentClass2();
			Console.WriteLine(SearchStructure(gs2, "John"));   // false

			Console.WriteLine("==== Grad #3 ====");
			GradStudentClass3 gs3 = new GradStudentClass3(student1, album1);
			Console.WriteLine(SearchStructure(gs3, "John"));   // true
			Console.WriteLine(SearchStructure(gs3, "George")); // true
			Console.WriteLine(SearchStructure(gs3, "Juan"));   // false
		}
	}
	/*
	 * The classes below are meant to be samples to be used for testing. You may
	 * change these. Also, be aware that your code should work with other class
	 * definitions.
	 */
	public class Student {
		public string firstName;
		public string lastName;
		public double GPA;
		public Student(string firstName, string lastName, double GPA) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.GPA = GPA;
		}
	}
	public class Album {
		public string artist;
		public string producer;
		public string name;
		public Album(string artist, string producer, string name) {
			this.artist = artist;
			this.producer = producer;
			this.name = name;
		}
	}

	public class GradStudentClass1 {
		public GradStudentClass1() {

		}
		public string FullName { get { return "John"; } }
	}
	public class GradStudentClass2 {
		public GradStudentClass2() {
		}
		public string FullName { 
			set {

			}
		}
	}
	public class GradStudentClass3 {
		public Student student;
		public Album album;
		public GradStudentClass3(Student student, Album album) {
			this.student = student;
			this.album = album;
		}
	}
}
