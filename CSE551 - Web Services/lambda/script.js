/*
*	Name: Noah Dunn 
*	Instructor: Dr. Scott Campbell
*	Class: CSE 551
*	Date: 4/24/2020
*	Assignment: Lambda-1
*	With assistance from:
*	Dr. Campbell, 
*	My Previous CSE383 work,
*	My Previous CSE451 work,
*	W3 Schools, and the Following:
*   https://stackoverflow.com/questions/144810/jquery-get-textarea-text
*   https://stackoverflow.com/questions/596314/jquery-ids-with-spaces
*   https://stackoverflow.com/questions/20291366/how-to-wait-until-jquery-ajax-request-finishes-in-a-loop
*   https://stackoverflow.com/questions/8045071/how-do-i-select-elements-on-multiple-attribute-values
*   https://api.jquery.com/category/selectors/
*   https://stackoverflow.com/questions/24154267/what-is-the-correct-way-to-support-apostrophes-in-javascript-when-building-up-ht
*/
$(document).ready(function() {
	// On page load we need to clear out the values in our table
	$("#tableContent").html("");
	// Hide the error box
	$("#msg").hide();
	// Instantiate the click box for our chec
	$("#yearSearch").click(checkYear);
	
});

/* This code is to prevent malformed HTML from ' or " or misc special characters */
var entityMap = {
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': '&quot;',
    "'": '&#39;',
    "/": '&#x2F;'
};

function escapeHtml(string) {
    return String(string).replace(/[&<>"'\/]/g, function (s) {
        return entityMap[s];
    });
}

function addComment(){
	//  Grab the title from the button that was clicked
	var title = $(this).val();
	//  Build the HTML id 
	var id = title + "textarea";
	//  Grab the correct text information
	var comment = $("[id='" + $.escapeSelector(id) + "']").val();
	//  Grab the year
	var year = $("#yearVal").text();
	//  Build the url
	var apiURL = "https://hpflx3jo63.execute-api.us-east-1.amazonaws.com/default/movieapi/" + year + "/" + title;
	// If there is no comment present, no need to call the API
	if(comment != undefined && comment != ""){
	//  Build the body for the POST request
		var body = JSON.stringify({"movie_comment" : comment });
		$.ajax({
			type: "POST",
			url: apiURL,
			data: body,
			success:function(result){
				//  Grab the name and ID for a comment
				var name = this.url.split("/")[6];
				var id =   result.Data.Item.COMMENT_ID;
				//  Parse the comment from the request
				var comment = JSON.parse(this.data).movie_comment;
				//  Log the success
				console.log("Comment Successfully Added")
				$("#msg").html("Comment Successfully Added!");
				$("#msg").show();
				// Build the string to add to the current table
				var bigTableString = "";
				bigTableString +="<p id='" + id +  "'>" + escapeHtml(comment) + "</p><button type='submit' class='btn btn-warning editButton' value='" + id + "+" + escapeHtml(name) + "'>Edit</button> "
				bigTableString += "<button type='submit' class='btn btn-danger deleteButton' value='" + id + "+" + escapeHtml(name) + "'>Delete</button>"
				bigTableString += "<br id='" + id + "br'/>";
				//  Append the entry to the specified table entry
				$("[id='" + $.escapeSelector(name) + "']").append(bigTableString);
			},
			complete:function(result){
				var id = result.responseJSON.Data.Item.COMMENT_ID;
				var name = title;
				//  Throw the click listeners on the buttons that were just created
				$("[value='" + id + "+" + $.escapeSelector(name) + "'][class*='editButton']").click(editComment);
				$("[value='" + id + "+" + $.escapeSelector(name) + "'][class*='deleteButton']").click(deleteComment);
	
			},
			error:function(result){
				console.log("Error Adding Comment")
				console.log(result);
				$("#msg").html("Error Adding Comment");
				$("#msg").show();
			}
		})
		// Clear the text 
		$("[id='" + $.escapeSelector(id) + "']").val("");		
	}
	else { 
		$("#msg").text("Please insert your comment into the text box beside the movie you want to add a comment to");
		$("#msg").show();
	}
}
function editComment() { 
	var idName = $(this).val().split("+");
	var id = idName[0];
	var name = idName[1];
	//  Grab the correct text information
	var comment = $("[id='" + $.escapeSelector(name) + "textarea" + "']").val();
	//  The API endpoint 
	var apiURL = "https://hpflx3jo63.execute-api.us-east-1.amazonaws.com/default/movieapi/" + id + "/" + name;
	// Only if the comment is not blank 
	if(comment != undefined && comment != ""){
		$.ajax({url: apiURL,
			   type: "PUT",
			   data: JSON.stringify({"movie_comment": comment}),
			   success: function(result) { 
					console.log("Comment Successfully Edited!")
					$("#msg").html("Comment Successfully Edited!");
					$("#msg").show();
					var id = this.url.split("/")[5];
					//  Change the comment to the edited version
					$("[id='" + id + "']").html(JSON.parse(this.data).movie_comment);
			   },
			   error: function(result) {
					console.log("Error Editing Comment!")
					$("#msg").html("Error Editing Comment!");
					$("#msg").show();
			   }
			   
		})
		// Clear the text 
		$("[id='" + $.escapeSelector(name) + "textarea']").val("");		
	}
	else { 
		console.log("Attempted a blank edit!");
		$("[id='" + $.escapeSelector(name) + "textarea']").val($("[id='" + id + "']").html());
		$("#msg").text("The Comment has been loaded into the text box, please edit it and then hit 'edit' again to submit");
		$("#msg").show();
	}
}

