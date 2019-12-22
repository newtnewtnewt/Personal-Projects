/*
* Author: Noah Dunn 
* Class: CSE 383
* Lab 10/23: Wordpress assignment
* With assistance from the following resources:
* W3 Schools, Dr. Campbell
https://developer.wordpress.org/rest-api/reference/posts/#update-a-post
https://stackoverflow.com/questions/5507234/use-basic-authentication-with-jquery-and-ajax
*/



/*
* Code provided by Dr. Campbell 
*
*
*/
function editPost() {
  id=$(this).attr('postid');
  console.log(id);
    a=$.ajax({
      url: "http://ceclnx01.cec.miamioh.edu/~campbest/wordpress/?rest_route=/wp/v2/posts/" + id,
      method: "GET",
      username: $("#user").val(),
      password: $("#password").val()  
  });
    a.done(function(data) {
      //clear out old data
      console.log(data)
	  $("#postForm").show();
      $("#id").val(data.id)
      $("#title").val(data.title.rendered)
      $("#text").val(data.content.rendered)
    });
 
}

function checkFields(){
	if( $("#user").val() != "" && $("#password").val() != ""){
		$("#getPosts").show();
	}
	else{
		$("#getPosts").hide();
	}

}


function getPosts() {
	
    a=$.ajax({
      url: "http://ceclnx01.cec.miamioh.edu/~campbest/wordpress/?rest_route=/wp/v2/posts",
    method: "GET",
    username: $("#user").val(),
      password: $("#password").val() 
  });

    a.done(function(data) {
		
      //clear out old data
      console.log(data)
      let len = data.length
	  $("#posts").show();
      $("#posts ul").html("")
      for (let i=0;i<len;i++) {
        if (data[i].type == "post") {
          s="<li class='editPost' postid='" + data[i].id +"'>" +data[i].title.rendered + "</li>"
          $("#posts ul").append(s)
        }
      }
      $(".editPost").click(editPost)
  })
    .fail(function(error) {
    console.log(error);
 
  });

}

//  This function submits a POST call based on data in the input
//  text boxes
function updatePost(evt) {
  //  Prevent the default action
  evt.preventDefault();
  //  Grab the id
  var id = $("#id").val();
  //  Grab the title
  var title = $("#title").val();
  //  Grab the text in the box
  var text = $("#text").val();
  //  Create the desired payload
  var payload = { "id": id, "title": title, "content": text}
  a=$.ajax({
	  //  Create the request with the given id
      url: "http://ceclnx01.cec.miamioh.edu/~campbest/wordpress/?rest_route=/wp/v2/posts/" + id,
	  method: "POST",
	  //  Send the authentication ahead of time to avoid 401 error
	  headers: {
		"Authorization": "Basic " + btoa($("#user").val() + ":" + $("#password").val())
	  },
      //  The payload
	  data: payload

  });
  a.done(function(data) {
      //  Show the updated content stuff
	  $("#postForm").show();
      $("#id").val(data.id)
      $("#title").val(data.title.rendered)
      $("#text").val(data.content.rendered)
    });
 

}

$(document).ready(function() {
  //  Assign our post grab button function
  $("#getPosts").click(getPosts);
  //  Assign our submit button function
  $("#postForm").submit(updatePost);
  //  Put change events that fire when updating our text boxes
  $("#user").change(checkFields);
  $("#password").change(checkFields);
  //  Hide all irrelevant data
  $("#getPosts").hide();
  $("#posts").hide();
  $("#postForm").hide();
  
});