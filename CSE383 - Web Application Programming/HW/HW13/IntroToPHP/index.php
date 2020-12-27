<!-- Author: Noah Dunn 
  Class: CSE 383
  Homework: First PHP assignment
 With assistance from the following resources:
 W3 Schools, Dr. Campbell
Most of this was taken verbatim from
https://www.php.net/manual/en/reserved.variables.server.php
 -->
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="generator"
    content="HTML Tidy for HTML5 (experimental) for Windows https://github.com/w3c/tidy-html5/tree/c63cc39" />
    <title>First PHP run</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  </head>
  
  <body>
    <form class="form-inline" action="form1.php" method="post" >
    <div class="container">
		<!-- Our title all nice and centered -->
		<div class="row justify-content-center">
			<div class="col-lg-4" ></div>
			<div class="col-lg-6" >
			<?php
				//  We need the every key that is paired in $SERVER
				$indicesServer = array('PHP_SELF',
				'argv',
				'argc',
				'GATEWAY_INTERFACE',
				'SERVER_ADDR',
				'SERVER_NAME',
				'SERVER_SOFTWARE',
				'SERVER_PROTOCOL',
				'REQUEST_METHOD',
				'REQUEST_TIME',
				'REQUEST_TIME_FLOAT',
				'QUERY_STRING',
				'DOCUMENT_ROOT',
				'HTTP_ACCEPT',
				'HTTP_ACCEPT_CHARSET',
				'HTTP_ACCEPT_ENCODING',
				'HTTP_ACCEPT_LANGUAGE',
				'HTTP_CONNECTION',
				'HTTP_HOST',
				'HTTP_REFERER',
				'HTTP_USER_AGENT',
				'HTTPS',
				'REMOTE_ADDR',
				'REMOTE_HOST',
				'REMOTE_PORT',
				'REMOTE_USER',
				'REDIRECT_REMOTE_USER',
				'SCRIPT_FILENAME',
				'SERVER_ADMIN',
				'SERVER_PORT',
				'SERVER_SIGNATURE',
				'PATH_TRANSLATED',
				'SCRIPT_NAME',
				'REQUEST_URI',
				'PHP_AUTH_DIGEST',
				'PHP_AUTH_USER',
				'PHP_AUTH_PW',
				'AUTH_TYPE',
				'PATH_INFO',
				'ORIG_PATH_INFO') ;
				//  Slap all this in a table
				print '<table cellpadding="10">' ;
				//  Given every key in $_SERVER
				foreach ($indicesServer as $arg) {
				//  If it's there, print the key and the value
					if (isset($_SERVER[$arg])) {
						print '<tr><td>'.$arg.'</td><td>' . $_SERVER[$arg] . '</td></tr>' ;
					}
				//  Otherwise just the key
					else {
						print '<tr><td>'.$arg.'</td><td>-</td></tr>' ;
					}
				}
				//  Close the table
				print '</table>' ;  
			?>
			</div>
			<div class="col-lg-4"></div>
		</div>
	 </div>	
    </form>
  </body>
</html>
