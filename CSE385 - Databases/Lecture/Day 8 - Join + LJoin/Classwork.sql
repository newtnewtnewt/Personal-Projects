SELECT DISTINCT v1.VendorName, v1.VendorCity, v1.VendorState
FROM dbo.Vendors v1 
	JOIN Vendors v2 ON v1.VendorCity = v2.VendorCity AND
					   v1.VendorState = v2.VendorState AND
					   v1.VendorID <> v2.VendorID
ORDER BY v1.VendorState, v1.VendorCity, v1.VendorName

SELECT DISTINCT VendorName, i.InvoiceNumber, i.Balance FROM dbo.Vendors v 
							JOIN dbo.vwInvoices i
								ON v.VendorID = i.VendorID
WHERE i.Balance > 0
ORDER BY v.VendorName

SELECT DISTINCT VendorName, i.InvoiceNumber, i.Balance FROM dbo.Vendors v, dbo.vwInvoices i						
WHERE i.Balance > 0 AND v.VendorID = i.VendorID
ORDER BY v.VendorName


SELECT DISTINCT VendorName, i.InvoiceNumber, i.Balance FROM dbo.Vendors v, dbo.vwInvoices i
ORDER BY v.VendorName



SELECT DISTINCT VendorName, i.InvoiceNumber, i.InvoiceTotal FROM dbo.Vendors v 
							LEFT JOIN dbo.vwInvoices i
								ON v.VendorID = i.VendorID
ORDER BY v.VendorName
				
SELECT  VendorName, i.InvoiceNumber, i.InvoiceTotal FROM dbo.Vendors v 
							LEFT JOIN dbo.vwInvoices i
								ON v.VendorID = i.VendorID 
								WHERE i.InvoiceNumber IS NULL
ORDER BY v.VendorName				