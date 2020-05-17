/*
* Name: Noah Dunn
* Course: CSE551
* Professor: Dr. Campbell
* Date: 5/11/2020
* Assignment: Final Grad Project
* with help from: https://stackoverflow.com/questions/2450954/how-to-randomize-shuffle-a-javascript-array
*/

const AWS = require('aws-sdk');

// Set region
AWS.config.update({ region: 'us-east-1' });

const snsClient = new AWS.SNS({ apiVersion: '2010-03-31' });

exports.handler = async (event, context) => {

  if (event.httpMethod == 'POST') {
    if (event.body == null) {
      return {
        statusCode: 403,
        headers: {
          "Access-Control-Allow-Origin": "*"
        },
        body: JSON.stringify({ "Message": "You must supply a body in the form { participants: [[Bill Clinton (name), XXXYYYYZZZZ (PhoneNumber),  Videogames, boardgames, etc (Likes)]],[...],[...]}" }),
      };
    }
    else {
      try {
        // Grab the full JSON body
        const body = JSON.parse(event.body);
        if (body.participants.length < 2) {
          throw "You need at least 2 participants";
        }
        // Grab the participants Array
        const participantsArray = body.participants;
        //  Create a repsonse array
        const responseArray = new Array(participantsArray.length);
        //  Shuffle that array
        shuffle(participantsArray);
        //  Send the messages
        for (let i = 0; i < participantsArray.length; i++) {
          let sSanta, recipient;
          if (i == participantsArray.length - 1) {
            //  Grab a santa
            sSanta = participantsArray[participantsArray.length - 1];
            //  Grab the person they are gifting to
            recipient = participantsArray[0];
          } else {
            //  Grab a santa
            sSanta = participantsArray[i];
            //  Grab the person they are gifting to
            recipient = participantsArray[i + 1];
          }
          //  The santa needs to know the recipient's name and their likes
          const recipientName = recipient[0];
          const recipientLikes = recipient[2];
          //  The Santa's number is where the information will be sent
          const santaNumber = sSanta[1];
          const santaName = sSanta[0];
          //  The full drafted message to a santa
          const full_message = "Hello " + santaName + "! \r\n";
          full_message += "For Secret Santa this year, you have been selected to get a gift for: " + recipientName + "\n";
          full_message += "They really like: " + recipientLikes + "\r\n"
          full_message += "Merry Christmas!";
          // Create publish parameters
          const US_STARTING_CODE = "1001";
          const params = {
            Message: full_message, /* required */
            PhoneNumber: US_STARTING_CODE + santaNumber,
          };
          responseArray[i] = santaName + " -> " + recipientName;
          const publishText = await snsClient.publish(params).promise();
        }

        return {
          statusCode: 200,
          headers: {
            "Access-Control-Allow-Origin": "*"
          },
          body: JSON.stringify({ "Message": "Santa Complete!", "List": responseArray }),
        };

      } catch (err) {
        return {
          statusCode: 403,
          headers: {
            "Access-Control-Allow-Origin": "*"
          },
          body: JSON.stringify({ "Message": "You must supply a body in the form { participants: [[Bill Clinton (name), XXXYYYYZZZZ (PhoneNumber),  Videogames, boardgames, etc (Likes)]],[...],[...]}", "Error": err }),
        };
      }
    }
  }

};

function shuffle(array) {
  let currentIndex = array.length, temporaryValue, randomIndex;

  // While there remain elements to shuffle...
  while (0 !== currentIndex) {

    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;

    // And swap it with the current element.
    temporaryValue = array[currentIndex];
    array[currentIndex] = array[randomIndex];
    array[randomIndex] = temporaryValue;
  }

  return array;
}
