SELECT VendorID, Balance  
FROM dbo.vwInvoices

--Don't Grab Dupes
SELECT [Status] = 'Paid in full', VendorID, Balance 
FROM dbo.vwInvoices 
WHERE Balance = 0
UNION
SELECT [Status] = 'Outstanding Balance', VendorID, Balance 
FROM dbo.vwInvoices 
WHERE BALANCE > 0

--Grab Dupes
SELECT [Status] = 'Paid in full', VendorID, Balance 
FROM dbo.vwInvoices 
WHERE Balance = 0
UNION ALL
SELECT [Status] = 'Outstanding Balance', VendorID, Balance 
FROM dbo.vwInvoices 
WHERE BALANCE > 0


--This grabs dupes
SELECT [Status] = CASE WHEN BALANCE = 0 THEN 'Paid in Full'
					  WHEN BALANCE > 0 THEN 'Outstanding Balance'
					  ELSE					'Refund'
			      END
	   ,VendorID
	   ,Balance
FROM dbo.vwInvoices 

--This doesn't grab dupes
SELECT DISTINCT [Status] = CASE WHEN BALANCE = 0 THEN 'Paid in Full'
					  WHEN BALANCE > 0 THEN 'Outstanding Balance'
					  ELSE					'Refund'
			      END
	   ,VendorID
	   ,Balance
FROM dbo.vwInvoices 

WITH tbl AS(
	SELECT [Status] = 'Paid in full', VendorID, Balance 
	FROM dbo.vwInvoices 
	WHERE Balance = 0
	UNION ALL
	SELECT [Status] = 'Outstanding Balance', VendorID, Balance 
	FROM dbo.vwInvoices 
	WHERE BALANCE > 0
	)SELECT VendorID FROM tbl 
	WHERE tbl.Balance > 200

---We can also use Joins with these in the standard format

SELECT COUNT(DISTINCT PaymentDate) AS numOfVendors, --We do this to count the number of entries on one invoice in a single day
	   COUNT(*) AS numOfRecords,
	   AVG(InvoiceTotal) AS invoiceAverage,
	   SUM(InvoiceTotal) AS invoiceSum
FROM Invoices
WHERE InvoiceDate > '2015-09-01'

--Remember, COUNT(*) counts the actual number of rows
--			COUNT(xyz) counts the non-null entries


SELECT VendorID, 
	   MAX(InvoiceDate) AS LatestInvoice, --Latest Invoice
	   AVG(InvoiceTotal) AS AverageInvoice
FROM dbo.Invoices
GROUP BY VendorID
ORDER BY LatestInvoice DESC

SELECT TOP(5) 
	VendorID,
	AVG(InvoiceTotal) AS AverageInvoice
FROM dbo.Invoices
GROUP BY VendorID
ORDER BY AverageInvoice DESC

SELECT Invoices.VendorID,
	   MAX(InvoiceDate) AS LatestInvoice, --Latest Invoice
	   AVG(InvoiceTotal) AS AverageInvoice
FROM dbo.Invoices
     JOIN (SELECT TOP(5) 
		VendorID,
		AVG(InvoiceTotal) AS AverageInvoice
		FROM dbo.Invoices
		GROUP BY VendorID
		ORDER BY AverageInvoice DESC) AS TopVendors 
		ON TopVendors.VendorID = Invoices.VendorID
GROUP BY Invoices.VendorID
ORDER BY AverageInvoice DESC, LatestInvoice ASC 
	
		


