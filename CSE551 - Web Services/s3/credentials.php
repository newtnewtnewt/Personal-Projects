<?php
require "./vendor/autoload.php";
use Aws\Credentials\CredentialProvider;

$profile2 = 'default';                                       //this specifies which profile inside of credential file to use
$path2 = '/var/www/.aws/credentials';        //path to credential file

$provider2 = CredentialProvider::ini($profile2, $path2);
$provider2 = CredentialProvider::memoize($provider2);


?>
