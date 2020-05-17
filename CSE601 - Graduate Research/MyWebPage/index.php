<!-- Author: Noah Dunn 
  Class: CSE 383
  Final Project: The Whole thing
 With assistance from the following resources:
 W3 Schools, Dr. Campbell
 https://stackoverflow.com/questions/25854496/how-to-set-min-height-for-bootstrap-container
 https://stackoverflow.com/questions/18529274/change-navbar-color-in-twitter-bootstrap
 https://getbootstrap.com/docs/4.0/components/forms/
 -->
 <!DOCTYPE html>
 <?php require "quotegen.php" ?>
<html lang="en">
  <head>
    <meta name="generator"
    content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
    <title>Noah Dunn's Homepage</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	<script src="imageFlip.js"></script>
  </head>
  <body>
	<!-- First we need to construct our desired banner -->
	<div class = "container-fluid p-0" style="margin:0px;background-color:#AFED9D;font-family:Lucida Console;">
	<div class = "row">
		<div class="col-sm-3 align-items-start">
			<label> Click here for CEC --> </label>
			<a href="https://miamioh.edu/cec/index.html">
			<image alt="The logo for the CEC department" src="images/ceclogo.jpg" />
			</a>
		</div>
		<div class="col-sm-6 align-items-center" >
		  <h1 style="text-align:center;">Random Quote from My Collection:</h1>
		  <h3 style="text-align:center;"><?php print getQuote(); ?></h3>
		</div>
		<div class="col-sm-3 align-items-end">
				<image id="logo" alt="Your personalized logo" src="images/disasterRelief.jpg" />
			<label> <-- Click here <label/>
		</div>
	</div>
	</div>
	
	<nav class="navbar navbar-inverse" style="background-color: #4BA630;border-color: #4BA630;">
	
	  <div class="container-fluid">
		<div class="navbar-header">
		  <a class="navbar-brand" href="index.php" style="color:#FFFFFF">My Network</a>
		</div>
		<ul class="nav navbar-nav">
		  <li class="active"><a href="index.php" style="color:#FFFFFF;">Home</a></li>
		  <li><a href="https://www.linkedin.com/in/noah-dunn-49a4a1134/" style="color:#FFFFFF;">LinkedIn</a></li>
		  <li><a href="https://twitter.com/NewtINC" style="color:#FFFFFF;">Twitter</a></li>
		  <li><a href="https://github.com/newtnewtnewt" style="color:#FFFFFF;">GitHub</a></li>
		</ul>
	  </div>
	</nav>
	<div class="container">
	  <div class="jumbotron" style="background-color:#85FFB1;">
	    <p id = "zipCode" hidden></p>
		<h1 style="text-align:center;"> Welcome! </h1>
		<br />
		<h2>
		My name is Noah Dunn and I am a Graduate + Undergraduate Computer Science student at Miami University. I have a passion for programming,
		but in my spare time I enjoy playing video games, board games, reading anything I can get my hands on, and spending time with my family. 
		I also try to volunteer as much as possible, most recently with the Southern Baptist Convention's disaster relief, shown in the image above. 
		Please feel free to contact me through any of the channels located here.
		</h2>
		<a type="button" href="resume.pdf" class="btn btn-success" style="width:40%; height:100px; font-size:50px;">Resume</a> 
		<a type="button" href ="mailto:dunnnm2@miamiOH.edu" class="btn btn-success text-center" style="width:40%; text-align:center; height:100px; font-size:50px; margin-left: 19%;">Email</a>
		<br />
	  </div>
	</div>
	<div class="container">
		<button type="button" class="btn btn-info" style="width:100%;" data-toggle="collapse" data-target="#cLinks">Current Research and Projects</button>
			<div id="cLinks" class="collapse"> 
				<div class="container">
					<div class="row">
						<div class="col-xs-6">
							<h1><u><b>Machine-Learning Based Sepsis Prediction </b></u></h1>
							<br />
							<p> I am working with Dr. Md. Osman Gani to come up with high-success rate models for predicting Sepsis, 6 hours to the expected time that a real Doctor would be able to. 
								This kind of technology, which employs machine learning methodology, would enable a decreased mortality rate in patients, and the speedup of patient processing, relieving burdens particularly
								in overcrowded hospitals
							</p>
							<a type="button" href="https://physionet.org/content/challenge-2019/1.0.0/" class="btn btn-info" style="height:40px; font-size:15px;">Link to the Problem</a> 
						</div>
						<div class="col-xs-6">
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<img src = "images/taxi1.png"></img>
							<img src = "images/taxi2.png"></img>
						</div>
						<div class="col-xs-6">
							<h1><u><b>Distributed Taxicab System</b></u></h1>
							<br />
							<p> For my Senior Design Project, my group-mates and I have been working on a mulit-faceted taxi-hailing system that does not rely on a centralized server to communicate. 
								I have been responsible in large for utilizing Data Visualization techniques in Processing in order to display a useful image.
							</p>
							<a type="button" href="https://gitlab.csi.miamioh.edu/dunnnm2/taxicabdistribution" class="btn btn-info" style="height:40px; font-size:15px;">Source Code</a> 
						</div>
					</div>
				</div>
			</div>
		<button type="button" class="btn btn-warning" style="width:100%;" data-toggle="collapse" data-target="#vLinks">Past Projects</button>
			<div id="vLinks" class="collapse">
				<div class="container">
					<div class="row">
						<div class="col-xs-6">
							<h1><u><b>Appgregate </b></u></h1>
							<br />
							<p> This was a group project done for my CSE 201 - Introduction to Software Engineering Course. This application, built in ASP.NET with a mySQL backend, is an app store of appstores.
								It enables users of varying security power to add, request, and accept apps to the store, comment on those apps, and remove apps or comments. The full codebase comes complete with 
								NUnit testing and a fully fledged out Selenium tester.
							</p>
							<a type="button" href="https://docs.google.com/document/d/1jDLUSgC2w9mYFs4wNamQk1XAdN-yw3g6-wtQmHyAi7g/edit?usp=sharing" class="btn btn-info" style="height:40px; font-size:15px;">Developer Manual</a> 
							<a type="button" href="https://github.com/newtnewtnewt/StephanG12" class="btn btn-success" style="height:40px; font-size:15px;">Source Code</a> 
						</div>
						<div class="col-xs-6">
							<img src = "images/appgregate1.png"></img>
							<img src = "images/appgregate2.png"></img> 
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<h1><u><b>Toilet Talk </b></u></h1>
							<br />
							<p> This was a group project for my CSE 385 - Databases Course. The Web Application we built is able to locate and provide information about the availability of different restrooms on
								Miami's Campus. The application provided a review system, as well as a data collection service to acquire more data about the different bathrooms. 
							</p> 
							<a type="button" href="https://github.com/newtnewtnewt/Personal-Projects/tree/master/CSE385%20-%20Databases/ToiletTalk" class="btn btn-success" style="height:40px; font-size:15px;">Source Code</a> 
						</div>
						<div class="col-xs-6">
							<img src = "images/tt1.png"></img>
							<img src = "images/tt2.png"></img> 
						</div>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<h1><u><b>C++ Web Server</b></u></h1>
							<br />
							<p> This was the final project for my CSE 381 - Systems II course. The application runs a simple webserver that gathers timing data on the inputted linux commands from an HTML page, and serves
								the result of the timing test as both a table and a Google Analytics graph. 
							</p> 
							<a type="button" href="https://github.com/newtnewtnewt/Personal-Projects/tree/master/CSE381%20-%20Systems%20II/HW/HW9/homework9_starter_code" class="btn btn-success" style="height:40px; font-size:15px;">Source Code</a> 
						</div>
						<div class="col-xs-6">
						</div>
					</div>
				</div>
			</div>
		<button type="button" class="btn btn-info" style="width:100%;background-color:green;border-color:green;" data-toggle="collapse" data-target="#qLinks">Skill Set</button>
			<div id="qLinks" class="collapse">
				<div class="container">
					<ul class="list-group" style="text-align:center;list-style-position:inside;font-size:20px;">
						<li> Java </li>
						<li> C++ </li>
						<li> HTML </li>
						<li> Javascript </li>
						<li> PHP </li>
						<li> C# </li> 
						<li> Python </li>
						<li> ASP.NET </li>
						<li> SQL + mySQL </li>
					</ul>
				</div>
			</div>
		<button type="button" class="btn btn-danger" style="width:100%;" data-toggle="collapse" data-target="#gLinks">Academic Achievements</button>
			<div id="gLinks" class="collapse">
				<div class="container">
					<ul class="list-group" style="text-align:center;list-style-position:inside;font-size:20px;">
						<li> AWS Cloud Solutions Architect Associate Certification </li>
						<li> AWS Cloud Developer Associate Certification </li>
						<li> Top 10 Student Award from Tau Beta Pi </li>
						<li> Honors Student </li>
						<li> Recipient of the Darrel and Wilma Grothen Scholarship </li>
						<li> Recipient of the Redhawk Eminence Scholarship </li>
						<li> Recipient of the Class of 1942 Scholarship </li>
						<li> Bridges Scholar </li>
						<li> Consistent President + Dean's List Member </li>
						<li> Provost Student Academic Achievement Award Nominee </li>
					</ul>
				</div>
			</div>
		<button type="button" class="btn btn-primary" style="width:100%;" data-toggle="collapse" data-target="#sLinks">Education History</button>
			<div id="sLinks" class="collapse">
				<div class="container">
					<ul class="list-group" style="text-align:center;list-style-position:inside;font-size:20px;">
						<li> <b>M.S. in Computer Science</b> at Miami University - In Progress, expected completion: May 2021 </li>
						<li> <b>B.S. in Computer Science</b> at Miami University - In Progress, expected completion: May 2020 </li>
						<li> <b>High School Diploma </b> at Dover High School - Completed May of 2017 </li>
					</ul>
				</div>
			</div>
		<button type="button" class="btn btn-primary" style="width:100%;background-color:purple;border-color:purple" data-toggle="collapse" data-target="#wLinks">Work History</button>
			<div id="wLinks" class="collapse">
				<div class="container">
					<ul class="list-group" style="text-align:center;list-style-position:inside;font-size:20px;">
						<li> <b>Undergraduate Research Assistant</b> at Miami University - Jun 2019 -> Current </li>
						<li> <b>Teaching Assistant</b> at Miami University - Sep 2018 -> May 2019 </li>
						<li> <b>IT Cloud Intern </b> at GEAppliances - May 2019 -> Aug 2019 </li>
						<li> <b>Cook </b> at McDonald's - Jun 2018 -> Jan 2019 </li>
						<li> <b>Cook </b> at Fundays's - Jun 2017 -> Jan 2018 </li>
						<li> <b>Customer Service Representative </b> at Mooey's - Jun 2017 -> Jan 2018 </li>
					</ul>
				</div>
			</div>
		<button type="button" class="btn btn-primary" style="width:100%;background-color:pink;border-color:pink;" data-toggle="collapse" data-target="#voLinks">Volunteer History</button>
			<div id="voLinks" class="collapse">
				<div class="container">
					<ul class="list-group" style="text-align:center;list-style-position:inside;font-size:20px;">
						<li> <b>Disaster Relief Volunteer</b> volunteering for Southern Baptist Disaster Relief North Carolina - Jan 2020</li>
						<li> <b>Student Leader</b> working for The Bridge - Aug 2017 -> Present </li>
						<li> <b>Disaster Relief Volunteer</b> volunteering for Southern Baptist Disaster Relief Florida - Jan 2019</li>
						<li> <b>Out of State Volunteer</b> volunteering for Jesus Tent @ FBC Manchester - Jun 2018, Jun 2019 </li>
						<li> <b>Student Volunteer</b> volunteering for Tuscarawas County Humane Society - May 2017 </li>
						<li> <b>Misc. Volunteer </b> volunteering for Community Hospice Care Center - Jan 2015 -> Jun 2017 </li>
					</ul>
				</div>
			</div>
	</div>
	<br />
	<br />
	<br />
	<br /><br /><br /><br /><br /><br /><br /><br /><br />
	<footer>
		<p style="text-color:gray;left:70%;bottom:0;position:fixed;">Noah Dunn -- Copyright 2020</p>
	</footer>
  </body>
</html>
