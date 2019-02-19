SELECT VendorName + '''s invoice balance'
FROM dbo.Vendors

SELECT CAST(InvoiceTotal AS INT) / 10, CAST(InvoiceTotal AS INT) % 10
FROM dbo.Invoices

SELECT LEFT(VendorContactFName, 1) + LEFT(VendorContactLName, 1)
FROM dbo.Vendors
WHERE LEFT(VendorContactLName, 1) = 'M'

SELECT PaymentDate, CONVERT(CHAR(8), PaymentDate,1),
CONVERT(CHAR(8), PaymentDate,2),
'$' +  CONVERT(VARCHAR(100), PaymentTotal, 1),
CONVERT(CHAR(8), GETDATE(),1),
GETDATE(),
CAST(PaymentDate AS DATE),
CAST(PaymentDate AS VARCHAR(19)),
CAST(PaymentDate AS VARCHAR(11))
FROM dbo.Invoices

--CREATE TABLE Users(
--	userId INT PRIMARY KEY IDENTITY,
--	uN     VARCHAR(20) NOT NULL,
--	pw	   VARCHAR(20) NOT NULL, 
--)
--INSERT INTO dbo.Users(uN,pw)
--VALUES ('tom', 'myPW'),
--	   ('jack', 'inTheBox')

--SELECT * FROM dbo.Users

DECLARE @un VARCHAR(20) = 'tom'
DECLARE @pw VARCHAR(20) = 'myPW'



SELECT * FROM dbo.Users
WHERE un = @un AND CAST(pw AS VARBINARY) = CAST(@pw AS VARBINARY)


DROP TABLE Users
CREATE TABLE Users(
	userId INT PRIMARY KEY IDENTITY,
	uN     NVARCHAR(20) NOT NULL,
	pw	   NVARCHAR(20) NOT NULL, 
)
INSERT INTO dbo.Users(uN,pw)
VALUES ('tom', 'myPW'),
	   ('jack', 'inTheBox')

SELECT * FROM dbo.Users

DECLARE @un NVARCHAR(20) = 'tom'
DECLARE @pw NVARCHAR(20) = 'myPW'

SELECT * FROM dbo.Users
WHERE un = @un AND CAST(pw AS VARBINARY) = CAST(@pw AS VARBINARY)
