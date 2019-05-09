CREATE TABLE errorLog (
	errorLogId			INT		PRIMARY KEY		IDENTITY,
	error_number		INT,
	error_severity		INT,
	error_state			INT,
	error_procedure		VARCHAR(100),
	error_line			INT,
	error_message		VARCHAR(200),
	param_list			VARCHAR(MAX),
	userName			VARCHAR(200),
	error_date			DATETIME
)
GO

CREATE PROCEDURE spRecordError
	@param_list	VARCHAR(MAX) = ''
AS
BEGIN
	INSERT INTO errorLog VALUES (
		ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(),
		ERROR_LINE(), ERROR_MESSAGE(), @param_list, SUSER_NAME(), GETDATE()
	)
END
GO

CREATE TABLE TermsLogHistory (
	TermsID				INT,
	TermsDescription	VARCHAR(50),
	TermsDueDays		SMALLINT,
	isDeleted			BIT,
	updatedBy			VARCHAR(50),
	updatedOn			DATETIME
)
GO

CREATE TRIGGER termsLogTrigger ON dbo.Terms
AFTER INSERT, UPDATE
AS
BEGIN
	INSERT INTO TermsLogHistory
	SELECT 	 i.TermsID			
			,i.TermsDescription
			,i.TermsDueDays	
			,i.isDeleted		
			,SUSER_NAME()		
			,GETDATE()		
	FROM Inserted i 
		JOIN Terms t ON t.TermsID = i.TermsID
END
GO

CREATE PROCEDURE spAddUpdateDelete_Terms
	@TermsId			INT,
	@TermsDescription	VARCHAR(50),
	@TermsDueDays		SMALLINT,
	@isDeleted			BIT	= 0
AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		IF(@TermsID = 0) BEGIN
			IF NOT EXISTS(SELECT NULL FROM Terms WHERE TermsDueDays = @TermsDueDays)
				INSERT INTO dbo.Terms VALUES (@TermsDescription, @TermsDueDays, @isDeleted)
		END ELSE IF EXISTS (SELECT NULL FROM Terms WHERE TermsID = @TermsId) BEGIN
			IF(@isDeleted = 1) BEGIN
				IF EXISTS (SELECT NULL FROM dbo.Invoices WHERE TermsID = @TermsId) OR
				   EXISTS (SELECT NULL FROM dbo.Vendors WHERE DefaultTermsID = @TermsId) BEGIN
						-- Just soft delete
						UPDATE Terms SET isDeleted = 1 WHERE TermsID = @TermsId
				END ELSE BEGIN
						-- hard delete
						DELETE dbo.Terms WHERE TermsID = @TermsId
				END	
			END ELSE BEGIN
				UPDATE Terms SET	TermsDescription = @TermsDescription,
									TermsDueDays = @TermsDueDays,
									isDeleted = 0
				WHERE TermsID = @TermsId
			END
		END
	END TRY BEGIN CATCH
		IF(@@TRANCOUNT > 0) ROLLBACK TRAN
		DECLARE @params VARCHAR(MAX);
		SELECT @params =  '@TermsId=' + CAST(@TermsId AS VARCHAR(10)) +
						  '@TermsDescription=' + @TermsDescription +
						  '@TermsDueDays=' + CAST(@TermsDueDays AS VARCHAR(10)) +
						  '@isDeleted=' + CAST(@isDeleted AS VARCHAR(2))
		EXEC spRecordError @params
	END CATCH
	IF(@@TRANCOUNT > 0) COMMIT TRAN
END
GO

