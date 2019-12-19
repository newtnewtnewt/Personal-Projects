CREATE PROCEDURE [dbo].[spGetVendors]
	@pageNum     INT = 0,
	@recordCount INT = -1
AS
	SET NOCOUNT ON
	
	IF(@recordCount = -1) BEGIN
		SELECT VendorName, VendorCity, VendorState, VendorZipCode
		FROM dbo.Vendors
		ORDER BY VendorState, VendorCity
	END ELSE BEGIN
		SELECT VendorName, VendorCity, VendorState, VendorZipCode
		FROM dbo.Vendors
		ORDER BY VendorState, VendorCity
		OFFSET (@pageNum * @recordCount) ROWS
		FETCH NEXT 5 ROWS ONLY
	END
	
