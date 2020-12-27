SELECT *, [Balance] = InvoiceTotal - CreditTotal - PaymentTotal
FROM dbo.Invoices
WHERE InvoiceTotal - CreditTotal - PaymentTotal > 0

