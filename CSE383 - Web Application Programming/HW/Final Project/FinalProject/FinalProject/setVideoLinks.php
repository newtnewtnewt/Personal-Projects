<!-- Author: Noah Dunn 
  Class: CSE 383
  Final Project: The Whole thing
 With assistance from the following resources:
 W3 Schools, Dr. Campbell
 https://stackoverflow.com/questions/25854496/how-to-set-min-height-for-bootstrap-container
 https://stackoverflow.com/questions/18529274/change-navbar-color-in-twitter-bootstrap
 
 -->
 <?php 
	require_once("passwd.php");

	$mysqli = new mysqli('dunnnm2.aws.csi.miamioh.edu',$user,$pwd,$database);
	
	if ($mysqli->connect_error != "") {
		print $mysqli->connect_error;
		print "Error connecting to db";
		die;
	}
	
	function getVar($name) {
		if (!isset($_REQUEST[$name])) {
			return "";
		}
		else return htmlspecialchars($_REQUEST[$name]);
	}
	
	$cmd = getVar("cmd");
	if ($cmd=="submit") {
		$title = getVar("title");
		$vidID = getVar("vidID");
		if($title != "" && $vidID != ""){
			global $mysqli;
			$sql = "insert into Videos(title,videoID) values (?,?)";
			$stmt = $mysqli->prepare($sql);
			if (!$stmt)
			{
				print "Error on prepare in makeAccount";
				return "Failed on prepare";
			}
			$stmt->bind_param("ss",$title, $vidID);
			$stmt->execute();
		}
		
	}
	else if($cmd == "delete"){
		$pk = getVar("pk");
		if($pk != ""){
			global $mysqli;
			$sql = "delete from Videos where pk = ?";
			$stmt = $mysqli->prepare($sql);
			if (!$stmt)
			{
				print "Error on prepare in makeAccount";
				return "Failed on prepare";
			}

			$stmt->bind_param("s",$pk);
			$stmt->execute();
		}
	}
	
?>
 <!DOCTYPE html>
 <?php

/**
 *   Example for a simple cas 2.0 client
 *
 * PHP Version 5
 *
 * @file     example_simple.php
 * @category Authentication
 * @package  PhpCAS
 * @author   Joachim Fritschi <jfritschi@freenet.de>
 * @author   Adam Franco <afranco@middlebury.edu>
 * @license  http://www.apache.org/licenses/LICENSE-2.0  Apache License 2.0
 * @link     https://wiki.jasig.org/display/CASC/phpCAS
 */
session_start();
// Load the settings from the central config file
require_once 'config.php';
// Load the CAS lib
require_once 'vendor/autoload.php';
require_once 'vendor/apereo/phpcas/CAS.php';


// Enable debugging
phpCAS::setDebug('/tmp/cas');

// Initialize phpCAS
phpCAS::client(CAS_VERSION_2_0, $cas_host, $cas_port, $cas_context);

// For production use set the CA certificate that is the issuer of the cert
// on the CAS server and uncomment the line below
// phpCAS::setCasServerCACert($cas_server_ca_cert_path);

// For quick testing you can disable SSL validation of the CAS server.
// THIS SETTING IS NOT RECOMMENDED FOR PRODUCTION.
// VALIDATING THE CAS SERVER IS CRUCIAL TO THE SECURITY OF THE CAS PROTOCOL!
phpCAS::setNoCasServerValidation();

// force CAS authentication
phpCAS::forceAuthentication();

// Only thing I really needed to add, setting the session variable
$_SESSION['user'] = phpCAS::getUser();

// at this step, the user has been authenticated by the CAS server
// and the user's login name can be read with phpCAS::getUser().

// logout if desired
if (isset($_REQUEST['logout'])) {
	phpCAS::logout();
}

$user = phpCAS::getUser();

// for this test, simply print that the authentication was successfull
?>
<?php if($user == "campbest" || $user == "dunnnm2" ){ ?>
<html lang="en">
  <head>
    <meta name="generator"
    content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
    <title>Final Project</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	<script src="imageFlip.js"></script>
  </head>
  <body>
	<!-- First we need to construct our desired banner -->
	<div class = "container-fluid p-0" style="margin:0px;background-color:#AFED9D;font-family:Lucida Console;">
	<div class = "row">
		<div class="col-sm-3 align-items-start">
			<label> Click here for CEC --> </label>
			<a href="https://miamioh.edu/cec/index.html">
			<image alt="The logo for the CEC department" src="images/ceclogo.jpg" />
			</a>
		</div>
		<div class="col-sm-6 align-items-center" >
		  <h1 style="text-align:center;">CSE383 - The Final Frontier</h1>
		  <h3 style="text-align:center;">Noah Dunn</h3>
		</div>
		<div class="col-sm-3 align-items-end">
				<image id="logo" alt="Your personalized logo" src="images/smallnewtface.png" />
			<label> <-- Click here <label/>
		</div>
	</div>
	</div>
	
	<nav class="navbar navbar-inverse" style="background-color: #4BA630;border-color: #4BA630;">
	
	  <div class="container-fluid">
		<div class="navbar-header">
		  <a class="navbar-brand" href="index.php" style="color:#FFFFFF">Final Project</a>
		</div>
		<ul class="nav navbar-nav">
		  <li><a href="index.php" style="color:#FFFFFF;">Home</a></li>
		  <li><a href="setWeather.php" style="color:#FFFFFF;">Set Weather</a></li>
		  <li class="active"><a href="setVideoLinks.php" style="color:#FFFFFF;">Set Video Links</a></li>
		  <li><a href="setQuickLinks.php" style="color:#FFFFFF;">Set Quick Links</a></li>
		</ul>
	  </div>
	</nav>
	<div class="container">
		<form class="form" action="setVideoLinks.php" method="post" >
		<table class="table" style="padding-left:20;">
			<thead>
			  <tr>
				<th>Title</th>
				<th>VideoID</th>
				<th>Action</th>
			  </tr>
			 </thead>
			  <tbody id="vidList">
			  <?php 
			  
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
					$fullRow = "";
					$fullRow = "<tr>";
					$fullRow .= "<td>$title</td>";
					$fullRow .= "<td>$videoID</td>";
					$fullRow .= "<td><button type=\"Submit\" name=\"pk\" value=\"$pk\" class=\"btn btn-info\">Delete</button></td></tr>";
					print $fullRow;
				}
			  ?>
			</tbody>
	  </table>
	  <div><input type="hidden" name="cmd" value="delete"  hidden /> </div>
	  </form>
	  <div><label>Submit a new video </label></div>
	  <form class="form" action="setVideoLinks.php" method="post">
		  <div class="form-inline" style="padding-left:100px">
			<label for="title">Title:</label>
			<input name="title" placeholder="Further On Up the Road" type="text" class="form-control" id="title">
			<label for="vidID">Video ID:</label>
			<input name="vidID" type="text" placeholder="asdji1isaij2445" class="form-control" id="vidID">
			<input button type="Submit" class="btn btn-danger"></input>
		  </div>
		  <div><input type="hidden" name="cmd" value="submit"  hidden /> </div>
	  </form>
  </div>
    <br /><br /><br /><br /><br /><br /><br /><br /><br />
	<footer>
		<p style="text-color:gray;left:70%;bottom:0;position:fixed;">Created by Noah Dunn -- Copyright 2019 -- CSE383</p>
	</footer>
  </body>
</html>
<?php } ?>
