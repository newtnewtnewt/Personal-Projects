/*
   Scott Campbell
   cse451 final project
   This is a TEST file to invoke the robotDrawing.

   1) 
   create new robot:

   var bob  = new Robot("Bob","#ff0000") -> color at end is color to draw bob
   robotQueue.add(bob);

   update bob's position with an object:
   pos = {X:10,Y:10,orientation:"N"}
   robotQueue.updatePosition("Bob",pos);

   the robotDrawins page has its own timer for drawing the robots.



 */

var bob;
var alice;
var testPositions=[];
var max =200 

$(document).ready(() => {
		bob = new Robot("Bob","#ff0000");
		alice= new Robot("Alice","#00aa00");
		robotQueue.addRobot(bob);
		robotQueue.addRobot(alice);

		testPositions[0] = {X:0,Y:0,orientation:"E"}
		testPositions[1] = {X:40,Y:50,orientation:"E"}
		update();
		console.log(testPositions);
		setInterval(update, 1000);
		});

function update() {
	var apiURL = "https://kowy99xtbe.execute-api.us-east-1.amazonaws.com/default"; 
	var p1 = new Promise( (resolve, reject) => { 
		$.ajax({
			type: "GET",
			url: apiURL + "/bob",
			success:function(result){
				testPositions[0] = {X: result.xVal, Y: result.yVal, orientation: result.direction, timestamp: result.timeVal};
				resolve();
			},
			error:function(result){
				console.log("Error Getting Bob's Info")
				console.log(result);
				reject();
			}
		})
		}
		);
	
	var p2 = new Promise( (resolve, reject) => {
		$.ajax({
			type: "GET",
			url: apiURL + "/alice",
			success:function(result){
				testPositions[1] = {X: result.xVal, Y: result.yVal, orientation: result.direction, timestamp: result.timeVal};
				resolve();
			},
			error:function(result){
				console.log("Error Getting Alice's Info")
				console.log(result);
				reject();
			}
		})
		}
	);
	
	Promise.all([p1, p2]).then((values) => {
		console.log("update");
		robotQueue.updateRobot("Bob",testPositions[0]);
		robotQueue.updateRobot("Alice",testPositions[1]);
		$("#bobtag").html("Bob<br />" + "X: " + testPositions[0].X + "<br />" + "Y: " + testPositions[0].Y + "<br />" + "O: " + testPositions[0].orientation + "<br />Last Updated: "  + testPositions[0].timestamp);
		$("#alicetag").html("Alice<br />" + "X: " + testPositions[1].X + "<br />" + "Y: " + testPositions[1].Y + "<br />" + "O: " + testPositions[1].orientation + "<br />Last Updated: "  + testPositions[1].timestamp);
	});
} 

