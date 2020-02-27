var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    //$("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({"user": "test-user"}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/group', function (greeting) {
            showGreeting(JSON.parse(greeting.body).author, JSON.parse(greeting.body).text, JSON.parse(greeting.body).to);
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
    stompClient.send("/app/groupMsg", {}, JSON.stringify({
        //'from': $("#sender").val(),
        'text': $("#msgContent").val(),
        //'to': $("#receiver").val()
    }));
    showGreeting("Me", $("#msgContent").val());

}

function showGreeting(sender, message) {
    $("#greetings").append("<tr><td>" + sender + "</td><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});