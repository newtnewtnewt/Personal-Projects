--EXEC 99, 20

ALTER PROCEDURE [dbo].[spDoSomething]
	@num1 INT,
	@num2 INT 
AS
SET NOCOUNT ON
	BEGIN TRY
		SELECT [result] = @num1 / @num2;
	END TRY BEGIN CATCH
		---EXEC recordError('@num1= ' + CAST(@num1 AS VARCHAR(20)) + '@num2= ' + CAST(@num2 AS VARCHAR(20)))
		---We would remove everything down below if we did this
		SELECT [result] = 0,
		[Parameters] = '@num1= ' + CAST(@num1 AS VARCHAR(20)) + '@num2= ' + CAST(@num2 AS VARCHAR(20)),
		[Line] = ERROR_LINE(),
		[Message] = ERROR_MESSAGE(),
		[Number] =  ERROR_NUMBER(),
		[Procedure] = ERROR_PROCEDURE(),
		[Severity] = ERROR_SEVERITY(),
		[State] = ERROR_STATE(),
		[ErrorDate] = GETDATE()
	END CATCH

	CREATE TABLE ErrorLog (
		errorId		INT PRIMARY KEY IDENTITY,
		errorProc	VARCHAR(100),
		errorNum	INT,
		errorLine	INT,
		errorMsg	VARCHAR(2000),
		errorPrams	VARCHAR(MAX),
		errorSever	INT,
		errorState	INT,
		errorDate	DATETIME DEFAULT(GETDATE()),
		fixedDate	DATETIME DEFAULT(NULL)
	)