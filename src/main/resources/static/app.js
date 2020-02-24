var stompClient = null;

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
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({"user" : document.getElementById("sender").value}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/reply', function (greeting) {
            showGreeting(JSON.parse(greeting.body).from,JSON.parse(greeting.body).content,JSON.parse(greeting.body).to);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/personalMsg", {}, JSON.stringify({'from': $("#sender").val(), 'content': $("#msgContent").val(),'to': $("#receiver").val()}));
    showGreeting("Me",$("#msgContent").val(),$("#receiver").val());

}

function showGreeting(sender,message,receiver) {
    $("#greetings").append("<tr><td>" + sender + "</td><td>"+message+"</td><td>"+receiver+"</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});