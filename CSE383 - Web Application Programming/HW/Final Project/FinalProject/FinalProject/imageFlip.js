$(document).ready(function() {
	$("#logo").click(picClick);
})

function picClick(){
	$(this).attr("src", "images/me.jpg");
}