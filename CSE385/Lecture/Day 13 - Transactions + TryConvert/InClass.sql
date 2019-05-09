SELECT TOP(1) 
	InvoiceID,
	TRY_CONVERT(VARCHAR, invoicedate),
	TRY_CONVERT(VARCHAR, invoicedate, 1),
	TRY_CONVERT(VARCHAR, invoicedate, 2),
	TRY_CONVERT(VARCHAR, invoicedate, 107),
	TRY_CONVERT(INT, 'hello'),
	--==ISNULL(TRY_CONVERT(FLOAT, 'hello'), 'Cast Failed'), This doesn't work, we are trying to replace a FLOAT with VARCHAR 'Cast Failed'
	--Take out the TRYs and this sucker will crash
	CASE WHEN TRY_CONVERT(FLOAT, 'hello') IS NULL
		THEN 'Cast Failed'
		ELSE 'Cast Worked'
	END
FROM dbo.Invoices

CREATE TABLE errorLog (
	errorLogId		INT		PRIMARY KEY		IDENTITY,
	error_number	INT,
	error_severity	INT,
	error_state		INT,
	error_procedure	VARCHAR(100),
	error_line		INT,
	error_message	VARCHAR(200),
	param_list		VARCHAR(MAX),
	userName		VARCHAR(200),
	error_date		DATETIME
)
GO 

CREATE PROCEDURE spRecordError
		@param_list		VARCHAR(MAX) = ''
AS
BEGIN
	INSERT INTO errorLog VALUES
	(ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), 
	ERROR_PROCEDURE(), ERROR_LINE(), ERROR_MESSAGE(), 
	@param_list, SUSER_NAME(), GETDATE())
END

CREATE TABLE TermsLogHistory(
	TermsId INT,
	TermsDescription VARCHAR(50),
	TermsDueDays SMALLINT,
	isDeleted BIT,
	updatedBy VARCHAR(50),
	updatedOn DATETIME
)

CREATE TRIGGER termsLogTrigger ON Terms
AFTER INSERT, DELETE
AS 
BEGIN
	INSERT INTO dbo.TermsLogHistory
	SELECT 
		i.TermsID, 
		i.TermsDescription, 
		i.TermsDueDays, 
		i.isDeleted,
	SUSER_NAME(), 
	GETDATE()
	FROM Terms t
		JOIN Inserted i ON t.TermsID = i.TermsID
END

CREATE PROCEDURE spAddUpdateDelete_Term
	@TermsID			INT,
	@TermsDescription	VARCHAR(50),
	@TermsDueDate		SMALLINT,
	@isDeleted			BIT = 0
AS 
BEGIN
	BEGIN TRAN
	BEGIN TRY
		IF(@TermsID = 0) BEGIN
			INSERT INTO dbo.Terms VALUES (@TermsDescription, @TermsDueDays, @isDeleted)
		END ELSE IF EXISTS(SELECT NULL FROM Terms WHERE TermsID = @TermsId) BEGIN
			IF(@isDeleted = 1) BEGIN
				IF EXISTS(SELECT NULL FROM dbo.Invoices WHERE TermsID = @TermsID) OR
				   EXISTS(SELECT NULL FROM dbo.Vendors WHERE DefaultTermsID = @TermsID) BEGIN
						UPDATE Terms SET @isDeleted = 1 WHERE TermsID = @TermsID
			END ELSE BEGIN
				DELETE FROM dbo.Terms WHERE dbo.Terms = @TermsID
			END 
			END ELSE BEGIN
				UPDATE Terms SET TermsDescription = @TermsDescription,
								 TermsDueDays = @TermsDueDate
				WHERE TermsID = @TermsID
			END
		END
	END TRY BEGIN CATCH
		EXEC spRecordError @param_list =  '@TermsID =' + CAST(@TermsID AS VARCHAR(10)) +
										  '@TermsDescription=' + @TermsDescription +
										  '@TermsDueDays=' + CAST(@TermsDueDays AS VARCHAR(10)) +
										  '@isDeleted=' + CAST(@isDeleted AS VARCHAR(2))
		IF(@@TRANCOUNT > 0) ROLLBACK TRAN
	END CATCH
	IF(@@TRANCOUNT > 0) COMMIT TRAN
END

@TermsID			
@TermsDescription	
@TermsDueDate		
@isDeleted			


SELECT * FROM dbo.InvoiceLineItems
BEGIN TRAN
	DELETE FROM InvoiceLineItems
SELECT * FROM dbo.InvoiceLineItems
ROLLBACK TRAN
--COMMIT TRAN
SELECT * FROM dbo.InvoiceLineItems
