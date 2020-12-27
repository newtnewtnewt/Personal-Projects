
DECLARE @xml AS XML
SET @xml =			  '<Guests>
							<guest inviteeID="1" name = "Tom" />
							<guest inviteeID="2" name = "Jack" />
							<guest inviteeID="3" name = "Mary" />
					   </Guests>'
INSERT INTO Guests(inviteeID, guestName)
SELECT  ent.value('@inviteeID', 'int'),
		ent.value('@name', 'varchar(50)')
FROM @xml.nodes('/Guests/guest') foo(ent)

SELECT * FROM Guests

