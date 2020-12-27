//551 Grad Secret Santa Project
const Alexa = require('ask-sdk-core');
var jsdom = require("jsdom");
const { JSDOM } = jsdom;
const { window } = new JSDOM();
const { document } = (new JSDOM('')).window;
global.document = document;

var $ = jQuery = require('jquery')(window);

const SecretSantaIntent = {
  canHandle(handlerInput) {
    console.log("Begining launch request " + (handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'SecretSantaIntent'));
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'SecretSantaIntent';
  },
  handle(handlerInput) {
    console.log("Beginning a new secret santa...");
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    sessionAttributes.santaState = 'INVOKED';
    handlerInput.attributesManager.setSessionAttributes(sessionAttributes);
    console.log("Secret Santa Invoked");
    const speechOutput = 'Okay, please give me a name';
    const reProm = 'Give me a first name for the Secret Santa';
    console.log("Got through the first step");
    return handlerInput.responseBuilder
      .speak(speechOutput)
      .reprompt(reProm)
      .getResponse();
  },
};

const FirstNameIntent = {
  canHandle(handlerInput) {
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    console.log("Begining first name intent " + (handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'FirstNameIntent'));
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'FirstNameIntent'
    && (sessionAttributes.santaState == 'INVOKED');
  },
  handle(handlerInput) {
    console.log("Asking for firstname");
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    sessionAttributes.santaState = 'FIRSTNAME';
    var first = Alexa.getSlotValue(handlerInput.requestEnvelope, 'first');
    //var last = Alexa.getSlotValue(handlerInput.requestEnvelope, 'last');
    sessionAttributes.firstName = first;
    //sessionAttributes.lastName = last;        
    handlerInput.attributesManager.setSessionAttributes(sessionAttributes);
    console.log("Got and set first name");
    const speechOutput = 'Got ' + first + ' as the first name, what is the last name?';
    const reProm = 'Give me a last name for the Secret Santa';
    console.log("Got through the first name step");
    return handlerInput.responseBuilder
      .speak(speechOutput)
      .reprompt(reProm)
      .getResponse();
  },
};

const LastNameIntent = {
  canHandle(handlerInput) {
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    console.log("Begining last name intent " + (handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'LastNameIntent'));
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'LastNameIntent'
    && (sessionAttributes.santaState == 'FIRSTNAME');
  },
  handle(handlerInput) {
    console.log("Asking for last name");
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    sessionAttributes.santaState = 'LASTNAME';
    //var first = Alexa.getSlotValue(handlerInput.requestEnvelope, 'first');
    var last = Alexa.getSlotValue(handlerInput.requestEnvelope, 'last');
    //sessionAttributes.firstName = first;
    sessionAttributes.lastName = last;
    handlerInput.attributesManager.setSessionAttributes(sessionAttributes);
    console.log("Got and set last name");
    const speechOutput = 'Got ' + last + ' as the last name, what is their phone number?';
    const reProm = 'I need a phone number';
    console.log("Got through the last name step");
    return handlerInput.responseBuilder
      .speak(speechOutput)
      .reprompt(reProm)
      .getResponse();
  },
};

const NumberIntent = {
  canHandle(handlerInput) {
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    console.log("Begining number intent " + (handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'NumberIntent'));
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'NumberIntent'
    && (sessionAttributes.santaState == 'LASTNAME');
  },
  handle(handlerInput) {
    console.log("Asking for number");
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    sessionAttributes.santaState = 'PHONE';
    var number = Alexa.getSlotValue(handlerInput.requestEnvelope, 'Number');
    console.log('Got ' + number);
    sessionAttributes.number = number;
    handlerInput.attributesManager.setSessionAttributes(sessionAttributes);
    console.log("Got and set their phone number");
    const speechOutput = 'Okay, what does ' + sessionAttributes.firstName + ' ' + sessionAttributes.lastName + ' like?';
    const reProm = 'I need to know what they like.';
    console.log("Phone number intent finished");
    return handlerInput.responseBuilder
      .speak(speechOutput)
      .reprompt(reProm)
      .getResponse();
  },
};

const LikesIntent = {
  canHandle(handlerInput) {
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    console.log("Begining likes intent " + (handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'LikesIntent'));
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'LikesIntent'
    && (sessionAttributes.santaState == 'PHONE');
  },
  handle(handlerInput) {
    console.log("Asking for likes");
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    sessionAttributes.santaState = 'LIKES';
    var likes = Alexa.getSlotValue(handlerInput.requestEnvelope, 'likes');
    sessionAttributes.likes = likes;
    handlerInput.attributesManager.setSessionAttributes(sessionAttributes);
    console.log("Got and set the likes");
    const speechOutput = 'Okay, would you like to add another member?';
    const reProm = 'I can add another member to the Secret Santa if you want.';
    console.log("One more member ready to go");
    return handlerInput.responseBuilder
      .speak(speechOutput)
      .reprompt(reProm)
      .getResponse();
  },
};

const YesIntent = {
  canHandle(handlerInput) {
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    console.log("Checking can handle yes intent " + (handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'YesIntent'));
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'YesIntent'
    && (sessionAttributes.santaState == 'LIKES');
  },
  handle(handlerInput) {
    console.log("Doing the yes intent");
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    sessionAttributes.santaState = 'INVOKED';
    var name = sessionAttributes.firstName + " " + sessionAttributes.lastName;
    var number = sessionAttributes.number;
    var likes = sessionAttributes.likes;
    var person = {"name" : name, "number": number, "likes": likes};
    if(sessionAttributes.people == undefined){
                sessionAttributes.people = new Array();
                sessionAttributes.people[0] = person;
        }else
		sessionAttributes.people[sessionAttributes.people.length] = person;
    handlerInput.attributesManager.setSessionAttributes(sessionAttributes);
    console.log("Added " + name + " to the secret santa");
    const speechOutput = 'Okay, give me another first name';
    const reProm = 'Could I have the first name of the next person?';
    console.log("YesIntent finished");
    return handlerInput.responseBuilder
      .speak(speechOutput)
      .reprompt(reProm)
      .getResponse();
  },
};

