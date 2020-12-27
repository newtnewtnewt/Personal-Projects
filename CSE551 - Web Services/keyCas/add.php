<?php
/*
 * scott campbell
 * php program to display info
 * cse451
 * sprint 2020
 * https://getbootstrap.com/docs/4.0/components/forms/?
 */

session_start();


require_once("info.php");
require_once('config.php');
require_once('vendor/autoload.php');
require_once('vendor/apereo/phpcas/CAS.php');

use GuzzleHttp\Client;

// Enable debugging
phpCAS::setDebug('/tmp/cas');
phpCAS::client(CAS_VERSION_2_0, $cas_host, $cas_port, $cas_context);


// For quick testing you can disable SSL validation of the CAS server.
// THIS SETTING IS NOT RECOMMENDED FOR PRODUCTION.
// VALIDATING THE CAS SERVER IS CRUCIAL TO THE SECURITY OF THE CAS PROTOCOL!
phpCAS::setNoCasServerValidation();


$timeout = 300; // Number of seconds until it times out.
$user = "";

//  If the user issues logout command
if(isset($_REQUEST["cmd"]) && ($_REQUEST['cmd'] == "logout")){
	// Log them out
	error_log("User " . $user . " logged out");  
	phpCAS::logout();
}

else if(isset($_SESSION['login_timeout'])) {
	
			// See if the number of seconds since the last
			// visit is larger than the timeout period.
			$duration = time() - (int)$_SESSION['login_timeout'];
			if($duration > $timeout) {
				
				// Destroy the session and restart it.
				error_log("User: " . $_SESSION['user'] . " logged out");
				unset($_SESSION['login_timeout']);
				unset($_SESSION['user']);
				phpCAS::forceAuthentication();
			}
			else{
				$user = $_SESSION['user'];
			}
}
else{
	// force CAS authentication
	phpCAS::forceAuthentication();
	error_log('User: ' . phpCAS::getUser() . ' logged in');
	$_SESSION['user']	= phpCAS::getUser();
	$user = phpCAS::getUser();
}




function getVal($name) {
	if (isset($_REQUEST[$name]) && $_REQUEST[$name] != "")
		return htmlspecialchars(trim($_REQUEST[$name]));
	else
		return "";
}

$cmd=getVal("cmd");

if (!isset($_SESSION['user'])) {
	error_log("invalid access to add.php");
	header("Location: index.php");
	$_SESSION['msg'] = "Please login first";
	exit;
}

$uid = $_SESSION['user'];

error_log("$uid called add.php");

$key=getVal("key");
$value = getVal("value");
$dbUsed = getVal("dbUsed");


$msg="";
if ($key != "" && $value != "" && $dbUsed == 0) {
	error_log("$uid added $key with $value");
	$msg = add($key,$value,$addPass);
	if ($msg == "OK") {
	$_SESSION['msg'] = "$key added";
	header("Location: index.php");
	exit;
	}
}
else if ($key != "" && $value != "" && $dbUsed == 1) {
	$client2 = new Client([
		// Base URI is used with relative requests
		'base_uri' => 'http://campbest.451.csi.miamioh.edu/cse451-campbest-web-public-2020/week2/week2-rest.php/api/v1/info',
		// You can set any number of default request options.
		'timeout'  => 2.0
	]);
	//  Send the post request with the correct values
	$send = array();
	$send['key'] = $key;
	$send['value'] = $value; 
	$send['password'] = $addPass;
	$response2 = $client2->request('PUT','',[
	'json' => $send
	]);
	error_log("$uid added $key with $value");
	header("Location: index.php");
	exit;
	

}

	

?>
<!DOCTYPE html>

<HTML lang="en">
<HEAD>
  <META name="generator" content=
  "HTML Tidy for Linux (vers 25 March 2009), see www.w3.org">
  <META charset="utf-8">
  <META http-equiv="X-UA-Compatible" content="IE=edge">
  <META name="viewport" content=
  "width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

  <TITLE>Campbest Week3</TITLE><!-- Bootstrap -->
  <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

  <LINK rel="stylesheet" href="style.css" type="text/css">
</HEAD>

<BODY>
  <DIV class='container-fluid center' id='mainpage'>
    <H1>Display keys and values</H1>

<?php
if ($msg != "") {
	print "<div class='alert alert-danger'>";
	print $msg;
	print "</DIV>";
}
?>
 <form id="loginForm" action="add.php" method="post" class="was-validated">
  <div class="form-group">
    <label for="key">Enter Key</label>
    <input type="text" class="form-control" placeholder="Enter Key" id="key"name='key' required value='<?php print $key;?>'>
  </div>
  <div class="form-group">
    <label for="value">Value</label>
    <input type="text" class="form-control" placeholder="Value" id="value" name='value' required value='<?php print $value;?>'>
	<label for="inputState">State</label>
      <select id="inputState" class="form-control" name="dbUsed" required>
        <option selected value="0">Local Database</option>
        <option value="1">Dr. Campbell's Database</option>
      </select>
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>
<a href='index.php'><button>Cancel</button></a>

  </DIV><!-- close 1st container-->

  <DIV id="footer" class="mx-auto" style="width: 400px;">
    Scott Campell - CSE451 - Spring 2020 - Week3<BR>
  </DIV><!-- close footer-->
</BODY>
</HTML>

