TRUNCATE TABLE dbo.Guests
INSERT INTO dbo.Guests VALUES(4, 'Tommy')
INSERT INTO dbo.Guests VALUES(4, 'Jack')
DECLARE @xml AS XML	=	
					   '<Guests>
							<guest inviteeID="2" name = "Mike" />
							<guest inviteeID="2" name = "Don" />
							<guest inviteeID="2" name = "Lucille" />
							<delete guestId = "2" />
							<update guestId = "1" name = "Tom" />
					   </Guests>'


	--=========================================== ADD
	INSERT INTO Guests(inviteeID, guestName)
	SELECT  ent.value('@inviteeID', 'int'),
			ent.value('@name', 'varchar(50)')
	FROM @xml.nodes('/Guests/guest') foo(ent)

	--========================================== DELETE
	DELETE FROM dbo.Guests
	WHERE guestId IN (
		SELECT ent.value('@guestId', 'int')
		FROM @xml.nodes('/Guests/delete') foo(ent)
	)
	--========================================== UPDATE
	UPDATE dbo.Guests
	SET guestName = tbl.Name
	FROM( 
		SELECT [gid] = ent.value('@guestId', 'int'),
			   [name] = ent.value('@name', 'varchar(50)')
		FROM @xml.nodes('/Guests/delete') foo(ent)
	) AS tbl
	WHERE tbl.gid = guests.guestID



SELECT * FROM Guests
