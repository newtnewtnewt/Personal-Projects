<!-- 
Noah Dunn
CSE 383
Dr. Campbell
Assignment: 11/4/2019 Advanced MySQL Experimenting
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
    <title>MYSQL Pagination</title>
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
		//  Store the passwords for our mysql database in a seperate password file
		require_once('passwords.php');
		$mysqli = new mysqli("localhost",$user,$pwd,"383");

		//  If we get an error, we want to print it 
		if ($mysqli->connect_errno) {
			die("Can't connect to db -> did you update password?");
		}
		
		
		//  Print rows of data
		function printData($numPerPage, $pageOffset){
			//  Grab from Session variable if applicable
		
			//  Create a global for the connection mysql connection
			global $mysqli;
			//  A way to stash income
			global $income;
			
			//  A query to grab all the relevant information from each row given a particular income limit
			$query = "select pk, title, value, ttt, newInfo, moreInfo, num from exam2";
			//  Prep the query to prevent malicious input
			if(!$stmt = $mysqli->prepare($query)){
				die("Error on getData Select");
			}
			//  Bind the income to the ? above
			//  $stmt->bind_param("ii", $pageOffset, $numPerPage);
			//  Execute the query
			$stmt->execute();
			//  Store the values of each row into 5 seperate 
			$stmt->bind_result($v1, $v2, $v3, $v4, $v5, $v6, $v7);
			// Grab rows and print them in the proper format in the table
			while($stmt->fetch()){
				print "<tr><td>$v1</td><td>$v2</td><td>$v3</td><td>$v4</td><td>$v5</td><td>$v6</td><td>$v7</td></tr>";
			}
			
			
		}
		
		//  Print rows of data
		function printOtherData($numPerPage, $pageOffset){
			//  Grab from Session variable if applicable
		
			//  Create a global for the connection mysql connection
			global $mysqli;
			//  A way to stash income
			global $income;
			
			//  A query to grab all the relevant information from each row given a particular income limit
			$query = "select avg(value) from exam2 where value > 10";
			//  Prep the query to prevent malicious input
			if(!$stmt = $mysqli->prepare($query)){
				die("Error on getData Select");
			}
			//  Bind the income to the ? above
			//  $stmt->bind_param("ii", $pageOffset, $numPerPage);
			//  Execute the query
			$stmt->execute();
			//  Store the values of each row into 5 seperate 
			$stmt->bind_result($v1);
			// Grab rows and print them in the proper format in the table
			while($stmt->fetch()){
				print "<tr><td>$v1</td>";
			}
			
			$query = "select sum(value) from exam2";
			//  Prep the query to prevent malicious input
			if(!$stmt = $mysqli->prepare($query)){
				die("Error on getData Select");
			}
			//  Bind the income to the ? above
			//  $stmt->bind_param("ii", $pageOffset, $numPerPage);
			//  Execute the query
			$stmt->execute();
			//  Store the values of each row into 5 seperate 
			$stmt->bind_result($v2);
			// Grab rows and print them in the proper format in the table
			while($stmt->fetch()){
				print "<td>$v2</td></tr>";
			}
			
			
			
			
		}
	?>
    <div id='MainPage'>
      <div id="data">
        <h2>Data</h2>
        <table class='table'>
          <thead>
            <tr>
              <th>pk</th><th>title</th><th>value</th><th>ttt</th><th>newInfo</th><th>moreInfo</th><th>num</th>
			   	
			  <?php printData($numPerPage, $pageOffset); //  Call the main print body?>
            </tr>
          </thead>
        </table>
		<table class='table'>
          <thead>
            <tr>
              <th>AVG</th>
			  <th>SUM</th>
			  <?php printOtherData($numPerPage, $pageOffset); //  Call the main print body?>
            </tr>
          </thead>
        </table>
      </div>
    </div>
  </body>
</html>
