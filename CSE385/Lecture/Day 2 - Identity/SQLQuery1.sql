INSERT INTO tblFriends(lName, fName, age, phoneNum)
VALUES ('Ryan', 'Tom', 22, '123-456-7890'),
	   ('Peterson', 'Debbie', 32, '222-432-3543')

SET IDENTITY_INSERT tblFriends ON		
		INSERT INTO tblFriends(friendID, lName, fName, age, phoneNum)
		VALUES (6,'Ryan', 'Jack', 21, '123-456-7890'),
			   (12,'Coe', 'Seb', 54, '222-432-1111')
SET IDENTITY_INSERT tblFriends OFF

SELECT * FROM tblFriends