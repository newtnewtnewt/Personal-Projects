/*
* Author: Noah Dunn 
* Class: CSE 383
* Lab 10/21: Mega AJAX assignment
* With assistance from the following resources:
* W3 Schools, Dr. Campbell
https://stackoverflow.com/questions/1637019/how-to-get-the-jquery-ajax-error-response-text
https://openlibrary.org/dev/docs/api/search
https://api.jquery.com/text/
https://stackoverflow.com/questions/554273/changing-the-image-source-using-jquery
*/

//  We begin by hiding our secret box and enabling a new submit button option 
$(document).ready(function() {
	$("#submit").click(submitButton);
	$("#coverDiv").hide();
	$("#titleTag").hide();
	$("#noCoverArtTag").hide();
	$("#coverArt").hide();
})

function submitButton(){
		//  Disable the default action 
		event.preventDefault();
		
		//  Grab what the user placed into the input box
		var bookTitle = $("#bookTitle").val();
		//  Build our GET URL out of it
		var url = "http://openlibrary.org/search.json?title=" + bookTitle;
		
		//  Begin our GET AJAX call
		$.ajax({
		   "url": url,
		   "method": "GET"
		  })
		.done(function (response) {  //  If we succeed, display the succeed and clear the fields
			$("#bookList").html("");  //  Clear what's in the list already
			var responseJSON = JSON.parse(response);   //  Grab the response as a JSON Object
			//  Iterate through every single returned book
			for(var i = 0; i < responseJSON.docs.length; i++){
				//  If we can't find a title's isbn, set it's HTML isbn attribute to undefined
				if( typeof responseJSON.docs[i].isbn == 'undefined' ){
					$("#bookList").append("<li class = 'getCover'" +
					"isbn=null>" + 
					responseJSON.docs[i].title + "</li>");
				}
				//  If we can find it, append the stuff like normal.
				else{
					$("#bookList").append("<li class = 'getCover'" +
					"isbn=" + responseJSON.docs[i].isbn[0] + ">" + 
					responseJSON.docs[i].title + "</li>");
				}
			}
			//  Throw custom click events on all of them
			$(".getCover").click(getCover);
		}).fail(function (error) {  //  If we fail, display a message in console
			console.err("Something went horribly wrong");
		})	
}


//  A function applied to all of our individual list items
function getCover() {
	
	//  Display the overall div and the title of the book
	$("#coverDiv").show();
	$("#titleTag").show();
	//  Get the ISBN of the selected book and its title
	var isbnNum = $(this).attr('isbn');
	var title = $(this).text();
	//  Set the text of the title tage
	$("#titleTag").text(title);
	//  If its one of the values we marked as not having 
	//  an isbn, show the no cover art tag
	if(isbnNum == "null"){
		$("#noCoverArtTag").show();
	}
	else{	
		//  Hide the no art tag if its showing
		$("#noCoverArtTag").hide();
		//  Set the image's source to the custom API url
		var url = "http://covers.openlibrary.org/b/isbn/" + isbnNum + "-M.jpg"
		$("#coverArt").attr({ "src" : url });
		$("#coverArt").show(url);
	}
	
	
	
}
