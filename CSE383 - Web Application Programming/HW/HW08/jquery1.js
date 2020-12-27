function clickBox() {
  txt =$(this).html();
  $("ul").append("<li>" + txt + "</li>"); 
  $(this).css("background-color", "blue");
}

$(document).ready(function() {
  console.log("ready");
  $(".box").click(clickBox);
  $(".box2").css("margin-left", "10");
  setInterval(function(){ 
	negative = false;
	leftPos = $(".box2").css("margin-left");
	console.log(leftPos);
	leftPos = parseInt(leftPos.substring(0, leftPos.indexOf('p')));
	if(negative){
		leftPos -= 10;
		if(leftPos == 10){
			negative = false;
		}
	}
	else{
		leftPos += 10;
		if(leftPos == 300){
			negative = true;
		}	
	}
	
		
	$(".box2").css({'margin-left': leftPos + "px"});
  }, 100);
});
