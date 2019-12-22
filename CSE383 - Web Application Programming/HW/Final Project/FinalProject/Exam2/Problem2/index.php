<!-- Author: Noah Dunn 
  Class: CSE 383
  Homework 10-28-2019: PHP Assignment 
 With assistance from the following resources:
 W3 Schools, Dr. Campbell
 https://stackoverflow.com/questions/1484506/random-color-generator
 -->
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="generator"
    content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
    <title>PHP FORM</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
	<link rel="stylesheet" href="style.css" type="text/css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	
  </head>

<body>
<table style="width:100%">
<?php 
/*
* Author: Noah Dunn 
* Class: CSE 383
* Exam Question #2
* With assistance from the following resources:
* W3 Schools, Dr. Campbell
*/

for($i = 0; $i < 30; $i++){
	print "<tr><td style=\"color:#" . random_color() . "\">" . randomNumber(10) . "\n" . "</td></tr>";
}


function randomNumber($n) {
	$randomNumberString = "";
	for($i = 0; $i < $n; $i++){
		$randomNumberString .= rand(0, 9);
	}
	return $randomNumberString;
}

function random_color_part() {
    return str_pad( dechex( mt_rand( 0, 255 ) ), 2, '0', STR_PAD_LEFT);
}

function random_color() {
    return random_color_part() . random_color_part() . random_color_part();
}



?>
</tr>
</table>
</body>