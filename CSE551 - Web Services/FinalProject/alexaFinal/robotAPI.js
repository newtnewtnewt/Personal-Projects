/*
* Name: Noah Dunn 
* Instructor: Dr. Scott Campbell
* Class: CSE 551
* Date: 5/7/2020
* Assignment: Final Project API
* With assistance from:
* Dr. Campbell, 
* My Previous CSE383 work,
* My Previous CSE451 work,
* W3 Schools, and the Following:
* https://stackoverflow.com/questions/23327010/how-to-generate-unique-id-with-node-js
* https://tecadmin.net/get-current-date-time-javascript/
*/
var AWS = require('aws-sdk');

exports.handler = (event,context,callback) => {
    console.log(event.pathParameters);
    // Set the region 
    AWS.config.update({region: 'us-east-1'});
    //  Build the DynamoDB client
    var docClient = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'});
    
    if (event.httpMethod == 'GET') { //If the method is a get
        //setup for parsing path parameter
        console.log("test of path parameters",event.pathParameters==null)
        // Make sure pathParameters exist
        if ((typeof event.pathParameters !== 'undefined' )  && event.pathParameters != null && event.pathParameters.name != null) {
                var name = event.pathParameters.name;
                console.log("here we are!:" +  name);
                //  Grab params to query
                var params = {
                     "TableName": "RobotTable",
                     "KeyConditionExpression": "nameVal = :name",
                     "ExpressionAttributeValues": {
                         ":name": name,
                     }
                };
                
                //make call and on callbacks, return response
                docClient.query(params, function(err, data) {
                    console.log("making call");
                    // If the query is invalid
                    if (err) {
                        console.error("Unable to query. Error:", JSON.stringify(err, null, 2));
                        const response = {
                            statusCode: 500,
                            headers: {
                                "Access-Control-Allow-Origin": "*"
                            },
                            body: JSON.stringify({ "message": "Query did not succeed", "error": err, "params": params }),
                        }
                        callback(null,response);
                    }
                    else {
                        console.log("Query succeeded.");
                        console.log(data);
                        const response = {
                            statusCode: 200,
                            headers: {
                                "Access-Control-Allow-Origin": "*"
                            },
                            body: JSON.stringify(data.Items[0]),
                        };
                        callback(null,response);
            
                    }
                });
        }
    }
    else if(event.httpMethod == 'PUT'){
        console.log("test of path parameters",event.pathParameters==null)
        // Make sure pathParameters exist
        if ((typeof event.pathParameters !== 'undefined' )  && event.pathParameters != null && event.pathParameters.name != null && event.pathParameters.xVal != null && event.pathParameters.yVal != null && event.pathParameters.direction != null) {
                var name = event.pathParameters.name;
                var xV = event.pathParameters.xVal;
                var yV = event.pathParameters.yVal;
                var dir = event.pathParameters.direction;
                
                console.log("here we are!:" +  name);
                //  Grab params to query
                var date = new Date();
                date.setHours(date.getHours() - 4);
                var timeStamp = date.toLocaleString();
                console.log("Reee normies" + timeStamp);
                var params = {
                    TableName: "RobotTable",
                    Key: {
                      "nameVal" : name
                    },
                    UpdateExpression: "set xVal = :x, yVal = :y, direction = :d, timeVal = :t",
                    ExpressionAttributeValues: {
                      ":x" : parseInt(xV),
                      ":y" : parseInt(yV),
                      ":d" : dir,
                      ":t" : timeStamp
                    }
                };
                console.log(params);
                
                //make call and on callbacks, return response
                docClient.update(params, function(err, data) {
                    console.log("making call");
                    // If the query is invalid
                    if (err) {
                        console.error("Unable to query. Error:", JSON.stringify(err, null, 2));
                        const response = {
                            statusCode: 500,
                            headers: {
                                "Access-Control-Allow-Origin": "*"
                            },
                            body: JSON.stringify({ "message": "Query did not succeed", "error": err, "params": params }),
                        }
                        callback(null,response);
                    }
                    else {
                        console.log("Query succeeded.");
                        console.log(data);
                        const response = {
                            statusCode: 200,
                            headers: {
                                "Access-Control-Allow-Origin": "*"
                            },
                            body: JSON.stringify({"message": "robot updated", "params": params}),
                        };
                        callback(null,response);
            
                    }
                });
        }
    }
}