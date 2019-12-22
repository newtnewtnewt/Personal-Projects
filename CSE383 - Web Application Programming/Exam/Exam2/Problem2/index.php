<?php 
/*
* Author: Noah Dunn 
* Class: CSE 383
* Exam Question #2
* With assistance from the following resources:
* W3 Schools, Dr. Campbell
*/

for($i = 0; $i < 5; $i++){
	print randomNumber(10);
	print "<br />";
}


function randomNumber($n) {
	$randomNumberString = "";
	for($i = 0; $i < $n; $i++){
		$randomNumberString .= rand(0, 9);
	}
	return $randomNumberString;
}

?>