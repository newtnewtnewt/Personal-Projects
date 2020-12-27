/*
* Name: Noah Dunn
* Class: CSE 551
* Instructor: Dr. Campbell
* Date: 5/7/2020
* Assignment: Robots in Alexa
* Sources: Dr. Campbell, CSE451, CSE383, W3Schools and
* https://stackoverflow.com/questions/1801160/can-i-use-jquery-with-node-js
*/

// Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// Licensed under the Amazon Software License
// http://aws.amazon.com/asl/

/* eslint-disable  func-names */
/* eslint-disable  no-console */
/* eslint-disable  no-restricted-syntax */

const Alexa = require('ask-sdk');
const ddbAdapter = require('ask-sdk-dynamodb-persistence-adapter'); // included in ask-sdk
const ddbTableName = 'RunningSum';
const i18n = require('i18next');
const sprintf = require('i18next-sprintf-postprocessor');

var mapWidth = 200;
var mapHeight = 200;
//  Some devices to make turn operations easier
var directionMap = {"N" : 0, "E" : 1, "S" : 2, "W" : 3};
var directionArray = ["N", "E", "S", "W"];
var dRealMap = {"N" : "North", "E" : "East", "S" : "South", "W": "West"};

//  A package to get JQUERY to work on Node
var jsdom = require("jsdom");
const { JSDOM } = jsdom;
const { window } = new JSDOM();
const { document } = (new JSDOM('')).window;
global.document = document;

var $ = jQuery = require('jquery')(window);

/*
This function determines how much open space a bot can move
in a particular direction without hitting a wall
*/
async function distanceToWall(name, xMov, yMov, direction) {
	var response;
	//  Get the other bot's position
	if(name == "bob") { 
		response = await getRobotLocation("alice");
	}
	else {
		response = await getRobotLocation("bob");
	}
	//  Grab the X and Y of the other bot based on the currently active bot
	var xV = response.xVal;
	var yV = response.yVal;
	// Provide limits for each direction, insuring that a bot cannot go past the wall
	// Or into/past another bot
	if(direction == "N"){
		if (xV == xMov && yV > yMov) {
			return yV - yMov - 1;
		}
		else {
			return 199 - yMov;
		}
	}
	if(direction == "S"){
		if (xV == xMov && yV < yMov) {
			return yMov - yV - 1;
		}
		else {
			return yMov;
		}
	}
	if(direction == "E"){
		if (yV == yMov && xV > xMov) {
			return xV - xMov - 1;
		}
		else {
			return 199 - xMov;
		}
	}
	if(direction == "W"){
		if (yV == yMov && xV < xMov) {
			return xMov - xV - 1;
		}
		else {
			return xMov;
		}
	}
}

//  This will return the response of the API
function getRobotLocation(name){
	// Call the api and immediately return the AJAX call
	var apiURL = "https://kowy99xtbe.execute-api.us-east-1.amazonaws.com/default"; 
	return $.ajax({
		type: "GET",
		url: apiURL + "/" + name,
		success:function(result){	
		},
		error:function(result){
		}
	})
}

// Update the robot's position with the given parameters
function updateRobot(name, xVal, yVal, direction){
	// Call the api and immediately return the AJAX call
	var apiURL = "https://kowy99xtbe.execute-api.us-east-1.amazonaws.com/default"; 
	apiURL += "/" + name + "/" + xVal + "/" + yVal + "/" + direction
	console.log(apiURL);
	return $.ajax({
		type: "PUT",
		url: apiURL,
		success:function(result){	
		},
		error:function(result){
		}
	})
}
// Move the robot, given some parameters, including steps and direction
async function moveRobot(name, xV, yV, dir, steps, fb){
// If it's forward, perform additions to either the yVal or xVal depending on direction
if(fb == "forward" || fb == "forwards"){
	if(dir == "N"){
		console.log(fb + " " + steps + " steps ");
		await updateRobot(name, xV, yV + steps, dir)
		console.log("Done!");
		return;
	}
	else if(dir == "S"){
		console.log(fb + " " + steps + " steps ");
		await updateRobot(name, xV, yV - steps, dir)
		console.log("Done!");
		return; 
	}
	else if(dir == "E"){
		console.log(fb + " " + steps + " steps ");
		await updateRobot(name, xV + steps, yV, dir)
		console.log("Done!");
		return;
	}
	else {
		console.log(fb + " " + steps + " steps ");
		await updateRobot(name, xV - steps, yV, dir)
		console.log("Done!");
		return;
	}
// If it's backwards, perform additions to either the yVal or xVal depending on direction
// Opposite of the other one
} else {
	if(dir == "N"){
		console.log(fb + " " + steps + " steps ");
		await updateRobot(name, xV, yV - steps, dir)
		console.log("Done!");
		return;
	}
	else if(dir == "S"){
		console.log(fb + " " + steps + " steps ");
		await updateRobot(name, xV, yV + steps, dir)
		console.log("Done!");
		return;
	}
	else if(dir == "E"){
		console.log(fb + " " + steps + " steps ");
		await updateRobot(name, xV - steps, yV, dir)
		console.log("Done!");
		return;
	}
	else {
		console.log(fb + " " + steps + " steps ");
		await updateRobot(name, xV + steps, yV, dir)
		console.log("Done!");
		return;
	}
}

}

