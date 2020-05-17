<!--
Name: Noah Dunn 
Instructor: Dr. Scott Campbell
Class: CSE 551
Date: 2/14/2020
Assignment: PHP Recap
With assistance from:
Dr. Campbell, 
My Previous CSE383 work,
W3 Schools, and the Following:
https://stackoverflow.com/questions/35163164/how-to-center-content-in-a-bootstrap-column
https://stackoverflow.com/questions/2906582/how-to-create-an-html-button-that-acts-like-a-link
-->
<?php
require_once("info.php");
//  Enable session
session_start();
//  If we don't have a username and try to access this page, redirect user back to login
if(!isset($_SESSION["username"])){
	header("Location: index.php");
	exit;
}
//  If the user issues a delete request with a key by hitting a button
if(isset($_REQUEST["cmd"]) && isset($_REQUEST["key"]) && ($_REQUEST["cmd"] == "delete")){
	// Call the delete command in info.php
	delete(htmlspecialchars(trim($_REQUEST['key'])), "password");
	// Use this to display a message if completed
	$_SESSION['deletedsomething'] = true;
	// Redirect back to this page
	header("Location: display.php");
	exit;
}
//  If the user issues logout command
if(isset($_REQUEST["cmd"]) && ($_REQUEST['cmd'] == "logout")){
	// Log them out
	error_log("User logged out");
	session_unset();
	// Redirect back to index
	header("Location: index.php");
	exit;
}
?>
<!DOCTYPE html>
<html lang="en">
  <head>
	<!-- The body of text establishing our string encoding and the version of bootstrap we are using -->
    <title>PHP Intro</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
  </head>
  
  <body>
	<!-- Using a header jumbotron in order to achieve desired look -->
	<div class="page-header" style="margin-top:0px;background-color:#DAF7A6;">
	 <div class="jumbotron" style="text-align:center;background-color:#DAF7A6;">
		<h1>Keys and Vals</h1>
		<img src="NewtFace.png" alt="This is my newt logo" />
		
	</div> <!-- This ends the jumbotron tag -->
	</div>
	<!-- This kicks off our grid object -->
	<div class="container">
	  <div class="row">
		<div class="col-lg-3">
		</div> 
		<div class="col-lg-6">
		<?php if($_SESSION['deletedsomething']){ $_SESSION['deletedsomething'] = false; //  Flip our flag if we display the message ?>
				<div id="msg" class="alert alert-info">
					Record successfully deleted
				</div>
		<?php } ?>
		</div>
		<div class="col-lg-3">
		</div>
		</div>
		<div class="row">
		<div class="col-lg-3">
		</div> 
		<div class="col-lg-6">
			<h1>Welcome, <?php print $_SESSION['username'] ?>! </h1>
			<table id="keys" class="table">
			  <tr>
				<th>Key</th>
				<th>Value</th>
				<th>Delete Option</th>
			  </tr>		 
			  <tbody id="tableContent">
			  <?php 
			    // Grab an array of all the keys
				$keys = getKeys();
				// Get the total number of keys first to avoid constant execution
				$totalCount = count($keys);
				//  Loop through the keys and print key-value pair rows
				for($i = 0; $i < $totalCount; $i++){
					$rowOutput = "<tr><td>" . $keys[$i] . "</td><td>" . getValue($keys[$i]) . "</td>";
					$rowOutput .= "<td>" . "<a class='btn btn-danger' href='display.php?cmd=delete&key=" . $keys[$i] .  "'>Delete</button></td></tr>";
					print $rowOutput;
				}
			  ?>
			  </tbody>
			</table>
		</div>
		<div class="col-lg-3">
		</div> <!-- This ends the first left column block -->
		</div>
		<div class="row">
			<div class="col-lg-3"></div>
			<div class="col-lg-6"> 
				<a class="btn btn-success" href="add.php">Add Entry</a>
				<a class="btn btn-info" href="display.php?cmd=logout">Logout</a>
			</div>
			<div class="col-lg-3"></div>
		</div>
		</div>
	<footer>
		<div class="jumbotron" style="text-align:center;background-color:#DAF7A6;margin-bottom:0px;">
			<p>Noah Dunn</p>
			<p>Assignment: PHP + HTML Forms</p>
			<p>Date: 2/14/2020 </p>
		</div> <!-- This ends the footer block -->
	</footer> 
  </body>