/********************************************
	CSE 385 Lab 06
	4.23.2019
	
	Name: Noah Dunn
	Name: Bryan Hayes

********************************************/


--========================================================================================= QUESTION 1

--========================================================================================= QUESTION 2
BEGIN TRY
	BULK INSERT Customers
	FROM 'c:\temp\Lab_06-Customers.txt'
	WITH (
	FIRSTROW = 2,
	FIELDTERMINATOR = '\t',
	ROWTERMINATOR = '\n',
	KEEPIDENTITY
	)
END TRY BEGIN CATCH
   INSERT INTO Errors VALUES 
   (ERROR_MESSAGE(),
   ERROR_LINE(), 
   ERROR_NUMBER(), 
   ERROR_PROCEDURE(),
   ERROR_SEVERITY(),
   ERROR_STATE())
   
END CATCH
	--========================================================================================= QUESTION 3
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

		--=========================================== ADD
	INSERT INTO Customers(CustFirstName, CustLastName, CustAddress, CustCity, CustState, CustZip, CustPhone, CustFax, CustTotalOrders)
	SELECT  ent.value('@CustFirstName', 'nvarchar(50)'),
			ent.value('@CustLastName', 'nvarchar(50)'),
			ent.value('@CustAddress', 'nvarchar(255)'),
			ent.value('@CustCity',     'nvarchar(50)'),
			ent.value('@CustState', 'nvarchar(20)'),
			ent.value('@CustZip', 'nvarchar(20)'),
			ent.value('@CustPhone', 'nvarchar(30)'),
			ent.value('@CustFax', 'nvarchar(30)'),
			ent.value('@CustTotalOrders', 'int')
	FROM @xml.nodes('/Records/Add_Cust') foo(ent)

	--========================================== DELETE
	DELETE FROM Orders
	WHERE Orders.OrderID IN (
		SELECT ent.value('@OrderID', 'int')
		FROM @xml.nodes('/Records/Delete_Order') foo(ent)
	)
	
	
	--========================================== UPDATE
	UPDATE dbo.OrderDetails
	SET OrderID = tbl.oid, ItemID = tbl.iid, Quantity = tbl.nq
	FROM( 
		SELECT [oid] = ent.value('@OrderID', 'int'),
			   [iid] = ent.value('@ItemID', 'int'),
			   [nq] = ent.value('@NewQuantity', 'int')
		FROM @xml.nodes('/Records/Update_Order') foo(ent)
	) AS tbl
	WHERE tbl.iid = OrderDetails.ItemID AND tbl.oid = OrderDetails.OrderID

		

					
	--========================================================================================= QUESTION 4
	DROP TABLE IF EXISTS Customers_copy
	SELECT * INTO Customers_copy FROM Customers;
		
	--............................................................................
	

	
	--========================================================================================= QUESTION 5
	TRUNCATE TABLE Customers
	--========================================================================================= QUESTION 6
	SET IDENTITY_INSERT Customers ON
	INSERT INTO Customers SELECT * FROM Customers_copy		
	SET IDENTITY_INSERT Customers OFF
	--========================================================================================= QUESTION 7

	DECLARE tblCursor CURSOR FOR (
		SELECT TABLE_NAME
		FROM INFORMATION_SCHEMA.TABLES
		WHERE  TABLE_NAME = 'Customerss'
	)
	
	OPEN tblCursor;
	DECLARE @id INT 
	
	WHILE @@FETCH_STATUS = 0 BEGIN
		UPDATE Customers
		SET CustTotalOrders = CustTotalOrder + 1
		WHERE Customers.CustID = @id
		FETCH NEXT FROM tblCursor INTO @id 
	END
	
	DEALLOCATE tblCursor; 


	
	--========================================================================================= QUESTION 8






	--========================================================================================= QUESTION 9
	SELECT CustID, CustTotalOrders, 
	[CustomerStatus] = CASE WHEN CustTotalOrders > 5 
								THEN 'OVER THE TOP' 
							WHEN CustTotalOrders BETWEEN 3 AND 5 
								THEN 'Solid Customer'
							WHEN CustTotalOrder BETWEEN 1 AND 2 
								THEN 'Noob Customer'
							ELSE
								'Slacker'
						END
GO
