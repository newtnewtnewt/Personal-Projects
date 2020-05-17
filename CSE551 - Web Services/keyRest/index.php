<!--
Name: Noah Dunn 
Instructor: Dr. Scott Campbell
Class: CSE 551
Date: 2/21/2020
Assignment: PHP + REST
With assistance from:
Dr. Campbell, 
My Previous CSE383 work,
W3 Schools, and the Following:
https://getbootstrap.com/
-->
<?php
//  Grab our functions from the info page
require_once("info.php");
//  Enable the usage of session variables 
session_start();
//  A variable to store a username
$user = "";
//  A variable to stash if an error was hit or not
$errorPresent = false;

//  If the user attempts to issue a logout command, it will log them out
if(isset($_REQUEST["cmd"]) && ($_REQUEST["cmd"] == "logout")){
	error_log("User logged out");
	session_unset();
	exit;
}	

//  If a username and a password has been sent to this page, process to see if it goes through
if(isset($_REQUEST["username"]) && isset($_REQUEST["password"])){
	//  Grab the user to save no matter what
	$user = htmlspecialchars(trim($_REQUEST["username"]));
	//  If the password is correct
	if($_REQUEST["password"] == "PASSWORD"){
		//  Save the username to be used in display as a session variable
		$_SESSION["username"] = $user;
		$_SESSION["password"] = $_REQUEST["password"];
	        $_SESSION["uid"] = $user;	
		// Log it
		error_log("User " . $user . " successfully logged in ");
		// Redirect
		header("Location: display.php");
		exit;
	}
	else {
		// Turn our flag true to display the error later on
		$errorPresent = true;
		// Log it
		error_log("User " . $user . " attempted a login with an incorrect password");
	}

}
?>
<!DOCTYPE html>
<html lang="en">
  <head>
	<!-- The body of text establishing our string encoding and the version of bootstrap we are using -->
    <title>Form PHP</title>
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
				<?php if($errorPresent){ //  Hide the error if not applicable ?>
				<div id="msg" class="alert alert-danger">
					You did not enter the correct password
				</div>
				<?php } ?>
			</div>
			<div class="col-lg-3">
			</div> <!-- This ends the first left column block -->
	  </div>
	  <!--Trigger an HTML form POST request on this form for validation -->
	  <form method="post" action="index.php">
		  <div class="row">
				<div class="col-lg-3">
				</div> 
				<div class="col-lg-6">
					<p>User UniqueID:</p>
				</div>
				<div class="col-lg-3">
				</div> <!-- This ends the first left column block -->
			</div>
			<div class="row">
				<div class="col-lg-3">
				</div> 
				<div class="col-lg-6">
					<!-- This will fill the username back into the field if the password is incorrect -->
					<input type="text" class="form-control" value="<?php echo $user; ?>" id="username" name="username" required>
				</div>
				<div class="col-lg-3">
				</div> <!-- This ends the first left column block -->
			</div>
			<div class="row">
				<div class="col-lg-3">
				</div> 
				<div class="col-lg-6">
					<p>Password:</p>
				</div>
				<div class="col-lg-3">
				</div> <!-- This ends the first left column block -->
			</div>
			<div class="row">
				<div class="col-lg-3">
				</div> 
				<div class="col-lg-6">
					<!-- This is where we grab the password -->
					<input type="password" class="form-control" id="password" name="password" required>
				</div>
				<div class="col-lg-3">
				</div> <!-- This ends the first left column block -->
			</div>
			<div class="row">
				<div class="col-lg-3">
				</div> 
				<div class="col-lg-6">
				<br></br>
				</div>
				<div class="col-lg-3">
				</div> <!-- This ends the first left column block -->
			</div>
			<div class="row">
				<div class="col-lg-3">
				</div> 
				<div class="col-lg-6">
					<!-- Our submit button -->
					<input type="submit" class="btn btn-primary" value="Submit">
				</div>
				<div class="col-lg-3">
				</div> <!-- This ends the first left column block -->
			</div>
		</form>
	</div>
	<footer>
		<div class="jumbotron" style="text-align:center;background-color:#DAF7A6;margin-bottom:0px;">
			<p>Noah Dunn</p>
			<p>Assignment: PHP + REST</p>
			<p>Date: 2/21/2020 </p>
		</div> <!-- This ends the footer block -->
	</footer> 
  </body>
