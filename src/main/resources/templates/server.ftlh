<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka server</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script>
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

        function newTextChannel() {
            var text = document.getElementById('channel_name').value
            host = window.location.pathname;
            if (host.split("/")[1] == "server") {
                location.href = "https://" + location.host + "/text-channel/new/" + host.split("/")[2] + "/" + text;
            } else {
                location.href = "https://" + location.host + "/text-channel/new/" + host.split("/")[3] + "/" + text;
            }
        }

        function newVoiceRoom() {
            var room = document.getElementById('room_name').value
            host = window.location.pathname;
            if (host.split("/")[1] == "server") {
                location.href = "https://" + location.host + "/voice-room/new/" + host.split("/")[2] + "/" + room;
            } else {
                location.href = "https://" + location.host + "/voice-room/new/" + host.split("/")[3] + "/" + room;
            }
        }
    </script>
</head>
<body>
<a href="/main">Main page</a>
<div>
    <div>
        <form method="post">
            <input type="text" id="channel_name" name="textname"
                   placeholder="new text channel name"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="button" onclick="newTextChannel()">Add text channel</button>
        </form>
    </div>
    <div>
        <b>Text channels list:</b>
        <#list channels as channel>
            <tr>
                <td>
                    <button type="button" onclick="swapTextChannel(this)">${channel.text_channel_name} </button>
                </td>
            </tr>
        <#else>
            No text channels, try to create new.
        </#list>
    </div>
</div>
<div>
    <div>
        <form method="post">
            <input type="text" id="room_name" name="roomName"
                   placeholder="new voice room name"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="button" onclick="newVoiceRoom()">Add voice room</button>
        </form>
    </div>
    <div>
        <b>Voice rooms list:</b>
        <#list rooms as room>
            <tr>
                <td>
                    <button type="button" onclick="swapVoiceRoom(this)">${room.voice_room_name} </button>
                </td>
            </tr>
        <#else>
            No voice rooms, try to create new.
        </#list>
    </div>
</div>
</body>
</html>
