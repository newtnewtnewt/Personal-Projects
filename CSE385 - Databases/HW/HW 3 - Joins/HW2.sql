---===========================Q1
SELECT * 
FROM Vendors JOIN Invoices ON Vendors.VendorID = Invoices.VendorID
---===========================Q2
SELECT Vendors.VendorName, Invoices.InvoiceNumber, Invoices.InvoiceDate, [Balance] = Invoices.InvoiceTotal - (Invoices.PaymentTotal + Invoices.CreditTotal)
FROM Vendors JOIN Invoices ON Vendors.VendorID = Invoices.VendorID
WHERE Invoices.InvoiceTotal - (Invoices.PaymentTotal + Invoices.CreditTotal) > 0
ORDER BY VendorName
---===========================Q3
SELECT Vendors.VendorName, Vendors.DefaultAccountNo, GLAccounts.AccountDescription 
FROM Vendors JOIN GLAccounts ON Vendors.DefaultAccountNo = GLAccounts.AccountNo
ORDER BY AccountDescription, VendorName
---===========================Q4
SELECT Vendors.VendorName, Invoices.InvoiceNumber, Invoices.InvoiceDate, [Balance] = Invoices.InvoiceTotal - (Invoices.PaymentTotal + Invoices.CreditTotal)
FROM Vendors, Invoices
WHERE Vendors.VendorID = Invoices.VendorID AND Invoices.InvoiceTotal - (Invoices.PaymentTotal + Invoices.CreditTotal) > 0
ORDER BY VendorName
---===========================Q5
SELECT VendorName Vendor, InvoiceDate Date, InvoiceNumber Number, InvoiceSequence #, InvoiceLineItemAmount LineItem
FROM Vendors v, Invoices i, InvoiceLineItems li
WHERE v.VendorID = i.VendorID AND i.InvoiceID = li.InvoiceID AND v.DefaultAccountNo = li.AccountNo
ORDER BY Vendor, Date, Number, #
---===========================Q6
SELECT v1.VendorID, v1.VendorName, [Name] = v1.VendorContactFName + ' ' + v1.VendorContactLName
FROM Vendors v1, Vendors v2
WHERE v1.VendorContactFName = v2.VendorContactFName AND v1.VendorID <> v2.VendorID
ORDER BY v1.VendorContactFName + ' ' + v1.VendorContactLName
---===========================Q7
SELECT g.AccountNo, g.AccountDescription 
FROM GLAccounts g LEFT JOIN InvoiceLineItems ON InvoiceLineItems.AccountNo = g.AccountNo
WHERE InvoiceLineItems.AccountNo IS NULL
ORDER BY g.AccountNo 
---===========================Q8
SELECT VendorName,VendorState FROM Vendors 
WHERE VendorState = 'CA'
UNION SELECT VendorName, 'Outside CA' FROM Vendors
WHERE VendorState <> 'CA'
ORDER BY VendorName