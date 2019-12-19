CREATE DATABASE dunnnm2_DB
GO
USE dunnnm2_DB
CREATE TABLE tblCars(
	carId INT PRIMARY KEY IDENTITY,	
	make VARCHAR(20) NOT NULL,
	model VARCHAR(20) NOT NULL,
	year INT NOT NULL
);
SET IDENTITY_INSERT tblCars ON
INSERT INTO tblCars(carId,make,model,year)
VALUES (11, 'Dodge', 'Intrepid', 1993),
	   (35, 'Chevy','Cavilier', 2001),
	   (61, 'Hyundai', 'Sonata', 2013)
SELECT * FROM tblCars
