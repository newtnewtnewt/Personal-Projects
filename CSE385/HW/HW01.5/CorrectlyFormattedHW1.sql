--============================================================================== Q1
SELECT VendorContactFName, VendorContactLName, VendorName
FROM Vendors
ORDER BY VendorContactLName, VendorContactFName

--============================================================================== Q2


SELECT InvoiceNumber AS Number, InvoiceTotal AS Total, PaymentTotal + CreditTotal AS Credits, InvoiceTotal - (PaymentTotal + CreditTotal) AS Balance from Invoices

--============================================================================== Q3

SELECT VendorContactLName + ',' + VendorContactFName AS 'Full Name' 
FROM Vendors 
ORDER BY VendorContactLName, VendorContactFName

--============================================================================== Q4
SELECT InvoiceTotal, 0.1 * InvoiceTotal AS '10%', InvoiceTotal + InvoiceTotal * 0.1 AS 'Plus 10%' 
FROM Invoices 
WHERE InvoiceTotal >= 1000 
ORDER BY InvoiceTotal DESC

--============================================================================== Q5
SELECT InvoiceTotal, 0.1 * InvoiceTotal AS '10%', InvoiceTotal + InvoiceTotal * 0.1 AS 'Plus 10%' 
FROM Invoices 
WHERE InvoiceTotal >= 500 AND InvoiceTotal <= 10000 
ORDER BY InvoiceTotal DESC

--============================================================================== Q6
SELECT VendorContactLName + ',' + VendorContactFName AS 'Full Name' 
FROM Vendors 
WHERE VendorContactLName LIKE '[ABCE]%' 
ORDER BY VendorContactLName, VendorContactFName

--============================================================================== Q7
SELECT PaymentDate from Invoices 
WHERE (PaymentDate IS NOT NULL AND InvoiceTotal - (PaymentTotal + CreditTotal) > 0) OR  (PaymentDate IS NULL AND InvoiceTotal - (PaymentTotal + CreditTotal) = 0)