
$(document).ready(function() {
	loadWeather();
})

function loadWeather(){
		//  Build our GET URL out of it
		var url = "https://ceclnx01.cec.miamioh.edu/~campbest/weather.php/v1/54321/";
		//  Begin our GET AJAX call
		var zipCode;
		if(localStorage.getItem('zip')){
			zipCode = localStorage.getItem('zip');
		}
		else {
			zipCode = "45056";
		}
		url += zipCode;
		$.ajax({
		   "url": url,
		   "method": "GET"
		  })
		.done(function (response) {  //  If we succeed, display the succeed and clear the fields
			
			$("#location").text("Location: " + response.location);
			$("#currTemp").text("Temperature: " + response.currentTemperature);
			$("#currCond").text("Current Weather: " + response.currentConditions);
			$("#forecast").text("Forecast: " + response.forecast);
		}).fail(function (error) {  //  If we fail, display a message in console
			console.error("Something went horribly wrong");
		})	
}