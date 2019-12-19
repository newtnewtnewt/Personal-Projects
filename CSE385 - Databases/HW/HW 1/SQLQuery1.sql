USE TestBase
CREATE TABLE Vendors(
		VenderContactFName VARCHAR(20) NOT NULL,
		VenderContactLName VARCHAR(20) NOT NULL, 
		VendorName VARCHAR(20) NOT NULL
);

INSERT INTO Vendors(VenderContactFName, VenderContactLName, VendorName)
	VALUES('Newt', 'Boot', 'Loot'), ('Oot', 'Soot', 'Doot'),
	('X', 'Y', 'Loot'), ('A', 'B', 'Cru-te'), ('X', 'A', 'Loot'),
	('Alpha', 'Newt', 'Loot'),('Newt', 'Alpha', 'Loot'),('Alpha', 'Boot', 'Loot')

SELECT VenderContactFName, VenderContactLName, VendorName from Vendors ORDER BY VenderContactLName,VenderContactFName

	
CREATE TABLE Invoices(
	InvoiceNumber INT NOT NULL,
	InvoiceTotal  FLOAT NOT NULL,
	PaymentTotal FLOAT NOT NULL,
	CreditTotal FLOAT NOT NULL, 
);

INSERT INTO Invoices(InvoiceNumber, InvoiceTotal, PaymentTotal, CreditTotal)
	VALUES(1 ,2 ,3 ,4),(5 ,6 ,7 ,8)

SELECT InvoiceNumber AS Number, InvoiceTotal AS Total, PaymentTotal + CreditTotal AS Credits, InvoiceTotal - (PaymentTotal + CreditTotal) AS Balance from Invoices

SELECT VenderContactLName + ',' + VenderContactFName AS 'Full Name' FROM Vendors ORDER BY VenderContactLName, VenderContactFName

INSERT INTO Invoices(InvoiceNumber, InvoiceTotal, PaymentTotal, CreditTotal)
	VALUES(6 ,10000 ,3 ,4),(7 ,20000 ,7 ,8)

SELECT InvoiceTotal, 0.1 * InvoiceTotal AS '10%', InvoiceTotal + InvoiceTotal * 0.1 AS 'Plus 10%' FROM Invoices WHERE InvoiceTotal >= 500 AND InvoiceTotal <= 10000 ORDER BY InvoiceTotal DESC

SELECT VenderContactLName + ',' + VenderContactFName AS 'Full Name' FROM Vendors WHERE VenderContactLName LIKE '[ABCE]%' ORDER BY VenderContactLName, VenderContactFName

ALTER TABLE Invoices ADD PaymentDate VARCHAR(20)

SELECT * FROM Invoices

INSERT INTO Invoices(InvoiceNumber, InvoiceTotal, PaymentTotal, CreditTotal, PaymentDate)
	VALUES(10 ,10 ,5 ,5, NULL),(11 ,20 ,1 ,1, 'Six of June, 1944')

SELECT PaymentDate from Invoices WHERE (PaymentDate IS NOT NULL AND InvoiceTotal - (PaymentTotal + CreditTotal) > 0) OR  (PaymentDate IS NULL AND InvoiceTotal - (PaymentTotal + CreditTotal) = 0)

