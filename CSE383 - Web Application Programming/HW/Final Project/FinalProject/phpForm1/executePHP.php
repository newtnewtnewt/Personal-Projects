<!-- Author: Noah Dunn 
  Class: CSE 383
  Homework 10-28-2019: PHP Assignment 
 With assistance from the following resources:
 W3 Schools, Dr. Campbell
 https://stackoverflow.com/questions/20922863/how-to-match-any-word-in-a-string-with-regex-in-php
 https://stackoverflow.com/questions/19271381/correctly-determine-if-date-string-is-a-valid-date-in-that-format
 https://getbootstrap.com/docs/4.0/components/alerts/
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
   //  A way of validating a date in the desired format we want it
   function validateDate($date, $format = 'Y-m-d') {
    $d = DateTime::createFromFormat($format, $date);
    // The Y ( 4 digits year ) returns TRUE for any integer with any number of digits so changing the comparison from == to === fixes the issue.
    return $d && $d->format($format) === $date;
	}
	//  The varialbes we actually care about from the form
	$cmd = getVar("cmd");
	$uid="";
	$title="";
	$message="";
	$displaydate="";
	$uidError=false;
	$titleError=false;
	$messageError=false;
	$displaydateError=false;
	$success = false;
	
	//  If the form shoots off a hidden cmd parameter, we only want to care if its a submit
	if ($cmd=="submit") {
			//  Get our UID from the form
			$uid = getVar("uid");
			$pattern =  "/^(\w+){1}$/";
			//  Check if its a single word with a length greater than 2
			if (!preg_match($pattern, $uid) || !(strlen($uid) >= 2)){
				$uidError = true;
			}
			//  Grab the title from the form
			$title = getVar("title");
			//  Check if the length of the title is between 4 and 30 characters
			if(!(strlen($title) >= 4 && strlen($title) <= 30)){
				$titleError = true;
			}
			//  Grab the message from the form
			$message = getVar("message");
			//  Check if the length of the message is between 4 and 200 characters
			if(!(strlen($message) >= 4 && strlen($message) <= 200)){
				$messageError = true;
			}
			//  Grab the display date from the form
			$displaydate = getVar("displaydate");
			//  Check if it's a valid date in the correct format
			if(!validateDate($displaydate)){
				$displaydateError = true;
			}
				
	}
	//  If there's no errors, we are good! 
	if(!$uidError && !$titleError && !$messageError && !$displaydateError){
		$success = true;
	}
	
 ?>
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
				<h1 class="text-left bold">PHP Server Form</h1>
			</div>
			<div class="col-lg-4"></div>
		</div>
		
		<!-- Grabbing user's UID as name uid -->
		<div class="row justify-content-md-center">
			<div class="col-lg-4" ></div>
			<div class="col-lg-2 text-right">
				<label for="uid">Your Unique ID:</label> 
			</div>
			
			<div class="col-lg-6 text-left">
				<input type="text" class="form-control" name="uid" id="uid" value= <?php if($success){ echo ' ';} else {print "\"$uid\"";} ?> >
			</div>	
		</div>
		<!-- Subtitle for UID -->
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label class = "graySubs">This is your Unique ID(at least 2 characters and a single word)(</label>
			</div> 
			<div class="col-md-6"></div>
		</div>

		<!-- Title of Display Message -->
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label for="title">Title for Display Message</label> 
			</div>
			<div class="col-md-6">
				<!-- If we have a success, we want to empty the field, otherwise we'll just return what we have -->
				<input type="text" name="title" class="form-control" id="title" value=<?php  if($success){ echo "";} else {print "\"$title\"";} ?>  >
			</div>

		</div>
		
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>	
			<div class="col-md-2 text-right">
				<label class="graySubs">Should be between 4 and 30 characters in length</label>
			</div> 
			<div class="col-md-6"></div>
		</div>
		
		<!-- Stash Message data -->
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label for="quote">Message </label> 
			</div> 
			<div class="col-md-6">
				<!-- If we have a success, we want to empty the field, otherwise we'll just return what we have -->
				<textarea class="form-control" name="message" rows="3" id="message"><?php if($success){ echo "";} else {print "$message";}?></textarea>
			</div>
		</div>
			
		
		<div class="row justify-content-md-center">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label class="graySubs">The message body (between 4 and 200 characters)  </label>
			</div> 
			<div class="col-lg-2 text-right"> </div>			
		</div>
		
		<!-- Stash the displaydate -->
		<div class="row justify-content-md-center">
			<div class="col-lg-4" ></div>
			<div class="col-lg-2 text-right">
				<label for="displaydate">End Display Date: </label> 
			</div>
			
			<div class="col-lg-6 text-left">
				<!-- If we have a success, we want to empty the field, otherwise we'll just return what we have -->
				<input type="text" class="form-control" name="displaydate" id="displaydate" value= <?php if($success){ echo "";} else {print "\"$displaydate\"";} ?>  >
			</div>		

		</div>
		
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<div class="col-md-2 text-right">
				<label class="graySubs">The final date to display this message(valid date in YYYY-MM-DD) </label>
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
		
		
		<div><input type="hidden" name="cmd" value="submit"  hidden /> </div>
	
		<div class="justify-content-md-center row">
			<div class="col-lg-4" ></div>
			<!-- If we succeed, display a success message with a green background -->
			<?php if($success){ ?>
			<div class="alert alert-success col-md-4 text-center" role="alert">
				<h4 class="alert-heading">Success</h4>
				<p> You inputted everything correctly and submitted! </p>
				<div class="col-md-2"></div>
			</div>
			<!-- If we fail, print error messages accordance to what failed -->
			<?php } else { ?>
			<div class="alert alert-danger col-md-4 text-center" role="alert">
			<h4 class="alert-heading">Errors</h4>
				<?php if($uidError){ print "<p> The uid field must be a single word, containing >= 2 characters </p>";} ?> 
				<?php if($titleError){ print "<p> The title must be between 4 and 30 characters </p>";}?>
				<?php if($messageError){ print "<p> The message must be between 4 and 200 characters </p>";}?>
				<?php if($displaydateError){ print "<p> The date must be in YYYY-MM-DD format, and must be valid <p>";}?>
				<div class="col-md-2"></div>
			</div>
			<?php } ?>
		</div>
		</div>
    </form>
  </body>
</html>

