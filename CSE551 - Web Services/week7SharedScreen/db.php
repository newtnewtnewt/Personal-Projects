<?php
/*
 * scott campbell
 * store data in table for screen
 * */



require_once("password.php");

$mysqli = mysqli_connect("localhost", $user,$pass,$db);
if (mysqli_connect_errno($mysqli)) {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
	die;
}

//lock table
function lockTable($type) {
	global $mysqli;
	global $db;

	$result=$mysqli->query("lock tables $db $type");
	if (!$result) {
		error_log("Error on getLock $type");
	}
}

function unlockTable() {
	global $mysqli;
	global $db;

	$result=$mysqli->query("unlock tables");
	if (!$result) {
		error_log("Error on getLock $type");
	}

}


//run a query to delete points after 60 second
function FadeOutPoints() {
	global $db;
	global $mysqli;
	lockTable("write");
	$mysqli->query("delete from $db where (unix_timestamp() - unix_timestamp(createTime)) > 60");
	unlockTable();
}

//return a list of all points indb
function getPoints() {
	global $mysqli;
	global $db;

	FadeOutPoints();

	lockTable("read");
	$result = $mysqli->query("select `x`,`y`,`x1`,`y1`,`r`,`g`,`b`,unix_timestamp(createTime) as createTime from $db order by createTime");
	if (!$result) {
		error_log("Error on getValue " . $mysqli->error);
		unlockTable();
		return array();
	}
	$points =array();
	while ($row = $result->fetch_assoc()) {
		array_push($points,$row);
	}

	unlockTable();
	return $points;
}

//add point to db
function addPoint($x,$y,$x1,$y1,$r,$g,$b) {
	global $mysqli;
	global $db;
	lockTable("write");
	if (!is_numeric($x) || !is_numeric($y) || !is_numeric($x1) || !is_numeric($y1) || $x >= $x1 || $y >= $y1) {
		return array("status"=>"fail","message"=>"invalid input");
	}
	$mysqli->query("insert into $db (x,x1,y,y1,r,g,b) values ('$x','$x1','$y','$y1','$r','$g','$b')");
	print $mysqli->error;
	unlockTable();
}

//clear all points from db
function clearPoints() {
	global $mysqli;
	global $db;
	lockTable("write");
	$mysqli->query("delete from $db");
	print $mysql->error;
	unlockTable();
}


//helper function to create shared data table
function createScreenTable() {
	global $db;
	global $mysqli;
	print "creating db\n";
	$mysqli->query("drop table `$db`");
	$r = $mysqli->query("CREATE TABLE `$db` (
		`pk` int(11) NOT NULL AUTO_INCREMENT,
		`owner` text,
		`x` int,
		`x1` int,
		`y` int,
		`y1` int,
		`r` int,
		`g` int,
		`b` int,
		`createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
		PRIMARY KEY (`pk`)
	)");
	print $mysqli->error;
}
/* This adds a user to our activeUsers table. If they are already in there, it updates the time */ 
function loadUser($currentUser){
	global $db;
	global $mysqli;
	// If the user is not non-existant
	if($currentUser != ""){
		// Run the insert
		$attemptInsert = $mysqli->query("INSERT INTO activeUsers (uid, lastlog) VALUES ('$currentUser', UNIX_TIMESTAMP())");
		// If an error is created because the user is already in there
		if(!$attemptInsert){
			// Update the table
			$attemptUpdate = $mysqli->query("UPDATE activeUsers SET lastlog=UNIX_TIMESTAMP() WHERE uid = '$currentUser'");
		}
	}
}
/* This provides a string for entry into an unordered list, based on what is stored in our database*/
function loadUsers(){
	global $db;
	global $mysqli;
	$result = $mysqli->query("SELECT uid FROM activeUsers WHERE lastlog >= UNIX_TIMESTAMP() - 1");
	$totalString = "";
	$userList = array();
	while ($row = $result->fetch_assoc()) {
		array_push($userList, $row);
	}
	foreach($userList as $user){
		$totalString .= "<li>" . $user["uid"] . "</li>"; 
	}
	return $totalString;
}

//uncomment and run from command line ONCE to create table
//make sure to recomment before using in rest server!!!!!!!!
//createScreenTable();
//addPoint(0,0,10,10,255,255,255);
//print_r(getPoints());

?>
