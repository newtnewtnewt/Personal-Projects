CREATE PROCEDURE [dbo].[JSonSP]
	@InstructorID INT = -1
AS
SELECT TOP(3) 
		Instructors.InstuctorID, 
		Instructors.City,
		Instructors.State,
		Instructors.Zip,
		Instructors.Phone,
		[Salary] = CAST(Instructors.Salary AS INT),
		[Courses] = (
			SELECT Courses.Name
			FROM dbo.Instructors
			JOIN CoursesInstuctors ON Instructors.InstructorId = CourseInstructors.InstructorID
			JOIN Courses ON Courses.CourseID = CourseInstructors.CourseID
			WHERE Instructors.InstructorID = -1 OR Instructors.InstructorID = @InstructorID
		)

		--== Sending Data in XML is fine, when it becomes nested like the data below, the XML output needs modified.
FROM dbo.Instuctors
WHERE @InstructorId = Instructors.InstructorID
FOR JSON PATH('Instructors'), ROOT('Instructors')

RETURN 0
