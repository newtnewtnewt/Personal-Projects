SELECT TOP(5) 
	VendorID, 
	VendorName,
	--== Sending Data in XML is fine, when it becomes nested like the data below, the XML output needs modified.
	[Invoices] = (
		SELECT * FROM dbo.Invoices
		WHERE VendorID = Vendors.VendorID
		FOR XML PATH('Invoice'), ROOT('Invoices')
	)
FROM dbo.Vendors
FOR XML PATH('Vendor'), ROOT('Vendors')

SELECT TOP(5) 
	VendorID, 
	VendorName,
	[Invoices] = (
		SELECT * FROM dbo.Invoices
		WHERE VendorID = Vendors.VendorID
		FOR JSON PATH
	)
FROM dbo.Vendors
FOR JSON PATH, ROOT('Vendors')

--=== This will work but it will not pull it up because of the number of entries.
SELECT *,
	[Invoices] = (
		SELECT * FROM dbo.Invoices
		WHERE VendorID = Vendors.VendorID
		FOR JSON PATH
	)
FROM dbo.Vendors
FOR JSON PATH, ROOT('Vendors')

SELECT TOP(5) 
	VendorID, 
	VendorName,
	VendorCity,
	VendorState,
	[Invoices] = (
			SELECT *, 
			[Items] = (
							SELECT * 
							FROM InvoiceLineItems 
							WHERE InvoiceID = Invoices.InvoiceID
							FOR JSON PATH
					  )
			FROM dbo.Invoices
			WHERE VendorID = Vendors.VendorID
			FOR JSON PATH
	)
FROM dbo.Vendors
WHERE VendorID IN (SELECT DISTINCT VendorID FROM Invoices)
FOR JSON PATH, ROOT('Vendors')



