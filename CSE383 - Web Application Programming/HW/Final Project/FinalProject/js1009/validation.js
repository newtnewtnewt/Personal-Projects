/*
* Author: Noah Dunn 
* Class: CSE 383
* Homework 10/09: JS Validation
* With assistance from the following resources:
* W3 Schools, 
https://stackoverflow.com/questions/8824831/make-div-stay-at-bottom-of-pages-content-all-the-time-even-when-there-are-scrol
https://stackoverflow.com/questions/3584145/how-to-change-the-text-of-a-label
*
*
*/

//  Get all our event listeners applied on boot-up
$(document).ready(function() {
	//  All our change events that only need to activate onChange
	var date = Date();
	$("#date").html(date.toLocaleString());
	$("#uid").change(checkUid);
	$("#mobileDevices").change(checkMobileDevices);
	$("#quote").change(checkQuote);
	$("#quoteCite").change(checkSource);
	//  Our submit button's special event that activates on click
	$("#submitButton").click(submitButton);
	//  Updating our clock every 5 seconds 
	var timer = setInterval(updateTimer,5000)
})
function updateTimer(){
	var date = Date();
	$("#date").html(date.toLocaleString());
}


//  Has to be a single word > 2 characters
function checkUid(){
	//  Our regex accepts all single words of 2 chars or more
	var uidRegex = /^[A-Za-z]{2,}$/;
	//  Grabbing our uid entered from the page
	var uidValue = $("#uid").val();
	//  Display the error if things go wrong, otherwise, return true and get rid of any errors
	if ( !(uidValue.match(uidRegex)) ) {
		$("#uidError").show();
	}
	else{
		$("#uidError").hide();
		return true;
	}
}
//  Has to be a number > 0 but < 20
function checkMobileDevices(){
	//  We need a number 1-20
	var mobileRegex = /[0-9]{1,2}/;
	//  Grab the number of phone devices
	var phones = $("#mobileDevices").val();
	//  Check if the phones are in the limit, and display error or return true
	if (!phones.match(mobileRegex) || phones < 1 || phones > 20  ) {
		$("#phoneError").show();
		return false;
	}
	else{
		$("#phoneError").hide();
		return true;
	}

}
//  Has to be at least 4 words and no characters other than a-z A-Z 0-9 and  . , - ' "
function checkQuote(){
	//  All valid 4 word quotes with the appropriate dictionary of letters
	var quoteRegex = /^(([a-zA-Z0-9.,\-'"]+\s){4,})/;
	//  Grab the value in quote and append a space to be safe
	var quote = $("#quote").val() + " ";
	
	//  Check if the quote matches our regex, and return corresponding value
	if(!quote.match(quoteRegex)){
		$("#quoteError").show();
		return false;
	}
	else{
		$("#quoteError").hide();
		return true;
	}
	
}

// quoteCite must contain only a-zA-Z0-9 and . , - ' " / :
function checkSource(){
	//  Check source against the appropriate dictionary
	var sourceRegex = /^[a-zA-Z0-9.,\-'"\/:]{2,}$/
	var source = $("#quoteCite").val();
	//  Return correct corresponding value
	if(!source.match(sourceRegex)){
		$("#sourceError").show();
		return false;
	}
	else{
		$("#sourceError").hide();
		return true;
	}
}

function submitButton(){
	//  If anything is invalid, prevent the user from sending it
	if(!(checkUid() && checkQuote() && checkSource() && checkMobileDevices())){
		event.preventDefault();
	}
}
