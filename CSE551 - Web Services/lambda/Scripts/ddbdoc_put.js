var AWS = require('aws-sdk');
// Set the region 
AWS.config.update({region: 'us-east-1'});

// Create DynamoDB document client
var docClient = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'});

var params = {
  TableName: 'MOVIE_COMMENTS',
  Item: {
    'MOVIE_YEAR': 1982,
    'MOVIE_NAME': 'TRON',
    'COMMENT': 'THE GRID A DIGITAL FRONTIER'
  }
};

docClient.put(params, function(err, data) {
  if (err) {
    console.log("Error", err);
  } else {
    console.log("Success", data);
  }
});

