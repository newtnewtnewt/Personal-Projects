<?php
require 'vendor/autoload.php';
require 'campbellcredentials.php';

use Aws\S3\S3Client;
use Aws\S3\Exception\S3Exception;

$bucket = 'testcampbest';
$keyname = 'dunnnm2';
$filename = 'dunnnm2.txt';
$s3 = new S3Client([
    'version' => '2006-03-01',
    'region'  => 'us-east-2',
    'credentials' => $provider
]);

try {
    // Upload data.
    $result = $s3->putObject([
        'Bucket' => $bucket,
	'Key'    => $keyname,
	'ACL'    => 'private',
	'ContentType' => "text/plain",
        'Body'   => fopen($filename, 'r+')
    ]);

    // Print the URL to the object.
    echo $result['ObjectURL'] . PHP_EOL;
} catch (S3Exception $e) {
    echo $e->getMessage() . PHP_EOL;
}
?>
