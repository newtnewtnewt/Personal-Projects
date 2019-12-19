/*
     Lab_02
     Date: 2/26/2019
     Lab Partners:  Noah Dunn 
*/

DROP TABLE Lab_02

CREATE TABLE Lab_02 (
	id			INT PRIMARY KEY , 
	first_name	VARCHAR(20),
	last_name	VARCHAR(20),
	job_title	VARCHAR(50),
	email		VARCHAR(50),
	gender		VARCHAR(20),
	salary		INT,
	address		VARCHAR(50),
	city		VARCHAR(20),
	state		VARCHAR(20)
	)

BULK INSERT Lab_02 FROM 'C:\temp\Lab_02_Data.txt'
WITH (FIRSTROW = 3, FIELDTERMINATOR = '\t', ROWTERMINATOR = '\n', KEEPIDENTITY)

SELECT * FROM dbo.Lab_02 WHERE (salary BETWEEN 500000 AND 1000000) AND (state IN ('NY', 'PA', 'NJ')) AND (email NOT LIKE '%.gov')

SELECT COUNT(*) AS 'Count', gender, state FROM Lab_02 GROUP BY gender, state Having (COUNT(*) > 10)  