/* An intent will fire this body of code when the LocationRequest is called*/
const LocationRequest = {
  //  We want to check to make sure this only fires at the beginning of an interaction 
  //  Sequence 
  canHandle(handlerInput) {
      return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
      && Alexa.getIntentName(handlerInput.requestEnvelope) === 'LocationRequest'
  },
  async handle(handlerInput) {
	console.log("LocationRequest");
	var name = Alexa.getSlotValue(handlerInput.requestEnvelope, 'name');
	if(name != undefined && name != null) { name = name.toLowerCase(); }
	if(name != undefined && name != null && name == "bob" || name == "alice"){
		// Get the specified bot's location information
		var result = await getRobotLocation(name);
		var nameV = result.nameVal;
		var xV = result.xVal;
		var yV = result.yVal;
		var dir = result.direction;
		// Calculate distance to Wall or other robot
		var dist = await distanceToWall(nameV, xV, yV, dir);
		console.log(xV, " ", yV, " ", dir, " ", dist);
		// Have Alexa spit out result
		var output = handlerInput.responseBuilder
			.speak(nameV + " is currently at position " + xV + " by " + yV + " facing " + dRealMap[dir] + ", they can move " + dist + " units forward")
			.withShouldEndSession(false)
			.getResponse();
		return output;
		
	} else {
		return handlerInput.responseBuilder
		  .withShouldEndSession(false)
		  .speak("The only bots available on this map are Bob and Alice")
		  .getResponse();
	} 
  },
};

/* An intent will TurnRequest is called*/
const TurnRequest = {
  //  We want to check to make sure this only fires at the beginning of an interaction 
  //  Sequence 
  canHandle(handlerInput) {
      return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
      && Alexa.getIntentName(handlerInput.requestEnvelope) === 'TurnRequest'
  },
  async handle(handlerInput) {
	console.log("TurnRequest");
	// Grab the turn input
	var turn = Alexa.getSlotValue(handlerInput.requestEnvelope, 'turndirection');
	if(turn != undefined && turn != null) { turn = turn.toLowerCase(); }
	if(turn != undefined && turn != null && turn == "left" || turn == "right"){
		//  Grab the name input 
		var name = Alexa.getSlotValue(handlerInput.requestEnvelope, 'name');
		if(name != undefined && name != null) { name = name.toLowerCase(); }
		if(name != undefined && name != null && name == "bob" || name == "alice"){
			// Get the specified bot's location information
			var result = await getRobotLocation(name);
			var dir = result.direction;
			var xV = result.xVal;
			var yV = result.yVal;
			var newDir;
			//  If the turn is a right, we 'increment' the direction using the map
			if(turn == "right"){
				newDir = directionArray[(directionMap[dir] + 1 + 4) % 4];
			}
			// If it's a left, we 'decrement' the direction using the map
			else {
				newDir = directionArray[(directionMap[dir] - 1 + 4) % 4];
			}
			//  Print the change of direction to the logs
			console.log(dir, "->", newDir);
			//  Wait on the update
			var response = await updateRobot(name, xV, yV, newDir);
			// Print the response
			console.log("Put response " + JSON.stringify(response));
			// Respond with the bot and which wey they turned
			var output = handlerInput.responseBuilder
				.speak(name + " turned " + turn)
				.withShouldEndSession(false)
				.getResponse();
			return output;
		} else {
			//  If the user does not offer Bob or Alice, let them know their options
			var output = handlerInput.responseBuilder
				.speak("Bob and Alice are the only bots available on this map")
				.withShouldEndSession(false)
				.getResponse();
			return output;
		}
		
	} else {
		//  If the user tries to say something that is not right or left
		return handlerInput.responseBuilder
		  .withShouldEndSession(false)
		  .speak("The bot can only turn right or left")
		  .getResponse();
	} 
  },
};

