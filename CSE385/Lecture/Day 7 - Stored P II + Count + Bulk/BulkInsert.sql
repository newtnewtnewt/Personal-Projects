--USE AP
--CREATE TABLE Rates(
--	ID	 INT PRIMARY KEY IDENTITY,	
--	year INT,
--	rate FLOAT,
--)
--DROP TABLE dbo.Rates


TRUNCATE TABLE RATES

BULK INSERT Rates
FROM 'c:\temp\US_Personal_Savings_Rates_1960-2017.txt'
WITH (
	FIRSTROW = 2,
	FIELDTERMINATOR = '\t',
	ROWTERMINATOR = '\n',
	KEEPIDENTITY
	)

---delete FROM RATES

SELECT * FROM Rates

SELECT Vendors.VendorID, 
	   VendorName,
	   COUNT(*) AS InvoiceCount,
	   [Balance] = SUM(invoiceTotal - PaymentTotal - CreditTotal)
FROM dbo.Vendors
	JOIN dbo.Invoices
		ON Invoices.VendorID = Vendors.VendorID
WHERE (InvoiceTotal - PaymentTotal - CreditTotal) > 0
GROUP BY Vendors.VendorID, VendorName
HAVING COUNT(*) > 1 
ORDER BY VendorName

SELECT v.VendorID, 
	   VendorName,
	   COUNT(*) AS InvoiceCount,
	   [Balance] = SUM(invoiceTotal - PaymentTotal - CreditTotal)
FROM dbo.Vendors AS v
	JOIN dbo.Invoices AS i
		ON i.VendorID = v.VendorID
WHERE (InvoiceTotal - PaymentTotal - CreditTotal) > 0
GROUP BY v.VendorID, VendorName
HAVING COUNT(*) > 1 
ORDER BY VendorName

SELECT DISTINCT v1.VendorName, v1.VendorCity, v1.VendorState FROM dbo.Vendors v1 
		JOIN dbo.Vendors v2
			ON (v2.VendorCity = v1.VendorCity) AND
			   (v2.VendorState = v1.VendorState) AND
			   (v2.VendorID <> v1.VendorID)
			ORDER BY v1.VendorCity, v1.VendorState, v1.VendorName



