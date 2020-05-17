<?php
require "./vendor/autoload.php";
use Aws\Credentials\CredentialProvider;

$profile = 'campbest-testcampbest';                                       //this specifies which profile inside of credential file to use
$path = '/var/www/.aws/credentials';        //path to credential file

$provider = CredentialProvider::ini($profile, $path);
$provider = CredentialProvider::memoize($provider);


?>
