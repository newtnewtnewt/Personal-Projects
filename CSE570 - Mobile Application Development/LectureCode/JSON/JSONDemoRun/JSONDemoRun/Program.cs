using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace JSONDemo
{
    public class Student
    {
        public string Name { get; set; }
        public int Age { get; set; }

       
        public override string ToString()
        {
            return string.Format("{0} ({1})", Name, Age);
        }
    }
    public class Course
    {
        public List<Student> students;
        public Course()
        {
        }
        public Course(params Student[] S)
        {
            students = new List<Student>(S.Length);
            foreach (Student s in S)
            {
                students.Add(s);
            }
        }
        public override string ToString()
        {
            string result = "[";
            foreach (Student s in students)
            {
                result += "<" + s.ToString() + ">";
            }
            return result + "]";
        }
    }
    class JSONDemo
    {
        public static void Main(string[] args)
        {
            Student student1 = new Student { Name = "Willie", Age = 18 };
            Student student2 = new Student { Name = "Fred", Age = 19 };
            Student student3 = new Student { Name = "Jenni", Age = 19 };

            Course course1 = new Course(student1, student2, student3);

            string student1Str = JsonConvert.SerializeObject(student1);
            Student student1Deserialized = JsonConvert.DeserializeObject<Student>(student1Str);

            string course1Str = JsonConvert.SerializeObject(course1);
            Course course1Deserialized = JsonConvert.DeserializeObject<Course>(course1Str);

            Console.WriteLine("\nStudent 1:");
            Console.WriteLine(student1);
            Console.WriteLine(student1Str);
            Console.WriteLine(student1Deserialized);
            Console.WriteLine("\nCourse 1:");
            Console.WriteLine(course1);
            Console.WriteLine(course1Str);
            Console.WriteLine(course1Deserialized);
            Console.WriteLine("-------------");
            Console.WriteLine("-------------");
            Console.WriteLine("-------------");
            String str = "ABC" + (char)5 + "DEF";
            JsonConvert.SerializeObject(str);


    }
    }
}