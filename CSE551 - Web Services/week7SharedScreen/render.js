/*
 * Scott Campbell
 * cse451
 * */

var r=g=b=0;

function handleClick(evt) {
	x=evt.offsetX;
	y=evt.offsetY;
	console.log("x=" + x + "," + y);
	var selectedBrush = $("#imageOption").val();
	if(selectedBrush == "dot"){
		if (x<390 && y<390) {
			$.ajax({ 
				url:'screenAPI.php',
				method: 'post',
				data: JSON.stringify({x:x,y:y,x1:(x+10),y1:(y+10),r:r,g:g,b:b})
			});
		}
	}
	else {
		if (x<390 && y<390) {
			$.ajax({ 
					url:'screenAPI.php/',
					method: 'post',
					data: JSON.stringify({x:x,y:y,x1:(x+10),y1:(y+10),r:r,g:g,b:b})
				});
		}
	}
}

$(document).ready(function() {
	//setup colors for this user
	r = Math.floor(Math.random()*255);
	g = Math.floor(Math.random()*255);
	b = Math.floor(Math.random()*255);

	$("#msg").append("Your color is  = " + r + " " + g + " " + b);
	// refresh image every second-->
	window.setInterval(() => {
		var d = new Date()
		//append the current time to get around caching
		$("img").attr("src",'makeCommonImage.php?t='+d.getTime());
		//We need to run a check to see what users are logged in/logged out
		$.ajax({  url:'screenAPI.php/' + currentUser,
				  method: 'get',
				  success: function(result){
					  //  This simply appends all our currently on users to a list. 
					$("#allUsers").html(result.users);
				  }
			  });
	},300);
		

	<!-- on click, get coordinate and add point-->
		$("#pic").click(handleClick)
	$("#clear").click(() => {
		$.ajax({
			url: 'screenAPI.php',
			method: 'delete'
		});
	});
});
