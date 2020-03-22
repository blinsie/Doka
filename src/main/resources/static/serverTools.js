function swapTextChannel(anElement) {
    var text = anElement.innerText;
    host = window.location.pathname;
    location.href = "https://" + location.host + "/text-channel/swap/" + host.split("/")[2] + "/" + text;
}

function swapVoiceRoom(anElement) {
    var room = anElement.innerText;
    host = window.location.pathname;
    location.href = "https://" + location.host + "/voice-room/swap/" + host.split("/")[2] + "/" + room;
}

function getServerName() {
    host = window.location.pathname;
    if (host.split("/")[1] == "server") {
        return host.split("/")[2];
    } else {
        return host.split("/")[3];
    }
}

function setServerNameInInput() {
    document.getElementById('server_name_input2').value = getServerName();
    document.getElementById('server_name_input1').value = getServerName();

}

function setServerNameOnPage() {
    var res = getServerName()
    var z = document.getElementById("server_name");
    z.innerText = "Server name: " + res;
}