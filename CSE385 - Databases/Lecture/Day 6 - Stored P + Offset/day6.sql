USE AP
SELECT * FROM dbo.Users
WHERE un = 'tom' AND CAST(pw AS VARBINARY) = CAST('myPW' AS VARBINARY)

USE AP
SELECT * FROM dbo.Users
WHERE un = 'tom' AND pw COLLATE Latin1_General_CS_AS = 'myPW'

SELECT *, [days] = DATEDIFF(DAY,InvoiceDate,InvoiceDueDate) FROM dbo.Invoices
WHERE DATEDIFF(DAY,InvoiceDate,InvoiceDueDate) > 30 ORDER BY days

SELECT *, [days] = DATEDIFF(DAY,InvoiceDate,InvoiceDueDate) FROM dbo.Invoices
WHERE DATEDIFF(DAY,InvoiceDate,InvoiceDueDate) BETWEEN 30 AND 40 
ORDER BY days

SELECT DISTINCT VendorState, VendorCity
FROM dbo.Vendors
GROUP BY VendorState, VendorCity
ORDER BY VendorState, VendorCity

SELECT VendorState, VendorCity, [numOfVendors] = COUNT(*)
FROM dbo.Vendors
GROUP BY VendorState, VendorCity
ORDER BY VendorState, VendorCity

SELECT VendorState, [numOfVendors] = COUNT(*)
FROM dbo.Vendors
GROUP BY VendorState
ORDER BY VendorState

---This code breaks because variable is not in parenthesis
--DECLARE @cnt INT = 5

--SELECT TOP @cnt * FROM dbo.Vendors
--ORDER BY VendorName


DECLARE @cnt INT = 5
SELECT TOP(@cnt) * FROM dbo.Vendors
ORDER BY VendorName

DECLARE @cnt INT = 5
SELECT TOP(@cnt) VendorID, InvoiceDate 
FROM dbo.Invoices
ORDER BY InvoiceDate

DECLARE @cnt INT = 5
SELECT TOP(@cnt) WITH TIES VendorID, InvoiceDate 
FROM dbo.Invoices
ORDER BY InvoiceDate

SELECT * FROM dbo.Vendors

SELECT VendorID FROM dbo.Invoices
WHERE InvoiceDate = '1/5/2016'

---We want to make a subquery
SELECT * 
FROM dbo.Vendors
WHERE VendorID IN (2, 55, 123)

---Let's try more advanced
SELECT * 
FROM dbo.Vendors
WHERE VendorID IN (
	SELECT VendorID
	FROM dbo.Invoices
	WHERE InvoiceDate = '1/5/2016'
)

SELECT TOP(5) VendorName, VendorCity, VendorState, VendorZipCode
FROM dbo.Vendors
ORDER BY VendorState, VendorCity 
	

SELECT VendorName, VendorCity, VendorState, VendorZipCode
FROM dbo.Vendors
ORDER BY VendorState, VendorCity 
	OFFSET 5 ROWS
	FETCH NEXT 5 ROWS ONLY