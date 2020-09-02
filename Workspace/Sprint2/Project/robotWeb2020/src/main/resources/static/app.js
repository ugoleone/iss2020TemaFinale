var stompClient = null;
var hostAddr = "http://localhost:8080/move";

//SIMULA UNA FORM che invia comandi POST
function sendRequestData( params, method) {
    method = method || "post"; // il metodo POST � usato di default
    //console.log(" sendRequestData  params=" + params + " method=" + method);
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", hostAddr);
    var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "move");
        hiddenField.setAttribute("value", params);
     	//console.log(" sendRequestData " + hiddenField.getAttribute("name") + " " + hiddenField.getAttribute("value"));
        form.appendChild(hiddenField);
    document.body.appendChild(form);
    console.log("body children num= "+document.body.children.length );
    form.submit();
    document.body.removeChild(form);
    console.log("body children num= "+document.body.children.length );
}


function postJQuery(themove){
var form = new FormData();
form.append("name",  "move");
form.append("value", "r");

let myForm = document.getElementById('myForm');
let formData = new FormData(myForm);


var settings = {
  "url": "http://localhost:8080/move",
  "method": "POST",
  "timeout": 0,
  "headers": {
       "Content-Type": "text/plain"
   },
  "processData": false,
  "mimeType": "multipart/form-data",
  "contentType": false,
  "data": form
};

$.ajax(settings).done(function (response) {
  //console.log(response);  //The web page
  console.log("done move:" + themove );
});

}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/it-unibo-iss');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/display', function (msg) {
             updateDashboard(JSON.parse(JSON.parse(msg.body).content));
        });
        stompClient.send("/app/state", {}, JSON.stringify({'name': '' }));
    });
}


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

/*
function sendMove() {
    stompClient.send("/app/move", {}, JSON.stringify({'name': $("#name").val()}));
}
*/

function emitClientChange(id){
	var button = document.getElementById(id);
    stompClient.send("/app/client", {}, JSON.stringify({'name': '' }));
    var res = button.innerHTML.split(" ");
    var firstPart= res[0] + " " + res[1] + " " + res[2]+ " ";
    var newState = "";
    console.log("CURRENT STATE: "+res[3]);
    switch (res[3]) {
	  case "Arrive":
	  	newState = firstPart + "WaitingToOrder";
	    console.log("New state ---> "+ newState);
	    button.innerHTML = newState;
	    break;
	  case "WaitingToOrder":
	  	newState = firstPart + "WaitingToPay";
	    console.log("New state ---> "+ newState);
	    button.innerHTML = newState;
	    break;
	  case "WaitingToPay":
	  	newState = firstPart + "WaitingToExit";
	    console.log("New state ---> "+ newState);
	    button.innerHTML = newState;
	    break;
	  case "WaitingToExit":
	  	newState = firstPart + "Exited";
	    console.log("New state ---> "+ newState);
	    button.innerHTML = newState;
	    button.disabled = true;
	    break;
	  default:
	    console.log("Spiacenti, lo stato del valore " + res[3] + "non e' supportato.");	 
	}
}

function sendTheMove(move){
	console.log("sendTheMove " + move);
    stompClient.send("/app/move", {}, JSON.stringify({'name': move }));
}

function sendUpdateRequest(){
	console.log(" sendUpdateRequest "  );
    stompClient.send("/app/update", {}, JSON.stringify({'name': 'update' }));
}

function showMsg(message) {
console.log(message );
    $("#applmsgs").html( "<pre>"+message.replace(/\n/g,"<br/>")+"</pre>" );
    //$("#applmsgintable").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
     $("form").on('submit', function (e) {
         e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });

//USED BY SOCKET.IO-BASED GUI  

    $( "#h" ).click(function() {  sendTheMove("h") });
    $( "#w" ).click(function() {  sendTheMove("w") });
    $( "#s" ).click(function() {  sendTheMove("s") });
    $( "#r" ).click(function() {  sendTheMove("r") });
    $( "#l" ).click(function() {  sendTheMove("l") });
    $( "#x" ).click(function() {  sendTheMove("x") });
    $( "#z" ).click(function() {  sendTheMove("z") });
    $( "#p" ).click(function() {  sendTheMove("p") });

    //$( "#rr" ).click(function() { console.log("submit rr"); redirectPost("r") });
    //$( "#rrjo" ).click(function() { console.log("submit rr"); jqueryPost("r") });

//USED BY POST-BASED GUI   
    
    $( "#ww" ).click(function() { sendRequestData( "w") });
    $( "#ss" ).click(function() { sendRequestData( "s") });
    $( "#rr" ).click(function() { sendRequestData( "r") });
    $( "#ll" ).click(function() { sendRequestData( "l") });
    $( "#zz" ).click(function() { sendRequestData( "z") });
    $( "#xx" ).click(function() { sendRequestData( "x") });
    $( "#pp" ).click(function() { sendRequestData( "p") });
    $( "#hh" ).click(function() { sendRequestData( "h") });

//USED BY POST-BASED BOUNDARY  
    $( "#start" ).click(function() { sendRequestData( "w") });
    $( "#stop" ).click(function()  { sendRequestData( "h") });

	$( "#update" ).click(function() { sendUpdateRequest(  ) });
	
	
//CLIENT BUTTONS
/*    $( "#clientButton1" ).click(function() {  emitClientChange("clientButton1") });
    $( "#clientButton2" ).click(function() {  emitClientChange("clientButton2") });
    $( "#clientButton3" ).click(function() {  emitClientChange("clientButton3") });
    $( "#clientButton4" ).click(function() {  emitClientChange("clientButton4") });
    $( "#clientButton5" ).click(function() {  emitClientChange("clientButton5") });
    $( "#clientButton6" ).click(function() {  emitClientChange("clientButton6") });
*/	
});



