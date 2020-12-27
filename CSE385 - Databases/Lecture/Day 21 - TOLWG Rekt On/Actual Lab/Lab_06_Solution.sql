ALTER PROCEDURE spLab_06
AS
BEGIN
	
	SET NOCOUNT ON;

	--========================================================================================= QUESTION 1
	--(20 pts)	Write the code that will import the Lab_06-Customers.txt file into the Customers table. 
	--			Include error checking. If an error occurs save it to an errors table.
		BEGIN TRY
			BULK INSERT Customers
			FROM 'C:\temp\Lab_06-Customers.txt'
			WITH (
				FIRSTROW = 2,
				FIELDTERMINATOR = '\t',
				ROWTERMINATOR = '\n',
				TABLOCK
			)
		END TRY BEGIN CATCH
			INSERT INTO Errors 
			SELECT	ERROR_MESSAGE(), ERROR_NUMBER(), ERROR_LINE(), ERROR_PROCEDURE(), ERROR_SEVERITY(), ERROR_STATE()
		END CATCH;

	--========================================================================================= QUESTION 2
	-- (20 pts)	Write the code that will process the XML variable.  Do not worry about duplicates 
	--			when you are adding customers
		DECLARE @xml AS XML =	'<Records>
									<Add_Cust 
										CustFirstName = "Janet"
										CustLastName = "Ricker"
										CustAddress = "2 Continental Dr."
										CustCity = "Carmel"
										CustState = "NY"
										CustZip = "10512"
										CustPhone = "9142254567"
										CustFax = "9142254568"
										CustTotalOrders = "0" />

									<Add_Cust 
										CustFirstName = "Tom"
										CustLastName = "Ryan"
										CustAddress = "54 Main Street"
										CustCity = "Boston"
										CustState = "MA"
										CustZip = "02129"
										CustPhone = "5557778888"
										CustFax = "5557775656"
										CustTotalOrders = "3" />

									<Update_Order	
										OrderID = "29"
										ItemID = "10"
										NewQuantity = "3" />

									<Delete_Order
										OrderID = "824" />
								 </Records>'
		-- ADD
		INSERT INTO Customers(	[CustFirstName],[CustLastName], [CustAddress], [CustCity],
							[CustState], [CustZip], [CustPhone],[CustFax],[CustTotalOrders])
		SELECT	 ent.value('@CustFirstName',		'nvarchar(50)')	
				,ent.value('@CustLastName', 		'nvarchar(50)')	
				,ent.value('@CustAddress', 		'nvarchar(255)')	 
				,ent.value('@CustCity', 		'nvarchar(50)')	 
				,ent.value('@CustState', 		'nvarchar(20)')	
				,ent.value('@CustZip', 			'nvarchar(20)')		 
				,ent.value('@CustPhone', 		'nvarchar(30)')	 
				,ent.value('@CustFax', 			'nvarchar(30)')		 
				,ent.value('@CustTotalOrders', 	'int')		 
		FROM @xml.nodes('/Records/Add_Cust') foo(ent)
			 
		
		-- UPDATE
		UPDATE OrderDetails
		SET Quantity = tbl.[NewQuantity]
			FROM ( 
					SELECT	ent.value('@OrderID','int')			[OrderID],  
							ent.value('@ItemID', 'int')			[ItemID],  
							ent.value('@NewQuantity', 'int')	[NewQuantity]  
					FROM @xml.nodes('/Records/Update_Order') foo(ent)
			) tbl 
		WHERE OrderDetails.[OrderID] = tbl.[OrderID] AND OrderDetails.[ItemID] = tbl.[ItemID]

		-- DELETE
		DELETE FROM OrderDetails 
		WHERE OrderID = (
						SELECT ent.value('@OrderID', 'int') AS id  
						FROM @xml.nodes('/Records/Delete_Order') foo(ent)
					  ) 

					
		DELETE FROM Orders 
		WHERE OrderID = (
						SELECT ent.value('@OrderID', 'int') AS id  
						FROM @xml.nodes('/Records/Delete_Order') foo(ent)
					  )		
					
	--========================================================================================= QUESTION 3
	-- (5 pts)	Create a complete copy of the Customer's table called Customers_copy but start 
	--			your answer with the following given code that tries to drop the Customers_copy 
	--			table first. This can be found in chapter 7 of your book – pages 216-217.				        
		DROP TABLE IF EXISTS Customers_copy
	--...............................................
		SELECT * INTO  Customers_copy FROM Customers

	--========================================================================================= QUESTION 4
	-- (10 pts)	Now, remove ALL of the data from the Customer's table so that the CustID field 
	--			gets reset back to 1. If you don't know how to do this then you can write the 
	--			code that just simply deletes all the data from the table
		TRUNCATE TABLE Customers

	--========================================================================================= QUESTION 5
	-- (10 pts)	Write the code that will copy all the data from the Customers_copy table back to 
	--			the Customers table - and make sure the CustID field value is back to the original value
		SET IDENTITY_INSERT Customers ON
			INSERT INTO Customers	(		
										[CustID],[CustFirstName],[CustLastName],
										[CustAddress],[CustCity],[CustState],[CustZip],
										[CustPhone],[CustFax],[CustTotalOrders]
									) 
			SELECT	*	FROM Customers_copy
		SET IDENTITY_INSERT Customers OFF

	--========================================================================================= QUESTION 6
	-- (15 pts)	Using a Cursor update the Customers CustTotalOrders table with the total number 
	--			of orders each customer has. Also, do not forget to clear out the cursor from 
	--			memory after you are done...
	DECLARE @CustID int, @OrderCount int
	DECLARE OrderCountCursor CURSOR 
	LOCAL FAST_FORWARD
		FOR 
			SELECT CustID, COUNT(*)
			FROM Orders
			GROUP BY CustID
	OPEN OrderCountCursor
	FETCH NEXT FROM OrderCountCursor INTO @CustID, @OrderCount
	WHILE @@FETCH_STATUS = 0 BEGIN 
		UPDATE Customers SET CustTotalOrders = @OrderCount WHERE CustID = @CustID
		FETCH NEXT FROM OrderCountCursor INTO @CustID, @OrderCount 
	END
	CLOSE OrderCountCursor 
	DEALLOCATE OrderCountCursor
	
	--========================================================================================= QUESTION 7
	-- (10 pts)	Write the code that deletes all the records from all 6 tables and then undoes the delete.
	BEGIN TRAN
		TRUNCATE TABLE Customers
		TRUNCATE TABLE Customers_copy
		TRUNCATE TABLE Errors
		TRUNCATE TABLE Items
		TRUNCATE TABLE OrderDetails
		TRUNCATE TABLE Orders
	ROLLBACK TRAN

	--========================================================================================= QUESTION 8
	-- (10 pts)	Create a SELECT statement that will return the following data:
		
	-- CustID, CustTotalOrders, and CustomerStatus 
	-- 
	-- WHERE CustomerStatus is a new field you create on the fly. The output will be one of the
	-- following: 
	-- 		CustTotalOrders greater than 5:	OVER THE TOP
	-- 		CustTotalOrders between 3 and 5:	Solid Customer
	-- 		CustTotalOrders between 1 and 2:	Noob Customer
	-- 		CustTotalOrders with no orders:	Slacker
	-- 
	-- So, if you run this question’s code the first 8 rows will be:
	-- 		17	6	OVER THE TOP
	-- 		2	5	Solid Customer
	-- 		9	4	Solid Customer
	-- 		27	3	Solid Customer
	-- 		19	3	Solid Customer
	-- 		20	3	Solid Customer
	-- 		3	2	Noob Customer
	-- 		12	2	Noob Customer


	SELECT 	 [CustID]
			,[CustTotalOrders]
			,CASE
				WHEN  [CustTotalOrders] > 5 THEN 'OVER THE TOP'
				WHEN  [CustTotalOrders] BETWEEN 3 AND 5 THEN 'Solid Customer'
				WHEN  [CustTotalOrders] BETWEEN 1 AND 2 THEN 'Noob Customer'
				ELSE  'Slacker'
			END AS CustomerStatus
	FROM [ProductOrders].[dbo].[Customers]
	ORDER BY [CustTotalOrders] DESC
END
GO