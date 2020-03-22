<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka server</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="/serverTools.js"></script>
</head>
<body>
<a href="/main">Main page</a>
<div>
    <div id="server_name"></div>
    <div>
        <form action="/text-channel/new" method="post">
            <input type="text" id="channel_name" name="text_name"
                   placeholder="new text channel name"/>
            <input type="hidden" id="server_name_input1" name="server_name"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add text channel</button>
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
        <form action="/voice-room/new" method="post">
            <input type="text" id="room_name" name="room_name"
                   placeholder="new voice room name"/>
            <input type="hidden" id="server_name_input2" name="server_name"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add voice room</button>
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
<script>
    setServerNameOnPage();
    setServerNameInInput();
</script>
</body>
</html>
