--======================================================== Question 1
SELECT TOP(10)  Instructors.Name, Courses.Name
FROM Instructors, CourseInstructors, Courses
WHERE Instructors.InstructorID = CourseInstructors.InstructorID
AND CourseInstructors.CourseID = Courses.CourseID
ORDER BY Instructors.Name, Courses.Name
--======================================================== Question 2
SELECT Instructors.Name
FROM Instructors
WHERE Instructors.InstructorID NOT IN
(SELECT CourseInstructors.InstructorID FROM CourseInstructors)
--======================================================== Question 3
SELECT Instructors.Name 
FROM Instructors LEFT JOIN CourseInstructors
ON Instructors.InstructorID = CourseInstructors.InstructorID
WHERE CourseInstructors.InstructorID IS NULL
--======================================================== Question 4
SELECT Instructors.Name, ExamsQuestions.ExamID, [Total Points] = SUM(ExamsQuestions.Points), [Status] = 'Over'
FROM Instructors, Exams, ExamsQuestions
WHERE Instructors.InstructorID = Exams.InstructorID
AND Exams.ExamID = ExamsQuestions.ExamID
AND Exams.ExamDate BETWEEN '2018-01-01' AND '2018-12-31'
GROUP BY Instructors.Name, ExamsQuestions.ExamID
HAVING SUM(ExamsQuestions.Points) > 100
UNION
SELECT Instructors.Name, ExamsQuestions.ExamID, [Total Points] = SUM(ExamsQuestions.Points), [Status] = 'Under'
FROM Instructors, Exams, ExamsQuestions
WHERE Instructors.InstructorID = Exams.InstructorID
AND Exams.ExamID = ExamsQuestions.ExamID
AND Exams.ExamDate BETWEEN '2018-01-01' AND '2018-12-31'
GROUP BY Instructors.Name, ExamsQuestions.ExamID
HAVING SUM(ExamsQuestions.Points) < 100 AND SUM(ExamsQuestions.Points) > 0
ORDER BY Instructors.Name
--======================================================== Question 5
SELECT Instructors.Name, ExamsQuestions.ExamID, [Total Points] = SUM(ExamsQuestions.Points), [Status] = 
	CASE 
	WHEN SUM(ExamsQuestions.Points) > 100
		THEN 'Over'
	ELSE
			'Under'
	END
FROM Instructors, Exams, ExamsQuestions
WHERE Instructors.InstructorID = Exams.InstructorID
AND Exams.ExamID = ExamsQuestions.ExamID
AND Exams.ExamDate BETWEEN '2018-01-01' AND '2018-12-31'
GROUP BY Instructors.Name, ExamsQuestions.ExamID
HAVING SUM(ExamsQuestions.Points) > 0 AND SUM(ExamsQuestions.Points) <> 100
ORDER BY Instructors.Name
--======================================================== Question 6
SELECT  TAs.TAID, TAs.Name  
FROM CourseTAs JOIN TAs
ON CourseTAs.TAID = TAs.TAID 
GROUP BY TAs.Name, TAs.TAID
HAVING COUNT(*) > 2
ORDER BY TAs.Name
--======================================================== Question 7
SELECT i1.State,[Exam Count] = COUNT(DISTINCT Exams.ExamID) ,[Avg Points] = SUM(ExamsQuestions.Points) / COUNT(*)
FROM Instructors i1, Exams, ExamsQuestions
WHERE i1.InstructorID = Exams.InstructorID
AND Exams.ExamID = ExamsQuestions.ExamID
GROUP BY i1.State
ORDER BY i1.State
--======================================================== Question 8
SELECT * FROM Instructors
ORDER BY Instructors.State, Instructors.Name
OFFSET 40 ROWS 
FETCH NEXT 20 ROWS ONLY
--======================================================== Question 9
SELECT Exams.ExamID, TAs.Name, InstructorTAPayRate.Rate, Exams.TARate
FROM Instructors, Exams, InstructorTAPayRate, TAs
WHERE Instructors.InstructorID = Exams.InstructorID
AND Exams.TAID = TAs.TAID
AND Instructors.InstructorID = InstructorTAPayRate.InstructorID
AND InstructorTAPayRate.TAID = TAs.TAID
AND InstructorTAPayRate.Rate <> Exams.TARate
ORDER BY TAs.Name
--======================================================== Question 10
SELECT Instructors.Name, [Name] = 
CASE WHEN TAs.Name IS NULL
	THEN 'TA NEEDED'
	ELSE
		TAs.Name
END
FROM Instructors JOIN CourseInstructors
ON Instructors.InstructorID = CourseInstructors.InstructorID
LEFT JOIN Courses ON CourseInstructors.CourseID = Courses.CourseID
LEFT JOIN CourseTAs ON Courses.CourseID = CourseTAs.CourseID 
LEFT JOIN TAs ON CourseTAs.TAID = TAs.TAID
--======================================================== Question 11
SELECT DISTINCT i1.Name, TAs.Name, Exams.ExamID
FROM Exams JOIN Instructors i1
ON Exams.InstructorID = i1.InstructorID
JOIN TAs ON Exams.TAID = TAs.TAID
WHERE Exams.TAID NOT IN (SELECT TAs.TAID FROM
Instructors i2
	JOIN CourseInstructors ON i2.InstructorID = CourseInstructors.InstructorID 
	JOIN Courses ON Courses.CourseID = CourseInstructors.CourseID
	JOIN CourseTAs ON CourseInstructors.CourseID = CourseTAs.CourseID
	JOIN TAs ON CourseTAs.TAID = TAs.TAID
	WHERE i1.InstructorID = i2.InstructorID
)
ORDER BY TAs.Name






