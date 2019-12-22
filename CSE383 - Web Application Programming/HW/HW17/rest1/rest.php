<?php
/*
* Author: Noah Dunn 
* Class: CSE 383
* Lab 11/13/2019
* With assistance from the following resources:
* Dr. Campbell, Postman 
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


//  Any calls to this WebPage had to come in the form of an HTTP request
$method = $_SERVER['REQUEST_METHOD'];

if (isset($_SERVER['PATH_INFO'])){
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: POST, GET, OPTIONS');	//include needed methods here
	header('Access-Control-Allow-Credentials: true');
	//  Props to Zach Zampa for this line
	header("Access-Control-Allow-Headers: {$_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']}");		//include needed headers here
	header('Access-Control-Max-Age: 1728000');

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
	$userDelete = "";
	//  If a delete is sent, save the desired user
	if(count($pathArray) > 3 && $method == "DELETE"){
		$userDelete = $pathArray[3];
	}
	//  Here comes the 4 methods that our API accepts, GET, POST, PUT, DELETE
	//  GET allows us to display all the users in the DB 
	if ($method == 'GET') {
		//  If the request is a GET and the user provided the right path.
		if($version == "v1" && $command == "user"){
			//  Get a full list of our users
			$allUsers = getUsers();
			//  Begin our JSON object creation
			$response=array();
			//  Set our response status to OK
			$response['status'] = "OK";
			//  Indicate in the JSON that Users were retrieved
			$response['msg'] = "Users Retrieved";
			//  Make the user portion of the body equal to the output of getUsers
			$response['user'] = $allUsers;
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
			$response['msg'] = "You did not correctly place the GET request URL in the form rest.php/v1/user";
			header("HTTP/1.1 405 Invalid method");
			print json_encode($response);
		}
	//  Most of this is verbatim the GET request
	//  POST lets us add a user to the DB
	
	} elseif ($method=="POST") {
		//  Check that the args are valid
		if($version == "v1" && $command == "user"){
			//  In the case of a POST, we need to grab the JSON
			//  the user provided
			$body = getJson();
			//  Grab UID, email, password from what the user provided
			$uid = $body['uid'];
			$email = $body['email'];
			$password = $body['password'];
			//  Add a user to the DB
			addUser($uid, $email, $password);
			//  Prep our response, display it
			$response=array();
			$response['status'] = "OK";
			$response['msg'] = "User Submitted to the Database";
			header("HTTP/1.1 200 OK");
			print json_encode($response);
		}
		else{
			//  If the user gave incorrect pathing, display an error
			$response=array();
			$response['status'] = "ERROR";
			$response['msg'] = "You did not correctly place the POST request URL in the form rest.php/v1/user";
			header("HTTP/1.1 405 Invalid method");
			print json_encode($response);
		}
		//  PUT is called in case we want to update a password
	} else if ($method == "PUT") {	
		// Check valid pathing
		if($version == "v1" && $command == "user"){
			//  Grab JSON params for username and password
			$body = getJson();
			$uid = $body['uid'];
			$password = $body['password'];
			//  Update the password in the DB
			updatePassword($uid, $password);
			//  Prep and send the response
			$response=array();
			$response['status'] = "OK";
			$response['msg'] = "Password Updated";
			header("HTTP/1.1 200 OK");
			print json_encode($response);
		} else{
			//  If the passing is off display an error message
			$response=array();
			$response['status'] = "ERROR";
			$response['msg'] = "You did not correctly place the PUT request URL in the form rest.php/v1/user";
			header("HTTP/1.1 405 Invalid method");
			print json_encode($response);
		}	
		// We can use DELETE calls to remove users from our DB
	} else if ($method == "DELETE") {
		//  If the pathing is right and a user is provide 
		if($version == "v1" && $command == "user" && $userDelete != ""){
			//  Delete the user that was provided
			deleteUser($userDelete);
			//  Prep and send a response
			$response=array();
			$response['status'] = "OK";
			$response['msg'] = "User Deleted";
			header("HTTP/1.1 200 OK");
			print json_encode($response);
		}
		else {
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