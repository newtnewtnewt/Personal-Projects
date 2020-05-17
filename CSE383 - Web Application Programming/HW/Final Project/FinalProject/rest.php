<?php
/*
* Author: Noah Dunn 
* Class: CSE 383
* Final Project REST API
* With assistance from the following resources:
* Dr. Campbell, Postman 
* https://dev.mysql.com/doc/refman/5.5/en/creating-tables.html
https://www.eversql.com/sql-syntax-check-validator/
*/
require("userModel.php");
header("content-type: application/json");

/*
*  Function provided by Dr. Campbell, this method
*  generates na appropriate JSON object given a temporary
*  file provided as input to PHP
*
*/

function getJson() {
	//   Read the JSON in as a string
	$jsonStringIn = file_get_contents('php://input');
	//  Create an array object for the JSON object
	$json = array();
	//  Build the response if needed
	$response = array();
	try {
		//  We attempt to decode what was provided, in order to see if its actually JSON
		$json = json_decode($jsonStringIn,true);
		return $json;
	}
	catch (Exception $e) {
		//  If the conversion fails, we provided the client with a message indicating why 
		header("HTTP/1.0 500 Invalid content -> probably invalid JSON format");
		$response['status'] = "fail";
		$response['message'] = $e->getMessage();
		print json_encode($response);
		exit;
	}
}

function getToken(){
	$secret = "campbellschickennoodlesoup";
	$time = Date("YMDh");
	$token = hash_hmac("ripemd160", "$time", "$secret");
	return $token;
}

//  Any calls to this WebPage had to come in the form of an HTTP request
$method = $_SERVER['REQUEST_METHOD'];
$token = getToken();

