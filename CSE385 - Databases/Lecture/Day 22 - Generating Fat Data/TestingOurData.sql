SELECT studentId, COUNT(DISTINCT StudentClasses.classID) 
FROM StudentClasses 
GROUP BY StudentClasses.studentId
HAVING COUNT(DISTINCT StudentClasses.classID) > 1