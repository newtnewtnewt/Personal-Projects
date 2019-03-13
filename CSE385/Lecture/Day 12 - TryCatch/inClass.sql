DROP TABLE tbl2
DROP TABLE tbl1

CREATE TABLE tbl1(
	tbl1_id INT PRIMARY KEY IDENTITY,
	val     VARCHAR(20),
)

INSERT INTO tbl1
VALUES ('a'), ('b'), ('c')

CREATE TABLE tbl2(
	tbl2_id INT PRIMARY KEY IDENTITY,
	tbl1_id INT FOREIGN KEY REFERENCES tbl1(tbl1_id),
	val VARCHAR(20)
)

INSERT INTO tbl2
VALUES (1, 'a'), (2,'b'), (3, 'c')

SELECT *
	FROM tbl1 RIGHT JOIN tbl2 t2
		ON t2.tbl1_id = tbl2_id

DELETE FROM tbl1 WHERE tbl1_id = 1

