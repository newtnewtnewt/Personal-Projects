CREATE PROCEDURE [dbo].[spDoSomething]
	@num1 INT,
	@num2 INT 
AS

BEGIN TRY
	SELECT [result] = @num1 / @num2;
END TRY BEGIN CATCH
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

