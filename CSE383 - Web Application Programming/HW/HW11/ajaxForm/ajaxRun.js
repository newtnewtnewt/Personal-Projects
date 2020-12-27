/*
* Author: Noah Dunn 
* Class: CSE 383
* Homework 10/16: Second AJAX Assignment
* With assistance from the following resources:
* W3 Schools, Dr. Campbell
https://stackoverflow.com/questions/1637019/how-to-get-the-jquery-ajax-error-response-text
https://www.tutorialrepublic.com/faq/how-to-check-or-uncheck-a-checkbox-dynamically-using-jquery.php
*/

//  We begin by hiding our message and overriding our Submit button's default submit configuration
$(document).ready(function() {
	$("#msg").hide();
	$("#submit").click(submitButton);
})

function submitButton(){
		//  Disable the default action 
		event.preventDefault();
		//  I saved both values for the POST but only needed the named vals
		
		//  Grabs the UID 
		var zeroVal = $("#uid").val();
		var uid = zeroVal;
		
		//  Grab the birth Year 
		var oneVal = $("#birthYear").val();
		var birthYear = oneVal;
		
		//  Grabs the selected Grad Year
		var twoVal = $("#gradYear option:selected").val();
		var gradYear = twoVal;
		
		//  Determines if the cse box is checked or not
		var threeVal = $("#cseCheckbox").is(":checked");
		var cseCheckbox = threeVal;
		
		//  Determine if the car box is checked or not
		var fourVal = $("#car").is(":checked");
		var carCheck = fourVal;
		
		//  Determine the field for mobile devices
		var fiveVal = $("#mobileDevices").val();
		var mobileDevices = fiveVal;
		
		//  Determine the quote field
		var sixVal = $("#quote").val();
		var quote = sixVal;
		
		//  Determine the quote citation field
		var sevenVal = $("#quoteCite").val();
		var quoteCite = sevenVal;
		
		
		
		//  Build a JSON with all the fields gathered above using JQUERY
		var data = {
					"uid":uid,
					"gradYear" : gradYear,
					"birthYear": birthYear,
					"cse": cseCheckbox,
					"car": carCheck,
					"mobileDevices": mobileDevices,
					"quote": quote,
					"quoteCite": quoteCite
					};
		//  Turn it into a string for use in AJAX
		var jsonDataString = JSON.stringify(data);
		//  Make our post call using AJAX
		$.ajax({
		   "url": "http://ceclnx01.cec.miamioh.edu/~campbest/cse383-f19-campbest-public/ajax1/form-json.php",
		   "method": "POST",
		   "data":  jsonDataString
		  })
		.done(function (response) {  //  If we succeed, display the succeed and clear the fields
			$("#msg").html("You submitted the form successfully");
			$("#msg").show();
			console.log(response);
			clearFields();
		}).fail(function (error) {  //  If we fail, display the fail and clear the fields
			$("#msg").html("You failed to submit the form correctly");
			$("#msg").show();
			clearFields();
		})	
//  A function to clear all fields
function clearFields(){
	$("#uid").val("");
	$("#birthYear").val("");
	$("#gradYear").val(2019);
	$("#cseCheckbox").prop("checked", false);
	$("#car").prop("checked", false);
	$("#mobileDevices").val("");
	$("#quote").val("");
	$("#quoteCite").val("");
}	
}


