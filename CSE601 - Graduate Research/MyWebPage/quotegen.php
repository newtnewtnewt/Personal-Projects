<?php
function getQuote(){
	//  Open the quotes text file
	$input = fopen('quotes.txt', 'r');
	
	// Count total number, and then print a random quote
	$count = 0;
	while(!feof($input)){
		$count++;
		fgets($input);
	}
	fclose($input);
	$numOfRuns = mt_rand(0, $count - 1);
	$input = fopen('quotes.txt', 'r');
	$curentQuote = "";
	for($i = 0; $i < $numOfRuns; $i++){
		$currentQuote = fgets($input);
	}
	$currentQuote = fgets($input);
	return $currentQuote;
}

?>