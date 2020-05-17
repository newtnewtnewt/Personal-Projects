<?php
/*
* Author: Noah Dunn 
* Class: CSE 383
* Final Project REST API User Model
* With assistance from the following resources:
* Dr. Campbell, Postman 
* https://dev.mysql.com/doc/refman/5.5/en/creating-tables.html
https://www.eversql.com/sql-syntax-check-validator/
*/

require_once("passwd.php");

$mysqli = new mysqli('localhost',$user,$pwd,$database);
if ($mysqli->connect_error != "") {
	print "Error connecting to db";
	die;
}


function addVideo($title, $videoID) {
	global $mysqli;
	if ($title == "" || $videoID == "")
		return "Error - invalid inputs";
	$sql = "insert into Videos(title,videoID) values (?,?)";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		print "Error on prepare in makeAccount";
		return "Failed on prepare";
	}
	$sanTitle = htmlspecialchars($title);
	$sanVidID = htmlspecialchars($videoID);
	$stmt->bind_param("ss",$sanTitle,$sanVidID);
	$stmt->execute();
	return null;
}

function addLink($title, $url) {
	global $mysqli;
	if ($title == "" || $url == "")
		return "Error - invalid inputs";
	$sql = "insert into Links(title, url) values (?,?)";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		print "Error on prepare in makeAccount";
		return "Failed on prepare";
	}

	$sanTitle =htmlspecialchars($title);
	$sanURL =htmlspecialchars($url);
	$stmt->bind_param("ss", $sanTitle, $sanURL);
	$stmt->execute();
	return null;
}

function getVideos() {
	global $mysqli;
	//  Let's get all the video information
	$sql = "select pk,title,videoID from Videos";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		return null;
	}

	$stmt->bind_result($pk, $title, $videoID);
	$stmt->execute();
	$result = array();
	while ($stmt->fetch()) {
		$user = array('pk'=>$pk, 'title'=>$title,'videoID'=>$videoID);
		array_push($result,$user);
	}
	return $result;
}
function getLinks() {
	global $mysqli;
	$sql = "select pk, title, url from Links";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		return null;
	}

	$stmt->bind_result($pk, $title, $url);
	$stmt->execute();
	$result = array();
	while ($stmt->fetch()) {
		$user = array('pk'=>$pk,'title'=>$title,'url'=>$url);
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
function deleteLink($pk) {
	global $mysqli;
	if ($pk == "" )
		return "Error - invalid inputs";
	$sql = "delete from Links where pk = ?";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		print "Error on prepare in makeAccount";
		return "Failed on prepare";
	}

	$stmt->bind_param("i",$pk);
	$stmt->execute();
	return("ok");
}
function deleteVideo($pk) {
	global $mysqli;
	if ($pk == "" )
		return "Error - invalid inputs";
	$sql = "delete from Videos where pk = ?";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		print "Error on prepare in makeAccount";
		return "Failed on prepare";
	}

	$stmt->bind_param("s",$pk);
	$stmt->execute();
	return("ok");
}

?>