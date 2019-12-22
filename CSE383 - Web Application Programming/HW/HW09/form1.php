<!doctype html>
<!--
Scott Campbell
CSE383
Fall 19
Form Assignment

Assumes the form is sending the following fields:
uid -> text
gradYear -> graduation year -> radio button (use 2019,2020,2021,2022,2023,2024,0 (label = other))
birthYear -> use select box (1960->now)
cse -> Are you a CSE major-> use checkbox
car -> Do you own a car -> checkbox
mobileDevices -> Number of mobile devices (including laptops, ipads, etc) you own -> text input
quote -> Favorite quote -> text area
quoteCite -> Source of quote (site, who said it, etc) -> textbox
cmd -> Hidden field with value = "submit"
MUST use POST
-->
<?php 
function getVar($name) {
	if (!isset($_REQUEST[$name])) {
		return "";
	}
	else return htmlspecialchars($_REQUEST[$name]);
}

//connect to DB
@$mysqli = new mysqli("localhost","383","55gf343asdf","383");

if ($mysqli->connect_errno) {
	die("Can't connect to db -> did you update password?");
}
// return average age
function avgAge() {
	global $mysqli;
	$result = $mysqli->query("select avg(birthYear) from form where birthYear>0");
	if (!$result) {
		print ($mysqli->error);
		return "";
	} else {
		$a = $result->fetch_array();
		return 2016-$a[0];
	}
}
// return average grad 
function gradYear() {
	global $mysqli;
	$result = $mysqli->query("select avg(gradYear) from form where gradYear>0");
	if (!$result) {
		print ($mysqli->error);
		return "";
	} else {
		$a = $result->fetch_array();
		return $a[0];
	}
}
// return num CSE
function cse() {
	global $mysqli;
	$result = $mysqli->query("select count(pk) from form where cse!=''");
	if (!$result) {
		print ($mysqli->error);
		return "";
	} else {
		$a = $result->fetch_array();
		return $a[0];
	}
}

//return last quote
function lastQuote() {
	global $mysqli;
	$result = $mysqli->query("select quote from form order by pk desc");
	if (!$result) {
		print ($mysqli->error);
		return "";
	} else {
		$a = $result->fetch_array();
		return $a[0];
	}

}
// return random quote
function rndQuote() {
	global $mysqli;
	$result = $mysqli->query("select quote from form order by rand() limit 1");
	if (!$result) {
		print ($mysqli->error);
		return "";
	} else {
		$a = $result->fetch_array();
		return $a[0];
	}

}

//return sum of all devices
function numDevices() {
	global $mysqli;
	$result = $mysqli->query("select sum(mobileDevices) from form");
	if (!$result) {
		print ($mysqli->error);
		return "";
	} else {
		$a = $result->fetch_array();
		return $a[0];
	}

}


//crate default values

$cmd = getVar("cmd");
$uid="";
$gradYear = "";
$birthYear = "";
$cse="";
$car="";
$mobileDevices="";
$quote="";
$quoteCite="";

//if form submittal submit
if ($cmd=="submit") {
	$uid = getVar("uid");
	$gradYear = getVar("gradYear");
	$birthYear = getVar("birthYear");
	$cse = getVar("cse");
	$car = getVar("car");
	$mobileDevices = getVar("mobileDevices");
    if (!is_numeric($mobileDevices))
      $mobileDevices = -1;
	$quote = getVar("quote");
	$quoteCite = getVar("quoteCite");

	$stmt = $mysqli->prepare("insert into form(uid,gradYear,birthYear,cse,car,mobileDevices,quote,quoteCite) values (?,?,?,?,?,?,?,?)");
	if (!$stmt) {
		die("error on prepare statement");
	}
    if (!$stmt->bind_param("siississ",$uid,$gradYear,$birthYear,$cse,$car,$mobileDevices,$quote,$quoteCite)) {
      print "Error on bind";
    } else {
      if (!$stmt->execute()) {
        print "Error on execute";
        print $mysqli->error;
      }
    }
}

?>


<!DOCTYPE html>
<html lang="en">
<head>
<title>Campbest Form 1 Project</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
/* Set height of the grid so .sidenav can be 100% (adjust as needed) */
.row.content {height: 550px}

/* Set gray background color and 100% height */
.sidenav {
	background-color: #f1f1f1;
height: 100%;
}

/* On small screens, set height to 'auto' for the grid */
@media screen and (max-width: 767px) {
	.row.content {height: auto;}
}
</style>
</head>
<body>

<nav class="navbar navbar-inverse visible-xs">
<div class="container-fluid">
<div class="navbar-header">
<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
<span class="icon-bar"></span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
</button>
<a class="navbar-brand" href="#">Logo</a>
</div>
<div class="collapse navbar-collapse" id="myNavbar">
<ul class="nav navbar-nav">
<li class="active"><a href="#">Display Results</a></li>
<li><a href="index.html">List</a></li>
<li><a href="http://miamioh.edu">Miami</a></li>
</ul>
</div>
</div>
</nav>

<div class="container-fluid">
<div class="row content">
<div class="col-sm-3 sidenav hidden-xs">
<h2>Logo</h2>
<ul class="nav nav-pills nav-stacked">
<li class="active"><a href="form1.php">Redisplay Results</a></li>
<li><a href="index.html">Back to form</a></li>
</ul><br>
</div>
<br>

<div class="col-sm-9">
<div class="well">
<h4>You Submitted</h4>
<div>Uid=<?php print $uid;?></div>
<div>Graduation Year=<?php print $gradYear;?></div>
<div>BirthYear=<?php print $birthYear;?></div>
<div>CSE=<?php print $cse;?></div>
<div>Car=<?php print $car;?></div>
<div>Number of mobile devices=<?php print $mobileDevices;?></div>
<div>Quote = <?php print $quote;?></div>
<div>Quote Source = <?php print $quoteCite;?></div>

</div>
<div class="row">
<h2>Average Answers from all users</h2>
<div class="col-sm-3">
<div class="well">
<h4>Total Mobile Devices</h4>
<p><?php  print numDevices();?></p>
</div>
</div>
<div class="col-sm-3">
<div class="well">
<h4>Average Age</h4>
<p><?php  print avgAge();?></p>
</div>
</div>
<div class="col-sm-3">
<div class="well">
<h4>NumCSE</h4>
<p><?php  print cse();?></p>
</div>
</div>
<div class="col-sm-3">
<div class="well">
<h4>gradYear</h4>
<p><?php  print gradYear();?></p>
</div>
</div>
</div>
<div class="row">
<div class="col-sm-4">
<div class="well">
<p>Last Quote Entered by Anyone: <?php  print lastQuote();?></p>
</div>
</div>
<div class="col-sm-4">
<div class="well">
<p>Random Quote: <?php  print rndQuote();?></p>
</div>
</div>
<div class="col-sm-4">
<div class="well">
<p>Another Random Quote: <?php  print rndQuote();?></p>
</div>
</div>
</div>
</div>
</div>
</div>

</body>
</html>
