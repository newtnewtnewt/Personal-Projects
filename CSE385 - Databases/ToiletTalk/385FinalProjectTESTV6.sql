--USE [master]
--GO

--DROP DATABASE [FinalProject385Test]
--GO

CREATE DATABASE [FinalProject385Test]
GO

USE [FinalProject385Test]
GO

CREATE TABLE restrooms (
	restroomId			INT				PRIMARY KEY		IDENTITY,
	buildingName		VARCHAR(50)		NOT NULL,
	floorNumber			INT				NOT NULL,
	roomNumber			VARCHAR(10)		NOT NULL,
	roomType			VARCHAR(25)
)
GO

BULK INSERT restrooms
FROM	'C:\temp\Restrooms.txt'
WITH (
		FIELDTERMINATOR = '\t',
		ROWTERMINATOR = '\n',
		FIRSTROW = 2
)
GO

CREATE TABLE floorPlans (
	floorPlanId		INT					PRIMARY KEY		IDENTITY,
	stalls			INT,
	urinals			INT,
	sinks			INT,
	floorPlanLink	VARCHAR(MAX),
	restroomId		INT					FOREIGN KEY REFERENCES restrooms(restroomId)
)
GO

CREATE TABLE users (
	userId			INT					PRIMARY KEY		IDENTITY,
	userName		VARCHAR(30)			NOT NULL,
	pw				VARCHAR(30)			NOT NULL
)
GO

--INSERT INTO  users(
--	userName,
--	pw
--)

--VALUES( 'matherly', '1234')
--GO	

CREATE TABLE ratings (
	ratingId		INT					PRIMARY KEY		IDENTITY,
	rating			INT					CHECK (rating BETWEEN -1 AND 5),
	stalls			INT					CHECK (stalls BETWEEN -1 AND 5),
	urinals			INT					CHECK (urinals BETWEEN -1 AND 5),
	sinks			INT					CHECK (sinks BETWEEN -1 AND 5),
	ratingComment	VARCHAR(MAX),
	restroomId		INT					FOREIGN KEY REFERENCES restrooms(restroomId),
	userId			INT					FOREIGN KEY REFERENCES users(userId)
)
GO

--stored procedure to get a link to the building blueprints by restroomID.
CREATE PROCEDURE [dbo].[spGetLinkbyRestroomID]
	@restroomID			INT
AS
BEGIN
	SELECT floorPlanLink
	FROM floorPlans
	WHERE restroomId = @restroomID

END
GO

--stored procedure to get a restroom using a building name and floor number (integer values only)
CREATE PROCEDURE [dbo].[spGetRestroomByFloorNumber]
	@buildingName		VARCHAR(50),
	@floorNumber		VARCHAR(1)
AS
BEGIN
	SELECT *
	FROM restrooms
	WHERE (buildingName = @buildingName) AND
			(floorNumber = @floorNumber)
END

GO
--EXEC spGetRestroomByFloorNumber 'benton hall', '2'


--stored procedure to get all buildings and restrooms and ordering them in ascending order by buildings and room numbers
CREATE PROCEDURE [dbo].[spGetRestroomsByAllBuilding]
AS
BEGIN
	SELECT buildingName, floorNumber, roomNumber
	FROM restrooms
	ORDER BY buildingName, roomNumber
END
GO

--EXEC spGetRestroomsByAllBuilding


--stored procedure to get all restrooms room numbers and ratings using a building name and floor number (integer values only)
CREATE PROCEDURE [dbo].[spGetRestroomsByBuildingAndFloorAndRating]
	@buildingName		VARCHAR(50),
	@floorNumber		VARCHAR(1)
	
AS
BEGIN
	SELECT	rest.buildingName, rest.roomNumber, r.rating
	FROM restrooms	AS			rest
		LEFT JOIN ratings AS	r
			ON (rest.restroomId = r.restroomId)
	WHERE (rest.buildingName = @buildingName) AND
			(rest.floorNumber = @floorNumber)
END
GO

--EXEC spGetRestroomsByBuildingAndFloorAndRating 'benton hall', '2'


--stored procedure to get all restrooms in a specific building using a building name
CREATE PROCEDURE [dbo].[spGetRestroomsByBuildingName]
	@buildingName		VARCHAR(50)
AS
BEGIN
	SELECT floorNumber, roomNumber, roomType
	FROM restrooms
	WHERE (buildingName = @buildingName)
END
GO

--EXEC spGetRestroomsByBuildingName 'benton hall'

--stored procedure to insert a rating.
CREATE PROCEDURE [dbo].[spInsertRating]
	@buildingName	VARCHAR(50),
	@roomNumber		VARCHAR(10),
	@rating			INT,
	@stalls			INT,
	@urinals		INT,
	@sinks			INT,
	@comment		VARCHAR(MAX),
	@userName		VARCHAR(30)
