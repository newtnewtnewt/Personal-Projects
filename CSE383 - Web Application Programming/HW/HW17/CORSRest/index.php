<!-- 
Noah Dunn
CSE 383
Dr. Campbell
Assignment: 11/20/2019 CORS
Resources:
Dr. Campbell, W3 Schools,
https://stackoverflow.com/questions/38380462/syntaxerror-unexpected-token-o-in-json-at-position-1/38380728

=-->
<!-- JQuery Library Access -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!-- Enables not really used bootstrap elements -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script> 
<!-- The external JS -->

<?php

/**
 *   Example for a simple cas 2.0 client
 *
 * PHP Version 5
 *
 * @file     example_simple.php
 * @category Authentication
 * @package  PhpCAS
 * @author   Joachim Fritschi <jfritschi@freenet.de>
 * @author   Adam Franco <afranco@middlebury.edu>
 * @license  http://www.apache.org/licenses/LICENSE-2.0  Apache License 2.0
 * @link     https://wiki.jasig.org/display/CASC/phpCAS
 */
session_start();
// Load the settings from the central config file
require_once 'config.php';
// Load the CAS lib
require_once 'vendor/autoload.php';
require_once 'vendor/apereo/phpcas/CAS.php';

// Enable debugging
phpCAS::setDebug('/tmp/cas');

// Initialize phpCAS
phpCAS::client(CAS_VERSION_2_0, $cas_host, $cas_port, $cas_context);

// For production use set the CA certificate that is the issuer of the cert
// on the CAS server and uncomment the line below
// phpCAS::setCasServerCACert($cas_server_ca_cert_path);

// For quick testing you can disable SSL validation of the CAS server.
// THIS SETTING IS NOT RECOMMENDED FOR PRODUCTION.
// VALIDATING THE CAS SERVER IS CRUCIAL TO THE SECURITY OF THE CAS PROTOCOL!
phpCAS::setNoCasServerValidation();

// force CAS authentication
phpCAS::forceAuthentication();

// Only thing I really needed to add, setting the session variable
$_SESSION['user'] = phpCAS::getUser();

// at this step, the user has been authenticated by the CAS server
// and the user's login name can be read with phpCAS::getUser().

// logout if desired
if (isset($_REQUEST['logout'])) {
	phpCAS::logout();
}



// for this test, simply print that the authentication was successfull
?>
<script src="user.js"></script/> 
<html>
  <head>
    <title>phpCAS simple client</title>
  </head>
  <body>
	<h1>All users</h1>
    <ul id="userList" text="Full list of users">
	</ul>
  </body>
</html>
