/*
* Author: Noah Dunn 
* Class: CSE 383
* Homework 10/14: First AJAX Assignment
* With assistance from the following resources:
* W3 Schools, Dr. Campbell
https://plainjs.com/javascript/manipulation/append-or-prepend-to-an-element-29/
*/

//  Our rest function is located in its entirety at this base URL
var URL = "http://ceclnx01.cec.miamioh.edu/~campbest/cse383-f19-campbest-public/ajax1/info.php";
//  The value we want to run our timer at in MS
var timerLength = 1000; 

//  The code we need to run on load
$(document).ready(function() {
	//  Update function hosts all our needed updater functions, called every @param timerLength of time
	var timer = setInterval(updateTimer,timerLength);
})

function updateTimer(){
	//  Loading our suite of page updaters
	getLoad();
	getUsers();
	getNetwork();
	changeTime();
}

/*
* Change the time to the current time
*/ 
function changeTime(){
	var now = Date();
	$("#time").html("It is now: " + now.toLocaleString() + "\n"); 	
}

/* 
* This function call determines bytes and byte thoroughput on our network
*/
function getNetwork() {
	//  Make a GET request using AJAX on the network portion of our REST server
    a=$.ajax({
    url: URL + '/api/v1/network',
    method: "GET"
  }).done(function(data) {  //  When any output is recieved from the URL source, execute the following
		//  Grab the current Byte amounts stored on the page
		var prevTXB = $("#txbytes").html();
		var prevRXB = $("#rxbytes").html();
		//  Set the new Byte amounts for the page
		$("#txbytes").html(data.network.txbytes);
		$("#rxbytes").html(data.network.rxbytes);
		//  Calculate the difference between old byte amounts and the new byte amounts, divide by the length of time to get thoroughput
		$("#txavg").html((data.network.txbytes - prevTXB) / (timerLength / 1000));
		$("#rxavg").html((data.network.rxbytes - prevRXB) / (timerLength / 1000));
  })
	//  If it fails, append a date and Error to the log
    .fail(function(error) {
    var date = Date();
	$("#log ul").html("<li> " + date.toLocaleString() + " Error getting Network " + "</li>"+ ($("#log ul").html()));
  });

}
/*
*  A function to determine the list of users and all their IP addresses 
*  
*/
function getUsers(){
    a=$.ajax({
    url: URL + '/api/v1/who', //  Calls the 'who' portion of our REST API 
    method: "GET"
  }).done(function(data) { 
      //clear out old data
      $("#users").html(""); // Reset the whole old list to nothing
      len = data.who.length; //  Only worry about calculating the length one time
      for (i=0;i<len;i++) {
		 //  Append an entry to the table for each person and IP in the JSON object
        $("#users").append("<tr><td>" + data.who[i].uid +"</td><td>" + data.who[i].ip + "</td></tr>");
      }
  })
    .fail(function(error) {
		var date = Date();
		//  Write an error with the current DateTime
		$("#log ul").html("<li> " + date.toLocaleString() + " Error getting Who " + " </li>" + ($("#log ul").html()));
  });

}

/* 
* Determines load information based on the loadavg portion of the REST server
*/
function getLoad() {
    a=$.ajax({
    url: URL + '/api/v1/loadavg', //  Portion of the REST server we are calling
    method: "GET"
  }).done(function(data) {
	  //  All of the following are fields defined in the JSON we need to update simply 
      $("#onemin").html(data.loadavg.OneMinAvg);
	  $("#fivemin").html(data.loadavg.FiveMinAvg);
	  $("#fifteenmin").html(data.loadavg.FifteenMinAvg);
	  $("#numRunning").html(data.loadavg.NumRunning);
	  $("#ttlProc").html(data.loadavg.TtlProcesses);
  })
    .fail(function(error) {
    var date = Date();
	//  In case of an error, append to the log
	$("#log ul").html("<li>" + date.toLocaleString() + " Error getting LoadAvg" + "</li>" + ($("#log ul").html()));
  });

}
