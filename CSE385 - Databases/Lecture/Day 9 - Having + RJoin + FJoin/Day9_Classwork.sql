SELECT v.VendorID, v.VendorName , [InvoiceCount]  = COUNT(*), [Balance] = SUM(i.Balance)
FROM Vendors		v
	JOIN vwInvoices i
		ON v.VendorID = i.VendorID
GROUP BY v.VendorID, v.VendorName
HAVING SUM(i.Balance) > 200 AND COUNT(*) < 5
ORDER BY v.VendorName