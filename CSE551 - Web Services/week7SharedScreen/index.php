<?php
/* Noah Dunn
 * CSE 551
 * Scott Campbell 
 * 3/13/2020
 * PHP Dot Rest
 * Resources:
 * Dr. Campbell, W3 Schools,
 * https://xlinesoft.com/blog/2015/04/21/displaying-a-list-of-users-that-are-currently-logged-in/
 * 
 * */

// Load the settings from the central config file
require_once 'config.php';
require 'db.php';
// Load the CAS lib
require_once 'vendor/autoload.php';
require_once 'vendor/apereo/phpcas/CAS.php';

phpCAS::client(CAS_VERSION_2_0, $cas_host, $cas_port, $cas_context);

// For production use set the CA certificate that is the issuer of the cert
// on the CAS server and uncomment the line below
// phpCAS::setCasServerCACert($cas_server_ca_cert_path);

// For quick testing you can disable SSL validation of the CAS server.
// THIS SETTING IS NOT RECOMMENDED FOR PRODUCTION.
// VALIDATING THE CAS SERVER IS CRUCIAL TO THE SECURITY OF THE CAS PROTOCOL!
phpCAS::setNoCasServerValidation();

session_start();
if (!isset($_SESSION['user']) || !isset($_SESSION['session_time']) ||  (time()-$_SESSION['session_time']) >10) {

        // Initialize phpCAS
        // force CAS authentication
        phpCAS::forceAuthentication();

        $user = phpCAS::getUser();
        $_SESSION['user'] = $user;
        $_SESSION['session_time'] = time();
}
else
        $user =$_SESSION['user'] . " (session)";

loadUser($_SESSION['user']);

// logout if desired
if (isset($_REQUEST['logout'])) {
        phpCAS::logout();
}
?>
<!DOCTYPE html>
<!--
	scott campbell
	cse451
	discuss api design
-->

<HTML lang="en">
<HEAD>
  <META name="generator" content=
  "HTML Tidy for Linux (vers 25 March 2009), see www.w3.org">

  <TITLE>screen</TITLE>
  <meta charset="UTF-8">
  <!-- A necessary evil to grab what is stored in our php session -->
  <script> var currentUser = "<?php echo $_SESSION['user'] ?>";  </script>
  <SCRIPT src=
  "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"
  > </SCRIPT>
<SCRIPT src="render.js"></script>
<style>
#buttons {
clear:both;
}
#users {
padding-left: 20px;
}

#pic {
float: left;
padding-right: 20px;
}

</style>

</HEAD>

<BODY>
  <DIV id='pic'><IMG src='render.php' alt='shared screen display'></DIV>
<div id='users'><h2>Users</h2><ul id="allUsers"></ul></div>

  <DIV id='buttons'>
    <BUTTON id='clear'>Clear</BUTTON>
    <DIV id="msg"></DIV>
  </DIV>
  <div> 
	<select id="imageOption">
		<option  value="dot">Dot</option>
		<option  value="rectangle">Rectangle</option>
	</select>
  </div>
</BODY>
</HTML>