const NoIntent = {
  canHandle(handlerInput) {
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    console.log("Checking can handle no intent " + (handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'NoIntent'));
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'NoIntent'
    && (sessionAttributes.santaState == 'LIKES');
  },
  handle(handlerInput) {
    console.log("Doing the no intent");
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    console.log("NoIntent sessionAttr grabbed");
    var name = sessionAttributes.firstName + " " + sessionAttributes.lastName;
        var number = sessionAttributes.number;
        var likes = sessionAttributes.likes;
        var person = {"name" : name, "number": number, "likes": likes};
        if(sessionAttributes.people == undefined){
                sessionAttributes.people = new Array();
                sessionAttributes.people[0] = person;
        }else
                sessionAttributes.people[sessionAttributes.people.length] = person;
    let speechOutput;
    if(sessionAttributes.people != null)
	console.log("People in the SS: " + sessionAttributes.people.length);
    if(!(sessionAttributes.people == undefined || sessionAttributes.people == null) && sessionAttributes.people.length > 2){
    	sessionAttributes.santaState = 'SEND';
	speechOutput = 'Okay, would you like to send it out?';
	console.log("More than 2 people in the SS");
    }else{
	sessionAttributes.santaState = 'INVOKED';
	speechOutput = 'We need more than two people in the Secret Santa. How about another first name?';
	console.log("2 or fewer people in the SS, setting state back to " + sessionAttributes.santaState);
    }
    handlerInput.attributesManager.setSessionAttributes(sessionAttributes);
    console.log("State set to " + sessionAttributes.santaState);
    const reProm = speechOutput;
    console.log("NoIntent finished");
    return handlerInput.responseBuilder
      .speak(speechOutput)
      .reprompt(reProm)
      .getResponse();
  },
};

const SendIntent = {
  canHandle(handlerInput) {
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    console.log("Checking can handle send intent " + (handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'SendIntent'));
    return handlerInput.requestEnvelope.request.type === 'IntentRequest'
    && handlerInput.requestEnvelope.request.intent.name === 'SendIntent'
    && (sessionAttributes.santaState == 'SEND');
  },
  async handle(handlerInput) {
    console.log("Posting to API");
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    var people = new Array();
    console.log("prepping data");
    sessionAttributes.people.forEach(item => {
	people[people.length] = [item.name, item.number, item.likes];
    });
    console.log("data prepped " + people);
    var sendData = {"participants": people};
    var response = await sendSecretSanta(JSON.stringify(sendData));
    console.log("Response: " + response);
    handlerInput.attributesManager.setSessionAttributes(sessionAttributes);
    console.log("State set to " + sessionAttributes.santaState);
    console.log("SendIntent finished");
    return handlerInput.responseBuilder
      .speak('Secret Santa has been sent. Enjoy!')
      .withShouldEndSession(true)
      .getResponse();
  },
};

async function sendSecretSanta(sendData){
    return new Promise((resolve, reject)=>{
	$.ajax({
            type: "POST",
            url: "https://gcctp5xjeb.execute-api.us-east-1.amazonaws.com/default",
	    data: sendData,
            success:function(result){
                resolve(true);
            },
            error:function(result){
                console.log("Error calling API")
                console.log(result);
                reject();
            }
        })
    });
}

const UnhandledIntent = {
  canHandle() {
    return true;
  },
  handle(handlerInput) {
     return handlerInput.responseBuilder
                .speak('I must have misheard you, I do not know how to handle that.')
                .reprompt('Can you repeat what you said?')
                .getResponse();
	/*
    const sessionAttributes = handlerInput.attributesManager.getSessionAttributes();
    if(sessionAttributes.calcState === 'INVOKED'){
        return handlerInput.responseBuilder
                .speak('I am not sure what you mean, are you trying to use the calculator?')
                .reprompt('Are you trying to use the calculator?')
                .getResponse();
    }else if(sessionAttributes.calcState === 'CALCULATING'){
         return handlerInput.responseBuilder
                .speak('I am not sure what you mean, are you trying to use the calculator?')
                .reprompt('Are you trying to use the calculator?')
                .getResponse();
    }else{
         return handlerInput.responseBuilder
                .speak('I am not sure what you mean...')
                .reprompt('Can you repeat what you said?')
                .getResponse();
    }*/
  },
};

const ErrorHandler = {
  canHandle() {
    console.log("Error handler!");
    return true;
  },
  handle(handlerInput, error) {
    console.log('Error handled: ' + error.message);
    console.log('Error stack: ' + error.stack);

    return handlerInput.responseBuilder
      .speak('Approximately large error')
      .reprompt('That did not go as planned')
      .getResponse();
  },
};

let skill;
exports.handler = async function (event, context) {
        console.log(`REQUEST++++${JSON.stringify(event)}`);
        if (!skill) {
                skill = Alexa.SkillBuilders.custom()
                        .addRequestHandlers(
                                SecretSantaIntent,
				FirstNameIntent,
				LastNameIntent,
				NumberIntent,
				LikesIntent,
				YesIntent,
				NoIntent,
				SendIntent,
                                UnhandledIntent,
                        )
                        .addErrorHandlers(ErrorHandler)
                        .create();
        }

        const response = await skill.invoke(event, context);
        console.log(`RESPONSE++++${JSON.stringify(response)}`);

        return response;
};

