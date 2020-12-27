<!--
Name: Noah Dunn 
Instructor: Dr. Scott Campbell
Class: CSE 551
Date: 2/12/2020
Assignment: PHP Recap
With assistance from:
Dr. Campbell, 
My Previous CSE383 work,
W3 Schools, and the Following:
https://stackoverflow.com/questions/35163164/how-to-center-content-in-a-bootstrap-column
http://www.tizag.com/htmlT/htmlbr.php
https://parade.com/947956/parade/riddles/
https://www.daytondailynews.com/news/miami-highest-paid-employees-2017-team-payroll-project/DWQSAKMcoSQXtKMKjiiXSJ/
-->
<?php
require_once("info.php");

?>
<!DOCTYPE html>
<html lang="en">
  <head>
	<!-- The body of text establishing our string encoding and the version of bootstrap we are using -->
    <title>PHP Intro</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
  </head>
  
  <body>
	<!-- Using a header jumbotron in order to achieve desired look -->
	<div class="page-header" style="margin-top:0px;background-color:#DAF7A6;">
	 <div class="jumbotron" style="text-align:center;background-color:#DAF7A6;">
		<h1>Keys and Vals</h1>
		<img src="NewtFace.png" alt="This is my newt logo" />
		
	</div> <!-- This ends the jumbotron tag -->
	</div>
	<!-- This kicks off our grid object -->
	<div class="container">
	  <div class="row">
		<div class="col-lg-3">
		</div> 
		<div class="col-lg-6">
			<div id="msg"  class="alert alert-info" style="display:none"></div>
			<table id="keys" class="table">
			  <tr>
				<th>Key</th>
				<th>Value</th>
			  </tr>		 
			  <tbody id="tableContent">
			  <?php 
			    // Grab an array of all the keys
				$keys = getKeys();
				// Get the total number of keys first to avoid constant execution
				$totalCount = count($keys);
				//  Loop through the keys and print key-value pair rows
				for($i = 0; $i < $totalCount; $i++){
					$rowOutput = "<tr><td>" . $keys[$i] . "</td><td>" . getValue($keys[$i]) . "</td></tr>";
					print $rowOutput;
				}
			  ?>
			  </tbody>
			</table>
		</div>
		<div class="col-lg-3">
		</div> <!-- This ends the first left column block -->
		</div>
		</div>
	<footer>
		<div class="jumbotron" style="text-align:center;background-color:#DAF7A6;margin-bottom:0px;">
			<p>Noah Dunn</p>
			<p>Assignment: PHP Recap</p>
			<p>Date: 2/12/2020 </p>
		</div> <!-- This ends the footer block -->
	</footer> 
  </body>