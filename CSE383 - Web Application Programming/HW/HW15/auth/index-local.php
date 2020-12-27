<!-- 
Noah Dunn
CSE 383
Dr. Campbell
Assignment: 11/6/2019 Authentication
Resources:
Dr. Campbell, W3 Schools,
https://www.php.net/manual/en/function.password-verify.php
--->
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content=
	"width=device-width, initial-scale=1">
	<link rel="stylesheet" href= "https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"/>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"> </script>
	<script src= "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src= "https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js">
  </script>
 
	<!-- <script src = "displayForm.js" /> -->
  </head>
  <body>
	<?php
	session_start();
	
	function getVar($name) {
		if (!isset($_REQUEST[$name])) {
			return "";
		}
		else return htmlspecialchars($_REQUEST[$name]);
    }
	
	function checkAccount($user, $passwd) {
		global $mysqli;
		if ($user == "" || $passwd == "")
			return "Error - invalid inputs";
		$sql = "SELECT user, password FROM auth WHERE auth.user = ?";
		$stmt = $mysqli->prepare($sql);
		if (!$stmt)
		{
			print "Error on prepare in makeAccount";
			return "Failed on prepare";
		}

		$huser = htmlspecialchars($user);
		$stmt->bind_param("s",$huser);
		$stmt->execute();
		$stmt->bind_result($userNameDB, $passwordDB);
		$count = 0;
		while($stmt->fetch()){
			if(password_verify($passwd, $passwordDB)){
				return ("OK");
			}
		}
		return("Username/Password combination never found");
		
	}
	
	$usrName = getVar("user");
	$pass = getVar("password");
	
	require_once("passwd.php");

	$mysqli = new mysqli('localhost',$user,$pwd,$database);
	
	if (!$mysqli) {
		print "Error connecting to db";
		die;
	}
	
	
	if(isset($_REQUEST['cmd']) && $_REQUEST['cmd'] == submit){
		
		if(checkAccount($usrName, $pass) == "OK"){
			$_SESSION['user'] = $usrName;
		}
		else{
			$_SESSION['user'] = "";
		}
	}
	
	?>
	<h1 style="text-align:center;">Local Database Checker</h1>
	<br />
	<form class="form-inline" action="index-local.php" method="post" >
	<div class="container" id="hidForm">
		<!-- Grabbing username as input -->
		<div class="row justify-content-md-center">
			<div class="col-lg-4" ></div>
			<div class="col-lg-2 text-right">
				<label for="user">Username:</label> 
			</div>
			
			<div class="col-lg-6 text-left">
				<input type="text" class="form-control" name="user" id="user" required />
			</div>		
		</div>
		<!-- Grabbing password as input -->
		<div class="row justify-content-md-center">
			<div class="col-lg-4" ></div>
			<div class="col-lg-2 text-right">
				<label for="pass">Password:</label> 
			</div>
			
			<div class="col-lg-6 text-left">
				<input type="password" class="form-control" name="password" id="password" required />
			</div>		
		</div>

		
		<!-- Trigger the submission of the form upon button input -->
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<input button type="submit" class="btn btn-danger"></button>
			</div> 
			<div class="col-md-6">
			<?php if(isset($_SESSION['user']) && $_SESSION['user'] != "") { print "Success! Current user: " . $_SESSION['user']; } ?>
			</div>
		</div>
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<a href='index.html'>Back to home page</a>
			<div class="col-md-6"></div>
		<div><input type="hidden" name="cmd" value="submit"  hidden /> </div>
		
	</div>
	</form>
  </body>
</html>
