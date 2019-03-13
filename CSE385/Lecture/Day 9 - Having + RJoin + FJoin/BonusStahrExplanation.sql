DROP TABLE Lab_02

CREATE TABLE Lab_02(
	id	INT PRIMARY KEY IDENTITY
	,first_name	VARCHAR(20)
	,last_name VARCHAR (20)	
	,job_title VARCHAR (50)
	,email	   VARCHAR (50)
	,gender	   VARCHAR (20)
	,salary	   INT
	,address   VARCHAR (50)
	,city	   VARCHAR (20)
	,state     VARCHAR (20)
);



BULK INSERT Lab_02 FROM 'c:\temp\Lab_02_Data.txt'
WITH(
	FIRSTROW =3,
	FIELDTERMINATOR = '\t',
	ROWTERMINATOR = '\n',
	KEEPIDENTITY
);

SELECT * FROM Lab_02

--Return all employees that make $500,000 to $1,000,000, 
--are from one of the following states: NY, PA, NJ, and 
--does not have a .gov email address.

SELECT * FROM Lab_02 
WHERE (salary BETWEEN 500000 AND 1000000)
	AND (STATE = 'NY' OR STATE = 'PA' OR STATE = 'NJ')
	AND (email NOT LIKE '%.gov')

SELECT * FROM Lab_02 
WHERE (salary BETWEEN 500000 AND 1000000)
	AND (STATE IN ('NY', 'PA', 'NJ'))
	AND (email NOT LIKE '%.gov')

--I want to know how many males and 
--how many females are from each state 
--but only if there are 10 or more from that state. 
--(ex. Males NY 5 and Females NY 20 
--would only return the Females row but not the Males)

SELECT state, gender, COUNT(*) AS cnt
FROM Lab_02
GROUP BY STATE, gender
HAVING COUNT(*) > 10 
ORDER BY STATE, gender


--======In this case, we use a WHERE line in pursuit of avoiding countings
--======Unnecessary answers. The other case may be when doing averages.
SELECT state, gender, COUNT(*) AS cnt
FROM Lab_02
WHERE state NOT IN ('AZ', 'CA')
GROUP BY STATE, gender
HAVING COUNT(*) > 10 
ORDER BY STATE, gender

SELECT state, gender, COUNT(*) AS cnt
FROM Lab_02
GROUP BY STATE, gender
HAVING COUNT(*) > 10 AND state NOT IN ('AZ', 'CA')
ORDER BY STATE, gender




