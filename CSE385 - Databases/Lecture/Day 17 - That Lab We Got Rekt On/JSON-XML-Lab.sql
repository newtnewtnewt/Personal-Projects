---==== FOR NOAH DUNN AND BRYAN HAYES

DROP TABLE IF EXISTS StudentCourses, Students

CREATE TABLE Students(
	StudentId INT IDENTITY PRIMARY KEY,
	StudentName VARCHAR(50),
	StudentYear VARCHAR(4)
)
CREATE TABLE StudentCourses(
	StudentId INT IDENTITY FOREIGN KEY REFERENCES Students(StudentID),
	CourseID INT FOREIGN KEY REFERENCES Courses(CourseID)
)
BULK INSERT Students
FROM 'c:\temp\Students.txt'
WITH (
	FIRSTROW = 2,
	FIELDTERMINATOR = '\t',
	ROWTERMINATOR = '\n'
	)


BULK INSERT StudentCourses
FROM 'c:\temp\StudentCourses.txt'
WITH (
	FIRSTROW = 2,
	FIELDTERMINATOR = '\t',
	ROWTERMINATOR = '\n'
	)

DECLARE @xml AS XML
SET @xml =			  '<Process>
							<addStudent name="Cindy Boo" year="2019" />
								<addStudent name="Sammy Sam" year="2018" />

								<addStudentCourse studentId="3" courseId="45" />
								<addStudentCourse studentId="3" courseId="30" />
								<addStudentCourse studentId="3" courseId="96" />

								<dropStudent studentId="411" courseId="119" />
							<dropStudent studentId="696" courseId="33" />

						<updateStudent studentId="5" newName="Tim Tim" year="2017" />
					</Process>'


INSERT INTO Students(StudentName, StudentYear)(
SELECT  ent.value('@name', 'varchar(50)'),
		ent.value('@year', 'varchar(4)')
FROM @xml.nodes('/Process/addStudent') foo(ent)
)

SET IDENTITY_INSERT StudentCourses ON 
INSERT INTO StudentCourses(StudentId, CourseID)(
SELECT  ent.value('@studentId', 'int'),
		ent.value('@courseId', 'int')
FROM @xml.nodes('/Process/addStudentCourse') foo(ent)
)
SET IDENTITY_INSERT StudentCourses OFF

DELETE FROM StudentCourses
WHERE studentId IN (
	SELECT  studentid = ent.value('@studentId', 'int')
	FROM @xml.nodes('/Process/dropStudent') foo(ent)
)

UPDATE Students
SET Students.StudentName = tbl.name,
	Students.StudentYear = tbl.year
	FROM( 
		SELECT [sid] = ent.value('@studentId', 'int'),
			   [name] = ent.value('@name', 'varchar(50)'),
			   [year] = ent.value('@year', 'varchar(4)')
		FROM @xml.nodes('/Process/updateStudent') foo(ent)
	) AS tbl
	WHERE tbl.sid = Students.StudentId


﻿SELECT TOP(5) 
	VendorID, 
	VendorName,
	--== Sending Data in XML is fine, when it becomes nested like the data below, the XML output needs modified.
	[Invoices] = (
		SELECT * FROM dbo.Invoices
		WHERE VendorID = Vendors.VendorID
		FOR XML PATH('Invoice'), ROOT('Invoices')
	)
FROM dbo.Vendors
FOR XML PATH('Vendor'), ROOT('Vendors')






