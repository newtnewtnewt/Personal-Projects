<?php
/*
 * Scott Campbell
 * UserModel for accessing the database
 *
 * run this script to create database:
 *
 * create table auth (pk int not null auto_increment, user varchar(100), password varchar(100), email varchar(100), primary key(pk));
 *
 * passwd.php file needs $user, $pwd, $database
 * */

require_once("passwd.php");

$mysqli = new mysqli('localhost',$user,$pwd,$database);
if ($mysqli->connect_error != "") {
	print "Error connecting to db";
	die;
}


//warning -> does not check to ensure it exists first
//returns string msg
function addUser($user,$passwd,$email) {
	$check = getUser($user);
	if ($check != null)
	{
		return "Fail - user exists";
	}
	global $mysqli;
	if ($user == "" || $passwd == "")
		return "Error - invalid inputs";
	$sql = "insert into auth(user,password,email) values (?,?,?)";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		print "Error on prepare in makeAccount";
		return "Failed on prepare";
	}

	$huser =htmlspecialchars($user);
	$ppassword =password_hash($passwd,PASSWORD_DEFAULT);
	$stmt->bind_param("sss",$huser,$ppassword,$email);
	$stmt->execute();
	return null;
}

//returns array of users
function getUsers() {
	global $mysqli;
	$sql = "select user,email from auth order by user";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		return null;
	}

	$stmt->bind_result($huser,$email);
	$stmt->execute();
	$result = array();
	while ($stmt->fetch()) {
		$user = array('user'=>$huser,'email'=>$email);
		array_push($result,$user);
	}
	return $result;
}

//user details
function getUser($user) {
	global $mysqli;
	$sql = "select pk,user,email from auth where user = ?";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		return null;
	}

	$stmt->bind_param("s",$user);
	$stmt->bind_result($pk,$huser,$email);
	$stmt->execute();
	$result = array();
	$a=$stmt->fetch();
	if ($a)	
		$user = array('pk'=>$pk,'user'=>$huser,'email'=>$email);
	else
		$user=null;
	return $user;
}


function updatePassword($user,$passwd) {
	global $mysqli;
	if (getUser($user) == null)
		return "Fail - user does not exist";
	if ($user == "" || $passwd == "")
		return "Error - invalid inputs";
	$sql = "update auth set password=? where user =?";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		print "Error on prepare in makeAccount";
		return "Failed on prepare";
	}

	$ppassword =password_hash($passwd,PASSWORD_DEFAULT);
	$stmt->bind_param("ss",$huser,$ppassword);
	$stmt->execute();
	return("ok");

}

function deleteUser($user) {
	global $mysqli;
	if ($user == "" )
		return "Error - invalid inputs";
	$sql = "delete from auth where user=?";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		print "Error on prepare in makeAccount";
		return "Failed on prepare";
	}

	$stmt->bind_param("s",$user);
	$stmt->execute();
	return("ok");
}
?>