function deleteComment() { 
	// Grab the id and name from the button
	var idName = $(this).val().split("+");
	var id = idName[0];
	var name = idName[1];
	var apiURL = "https://hpflx3jo63.execute-api.us-east-1.amazonaws.com/default/movieapi/" + id + "/" + name;
	$.ajax({url:apiURL,
			type: "DELETE",
			success: function(result) { 
				console.log("Comment successfully deleted!");
				// Grab ID and name from the URL 
				var id = this.url.split("/")[5];
				var nameID = this.url.split("/")[6]; 
				//  Delete all the buttons and such
				$("[id='" + id + "']").remove();
				$("[value='" + id + "+" + $.escapeSelector(nameID) + "']").remove();
				//Delete the breakline
				$("[id='" + id + "br" +  "']").remove();
				//Show a message
				$("#msg").text("Comment successfully deleted!");
				$("#msg").show();
			},
			error: function(result){
				console.log("Comment was not deleted!");
				$("#msg").text("Comment was not deleted!");
				$("#msg").show();
			}
	})
}
function checkYear(){
		var apiURL = "https://hpflx3jo63.execute-api.us-east-1.amazonaws.com/default/movieapi/"
		event.preventDefault();
		$("#msg").hide();
		var year = $("#yearInput").val();
		//  No need to send an AJAX request if the values aren't valid
		if(year > 1920 && year <= 2018){
			//  Build the API Url with the year
			$.ajax({url:(apiURL + year),
				success:function(result){
				//  Change the header tag to the correct year
				$("#yearVal").text(year);
				//  Clear the table
				$("#tableContent").html("");
				//Promises array 
				var promises = [];
				//  Go through the full movie list
				for(var i = 0; i < result.movies.length; i++){
					//  Grab the title
					var title = result.movies[i].title;
					var passIn = {"genres": result.movies[i].info.genres, "plot": result.movies[i].info.plot}
					//  Save the individual ajax requests for the comments to be used as Promises
					var request = $.ajax({url: apiURL + year + "/"+ title,
								          body: JSON.stringify(passIn),
						success:function(result) {
							// Grab the title in question from the url
							var titleVal = this.url.split("/")[6];
							// Begin constructing the full table as HTML
							var bigTableString = "<tr><td>" + titleVal +"</td><td>";
							//  Add Genres and plot
							var genres = JSON.parse(this.body).genres;
							var plot = JSON.parse(this.body).plot;
							if(genres != null && genres != undefined){
								genres.forEach(genre => bigTableString += genre + ", ");
								bigTableString = bigTableString.substr(0, bigTableString.length - 2);
							}
							else {
								bigTableString += "N/A";
							}
							if(plot != null && plot != undefined){
								bigTableString += "</td><td>" + plot + "</td>";
							}
							else {
								bigTableString += "</td><td>N/A</td>"
							}
							bigTableString  += "<td style='width:400px;'" + " id ='" + escapeHtml(titleVal) + "'>";
							// Grab everything 
							var commentsArray = result.comments;
							var name, id, year, comment;
							if (commentsArray.length > 0) {
								year = commentsArray[0].MOVIE_YEAR;
							}
							for(var j = 0; j < commentsArray.length; j++){
								comment = commentsArray[j].MOVIE_COMMENT;
								id = commentsArray[j].COMMENT_ID;
								bigTableString +="<p id='" + id +  "'>" + escapeHtml(comment) + "</p><button type='submit' class='btn btn-warning editButton' value='" + id + "+" + escapeHtml(titleVal) + "'>Edit</button> " 
								bigTableString += "<button type='submit' class='btn btn-danger deleteButton' value='" + id + "+" + escapeHtml(titleVal) + "'>Delete</button>" 
								bigTableString += "<br id='" + id + "br'/>";	
							}
							//  Build a row in the table
							bigTableString += "</td><td><textarea rows='4' cols='50' style='resize:none' id='" + escapeHtml(titleVal) + "textarea'></textarea></td><td><button class='addButton' value='" + escapeHtml(titleVal) + "'style='width:50px;height:25px;'> Add </button></td></tr>'"
							//  Append it
							$("#tableContent").append(bigTableString);
						},
						error:function(result){
							console.error("API is down!"); 
						}
					})
					promises.push(request);
					
				}
				//  When all the internal loops are done, add the click listeners to the edit, add, and delete buttons
				$.when.apply(null, promises).done(function() { 
					$(".addButton").click(addComment);
					$(".editButton").click(editComment);
					$(".deleteButton").click(deleteComment);
				})
			},
			error:function(result){
				//  If the API is broken, send this back. 
				$("msg").text("API not available currently");
				$("#msg").show();
			}
			});
		}
		else {
			//  Client-side validation for input
			$("#msg").text("Please try entering a year between 1920 and 2018, inclusive");
			$("#msg").show();
		}
}