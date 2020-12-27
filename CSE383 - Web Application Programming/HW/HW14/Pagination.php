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
		//  Begin our session to grab cookies with
		session_start();
		$numPerPage = 5; //  Default value for the amountt of items on a page
		$pageOffset = 0; //  This tells us where we are at in the database

		//  If a request has been made for a certain number of items, fulfill it and save it in sessions
		if (isset($_REQUEST['numPerPage']) && is_numeric($_REQUEST['numPerPage'])) {
			$numPerPage = $_REQUEST['numPerPage'];
			$_SESSION['numPerPage'] = $numPerPage;
		}
		//  This grabs our current page offset and also saves it in a session variable
		if (isset($_REQUEST['pageOffset']) && is_numeric($_REQUEST['pageOffset'])) {
			$pageOffset = $_REQUEST['pageOffset'];
			$_SESSION['pageOffset'] = $pageOffset;
		}
		//  Set a variable for pageOffset to use in the body of this php form
		if (isset($_SESSION['pageOffset'])){
			$pageOffset = $_SESSION['pageOffset'];
		}
		//  Set a variable for the number of items on the page
		if (isset($_SESSION['numPerPage'])){
			$numPerPage = $_SESSION['numPerPage'];
		}
		
		//  This activates when the user goes to the next or previous page
		if (isset($_REQUEST['cmd'])){
			if($_REQUEST['cmd'] == 'prev'){
				//  This manipulates page offset based on current value of numPerPage
				if(isset($_SESSION['pageOffset'])){
					$_SESSION['pageOffset'] = $_SESSION['pageOffset'] - $numPerPage;
					$pageOffset -= $numPerPage;
					//  Avoid backing far beyond the scope of the data
					if($pageOffset < 0){
						$_SESSION['pageOffset'] = 0;
						$pageOffset = 0;
					}
				}
			}
			//  Activates when the user hits the next button 
			else if($_REQUEST['cmd'] == 'next'){
				// Save our new offset value in a session variable
				if(isset($_SESSION['pageOffset'])){
					$_SESSION['pageOffset'] = $_SESSION['pageOffset'] + $numPerPage;
				}
				else{
					$_SESSION['pageOffset'] = $numPerPage;
				}
				$pageOffset += $numPerPage;
			}
		}

		
		
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
		function printData($numPerPage, $pageOffset){
			//  Grab from Session variable if applicable
		
			//  Create a global for the connection mysql connection
			global $mysqli;
			//  A way to stash income
			global $income;
			
			//  A query to grab all the relevant information from each row given a particular income limit
			$query = "select city, Gender, age, income, illness from toy_dataset where income > ? Limit ?, ?";
			//  Prep the query to prevent malicious input
			if(!$stmt = $mysqli->prepare($query)){
				die("Error on getData Select");
			}
			//  Bind the income to the ? above
			$stmt->bind_param("iii", $income, $pageOffset, $numPerPage);
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
			$query = "select Gender,count(id),avg(income),avg(age),avg(illness) from toy_dataset where income > ? group by Gender;";
			//  Preps the query to void our invalid information
			if(!$stmt = $mysqli->prepare($query)){
				die("Counts were invalid");
			}
			//  Bind the parameter that the user inputs in a GET request 
			$stmt->bind_param("i", $income);
			//  Execute the query
			$stmt->execute();
			//  Bind the colums to these values for printing
			$stmt->bind_result($gender, $idCount,  $ageAvg, $incomeAvg, $illnessAvg);
			$counter = 0;
			while($stmt->fetch()){
				$counter++;
				//  Print it in the correct format
				print "<tr><td>$gender</td><td>$idCount</td><td>$incomeAvg</td><td>$ageAvg</td><td>$illnessAvg</td> </tr>";
			}
			return $counter;
			
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
			  <?php printSummary(); ?>
            </tr>
          </thead>
        </table>
      </div>
      <div id="data">
        <h2>Data</h2>
		<h2> Records <?php echo $pageOffset ?> - <?php echo ($pageOffset + $numPerPage - 1) //  Spit the current pages out ?> </h2>
        <table class='table'>
          <thead>
            <tr>
              <th>City</th>
              <th>Gender</th>
              <th>Age</th>
              <th>Income</th>
              <th>Illness</th>
			  <?php printData($numPerPage, $pageOffset); //  Call the main print body?>
            </tr>
          </thead>
        </table>
      </div>
	  <div>
		<?php if ($pageOffset != 0) { // Only generate this when we aren't on the first page?>
		<a href='Pagination.php?cmd=prev'><button>Prev</button></a>
		<?php } ?>
		<a href='Pagination.php?cmd=next'><button>Next</button></a>
		<form method='get' action='Pagination.php' style='position: inline'>
			Num Per Page:
			<input type='text' name='numPerPage' value='5'>
			<input type='submit' name='Submit' value='Update'>
		</form>
	</div>
    </div>
  </body>
</html>
