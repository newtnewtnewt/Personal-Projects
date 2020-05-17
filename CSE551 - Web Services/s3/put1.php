<?php
/*
Example program to upload content to s3
Assumes the credentials are in /var/www/.aws/credentials

*/


require "./vendor/autoload.php";
use Aws\S3\S3Client;
use Aws\Exception\AwsException;
use Aws\Credentials\CredentialProvider;

$profile = 'default';                                       //this specifies which profile inside of credential file to use
$path = '/var/www/.aws/credentials';        //path to credential file

$provider = CredentialProvider::ini($profile, $path);
$provider = CredentialProvider::memoize($provider);

$s3Client = new S3Client([
	'region' => 'us-east-1',
	'version' => '2006-03-01',
	'credentials' => $provider
]);

$key="410Test";
$bucket = 'dunnnm2-451-s20-bucket1';
$contents = "It is now " . time();
$url="";


try {
	$result = $s3Client->putObject([
		'Bucket' => $bucket,
		'Key' => $key,
		'Body' => $contents,
		'ACL' => 'public-read',
		'ContentType' => "text/plain"
	]);
	$url = $result['@metadata']['effectiveUri'];
	print "Url=$url\n";
} catch (S3Exception $e) {
	print "Error " . $e;
}
