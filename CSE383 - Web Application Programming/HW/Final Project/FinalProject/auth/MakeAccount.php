<?php
/*
 * Scott Campbell
 * Make Accounts
 *
 * run this script to create database:
 *
 * create table auth (pk int not null auto_increment, user varchar(100), password varchar(100), email varchar(100), primary key(pk));
 *
 * passwd.php file needs $user, $pwd, $database
 * */

require_once("passwd.php");

$mysqli = new mysqli('localhost',$user,$pwd,$database);
if (!$mysqli) {
	print "Error connecting to db";
	die;
}


//warning -> does not check to ensure it exists first
function makeAccount($user,$passwd,$email) {
	global $mysqli;
	if ($user == "" || $passwd == "")
		return "Error - invalid inputs";
	$sql = "insert into auth(user,password,email) values (?,?,?)";
	$stmt = $mysqli->prepare($sql);
	if (!$stmt)
	{
		print "Error on prepare in makeAccount";
		return "Failed on prepare";
	}

	$huser =htmlspecialchars($user);
	$ppassword =password_hash($passwd,PASSWORD_DEFAULT);
	$stmt->bind_param("sss",$huser,$ppassword,$email);
	$stmt->execute();
	return("ok");
}

$msg = "Page called without required parameters";

if (isset($_REQUEST['user']) && isset($_REQUEST['password']) && isset($_REQUEST['email']) && ($_REQUEST['user'] != "") && ($_REQUEST['password'] != "") && ($_REQUEST['email'] != "") ) {
	$r=makeAccount($_REQUEST['user'],$_REQUEST['password'],$_REQUEST['email']);
	if ($r=="ok") {
		$msg = "Account created";
	}
	else $msg = "Account Did not get created";
}

?>
<!doctype html>
<html>
<head>
<title>Auth</title>
</head>
<body>
<h1>Create Account Result</h1>
<div>
<?php
print $msg;
?>
</div>
<a href='index.html'>Back to home page</a>
</body>
</html>



