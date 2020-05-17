<?php
/*
* Name: Noah Dunn 
* Instructor: Dr. Campbell
* Class: CSE 551
* Date: 03/06/2020
* Assignment: REST Server
* Sources: Dr. Campbell, My Previous Assignments
*/



//the following is used to allow any site to access our rest api
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET,POST,PUSH,OPTIONS");
header("content-type: application/json");
header("Access-Control-Allow-Headers: Content-Type");

require_once('vendor/autoload.php');
require_once('vendor/apereo/phpcas/CAS.php');
require_once('wikiModel.php');
use GuzzleHttp\Client;

function sendJson($result) {
	$returnData = array();
	//$returnData['status'] = $status;
	//$returnData['msg'] = $msg;
	foreach ($result as $k=>$v) {
		$returnData[$k] = $v;
	}

	print json_encode($returnData);
	exit;
}

//parse parts
if (isset($_SERVER['PATH_INFO'])) {
	$parts = explode("/",$_SERVER['PATH_INFO']);
	//sanitize
	for ($i=0;$i<count($parts);$i++) {
		$parts[$i] = htmlspecialchars($parts[$i]);
	}
} else {
	$parts = array();
}

array_shift($parts);	//get rid of first part of url which is bogus
//get method type

$method = strtolower($_SERVER['REQUEST_METHOD']);

if ($method == "options") {
	exit;
}

// First GET call, grabs all available titles from the wiki database
// and their primary key
if ($method=="get" &&  sizeof($parts) == 3 && $parts[0] == "api"  && $parts[1] == "v1" && $parts[2] == "wiki") {
	// Grab the articles
	$articles = getArticles();
	//  Slap them in an array called keys
	$retData = array("keys"=>$articles);
	sendJSON($retData);
}

// Delete all the information from a given wiki entry in the database 
// given a primary key that corresponds with that entry.
elseif ($method=="delete" &&  sizeof($parts) == 3 && $parts[0] == "api"  && $parts[1] == "v1" && $parts[2] == "wiki") {
	$json_body = array();
	# Get JSON as a string
	$json_str = file_get_contents('php://input');

	# Get as an object
	$json_body = json_decode($json_str,true);
	
	// Our respondant array
	$result_array = array();
	// If they provided a delete pk 
	if(isset($json_body['pk'])){
		delete($json_body['pk'] ,$addPass);
		$result_array['status'] = '200 OK';
		$result_array['msg'] = 'The key ' . $json_body['pk'] . 'was deleted';
	}
	// If they didn't provide the delete pk
	else {
		$result_array['status'] = 'FAIL';
		$result_array['msg'] = 'That key does not exist';
	}	
	sendJson($result_array);
}

// Obtains the title, rating, reason, and the number of times this wiki entry has been accessed from the wiki
// database for a given primary key in the path.
elseif ($method=="get" &&  sizeof($parts) == 4 && $parts[0] == "api"  && $parts[1] == "v1" && $parts[2] == "wiki") {
	$value= getFullArticle(htmlspecialchars($parts[3]));
	$retData = array("value"=>$value);
	error_log("key=" . $parts[3] . " response= " . $value);

	sendJSON($retData);
}
elseif ($method=="put" &&  sizeof($parts) == 3 && $parts[0] == "api"  && $parts[1] == "v1" && $parts[2] == "wiki") {
	$json_body = array();
	# Get JSON as a string
	$json_str = file_get_contents('php://input');

	# Get as an object
	$json_body = json_decode($json_str,true);
	
	
	if(isset($json_body['title']) && isset($json_body['rating']) && isset($json_body['reason'])){
		
		$client = new Client([
				// Base URI is used with relative requests
				'base_uri' => 'https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=' . $json_body['title'],
				// You can set any number of default request options.
				'timeout'  => 2.0,
			]);

			//make get request
			$response = $client->request('GET','');

			if ($response->getStatusCode() == 200) {
				//  Grab the body from our response
				$s=$response->getBody();
				//  Decode the JSON
				$jsonData=json_decode($s, true);
				// If we can't decode, throw an error
				if ($jsonData == null) {
					print "Error decoding json";
					return;
				}
				//  See if the wiki article actually exists, -1 on pages means it doesn't
				$existanceCheck = $jsonData['query']['pages']['-1'];
			
				$result_array = array();
				// Check if the -1 registers or not
				if(isset($existanceCheck)){
					$result_array['status'] = 'FAIL';
					$result_array['msg'] = 'That wiki page does not exist';
				}
				else {
					add($json_body ,$addPass);
					$result_array['status'] = '200 OK';
					$result_array['msg'] = 'The wiki page was found and added';
				}
				sendJson($result_array);
			}

		}
	
}
elseif ($method=="get" &&  sizeof($parts) == 3 && $parts[0] == "api"  && $parts[1] == "v1"  && $parts[2] == "temp") {
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
			$tempRay = array();
			$tempRay['temp'] = $_SESSION['weather'];
			sendJson($tempRay);
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
				$tempRay = array();
				$tempRay['temp'] = $_SESSION['weather'];
				sendJson($tempRay);
			}

		}
		 
		// Update the timout field with the current time.
		if(!isset($_SESSION['timeout'])){
			$_SESSION['timeout'] = time();
		}

}
?>