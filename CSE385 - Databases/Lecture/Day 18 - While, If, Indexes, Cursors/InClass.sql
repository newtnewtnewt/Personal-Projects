CREATE INDEX idx_VendorContactLName ON dbo.Vendors(VendorContactLName)

CREATE INDEX idx_VendorContactLNameFname ON dbo.Vendors(VendorContactLName, VendorContactFName)

ALTER INDEX ALL ON dbo.Vendors REORGANIZE

DECLARE @tblName VARCHAR(255)

DECLARE tblCursor CURSOR FOR (
	SELECT TABLE_NAME
	FROM INFORMATION_SCHEMA.TABLES
	WHERE  TABLE_TYPE = 'BASE TABLE'
)

OPEN tblCursor;

FETCH NEXT FROM tblCursor INTO @tblName

WHILE @@FETCH_STATUS = 0 BEGIN
	PRINT 'Indexing table: ' + @tblName;
	EXEC ('ALTER INDEX ALL ON ' + @tblName +   ' REORGANIZE')
	FETCH NEXT FROM tblCursor INTO @tblName
END

CLOSE tblCursor;      --This one might error
DEALLOCATE tblCursor; --Due this to avoid big boy memory problems

WHILE(SELECT SUM(Balance) FROM dbo.vwInvoices) >= 2000 BEGIN
	UPDATE dbo.vwInvoices SET CreditTotal = CreditTotal + .05
	WHERE Balance > 0

	IF(SELECT MAX(CreditTotal) FROM dbo.vwInvoices) > 3000
		BREAK;
	ELSE
		CONTINUE
	PRINT 'Hello'
END

PRINT 'World'

