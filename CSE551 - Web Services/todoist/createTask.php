<?php
/*
 * scott campbell
 * example guzzle client to call dark web
 * */

//this calls in all autoload packages installed via composer
require __DIR__ . '/vendor/autoload.php'; 
require "key.php";

//bring guzzle client into code
use GuzzleHttp\Client;

//base uri -> it is important it end in /
$uri = "https://api.todoist.com/rest/v1/";



//create a new client
$client = new Client([
    // Base URI is used with relative requests
    'base_uri' => $uri,
    // You can set any number of default request options.
    'timeout'  => 2.0,
]);


global $client;
try {
  $body = array("content" => "Death of a Salesman Reee", "due_string" => "tomorrow at 12:00", "due_lang" => "en", "priority" => 4);
  $body = json_encode($body);
  $header = array("Authorization"=>"Bearer " . $testToken2, "Content-Type" => "application/json", "X-Request-Id" => "3");
  //  Create the body for the task creation
  $response = $client->request('POST',"tasks",['headers'=>$header, "body" => $body]);
} catch (Exception $e) {
  print "There was an error getting the projects from todoist";
  header("content-type: text/plain",true);
  print_r($e);
  //$a=print_r($e,true);
  //error_log($a);
  exit;
}
?>
