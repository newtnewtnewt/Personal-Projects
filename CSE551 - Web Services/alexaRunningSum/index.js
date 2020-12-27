/*
* Name: Noah Dunn
* Class: CSE 551
* Instructor: Dr. Campbell
* Date: 5/2/2020
* Assignment: Calculator in Alexa
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
const languageStrings = {
  'en': require('./languages/en'),
  'es': require('./languages/es')
}


/* An intent will fire this body of code when the initial command is called*/
const LaunchRequest = {
  //  We want to check to make sure this only fires at the beginning of an interaction 
  //  Sequence 
  canHandle(handlerInput) {
	//  We only want to fire this Intent off if the user has not begun to 'play' 
	//  the calculator yet
    let isCurrentlyPlaying = false;
    const { attributesManager } = handlerInput;
    const sessionAttributes = attributesManager.getSessionAttributes();
	// Confirmation check
    if (sessionAttributes.gameState &&
      sessionAttributes.gameState === 'STARTED') {
      isCurrentlyPlaying = true;
    }
	// If the intent meets the correct requirements and we are not playing, proceed
    return !isCurrentlyPlaying &&
      Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
      && Alexa.getIntentName(handlerInput.requestEnvelope) === 'LaunchRequest'
  },
  async handle(handlerInput) {
	console.log("LaunchRequest");
	//  Send our first message back to the user
    const speechOutput = "Welcome to calculator, Would you like to sum up numbers?"
    const reprompt = "Welcome to calculator, Would you like to sum up numbers?"

    return handlerInput.responseBuilder
      .speak(speechOutput)
      .reprompt(reprompt)
      .getResponse();
  },
};

/* A handler to get us out of the calculator */
const ExitHandler = {
  // This fires when the user says stop or cancel 
  canHandle(handlerInput) {
    return Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest'
      && (Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.CancelIntent'
        || Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.StopIntent');
  },
  handle(handlerInput) {
	//  Reset all of our session variables from the calculator
    const { attributesManager } = handlerInput;
	const requestAttributes = attributesManager.getRequestAttributes();
	const sessionAttributes = attributesManager.getSessionAttributes();
	sessionAttributes.runningSum = 0;
	sessionAttributes.gameState = 'ENDED';
	sessionAttributes.isCurrentlyPlaying = false;
	//  Save them to the session
	attributesManager.setSessionAttributes(sessionAttributes);
    return handlerInput.responseBuilder
      .speak("Thanks for using the calculator!")
      .getResponse();
  },
};

const YesIntent = {
  canHandle(handlerInput) {
    // only start a new game if yes is said when not playing a game.
    let isCurrentlyPlaying = false;
    const { attributesManager } = handlerInput;
    const sessionAttributes = attributesManager.getSessionAttributes();

    if (sessionAttributes.gameState &&
      sessionAttributes.gameState === 'STARTED') {
      isCurrentlyPlaying = true;
    }
	// Run the check based on our session variables
    return !isCurrentlyPlaying 
      && Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' 
      && Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.YesIntent';
  },
  async handle(handlerInput) {
	//  Begin the game and save information that we have begun the game
    const { attributesManager } = handlerInput;
    const requestAttributes = attributesManager.getRequestAttributes();
    const sessionAttributes = attributesManager.getSessionAttributes();
	//  Starting values for our three variables
    sessionAttributes.gameState = 'STARTED';
	sessionAttributes.isCurrentlyPlaying = true;
    sessionAttributes.runningSum = 0;
	//  Save them to the session
	attributesManager.setSessionAttributes(sessionAttributes);

	console.log("YesIntent");
    return handlerInput.responseBuilder
      .speak("Great! Start by saying a number for me to sum!")
      .reprompt("Great! Start by saying a number for me to sum!")
      .getResponse();
  },
};

/* This allows the user to stop the calculator before they begin */
const NoIntent = {
  canHandle(handlerInput) {
	// 'No' fires only before we begin the calculator
    let isCurrentlyPlaying = false;
    const { attributesManager } = handlerInput;
    const sessionAttributes = attributesManager.getSessionAttributes();

    if (sessionAttributes.gameState &&
      sessionAttributes.gameState === 'STARTED') {
      isCurrentlyPlaying = true;
    }
	//  Check all the criteria to actually fire the event
    return !isCurrentlyPlaying 
      && Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' 
      && Alexa.getIntentName(handlerInput.requestEnvelope) === 'AMAZON.NoIntent';
  },
  async handle(handlerInput) {
	//  Get all the session attributes
    const { attributesManager } = handlerInput;
    const requestAttributes = attributesManager.getRequestAttributes();
    const sessionAttributes = attributesManager.getSessionAttributes();
	//  Stop the calculator
	console.log("NoIntent");
    sessionAttributes.gameState = 'ENDED';
	
    return handlerInput.responseBuilder
      .speak("See you later!")
      .getResponse();

  },
};

/* This is what add a number to the running Sum  */
const UserInputNum = {
	canHandle(handlerInput) {
    // Handle numbers only after the calculator has been started
    let isCurrentlyPlaying = false;
    const { attributesManager } = handlerInput;
    const sessionAttributes = attributesManager.getSessionAttributes();
	console.log(sessionAttributes.gameState);
    if (sessionAttributes.gameState &&
      sessionAttributes.gameState === 'STARTED') {
      isCurrentlyPlaying = true;
    }
	//  If the conditions are met, fire the handle
    return isCurrentlyPlaying 
      && Alexa.getRequestType(handlerInput.requestEnvelope) === 'IntentRequest' 
      && Alexa.getIntentName(handlerInput.requestEnvelope) === 'UserInputNum';
  },
  async handle(handlerInput) {
	  const { attributesManager } = handlerInput;
	  const requestAttributes = attributesManager.getRequestAttributes();
	  const sessionAttributes = attributesManager.getSessionAttributes();
	  // If the user inputs a valid AMAZON.NUMBER
	  try { 
		const newNum = parseInt(Alexa.getSlotValue(handlerInput.requestEnvelope, 'number'), 10);
		if(isNaN(newNum)){ // If it's invalid, throw an error
			throw "error";
		}
		//  Add to the running sum
		sessionAttributes.runningSum = sessionAttributes.runningSum + newNum;
		//  Save the value
		attributesManager.setSessionAttributes(sessionAttributes);
		
		// Tell the value to the user
		return handlerInput.responseBuilder
		  .speak("The sum is " + sessionAttributes.runningSum)
		  .reprompt("The sum is " + sessionAttributes.runningSum)
		  .getResponse();
		
	  } catch (error) {
		//  Tell the user their input was invalid
		return handlerInput.responseBuilder
		  .speak("Please give me a valid number")
		  .reprompt("Please give me a valid number")
		  .getResponse();
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
    console.log(`Error stack: ${error.stack(20)}`);
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
    LaunchRequest,
    ExitHandler,
    YesIntent,
	NoIntent,
	UserInputNum,
    UnhandledIntent
  )
  .addErrorHandlers(ErrorHandler)
  .lambda();

