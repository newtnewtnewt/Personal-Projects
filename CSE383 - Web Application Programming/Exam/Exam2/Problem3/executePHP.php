<!-- Author: Noah Dunn 
  Class: CSE 383
  Homework 10-28-2019: PHP Assignment 
 With assistance from the following resources:
 W3 Schools, Dr. Campbell
 https://stackoverflow.com/questions/20922863/how-to-match-any-word-in-a-string-with-regex-in-php
 https://stackoverflow.com/questions/19271381/correctly-determine-if-date-string-is-a-valid-date-in-that-format
 https://getbootstrap.com/docs/4.0/components/alerts/
 https://www.php.net/manual/en/function.intval.php
 -->
<!DOCTYPE html>
<html lang="en">
<?php
   // A function to parse our variable inputs through htmlspecialchars
   function getVar($name) {
	 if (!isset($_REQUEST[$name])) {
		return "";
	 }
	 else return htmlspecialchars($_REQUEST[$name]);
   }
   $total = 0;
   $fieldA = getVar("A");
   $fieldB = getVar("B");
   $fieldC = getVar("C");
   $fieldD = getVar("D");
   $total = intval($fieldA) + intval($fieldB) + intval($fieldC) + intval($fieldD);
   
   
?>
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
    <form class="form-inline" action="executePHP.php" method="post" >
    <div class="container">
		<!-- Our title all nice and centered -->
		<div class="row justify-content-center">
			<div class="col-lg-4" ></div>
			<div class="col-lg-6" >
				<h1 class="text-left bold">Problem 3</h1>
			</div>
			<div class="col-lg-4"></div>
		</div>
		
		<!-- Grabbing user's UID as name uid -->
		<div class="row justify-content-md-center">
			<div class="col-lg-4" ></div>
			<div class="col-lg-2 text-right">
				<label for="A">Field A: </label> 
			</div>
			
			<div class="col-lg-6 text-left">
				<input type="text" class="form-control" name="A" id="A" required />
			</div>	
		</div>
		<!-- Subtitle for UID -->
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label class = "graySubs">This is Field A</label>
			</div> 
			<div class="col-md-6"></div>
		</div>

		<!-- Title of Display Message -->
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label for="B">Field B: </label> 
			</div>
			<div class="col-md-6">
				<input type="text" name="B" class="form-control" id="B" required />
			</div>

		</div>
		
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>	
			<div class="col-md-2 text-right">
				<label class="graySubs">This is Field B</label>
			</div> 
			<div class="col-md-6"></div>
		</div>
		
		<!-- The message we want to send -->
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label for="C">Field C: </label> 
			</div> 
			<div class="col-md-6">
				<input type="text" class="form-control" name="C" rows="3" id="C" ></textarea>
			</div>
		</div>
			
		<!-- A subtitle for our message field -->
		<div class="row justify-content-md-center">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label class="graySubs"> Field C </label>
			</div> 
			<div class="col-lg-2 text-right"> </div>			
		</div>
		
		<!-- The display date for the message we want to send -->
		<div class="row justify-content-md-center">
			<div class="col-lg-4" ></div>
			<div class="col-lg-2 text-right">
				<label for="D">Field D: </label> 
			</div>
			
			<div class="col-lg-6 text-left">
				<input type="text" class="form-control" name="D" id="D" required />
			</div>		

		</div>
		<!-- Subtitle for the display date --> 
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label class="graySubs">This is Field D</label>
			</div> 
			<div class="col-md-6"></div>
		</div>
		
		<!-- Trigger the submission of the form upon button input -->
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<input button type="submit" class="btn btn-default"></button>
			</div> 
			<div class="col-md-6"></div>
		</div>
		<h1> Total = <?php print $total ?> </h1>
		<div><input type="hidden" name="cmd" value="submit"  hidden /> </div>
	</div>
	
    </form>
  </body>
</html>


