/*
 * Scott Campbell
 * Sample HELLO world
 * */

const Alexa = require('ask-sdk-core');


const LaunchRequestHandler = {
	canHandle(handlerInput) {
		return handlerInput.requestEnvelope.request.type === 'LaunchRequest';
	},
	handle(handlerInput) {
		const speechText = 'Welcome to the Alexa Skills Kit, you can say hello!';

		return handlerInput.responseBuilder
			.speak(speechText)
			.reprompt(speechText)
			.withSimpleCard('Hello World', speechText)
			.getResponse();
	}
};

const HelloWorldIntentHandler = {
	canHandle(handlerInput) {
		return handlerInput.requestEnvelope.request.type === 'IntentRequest'
			&& handlerInput.requestEnvelope.request.intent.name === 'HelloWorldIntent';
	},
	handle(handlerInput) {
		var d = new Date();
		var weekday = new Array(7);
		weekday[0] = "Sunday";
		weekday[1] = "Monday";
		weekday[2] = "Tuesday";
		weekday[3] = "Wednesday";
		weekday[4] = "Thursday";
		weekday[5] = "Friday";
		weekday[6] = "Saturday";

		var n = weekday[d.getDay()];

		const speechText = 'Hello World!, this is quite exciting to be able to say things to you, it is '+n
		return handlerInput.responseBuilder
			.speak(speechText)
			.withSimpleCard('Hello World', speechText)
			.getResponse();
	}
};

const IntroductionIntentHandler = {
        canHandle(handlerInput) {
                return handlerInput.requestEnvelope.request.type === 'IntentRequest'
                        && handlerInput.requestEnvelope.request.intent.name === 'IntroductionIntent';
        },
        handle(handlerInput) {
                const speechText = "<prosody rate='50%'><amazon:emotion name='disappointed' intensity='high'><voice name = 'Mia' > Hi! Im Noah Dunn and I am officially <say-as interpret-as='spell-out'>DUNN</say-as> with this semester</voice></amazon:emotion></prosody>"
                return handlerInput.responseBuilder
                        .speak(speechText)
                        .withSimpleCard('Hello World', speechText)
                        .getResponse();
        }
};


const HelpIntentHandler = {
	canHandle(handlerInput) {
		return handlerInput.requestEnvelope.request.type === 'IntentRequest'
			&& handlerInput.requestEnvelope.request.intent.name === 'AMAZON.HelpIntent';
	},
	handle(handlerInput) {
		const speechText = 'You can say hello to me!';

		return handlerInput.responseBuilder
			.speak(speechText)
			.reprompt(speechText)
			.withSimpleCard('Hello World', speechText)
			.getResponse();
	}
};

const CancelAndStopIntentHandler = {
	canHandle(handlerInput) {
		return handlerInput.requestEnvelope.request.type === 'IntentRequest'
			&& (handlerInput.requestEnvelope.request.intent.name === 'AMAZON.CancelIntent'
				|| handlerInput.requestEnvelope.request.intent.name === 'AMAZON.StopIntent');
	},
	handle(handlerInput) {
		const speechText = 'Goodbye!';

		return handlerInput.responseBuilder
			.speak(speechText)
			.withSimpleCard('Hello World', speechText)
			.withShouldEndSession(true)
			.getResponse();
	}
};

const SessionEndedRequestHandler = {
	canHandle(handlerInput) {
		return handlerInput.requestEnvelope.request.type === 'SessionEndedRequest';
	},
	handle(handlerInput) {
		//any cleanup logic goes here
		return handlerInput.responseBuilder.getResponse();
	}
};

const ErrorHandler = {
	canHandle() {
		return true;
	},
	handle(handlerInput, error) {
		console.log(`Error handled: ${error.message}`);

		return handlerInput.responseBuilder
			.speak('Sorry, I can\'t understand the command. Please say again.')
			.reprompt('Sorry, I can\'t understand the command. Please say again.')
			.getResponse();
	},
};



let skill;



exports.handler = async function (event, context) {
	console.log(`REQUEST++++${JSON.stringify(event)}`);
	if (!skill) {
		skill = Alexa.SkillBuilders.custom()
			.addRequestHandlers(
				LaunchRequestHandler,
				HelloWorldIntentHandler,
				HelpIntentHandler,
				CancelAndStopIntentHandler,
				SessionEndedRequestHandler,
				IntroductionIntentHandler
			)
			.addErrorHandlers(ErrorHandler)
			.create();
	}

	const response = await skill.invoke(event, context);
	console.log(`RESPONSE++++${JSON.stringify(response)}`);

	return response;
};


