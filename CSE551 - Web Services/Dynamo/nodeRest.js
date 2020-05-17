/*
* Name: Noah Dunn
* Course: CSE451
* Professor: Scott Campbell 
* Date: 4/17/2020 
* Assignment: A small REST Server to grab S3 and Dynamo information
* References From: Dr. Campbell, AWS Documentation, MDN Web Docs, Past Assignment, W3 Schools
*/
var http = require("http");
var AWS = require('aws-sdk');

http.createServer(function (request, response) {

	// Send the HTTP header 
	// HTTP Status: 200 : OK
	// Content Type: text/plain
	
	// Send out an OKAY header, regardless if the user sends garbled input.
	response.writeHead(200, {'Content-Type': 'application/json',"Access-Control-Allow-Origin":"*","Access-Control-Allow-Methods":"GET,POST,PUSH,OPTIONS","Access-Control-Allow-Headers":"Content-Type",'X-uid':'campbest'});
	// Grab the URL and split the path up
	var url = request.url;
	var urlParts = url.split("/");
	// Get rid of the leading "" char
	urlParts.shift();
	// Grab the HTTP method of calling
	var method = request.method;
	var message = "";
	
	
	// Create S3 service object
	s3 = new AWS.S3({apiVersion: '2006-03-01', region:'us-east-1'});
	// Update to the correct region
	AWS.config.update({
	  region: "us-east-1"
	});
	
	//  If the client use a GET request
	if (method === "GET") {
			if(urlParts.length == 2 && urlParts[0] == "v1" && urlParts[1] == "s3"){
				// Call S3 to list the buckets
				try {
					//  Call the list buckets command through S3
					s3.listBuckets(function(err, data) {
					  if (err) {
						// Report any error to the server log
						console.log("Error", err);
						response.end(JSON.stringify("") + "\r\n");
					  } else {
						//  Report a success to the console
						console.log("Success", data.Buckets);
						//  Build the full JSON string response as an array nested inside a JSON object
						var fullJSONString = "{bucketList: [";
						data.Buckets.forEach(element => fullJSONString += element.Name + ", ");
						fullJSONString = fullJSONString.substr(0, fullJSONString.length - 2) + "]}";
						console.log(JSON.stringify(fullJSONString));
						//  Send the full string
						response.end(JSON.stringify(fullJSONString) + "\r\n");
					  }
					});
				} catch(error){
					// If AWS crashes, or credentials are invalid, print to console.
					console.log("Either AWS is unavailable or you need to update your credentials");
					response.end(JSON.stringify("") + "\r\n");
				}
			}
			//  Alternatively, the user can supply a year value to retrieve DynamoDB information
			else if(urlParts.length == 3 && urlParts[0] == "v1" && urlParts[1] == "dynamo"){
				// Grab the year
				var year = urlParts[2];
				// Validate that the value is actually a year
				if(year.length == 4 && !isNaN(year)){
					//  Create a Dynamo Client
					var docClient = new AWS.DynamoDB.DocumentClient();
					//  Print year to server log
					console.log("Querying for movies from " + year + ".");
					//  The required parameters for the query, including the desired year we want to retrieve
					var params = {
						TableName : "Movies",
						KeyConditionExpression: "#yr = :yyyy",
						ExpressionAttributeNames:{
							"#yr": "year"
						},
						ExpressionAttributeValues: {
							":yyyy": parseInt(year)
						}
					};
					//  Execute the query
					docClient.query(params, function(err, data) {
						if (err) {
							// If the query errors out
							console.error("Unable to query. Error:", JSON.stringify(err, null, 2));
							response.end("Query Invalid" + "\r\n");
						} else {
							// If the query succeeds, build a full JSON object
							console.log("Query succeeded.");
							var fullJSONString = "{" + year +": [";
							data.Items.forEach(function(item) {
								fullJSONString += item.title + ", ";
								
							});
							fullJSONString = fullJSONString.substr(0, fullJSONString.length - 2) + "]}";
							console.log(JSON.stringify(fullJSONString));
							response.end(JSON.stringify(fullJSONString) + "\r\n");
							
						}
					});
				}
				else {
					//  This is the catch-all for bad input
					console.log("User attempted to input something that was not a year");
					response.end(JSON.stringify("User Attempted to Input an Invalid Year") + "\r\n");
				}
			}
		
	}
	//  If the user attempts anything other than a GET, that is invalid.
	else {
		// Inform the user and the server log what is going on here
		console.log("Only GET requests are allowed at this endpoint");
		response.end(JSON.stringify("Only GET requests are allowed at this endpoint"));
	}

}).listen(8083);

// Console will print the message
console.log('Server running at http://127.0.0.1:8083/');
