SELECT v.VendorName, i.InvoiceNumber, i.InvoiceTotal
FROM Vendors		v
	LEFT JOIN Invoices i
		ON v.VendorID = i.VendorID
		WHERE i.InvoiceID IS NULL

SELECT v.VendorName, i.InvoiceNumber, i.InvoiceTotal
FROM Vendors		v
	 JOIN Invoices i
		ON v.VendorID = i.VendorID

SELECT v.VendorName, i.InvoiceNumber, i.InvoiceTotal
FROM Vendors		v
	 RIGHT JOIN Invoices i
		ON v.VendorID = i.VendorID

SELECT v.VendorName, 
[InvoiceNumber] = ISNULL(i.InvoiceNumber, ''), 
[InvoiceTotal] = ISNULL(i.InvoiceTotal, 0)
FROM Vendors		v
 LEFT JOIN Invoices i
		ON v.VendorID = i.VendorID
		WHERE i.InvoiceID IS NULL

SELECT d.DeptName, d.DeptNo, e.LastName
	FROM Departments		d
		LEFT JOIN Employees		e
			ON d.DeptNo = e.DeptNo
WHERE e.EmployeeID IS NULL
	ORDER BY d.DeptName

SELECT d.DeptName, d.DeptNo, e.LastName
	FROM Departments		d
		RIGHT JOIN Employees		e
			ON d.DeptNo = e.DeptNo
	ORDER BY d.DeptName

SELECT d.DeptName, d.DeptNo, e.LastName
FROM Departments		    d
	FULL JOIN Employees		e
			ON d.DeptNo = e.DeptNo
	ORDER BY d.DeptName