/*
Name: Noah Dunn 
Instructor: Dr. Scott Campbell
Class: CSE 451
Date: 2/7/2020
Assignment: JS Assignment 1
With assistance from:
Dr. Campbell, 
My Previous CSE383 work,
W3 Schools
https://stackoverflow.com/questions/6685249/jquery-performing-synchronous-ajax-requests
*/
// The location of our API
var keysURL = "http://campbest.451.csi.miamioh.edu/cse451-campbest-web-public-2020/week2/week2-rest.php/api/v1/info";


$(document).ready(function() {
	// On page load we need to clear out the values in our table
	$("#tableContent").html("");
	// Hide the error box
	$("#msg").hide();
	// A helper method to load our table with AJAX calls
	loadTable();
	
});

//  This abstracts having to show and display different error messages 
function displayError(errorMessage){
	$("#msg").append(errorMessage);
	$("#msg").append("<br />");
	$("#msg").show();

}

function loadTable(){
      //  A variable to store the array of our keys
	  var keys;
	  //  We have to run SYNC because we need to call the first API and finish before calling the second
	  $.ajax({url:keysURL, 
		success:function(result){
			//  If the status is what we need, save the result to use in the second AJAX call
			if(result.status == "ok"){
				keys = result.keys;
				 if(keys.length != 0){
				   //  Loop through all the keys and try and find their values using the second AJAX call
				  for(i = 0; i < keys.length; i++){
					  //  The URL needed to get the corresponding val
					  var urlVal = keysURL + "/" + keys[i];
					
					 //  The second AJAX call to get value, not flagging ASYNC will give us conflicting keys
					$.ajax({url:urlVal, key:keys[i],
								success:function(result){
									//  If we don't get a value or if the status is wrong, we want to show an error
									if(result.value != undefined && result.status == "ok"){
										//  Add a table column
										$("#tableContent").append("<tr><td>" + this.key + "</td><td>" + result.value + "</td></tr>");
									}
									else{
										displayError("No Value was found to correspond to key: " + this.key + "Attempting to refresh");
										$.ajax(this);
									}
								},
								error:function(result){
									//  If the API is down, display an error
									displayError("The API for all retrieving values is not available. Attempting to Refresh");
									$.ajax(this);
								}
							  });
						  }
					   }
					   else{
						// If we have no keys, there is nothing to display
						displayError("The keys were not located correctly, Attempting to Refresh");
						$.ajax(this);
						return;
					   }
				}
				// If the status is not right, we print an error 
				else{
					displayError("There must have been an issue getting the keys, Attempting to refresh");
					$.ajax(this);
					return;
				}
        },
		//  If our AJAX call fails, we want to show the failure
        error:function(result){
			displayError("The API for retrieving all keys is not available. Attempting to refresh");
			$.ajax(this);
			return;
		}
		});
		//  If we have some keys to actually care about
		  
}