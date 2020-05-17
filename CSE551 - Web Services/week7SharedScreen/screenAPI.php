<?php
/*
   scott campbell
   rest api for screen
 */


//the following is used to allow any site to access our rest api

header("Access-Control-Allow-Origin: *"); header("Access-Control-Allow-Methods: GET,POST,PUSH,OPTIONS"); header("content-type: application/json");
header("Access-Control-Allow-Headers: Content-Type");

require_once("db.php");

function sendJson($status,$msg,$result) {
	$returnData = array();
	$returnData['status'] = $status;
	$returnData['msg'] = $msg;
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

if ($method=="get" &&  sizeof($parts) == 0) {
	$points = getPoints();
	sendJson("OK","",$points);
}
if($method=="get" && sizeof($parts) == 1){
	// Load our current user into the DB
	loadUser($parts[0]);
	// Grab all users that are available
	$users = loadUsers();
	//  Send the user list back to the JS
	sendJson("OK", "", array("users" => $users));
}

if ($method=="delete" &&  sizeof($parts) == 0) {
	clearPoints();
	sendJson("OK","",array());
}

else if ($method=="get" &&  sizeof($parts) == 4) {
	addPoint($parts[0],$parts[1],$parts[2],$parts[3],0,0,0);
	sendJSON("ok","",$retData);
}
else if ($method=="get" &&  sizeof($parts) == 7) {
	addPoint($parts[0],$parts[1],$parts[2],$parts[3],$parts[4],$parts[5],$parts[6]);
	sendJSON("ok","",$retData);
}

else if ($method=="post" && sizeof($parts) == 0) {
	$json_str = file_get_contents('php://input');
	$json = json_decode($json_str);
	addPoint($json->x,$json->y,$json->x1,$json->y1,$json->r,$json->g,$json->b);
	$retData=[];
	sendJSON("ok","",$retData);
}

else if ($method=="post" && sizeof($parts) == 1) {
	$json_str = file_get_contents('php://input');
	$json = json_decode($json_str);
	//390, 390
	$r = $json->r;
	$g = $json->g;
	$b = $json->b;
	$startX = $json->x;
	$startY = $json->y;
	$startX1 = $json->x1;
	$startY1 = $json->y1;
	$currX1 = $startX1;
	$currY1 = $startY1;
	for($currX = $startX; $currX < $startX + 40; $currX += 10, $currX1 += 10){
		for($currY = $startY; $currY < $startY + 40; $currY += 10, $currY1 += 10){
			if($currX1 < 390 && $currY1 < 390) {
				error_log("PTOF x1:" . $currX . " y1:" . $currY . " x2:" . $currX1 . " y2: " . $currY1);
				addPoint($currX, $currY, $currX1, $currY1, $r, $g, $b);
			}	
		}
		$currY1 = $startY1;
	}
	//addPoint($json->x,$json->y,$json->x1,$json->y1,$json->r,$json->g,$json->b);
	$retData=[];
	sendJSON("ok","",$retData);
}






header($_SERVER['SERVER_PROTOCOL'] . ' 404 Invalid URL' , true, 400);
?>
