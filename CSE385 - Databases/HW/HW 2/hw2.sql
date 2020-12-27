/*
    Homework 1-2
    dunnnm2
    Due: 2-24-2016  11:59 pm
*/
--======================================================================== Q1
CREATE TABLE Employees(
	id INT IDENTITY PRIMARY KEY,
	age INT NOT NULL,
	fName VARCHAR(20) DEFAULT '',
	lName nVARCHAR(20) NOT NULL,
	hired DATE NOT NULL,
	salary float DEFAULT 0.0
	)
--======================================================================== Q2
--==I assume Q2 and Q3 are identical?
BULK INSERT Employees
FROM  'C:\temp\HW_01-2_NewEmployees.txt'
WITH (
	FIRSTROW = 2,
	FIELDTERMINATOR = '\t',
	ROWTERMINATOR = '\n',
	KEEPIDENTITY
	)

--======================================================================== Q3
SELECT TOP(5) WITH TIES VendorName, VendorState FROM Vendors 
ORDER BY VendorState
--======================================================================== Q4
TRUNCATE TABLE Employees
--======================================================================== Q5
SELECT * FROM Vendors 
ORDER BY VendorName
OFFSET 10 ROWS 
FETCH NEXT 10 ROWS ONLY
--======================================================================== Q6
SELECT DISTINCT * FROM GLAccounts WHERE GLAccounts.AccountNo NOT IN (SELECT Vendors.DefaultAccountNo FROM Vendors)
--======================================================================== Q7
SELECT DISTINCT AccountNo, AccountDescription FROM dbo.GLAccounts LEFT JOIN dbo.Vendors ON GLAccounts.AccountNo = Vendors.DefaultAccountNo WHERE Vendors.DefaultAccountNo IS NULL
--======================================================================== Q8
SELECT Iv.InvoiceID, Iv.InvoiceNumber, Ac.AccountDescription, Ac.AccountNo 
FROM dbo.Invoices AS Iv JOIN dbo.Vendors as Ve ON Ve.VendorID = Iv.VendorID
JOIN dbo.GLAccounts as Ac ON Ve.DefaultAccountNo = AC.AccountNo
WHERE Iv.isDeleted = 0 AND (InvoiceTotal - PaymentTotal - CreditTotal) > 0  
ORDER BY InvoiceNumber
--======================================================================== Q9
SELECT VendorName, [InvoiceTotal] = SUM(InvoiceTotal), 
[PaymentTotal] = SUM(PaymentTotal), 
[Balance] = SUM(invoiceTotal - PaymentTotal - CreditTotal),
COUNT(*) As NumOfInvoices 
FROM Vendors, Invoices 
WHERE Vendors.VendorID = Invoices.VendorID AND Invoices.isDeleted <> 1 AND Invoices.CreditTotal = 0 
GROUP BY VendorName
HAVING COUNT(*) > 5


