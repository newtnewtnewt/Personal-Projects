<?php
/*
* Name: Noah Dunn
* Instructor: Dr. Campbell
* Course: CSE451
* Date: 4/11/2020
* S3 Bucket Upload, pass between two buckets
* Material: Previous Assignments, Dr. Campbell, AWS docs
https://stackoverflow.com/questions/1768894/how-to-write-into-a-file-in-php
 */
?>
	<html>
	<head>
		<title>Publish</title>
	</head>
	<body>
<?php
include "campbellcredentials.php";
include "credentials.php";
use Aws\S3\S3Client;
use Aws\Exception\AwsException;
use Aws\Credentials\CredentialProvider;

// Create the S3 Client hooked up to Dr. Campbells credentials
$s3Client = new S3Client([
    'region' => 'us-east-2',
    'version' => '2006-03-01',
    'credentials' => $provider
]);
//  The public bucket we are going to read from
$bucket = 'testcampbest';
//  Create an html file to be published
$output = fopen('everyoneinfo.html', 'w');
//  Begin writing the file
fwrite($output, "<!DOCTYPE HTML>" . PHP_EOL . "		<html>". PHP_EOL . "			<head><title>Everyone Info</info></title><head>" . PHP_EOL . "				<body>" . PHP_EOL);  
//  In case there is some connection error
try {
	//  Grab all objects from the bucket
	$objects = $s3Client->listObjects([
    	'Bucket' => $bucket
	]);
	//  Grab the key for each bucket
	foreach ($objects['Contents']  as $object) 
	{		
		//  The first line should be a header with the name of the key
		fwrite($output,"					<h2>" . $object['Key'] . "</h2>" . PHP_EOL);
		//  Get the individual object
		$objectData = $s3Client->getObject([
			'Bucket' => $bucket,
			'Key' => $object['Key']
		]);
		//  Grab the body of that bucket
		$contents = $objectData['Body']->getContents();
		//  Split the file according to what should be two lines (Brief Description and the photo url)
		$inputFileArray = explode("\n", $contents);
		//  If the user only provided an image, account for this
		if(sizeof($inputFileArray) > 1){
			fwrite($output,"					<p>" . $inputFileArray[0] . "</p>" . PHP_EOL);
			fwrite($output,"					<img src='" . $inputFileArray[1] . "'></img>" . PHP_EOL);
		}
		else {
			fwrite($output,"					<img src='" . $inputFileArray[0] . "'></img>" . PHP_EOL);
		}
	}
} catch (S3Exception $e) {
	echo $e->getMessage() . PHP_EOL;
}
//  Close out the file
fwrite($output,"				</body>" . PHP_EOL . "		</html>" . PHP_EOL);
fclose($output);

// Upload the file we just created to my bucket
$mybucket = 'dunnnm2-451-s20-bucket1';
$filename = 'everyoneinfo.html';
$keyname = 'Everyone.html';
// Create the client again 
$s3 = new S3Client([
    'version' => '2006-03-01',
    'region'  => 'us-east-1',
    'credentials' => $provider2
]);

try {
    // Upload data.
    $result = $s3->putObject([
        'Bucket' => $mybucket,
        'Key'    => $keyname,
        'ACL'    => 'public-read',
        'ContentType' => "text/html",
        'Body'   => fopen($filename, 'r+')
    ]);

    // Print the URL to the object.
	print "<p>Success!</p>\n";
    print "<a href='" . $result['ObjectURL'] . "'>Click here</a>\n";
} catch (S3Exception $e) {
    echo $e->getMessage() . PHP_EOL;
}
?></body>
</html>
