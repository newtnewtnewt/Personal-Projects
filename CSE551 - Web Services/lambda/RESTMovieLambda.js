/*
* Name: Noah Dunn 
* Instructor: Dr. Scott Campbell
* Class: CSE 551
* Date: 4/24/2020
* Assignment: Lambda-1
* With assistance from:
* Dr. Campbell, 
* My Previous CSE383 work,
* My Previous CSE451 work,
* W3 Schools, and the Following:
* https://stackoverflow.com/questions/23327010/how-to-generate-unique-id-with-node-js
*/
var AWS = require('aws-sdk');
//  Used to generate UID hashes
const crypto = require("crypto");

exports.handler = (event,context,callback) => {
    // Set the region 
    AWS.config.update({region: 'us-east-1'});
    //  Build the DynamoDB client
    var docClient = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'});
    
    if (event.httpMethod == 'GET') { //If the method is a get
        //setup for parsing path parameter
        console.log("test of path parameters",event.pathParameters==null)
        // Make sure pathParameters exist
        if ((typeof event.pathParameters !== 'undefined' )  && event.pathParameters != null && typeof event.pathParameters.year !== 'undefined') {
                
            // If we have a title, it's time to get comments
            if(event.pathParameters.title != undefined && event.pathParameters.title != null && event.pathParameters.year != undefined && event.pathParameters.year != null){
                try {
                    //  Parse the year as an int
                    year = parseInt(event.pathParameters.year);
                } catch (err) {
                    const response = {
                        statusCode: 500
                    }
                    console.log("can't parse year",event.pathParameters.year);
                    console.log("event",event)
                    callback(null,response)
                }
                var title = event.pathParameters.title;
                //  Grab params to query
                var params = {
                     "TableName": "MOVIE_COMMENTS",
                     "IndexName": "NAME_YEAR_INDEX",
                     "KeyConditionExpression": "MOVIE_NAME = :title and MOVIE_YEAR = :year",
                     "ExpressionAttributeValues": {
                         ":title": title,
                         ":year" : year
                     },
                     "ScanIndexForward": false
                };
                console.log("params",params,"year",year);
                
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
                        const response = {
                            statusCode: 200,
                            headers: {
                                "Access-Control-Allow-Origin": "*"
                            },
                            body: JSON.stringify({ "comments": data.Items }),
                        };
                        callback(null,response);
            
                    }
                });
            }
            // If we just have the year, we want to grab all movies for that year
            else{
                try {
                    //  Parse the year as an int
                    year = parseInt(event.pathParameters.year);
                } catch (err) {
                    const response = {
                        statusCode: 500
                    }
                    console.log("can't parse year",event.pathParameters.year);
                    console.log("event",event)
                    callback(null,response)
                }
                    //  Query the movies based on the year value
                var params = {
                    TableName: "Movies",
                    KeyConditionExpression: "#yr = :yyyy",
                    ExpressionAttributeNames: {
                        "#yr": "year"
                    },
                    ExpressionAttributeValues: {
                        ":yyyy": year
                    }
                };
                console.log("params",params,"year",year);
                
                //make call and on callbacks, return response
                docClient.query(params, function(err, data) {
                    console.log("making call");
                    // If the query is invalid
                    if (err) {
                        console.error("Unable to query. Error:", JSON.stringify(err, null, 2));
                        const response = {
                            statusCode: 500
                        }
                        callback(null,response);
                    }
                    else {
                        console.log("Query succeeded.");
                        const response = {
                            statusCode: 200,
                            headers: {
                                "Access-Control-Allow-Origin": "*"
                            },
                            body: JSON.stringify({ "movies": data.Items }),
                        };
                        callback(null,response);
            
                    }
                });
            }
        }
        else {
            console.log("Error");
            const response = {
                  statusCode: 500,
                  headers: {
                      "Access-Control-Allow-Origin": "*"
                  },
                  body: JSON.stringify({"Message": "You must provide either a valid year, or a valid uid and title"}),
            };
            callback(null,response);
        }
        
    }
    //  If the method is a POST, publish a comment
    else if (event.httpMethod == 'POST') {
        //  POST requires a comment, a year, and a title
        if(JSON.parse(event.body).movie_comment != null && JSON.parse(event.body).movie_comment != undefined && event.pathParameters.year != null && event.pathParameters.year != undefined
        && event.pathParameters.title != null && event.pathParameters.title != undefined){
            //  Grab information from the body and the path
            var comment = JSON.parse(event.body).movie_comment;
            var year = parseInt(event.pathParameters.year);
            var title= event.pathParameters.title
            console.log("Adding '" + comment + "' to comments (" + title + ", " + year + ")");
            //  Create a random UID
            const uid = crypto.randomBytes(16).toString("hex");
            //  Set the params for adding a comment
            var params = {
                TableName: 'MOVIE_COMMENTS',
                Item: {
                'COMMENT_ID': uid,
                'MOVIE_NAME': title,
                'MOVIE_YEAR': year,
                'MOVIE_COMMENT': comment
                }
            };
            //  Call the put method for an insertion
            docClient.put(params, function(err, data) {
                //  If it errors, send back a 500 with a message
                if (err) {
                    console.log("Error", err);
                    
                    const response = {
                           statusCode: 500,
                           headers: {
                               "Access-Control-Allow-Origin": "*"
                           },
                           body: JSON.stringify({"Message": "Comment was not able to be uploaded!", "Error": err}),
                    };
                    callback(null,response);
                // If it succeeds, send back a 200 and what was added
                } else {
                    console.log("Success", data);
                    const response = {
                           statusCode: 200,
                           headers: {
                               "Access-Control-Allow-Origin": "*"
                           },
                           body: JSON.stringify({"Message": "Comment sucessfully added!", 
                                  "Data" : params})
                    };
                    callback(null,response);
                }
            });
        }
        // If parameters aren't inputted, send a 500 and the information back
        else {
            console.log("Error");
            const response = {
                  statusCode: 500,
                  headers: {
                      "Access-Control-Allow-Origin": "*"
                  },
                  body: JSON.stringify({"Message": "You must provide a year and movie title as path parameters, as well as a comment in a JSON body!"}),
            };
            callback(null,response);
        }
    }
    //  If the method is a delete, erase a comment 
     else if (event.httpMethod == 'DELETE') {
         //  Check parameters
        if(event.pathParameters.year != undefined && event.pathParameters.year != null && event.pathParameters.title != undefined && event.pathParameters.title != null){
            //  Using the year pathparameter as a stand in for uid
            var uid = event.pathParameters.year;
            var title = event.pathParameters.title;
            console.log("Removing comment " + uid);
            //  Set deletion params
            var params = {
                Key: {
                    'COMMENT_ID': uid,
                    'MOVIE_NAME': title
                },
                TableName: 'MOVIE_COMMENTS'
            };
            //  Attempt to delete the comment
            docClient.delete(params, function(err, data) {
              // If it errors out, send back a 500 and a message
              if (err) {
                console.log("Error", err);
                const response = {
                  statusCode: 500,
                  headers: {
                      "Access-Control-Allow-Origin": "*"
                  },
                  body: JSON.stringify({"Message": "You must provide a year and movie title as path parameters, as well as a movie_comment in a JSON body!", "Error": err}),
                };
                callback(null,response);
              // If it succeeds, send a 200 and a success message
              } else {
                console.log("Success", data);
                const response = {
                           statusCode: 200,
                           headers: {
                               "Access-Control-Allow-Origin": "*"
                           },
                           body: JSON.stringify({"Message": "Comment sucessfully deleted!"})
                };
                callback(null,response);
              }
            });
        }
        //  If the parameters are invalid, call this
        else { 
            const response = {
                  statusCode: 500,
                  headers: {
                      "Access-Control-Allow-Origin": "*"
                  },
                  body: JSON.stringify({"Message": "You must provide a year and movie title as path parameters, as well as a movie_comment in a JSON body!"}),
            };
            callback(null,response);
        }
    }
    //  If the user wants to update a comment
    else if(event.httpMethod == "PUT"){
        // If the parameters are all set, the API should update the comment
        if(event.pathParameters.year != undefined && event.pathParameters.year != null && event.pathParameters.title != undefined && event.pathParameters.title != null &&
        JSON.parse(event.body).movie_comment != undefined && JSON.parse(event.body).movie_comment != null ) {
            var uid = event.pathParameters.year;  //  Using the year tag to hold the UID
            var title = event.pathParameters.title;
            var comment = JSON.parse(event.body).movie_comment;
            // Update parameters
            var params = {
                TableName: 'MOVIE_COMMENTS',
                Key: {
                  'COMMENT_ID' : uid,
                  'MOVIE_NAME' : title
                },
                UpdateExpression: 'set MOVIE_COMMENT = :c',
                ExpressionAttributeValues: {
                  ':c' : comment
                } 
            };
            //  Call update
            docClient.update(params, function(err, data) {
                // If the call fails, send back a 500 and a message
                if (err) {
                    console.log("Error", err);
                    
                    const response = {
                           statusCode: 500,
                           headers: {
                               "Access-Control-Allow-Origin": "*"
                           },
                           body: JSON.stringify({"Message": "Comment was not able to be uploaded!", "Error": err}),
                    };
                    callback(null,response);
                // If it succeeds, send a 200 and a message
                } else {
                    console.log("Success", data);
                    const response = {
                           statusCode: 200,
                           headers: {
                               "Access-Control-Allow-Origin": "*"
                           },
                           body: JSON.stringify({"Message": "Comment sucessfully added!", 
                                  "Data" : params})
                    };
                    callback(null,response);
                }
            });
        }
        // If the user does not correctly provide valid inputs
        else{
            console.log("Error");
            const response = {
                  statusCode: 500,
                  headers: {
                      "Access-Control-Allow-Origin": "*"
                  },
                  body: JSON.stringify({"Message": "You must provide a uid and movie title as path parameters, as well as a movie_comment in a JSON body!"}),
            };
            callback(null,response);
        }
        
    }
    // If the user goes for an invalid HTTP call or any other misc. error
    else {
        console.log("User attempted invalid call");
        const response = {
              statusCode: 500,
              headers: {
                  "Access-Control-Allow-Origin": "*"
              },
              body: JSON.stringify({"Message": "You did not attempt a valid REST call on this API"}),
        };
        callback(null,response);
    }
};
