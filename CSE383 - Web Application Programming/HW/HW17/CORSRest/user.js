
$(document).ready(function() {
	loadTable();
})

function loadTable(){
		//  Build our GET URL out of it
		var url = "https://dunnnm2.383.csi.miamioh.edu/cse383-f19-dunnnm2/rest1/rest.php/v1/user";
		//  Begin our GET AJAX call
		$.ajax({
		   "url": url,
		   "method": "GET",
		   "headers": {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		   }
		  })
		.done(function (response) {  //  If we succeed, display the succeed and clear the fields
			$("#userList").html("");  //  Clear what's in the list already
			var responseJSON = response;   //  Grab the response as a JSON Object
			//  Iterate through every single returned book
			for(var i = 0; i < responseJSON.user.length; i++){
					$("#userList").append("<li>" + responseJSON.user[i].user + "</li>");
			}
		}).fail(function (error) {  //  If we fail, display a message in console
			console.err("Something went horribly wrong");
		})	
}