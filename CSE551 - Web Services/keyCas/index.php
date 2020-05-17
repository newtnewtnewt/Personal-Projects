<!--
Name: Noah Dunn 
Instructor: Dr. Scott Campbell
Class: CSE 551
Date: 2/28/2020
Assignment: PHP CAS
With assistance from:
Dr. Campbell, 
My Previous CSE383 work,
W3 Schools, and the Following:
The CAS repo
https://stackoverflow.com/questions/35163164/how-to-center-content-in-a-bootstrap-column
https://stackoverflow.com/questions/2906582/how-to-create-an-html-button-that-acts-like-a-link
https://bytes.com/topic/php/insights/889606-setting-timeout-php-sessions
https://stackoverflow.com/questions/22244738/how-can-i-use-guzzle-to-send-a-post-request-in-json
https://www.javatpoint.com/how-to-get-current-page-url-in-php
-->
<?php
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
				phpCAS::forceAuthentication();
				$user = $_SESSION['user'];
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

//  If the user issues a delete request with a key by hitting a button
if(isset($_REQUEST["cmd"]) && isset($_REQUEST["key"]) && ($_REQUEST["cmd"] == "delete")){
	// Call the delete command in info.php
	delete(htmlspecialchars(trim($_REQUEST['key'])), "password");
	// Use this to display a message if completed
	$_SESSION['deletedsomething'] = true;
	// Redirect back to this page
	header("Location: index.php");
	exit;
}

?>
<!DOCTYPE html>
<html lang="en">
  <head>
	<!-- The body of text establishing our string encoding and the version of bootstrap we are using -->
    <title>PHP And REST</title>
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
		<h1>
		<?php
		
 
		// Check if the timeout field exists.
		if(isset($_SESSION['timeout'])) {
			// See if the number of seconds since the last
			// visit is larger than the timeout period.
			$duration = time() - (int)$_SESSION['timeout'];
			if($duration > $timeout) {
				// Destroy the session and restart it.
				unset($_SESSION['weather']);
			}
		}
		if(isset($_SESSION['weather'])){
			print "Current Temperature: " . $_SESSION['weather'] . " F (Cached)";
		}
		else {
		
			$client = new Client([
				// Base URI is used with relative requests
				'base_uri' => 'https://api.darksky.net/forecast/' . $apiKey .  '/39.5070,-84.7452',
				// You can set any number of default request options.
				'timeout'  => 2.0,
			]);

			//make get request
			$response = $client->request('GET','');

			if ($response->getStatusCode() == 200) {
				//  Grab the body from our response
				$s=$response->getBody();
				//  Decode the JSON
				$jsonData=json_decode($s);
				// If we can't decode, throw an error
				if ($jsonData == null) {
					print "Error decoding json";
					return;
				}
				//  Grab the temperature
				$temperature = $jsonData->currently->temperature;
				//  Save it as a session
				$_SESSION['weather'] = $temperature;
				//  Print the full value line
				print ("Current Temperature: " . $temperature . " F ");
			}

		}
		 
		// Update the timout field with the current time.
		if(!isset($_SESSION['timeout'])){
			$_SESSION['timeout'] = time();
		}
		if(!isset($_SESSION['login_timeout'])){
			$_SESSION['login_timeout'] = time();
		}
		
		?>
		</h1>
	</div> <!-- This ends the jumbotron tag -->
	</div>
	<!-- This kicks off our grid object -->
	<div class="container">
	  <div class="row">
		<div class="col-lg-3">
		</div> 
		<div class="col-lg-6">
		<?php if(isset($_SESSION['deletedsomething']) && $_SESSION['deletedsomething']){ $_SESSION['deletedsomething'] = false; //  Flip our flag if we display the message ?>
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
			<h1>Welcome, <?php print $user; ?>! </h1>
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
					$rowOutput .= "<td>" . "<a class='btn btn-danger' href='index.php?cmd=delete&key=" . $keys[$i] .  "'>Delete</button></td></tr>";
					print $rowOutput;
				}
				$client2 = new Client([
				// Base URI is used with relative requests
				'base_uri' => 'http://campbest.451.csi.miamioh.edu/cse451-campbest-web-public-2020/week2/week2-rest.php/api/v1/info',
				// You can set any number of default request options.
				'timeout'  => 2.0
				]);

				//make get request
				$response = $client2->request('GET','');

				if ($response->getStatusCode() == 200) {
					//  Grab the body from our response
					$s=$response->getBody();
					//  Decode the JSON
					$jsonData=json_decode($s);
					// If we can't decode, throw an error
					if ($jsonData == null) {
						print "Error decoding json";
						return;
					}
					foreach ($jsonData->keys as $k) {
						$send = array();
						$send['key'] = $k;
						$response2 = $client2->request('POST','',[
						'json' => $send
						]);
						if ($response2->getStatusCode() == 200) {
							//  Grab the body from our response
							$s=$response2->getBody();
							//  Decode the JSON
							$jsonData2=json_decode($s);
							// If we can't decode, throw an error
							if ($jsonData2 == null) {
								print "Error decoding json";
								return;
							}
							print ("<tr><td>" . $k . "</td><td>" . $jsonData2->value . "</td></tr>");
						}
					}
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
				<a class="btn btn-info" href="index.php?cmd=logout">Logout</a>
			</div>
			<div class="col-lg-3"></div>
		</div>
		</div>
	<footer>
		<div class="jumbotron" style="text-align:center;background-color:#DAF7A6;margin-bottom:0px;">
			<p>Noah Dunn</p>
			<p>Assignment: PHP + CAS</p>
			<p>Date: 2/28/2020 </p>
		</div> <!-- This ends the footer block -->
	</footer> 
  </body>