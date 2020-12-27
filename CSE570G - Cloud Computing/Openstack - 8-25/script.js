//Scott Campbell
//cse470
//august 23, 2020
//Simple Web Site Javascript

var today = new Date();
var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
var dateTime = time + ' ' + date;
document.write("<div id='hello'>Hello world, it is now " + dateTime + "</div>" )
