<?php
include "credentials.php";
use Aws\S3\S3Client;
use Aws\Exception\AwsException;
use Aws\Credentials\CredentialProvider;

$s3Client = new S3Client([
    'region' => 'us-east-1',
    'version' => '2006-03-01',
    'credentials' => $provider
]);

$buckets = $s3Client->listBuckets();
foreach ($buckets['Buckets'] as $bucket) {
	echo $bucket['Name'] . "\n";
	$bucket = $bucket['Name'];
	// Use the plain API (returns ONLY up to 1000 of your objects).
	try {
    	$objects = $s3Client->listObjects([
        	'Bucket' => $bucket
    	]);
	foreach ($objects['Contents']  as $object) 
	{	
		$objURL = $s3Client->getObjectUrl($bucket, $object['Key']);
        	echo $object['Key'] . " " . $objURL . PHP_EOL;
    	}
      } catch (S3Exception $e) {
    		echo $e->getMessage() . PHP_EOL;
	}

}

?>