AS
BEGIN
	 
	DECLARE @RestID INT
	SET @RestID = (SELECT r.restroomId FROM restrooms r WHERE  r.buildingName = @buildingName AND r.roomNumber = @roomNumber)
	DECLARE @UserID INT
	SET @UserID = (SELECT u.userId FROM users u WHERE  u.userName = @userName)
	
	INSERT INTO ratings (
		rating,
		stalls,
		urinals,
		sinks,
		ratingComment,	
		restroomId,
		userID
	)
	VALUES
	(
		@rating,
		@stalls,	
		@urinals,
		@sinks,	
		@comment,
		@RestID,
		@UserID
	)

END
GO

--EXEC spInsertRating 'benton hall', '110', 4, 3, 4, 'Looks GREAT!!!', 'matherly'

-- creates a stored procedure that you input a restroomID and it returns all the rating info
CREATE PROCEDURE [dbo].[spGetRestroomInfoAndRatingByRestroomID]
		@restroomID		INT

AS
BEGIN
	SELECT rr.buildingName,
		   rr.floorNumber,
		   rr.roomNumber,
		   fp.stalls,
		   fp.urinals,
		   fp.sinks,
		   fp.floorPlanLink,
		   r.rating
	FROM restrooms  rr
		 JOIN ratings r
		 ON rr.restroomID = r.restroomID
		 JOIN floorPlans fp
		 ON fp.restroomId = rr.restroomId
END
GO

-- creates a stored procedure that grabs all ratings and comments by restroom id 

CREATE PROCEDURE [dbo].[spGetRatingAndComment]
		@restroomID	INT

AS
BEGIN
	SELECT  u.username,
			r.sinks,
			r.stalls,
			r.urinals,
			r.ratingComment
	FROM ratings r
	JOIN users u ON r.userId = u.userId
	WHERE r.restroomId = @restroomID
END
GO

-- creates a procedure that grabs restrooms by type (male, female, etc)

CREATE PROCEDURE  [dbo].[spGetRestroomsByType]
	@staff				BIT,
	@male				BIT, 
	@female				BIT,
	@unisex				BIT,
	@searchStr			VARCHAR(150)

AS 
BEGIN 

	IF (@searchStr LIKE 'brick')
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
	ELSE IF (@male = 1 AND @female = 1 AND @unisex = 1 AND @staff = 1) OR (@male = 0 AND @female = 0 AND @unisex = 0 AND @staff = 0)
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Staff%' OR roomType LIKE '%Faculty%') OR roomType LIKE 'Mens' OR (roomType LIKE '%Family%' OR roomType LIKE '%All Gender%') OR roomType LIKE 'Womens');
	ELSE IF @male = 1 AND @staff = 1 AND @unisex = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Staff%' OR roomType LIKE '%Faculty%') OR roomType LIKE 'Mens' OR (roomType LIKE '%Family%' OR roomType LIKE '%All Gender%'));
	ELSE IF @female = 1 AND @staff = 1 AND @unisex = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Staff%' OR roomType LIKE '%Faculty%') OR roomType LIKE 'Womens' OR (roomType LIKE '%Family%' OR roomType LIKE '%All Gender%'));
	ELSE IF @male = 1 AND @female = 1 AND @unisex = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Family%' OR roomType LIKE '%All Gender%') OR roomType LIKE 'Womens' OR roomType LIKE 'Mens');
	ELSE IF @male = 1 AND @female = 1 AND @staff = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Staff%' OR roomType LIKE '%Faculty%') OR roomType LIKE 'Womens' OR roomType LIKE 'Mens');
	ELSE IF @male = 1 AND @staff = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Staff%' OR roomType LIKE '%Faculty%') OR roomType LIKE 'Mens');
	ELSE IF @male = 1 AND @unisex = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Family%' OR roomType LIKE '%All Gender%') OR roomType LIKE 'Mens');
	ELSE IF @female = 1 AND @staff = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND (roomType LIKE 'Womens' OR (roomType LIKE '%Staff%' OR roomType LIKE '%Faculty%'));
	ELSE IF @female = 1 AND @unisex = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Family%' OR roomType LIKE '%All Gender%') OR roomType LIKE 'Womens');
	ELSE IF @female = 1 AND @male = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND (roomType LIKE 'Mens' OR roomType LIKE 'Womens');
	ELSE IF @unisex = 1 AND @staff = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND ((roomType LIKE '%Family%' OR roomType LIKE '%All Gender%') OR (roomType LIKE '%Staff%' OR roomType LIKE '%Faculty%'));
	ELSE IF @female = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND roomType LIKE 'Womens';
	ELSE IF @unisex = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND (roomType LIKE '%Family%' OR roomType LIKE '%All Gender%');
	ELSE IF @male = 1
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND roomType LIKE 'Mens%';
	ELSE IF @staff = 1 
		SELECT	rest.restroomId, rest.buildingName, rest.floorNumber, rest.roomType, rest.roomNumber
		FROM restrooms	AS	rest
		WHERE buildingName LIKE '%' + @searchStr + '%' AND (roomType LIKE '%Staff%' OR roomType LIKE '%Faculty%');

	
END