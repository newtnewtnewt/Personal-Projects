
$(document).ready(function() {
	loadTable();
	$("#submitButton").click(submitButton);
})
function submitButton(){
	event.preventDefault();
	var titleVal = $("#title").val();
	var linkVal = $("#link").val();
	
	//  Build our PUT URL out of it
		var url = "https://dunnnm2.aws.csi.miamioh.edu/FinalProject/rest.php/v1/quickLinks/";
		//  Begin our PUT AJAX call
		var data = {"title" : titleVal, "url" : linkVal, "token" : token};
		var jsonDataString = JSON.stringify(data);
		$.ajax({
		   "url": url,
		   "method": "PUT",
		   "headers": {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		   },
		   "data":  jsonDataString
		   
		  })
		.done(function (response) {  //  If we succeed, display the succeed and clear the fields
			location.reload();
		}).fail(function (error) {  //  If we fail, display a message in console
			console.error("Something went horribly wrong");
		})	
}

function deleteButton(){
	
	var pk = $(this).attr('pk');
	//  Build our DELETE URL out of it
		var url = "https://dunnnm2.aws.csi.miamioh.edu/FinalProject/rest.php/v1/quickLinks/" + pk;
		//  Begin our DELETE AJAX call
		var data = {"token" : token};
		var jsonDataString = JSON.stringify(data);
		$.ajax({
		   "url": url,
		   "method": "DELETE",
		   "headers": {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		   },
			"data" : jsonDataString
		   
		  })
		.done(function (response) {  //  If we succeed, display the succeed and clear the fields
			location.reload();
		}).fail(function (error) {  //  If we fail, display a message in console
			console.error("Something went horribly wrong");
		})	
}

function loadTable(){
		//  Build our GET URL out of it
		var url = "https://dunnnm2.aws.csi.miamioh.edu/FinalProject/rest.php/v1/quickLinks";
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
			$("#qlBody").html("");  //  Clear what's in the list already
			var responseJSON = response;   //  Grab the response as a JSON Object
			//  Iterate through every single returned book
			//  pk, title, url 
			for(var i = 0; i < responseJSON.quickLinks.length; i++){
				    var fullRow = "<tr>";
					fullRow += "<td>" + responseJSON.quickLinks[i].title + "</td>";
					fullRow += "<td>" + responseJSON.quickLinks[i].url + "</td>";
					fullRow += "<td><button type=\"button\" class=\"delButton btn btn-info\" pk=" + responseJSON.quickLinks[i].pk + ">Delete</button></td>";
					fullRow += "</tr>";
					$("#qlBody").append(fullRow);
			}
			$(".delButton").click(deleteButton);
		}).fail(function (error) {  //  If we fail, display a message in console
			console.error("Something went horribly wrong");
		})	
}