/* An intent will MoveRequest is called*/
const MoveRequest = {
  //  We want to check to make sure this only fires at the beginning of an interaction 
  //  Sequence 
  canHandle(handlerInput) {
      return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
      && Alexa.getIntentName(handlerInput.requestEnvelope) === 'MoveRequest'
  },
  async handle(handlerInput) {
	// Print that the request was fired off
	console.log("MoveRequest");
	// Grab the name
	var name = Alexa.getSlotValue(handlerInput.requestEnvelope, 'name');
	if (name != undefined && name != null) { name = name.toLowerCase(); }
	if(name != undefined && name != null && name == "bob" || name == "alice"){
		// Get the specified bot's location information
		var result = await getRobotLocation(name);
		var dir = result.direction;
		var xV = result.xVal;
		var yV = result.yVal;
		const stepsNum = Alexa.getSlotValue(handlerInput.requestEnvelope, 'steps');
		const fb = Alexa.getSlotValue(handlerInput.requestEnvelope, 'forb');
		var distWall;
		//  If nothing is provided, default to forward
		if(fb == undefined) { fb = "forward"; }
		//  If it's forward calculate distance like normal
		if(fb == "forward"){
			distWall = await distanceToWall(name, xV, yV, dir);
		}
		//  If it's backward, act like you are calculating distance in reverse
		else {
			var directionMap = {"N" : "S", "E" : "W", "S" : "N", "W" : "E"};
			distWall = await distanceToWall(name, xV, yV, directionMap[dir]);
		}
		//  If the user provides a number of steps
		if(stepsNum != undefined){
			try {
				//  Try parsing
				var stepsParse = parseInt(stepsNum);
				//  If we are cornered, tell the user
				if(distWall == 0){
					console.log("The path is blocked");
					var output = handlerInput.responseBuilder
						.speak("I can't, the way " + fb + " is blocked")
						.withShouldEndSession(false)
						.getResponse();
					return output;
				}
				//  If there is an obstacle closer than the desired jump
				else if(distWall < stepsNum){
					console.log("I can only move " + distWall + " steps " + fb);
					var output = handlerInput.responseBuilder
						.speak("I can only move " + distWall + " steps " + fb)
						.withShouldEndSession(false)
						.getResponse();
					return output;
				}
				//  Do the move
				else { 
					console.log("Attempting move!");
					await moveRobot(name, xV, yV, dir, stepsParse, fb);
					console.log("Finished move!");
					var output = handlerInput.responseBuilder
						.speak("Okay!")
						.withShouldEndSession(false)
						.getResponse();
					return output;
				}
				
			}
			catch(error) { 
				//  If the int can't be parsed, inform the user
				console.log("Error: ", error);
				var output = handlerInput.responseBuilder
					.speak("The number of steps should be a valid integer!")
					.withShouldEndSession(false)
					.getResponse();
				return output;
			}
		} else {
			// If number of steps is not provided, default to just one
			if(distWall > 0){
				await moveRobot(name, xV, yV, dir, 1, fb);
				var output = handlerInput.responseBuilder
					.speak("Okay!")
					.withShouldEndSession(false)
					.getResponse();
				return output;
			} else {
				var output = handlerInput.responseBuilder
					.speak("I can't, the way " + fb  + " is blocked")
					.withShouldEndSession(false)
					.getResponse();
				return output;
			}
		}
	} else {
		//  Bob and Alice are the only valid pics
		var output = handlerInput.responseBuilder
			.speak("Bob and Alice are the only bots available on this map")
			.withShouldEndSession(false)
			.getResponse();
		return output;
	}
	
  },
};




// This fires if the user attempts an invalid call
const UnhandledIntent = {
  canHandle() {
    return true;
  },
  handle(handlerInput) {
	// Inform the user they attempted an invalid command
    const requestAttributes = handlerInput.attributesManager.getRequestAttributes();
	console.log("UnhandledIntent");
    return handlerInput.responseBuilder
      .speak("That intent is not supported by this skill! You are likely not following directions.")
      .reprompt("That intent is not supported by this skill! You are likely not following directions.")
      .getResponse();
  },
};


const ErrorHandler = {
  //  Errors require no conditions to fire
  canHandle() {
    return true;
  },
  handle(handlerInput, error) {
    // Print where the error occured
    console.log(`Error handled: ${error.message}`);
	if(error.stack != undefined){
		console.log(`Error stack: ${error.stack}`);
	}
	else { 
		console.log(`Here's what was found: ${error.toString()}`);
	}
    const requestAttributes = handlerInput.attributesManager.getRequestAttributes();
	console.log("ErrorHandler");
    return handlerInput.responseBuilder
      .speak("There was an error in the code")
      .reprompt("There was an error in the code")
      .getResponse();
  },
};

// Build the full suite of what we need for the skill, 
// In the order we need it
const skillBuilder = Alexa.SkillBuilders.custom();

exports.handler = skillBuilder
  .addRequestHandlers(
    LocationRequest,
	TurnRequest,
	MoveRequest,
    UnhandledIntent,
  )
  .addErrorHandlers(ErrorHandler)
  .lambda();

