CREATE TABLE [dbo].[tblFriends] (
    [friendId] INT          IDENTITY (1, 1) NOT NULL,
    [fName]    VARCHAR (20) DEFAULT ('') NOT NULL,
    [lName]    VARCHAR (20) DEFAULT ('') NOT NULL,
    [age]      INT          DEFAULT ((0)) NOT NULL,
    [phoneNum] VARCHAR (20) DEFAULT ('none') NOT NULL,
    PRIMARY KEY CLUSTERED ([friendId] ASC)
);

