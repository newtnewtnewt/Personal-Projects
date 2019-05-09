SELECT TOP(5) 
	v.VendorID, 
	v.VendorName,
	[Invoices] = (
		SELECT i.InvoiceID, 
			   i.InvoiceNumber,
			   i.InvoiceTotal,
			   t.TermsDescription,
			   [Items] = (
						 SELECT ili.InvoiceLineItemAmount,
								ili.InvoiceLineItemDescription
						 FROM dbo.InvoiceLineItems ili
						 WHERE ili.InvoiceID = i.InvoiceID
						 FOR JSON PATH
				)
		FROM Invoices i
			JOIN dbo.Terms t ON i.TermsID = t.TermsID
		FOR JSON PATH
		)
FROM dbo.Vendors v

FOR JSON PATH, ROOT('Vendors')


--===This is how you random entries===--
SELECT TOP(10) * 
FROM dbo.Vendors
ORDER BY NEWID()


--===This is an alternative to UNION or Case When===--
SELECT InvoiceID, 
	   VendorID, 
	   InvoiceNumber,
	   IIF(Balance > 0, 'Outstanding', 'OK') AS Status
FROM dbo.vwInvoices



