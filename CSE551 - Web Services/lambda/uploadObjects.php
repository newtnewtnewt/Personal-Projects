<?php
/*
* This is a quick php script for uploading files to S3
*/
require 'credentials.php';

use Aws\S3\S3Client;
use Aws\S3\Exception\S3Exception;

$bucket = 'dunnnm2-451-lambda1';
$keynames = array("index.html", "script.js");
$filenames = array("index.html", "script.js");
$filePermissions = array("public-read", "public-read");
$filetypes = array("text/html", "text/javascript");
$s3 = new S3Client([
    'version' => '2006-03-01',
    'region'  => 'us-east-1',
    'credentials' => $provider
]);

try {
	for($i = 0; $i < sizeof($filenames); $i++){
		// Upload data.
		$result = $s3->putObject([
			'Bucket' => $bucket,
		'Key'    => $keynames[$i],
		'ACL'    => $filePermissions[$i],
		'ContentType' => $filetypes[$i],
			'Body'   => fopen($filenames[$i], 'r+')
		]);
		// Print the URL to the object.
		echo $result['ObjectURL'] . PHP_EOL;
	}
    
} catch (S3Exception $e) {
    echo $e->getMessage() . PHP_EOL;
}
?>
