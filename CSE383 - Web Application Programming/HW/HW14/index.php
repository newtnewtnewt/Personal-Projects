<!-- 
Noah Dunn
CSE 383
Dr. Campbell
Assignment: 11/1/2019 Experimenting with MYSQL
Resources:
Dr. Campbell, W3 Schools,
https://www.a2hosting.com/kb/developer-corner/mysql/managing-mysql-databases-and-users-from-the-command-line
--->


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="generator"
    content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
    <meta charset="utf-8" />
    <title>MYSQL First Run</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
    crossorigin="anonymous"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
    <link rel="stylesheet" href="style.css" />
    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  </head>
  <body>
	<?php 
		//  We stash the user's desired income bracket they throw in
		$income=0;
		
		if(isset($_GET['income'])){
			$income = $_GET['income'];
		}
		//  Store the passwords for our mysql database in a seperate password file
		require_once('passwords.php');
		$mysqli = new mysqli("localhost",$user,$pwd,"toy_dataset");

		//  If we get an error, we want to print it 
		if ($mysqli->connect_errno) {
			die("Can't connect to db -> did you update password?");
		}
		//  Print rows of data
		function printData(){
			//  Create a global for the connection mysql connection
			global $mysqli;
			//  A way to stash income
			global $income;
			
			//  A query to grab all the relevant information from each row given a particular income limit
			$query = "select city, Gender, age, income, illness from toy_dataset where income > ? Limit 1000";
			//  Prep the query to prevent malicious input
			if(!$stmt = $mysqli->prepare($query)){
				die("Error on getData Select");
			}
			//  Bind the income to the ? above
			$stmt->bind_param("i", $income);
			//  Execute the query
			$stmt->execute();
			//  Store the values of each row into 5 seperate 
			$stmt->bind_result($city, $gender, $age, $income, $illness);
			// Grab rows and print them in the proper format in the table
			while($stmt->fetch()){
				print "<tr><td>$city</td><td>$gender</td><td>$age</td><td>$income</td><td>$illness</td> </tr>";
			}
			
		}
		//  Prints the summary information
		function printSummary(){
			//  MYSQL and income global
			global $mysqli;
			global $income;
			
			//  A query to grab all the relevant counts and averages
			$query = "select Gender,count(id),avg(income),avg(age),avg(illness) from toy_dataset where income > ?  group by Gender;";
			//  Preps the query to void our invalid information
			if(!$stmt = $mysqli->prepare($query)){
				die("Counts were invalid");
			}
			//  Bind the parameter that the user inputs in a GET request 
			$stmt->bind_param("i", $income);
			//  Execute the query
			$stmt->execute();
			//  Bind the colums to these values for printing
			$stmt->bind_result($gender, $idCount, $incomeAvg, $ageAvg, $illnessAvg);
			while($stmt->fetch()){
				//  Print it in the correct format
				print "<tr><td>$gender</td><td>$idCount</td><td>$incomeAvg</td><td>$ageAvg</td><td>$illnessAvg</td> </tr>";
			}
			
		}
	?>
    <div id='MainPage'>
      <h1>DB1</h1>
      <div id="summary">
        <h2>Summary</h2>
        <table class='table loadTable'>
          <thead>
            <tr>
              <th>Gender</th>
              <th>Count</th>
              <th>Age</th>
              <th>Income</th>
              <th>Illness</th>
			  <?php printSummary() ?>
            </tr>
          </thead>
        </table>
      </div>
      <div id="data">
        <h2>Data</h2>
        <table class='table'>
          <thead>
            <tr>
              <th>City</th>
              <th>Gender</th>
              <th>Age</th>
              <th>Income</th>
              <th>Illness</th>
			  <?php printData() ?>
            </tr>
          </thead>
        </table>
      </div>
    </div>
  </body>
</html>
