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
		//  Disable the default action 
		event.preventDefault();
		//  I saved both values for the POST but only needed the named vals
		
		//  Grabs the UID 
		var zeroVal = $("#uid").val();
		var uid = zeroVal;
		
		//  Grab the birth Year 
		var oneVal = $("#birthYear").val();
		var birthYear = oneVal;
		
		if(validateFields(uid, birthYear)){
		
			//  Build a JSON with all the fields gathered above using JQUERY
			var data = {
						"uid":uid,
						"birthYear": birthYear
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
		}
		else{
			$("#msg").html("You failed to submit the form correctly");
			$("#msg").show();
			clearFields();
		}
		
//  A function to clear all fields
function clearFields(){
	$("#uid").val("");
	$("#birthYear").val("");
}
function validateFields(fieldA, fieldB){
	if(fieldA.length >= 3 && fieldB.length >= 3){
		return true;
	}
	return false;
}	
}


