/*
* Author: Noah Dunn 
* Class: CSE 383
* Exam Question #1 
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
		
		//  I saved both values for the POST but only needed the named vals
		
		//  Grabs the UID 
		var zeroVal = $("#QWE").val();
		var uid = zeroVal;
		
		if(validateFields(uid)){
			$("#msg").html("You correctly submitted the form");
			$("#msg").show();
			clearFields();
		}
		else{
			event.preventDefault();
			$("#msg").html("You did not enter a string of exactly 3 characters");
			$("#msg").show();
			clearFields();
		}
		
//  A function to clear all fields
function clearFields(){
	$("#uid").val("");
	$("#birthYear").val("");
}
function validateFields(fieldA){
	if(fieldA.length == 3){
		return true;
	}
	return false;
}	
}