if(isset($_SERVER['PATH_INFO'])){
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS, PUT, DELETE');
	header("Access-Control-Allow-Headers: {$_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']}");
	if($_SERVER['REQUEST_METHOD'] == "OPTIONS") {	//this check is optional and allows us to control access
		header("Content-Length: 0");
		header("Content-Type: text/plain");
		exit(0);
	}

	//  This breaks the user's request into an array 
	$pathArray = explode('/', $_SERVER['PATH_INFO']);
	//  The version v1 is what we expect
	$version = $pathArray[1];
	//  The command user is what we expect
	$command = $pathArray[2];
	//  In case they send a delete request
	$pkDelete = "";
	//  If a delete is sent, save the desired Primary Key
	if(count($pathArray) > 3 && $method == "DELETE"){
		$pkDelete = $pathArray[3];
	}
	//  Here comes the 3 methods that our API accepts, GET, PUT, DELETE
	//  GET allows us to display all Links in the database, video or otherwise

	if ($method == 'GET') {
		//  If the request is a GET and the user provided the quickLinks path.
		if($version == "v1" && $command == "quickLinks"){
			//  Get a full list of our Quick Links
			$allLinks = getLinks();
			//  Begin our JSON object creation
			$response=array();
			//  Set our response status to OK
			$response['status'] = "OK";
			//  Indicate in the JSON that Links were retrieved
			$response['msg'] = "Links Retrieved";
			//  Make the quickLinks portion of the body equal to the output of getLinks
			$response['quickLinks'] = $allLinks;
			//  Indicate the process went well
			header("HTTP/1.1 200 OK");
			//  Display our response
			print json_encode($response);
		}
		else if($version == "v1" && $command == "videoLinks"){
			//  Get a full list of our videos
			$allVideos = getVideos();
			//  Begin our JSON object creation
			$response=array();
			//  Set our response status to OK
			$response['status'] = "OK";
			//  Indicate in the JSON that Links were retrieved
			$response['msg'] = "Videos Retrieved";
			//  Make the videoLinks portion of the body equal to the output of getVideos
			$response['videoLinks'] = $allVideos;
			//  Indicate the process went well
			header("HTTP/1.1 200 OK");
			//  Display our response
			print json_encode($response);
		}
		//  If the user provided a bad path
		else{
			//  Display an error message, throw a 405 error header in
			$response=array();
			$response['status'] = "ERROR";
			$response['msg'] = "You did not correctly place the GET request URL in the form rest.php/v1/videoLinks or rest.php/v1/quickLinks ";
			header("HTTP/1.1 405 Invalid method");
			print json_encode($response);
		}
	//  Most of this is verbatim the GET request
	
		//  PUT is called in case we want to add Links or Videos 
	} else if ($method == "PUT") {	
		// Check valid pathing
		if($version == "v1" && $command == "quickLinks"){
			//  Grab JSON params title and url
			$body = getJson();
			if(empty($body['token']) || ($body['token'] != $token)){
				$response['status'] = "Nice Try";
				$response['msg'] = "Nothing happened";
				header("HTTP/1.1 200 OK");
				print json_encode($response);
			    exit();
			}
			else{
				//  Update the password in the DB
				if(!empty($body['title']) && !empty($body['url'])) {
					$title = $body['title'];
					$url = $body['url'];
					addLink($title, $url);
				}
				//  Prep and send the response
				$response=array();
				$response['status'] = "OK";
				$response['msg'] = "Added Quick Link Successfully";
				header("HTTP/1.1 200 OK");
				print json_encode($response);
			}
		} else if($version == "v1" && $command == "videoLinks") { 
			//  Grab JSON params for title and videoID
			$body = getJson();
			$title = $body['title'];
			$videoID = $body['videoID'];
			//  Update the password in the DB
			if(!empty($body['title']) && !empty($videoID)) {
				$title = $body['title'];
				$videoID = $body['videoID'];
				addVideo($title, $videoID);
			}
			//  Prep and send the response
			$response=array();
			$response['status'] = "OK";
			$response['msg'] = "Added Video Link Successfully";
			header("HTTP/1.1 200 OK");
			print json_encode($response);
		} else {
			//  If the passing is off display an error message
			$response=array();
			$response['status'] = "ERROR";
			$response['msg'] = "You did not correctly place the PUT request URL in the form rest.php/v1/videoLinks or rest.php/v1/quickLinks";
			header("HTTP/1.1 405 Invalid method");
			print json_encode($response);
		}	
		// We can use DELETE calls to remove users from our DB
	} else if ($method == "DELETE") {
		//  If the pathing is right and the user provides a primary key
		$body = getJson();
		if($version == "v1" && $command == "quickLinks" && $pkDelete != ""){
			//  Delete the primary key that was provided
			if(empty($body['token']) || $body['token'] != $token){
				$response=array();
				$response['status'] = "Nice Try";
				$response['msg'] = "Nothing Happened";
				header("HTTP/1.1 200 OK");
				print json_encode($response);
			    exit();
			}
			else {
				deleteLink($pkDelete);
				//  Prep and send a response
				$response=array();
				$response['status'] = "OK";
				$response['msg'] = "Link Deleted";
				header("HTTP/1.1 200 OK");
				print json_encode($response);
			}
		} else if($version == "v1" && $command == "videoLinks" && $pkDelete != ""){
			//  Delete the primary key that was provided
			deleteVideo($pkDelete);
			//  Prep and send a response
			$response=array();
			$response['status'] = "OK";
			$response['msg'] = "User Deleted";
			header("HTTP/1.1 200 OK");
			print json_encode($response);
		} else {
			// Prep the ERROR response
			$response=array();
			$response['status'] = "ERROR";
			$response['msg'] = "You did not correctly place the DELETE request URL in the form rest.php/v1/user/<UID>";
			header("HTTP/1.1 405 Invalid method");
			print json_encode($response);
		}

	} else {
		 //  Our REST API only supports the above method
		 header("HTTP/1.1 405 Invalid method");
	}
}
else {
	//  Our REST API only supports the /v1/user path right now
	header("HTTP/1.1 405 Invalid method");
}

?>