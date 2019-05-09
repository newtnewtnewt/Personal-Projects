-- Member Name: Scott Harris
-- Member Name: Jacob Freedman

DROP TABLE IF EXISTS StudentCourses
DROP TABLE IF EXISTS Students
DROP PROCEDURE IF EXISTS sp_GetInstructorInfo


-- =========================================================================== 1
CREATE TABLE Students (
	StudentId		INT		PRIMARY KEY IDENTITY,
	StudentName		VARCHAR(50)		NOT NULL,
	StudentYear		VARCHAR(4)
)

CREATE TABLE StudentCourses (
	StudentId		INT		FOREIGN KEY REFERENCES Students(StudentId),
	CourseId		INT		FOREIGN KEY REFERENCES Courses(CourseId)
)

-- =========================================================================== 2
BULK INSERT Students
FROM 'C:/temp/Students.txt'
WITH(
	FIRSTROW = 2,
	FIELDTERMINATOR = '\t',
	ROWTERMINATOR = '\n',
	KEEPIDENTITY
)

BULK INSERT StudentCourses
FROM 'C:/temp/StudentCourses.txt'
WITH(
	FIRSTROW = 2,
	FIELDTERMINATOR = '\t',
	ROWTERMINATOR = '\n',
	KEEPIDENTITY
)

-- =========================================================================== 3
DECLARE @xml AS XML = '<Process>
							<addStudent name="Cindy Boo" year="2019" />
							<addStudent name="Sammy Sam" year="2018" />

							<addStudentCourse studentId="3" courseId="45" />
							<addStudentCourse studentId="3" courseId="30" />
							<addStudentCourse studentId="3" courseId="96" />

							<dropStudent studentId="411" courseId="119" />
							<dropStudent studentId="696" courseId="33" />

							<updateStudent studentId="5" newName="Tim Tim" year="2017" />
						</Process>'
--Insert New Student
INSERT INTO Students (StudentName, StudentYear)
	SELECT ent.value('@name', 'varchar(50)'), ent.value('@year', 'varchar(4)')
	FROM @xml.nodes('/Process/addStudent') foo(ent)
--Insert New StudentCourse
INSERT INTO StudentCourses (StudentId, CourseId)
	SELECT ent.value('@studentId', 'int'), ent.value('@courseId', 'int')
	FROM @xml.nodes('/Process/addStudentCourse') foo(ent)
--Drop Students
DELETE FROM StudentCourses
	WHERE EXISTS (
		SELECT ent.value('@studentId', 'int'), ent.value('@courseId', 'int')
		FROM @xml.nodes('/Process/dropStudent') foo(ent)
		WHERE ent.value('@studentId', 'int') = StudentId AND ent.value('@courseId', 'int') = CourseId
	)

--Update Student
UPDATE Students
SET
	studentName = tbl.newName,
	studentYear = tbl.year
	FROM(
		SELECT	[studentId] = ent.value('@studentId', 'int'),
				[newName] = ent.value('@newName', 'varchar(50)'),
				[year] = ent.value('@year', 'varchar(4)')
		FROM @xml.nodes('/Process/updateStudent') foo(ent)
	) AS tbl
WHERE Students.studentId = tbl.studentId

-- =========================================================================== 4
GO

CREATE PROCEDURE sp_GetInstructorInfo
	@InstructorID	INT
AS
	SET NOCOUNT ON

	SELECT	
			[InstructorName] = i.Name,
			i.City,
			i.State,
			i.Zip,
			i.Phone,
			[Salary] = CONVERT(INT, i.Salary),
			[Courses] = (
						SELECT	[Name] = c.Name,
								[TAs] = (
										SELECT	ta.Name
										FROM	TAs ta
										WHERE	ta.TAID = cta.TAID
										FOR JSON PATH
										),
								[Students] =	(
												SELECT	s.StudentId,
														s.StudentName,
														s.StudentYear
												FROM	Students s
													JOIN StudentCourses sc ON sc.StudentId = s.StudentId
												WHERE	sc.CourseId = c.CourseID
												FOR JSON PATH
												)
						FROM	CourseInstructors ci
							JOIN Courses c ON ci.CourseID = c.CourseID
							JOIN CourseTAs cta ON cta.CourseID = c.CourseID
						WHERE	ci.InstructorID = i.InstructorID
						FOR JSON PATH
						),
			[Exams] =	(
						SELECT	e.ExamID,
								e.ExamDate,
								[TAName] = ta.Name,
								e.TARate,
								[ExamQuestions] =	(
													SELECT	eq.ExamQuestionID,
															eq.Question,
															eq.Points
													FROM	ExamsQuestions eq
													WHERE	e.ExamID = eq.ExamID
													FOR JSON PATH
													)
						FROM	Exams e
							JOIN TAs ta ON ta.TAID = e.TAID
						WHERE e.InstructorID = i.InstructorID
						FOR JSON PATH
						)
	FROM Instructors i
	WHERE i.InstructorID = @InstructorID OR @InstructorID = -1
	FOR JSON PATH, ROOT('Instructors')

GO

EXEC sp_GetInstructorInfo @instructorID = 13