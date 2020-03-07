<html>
<head>
    <meta charset="utf-8">
    <title>Voice room</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/webrtc-adapter/release/adapter.js"></script>
    <script src="/js/conferenceroom.js"></script>
    <script src="/js/participant.js"></script>
    <script src="/js/kurento-utils.js"></script>
</head>
<body>
<a href="/main">Main page</a>
<div>
    <b>User name:</b>
    <span id="userName">${user_name}</span>
    <b>Voice room name:</b>
    <span id="voiceRoomName">${voice_room_name}</span>
    <b>Voice room Id:</b>
    <span id="voiceRoomId">${voice_room_id}</span>
</div>

<div id="container">
    <div id="wrapper">
        <div id="join" class="animate join">

            <h1>Join a Room</h1>
            <form onsubmit="register(); return false;" accept-charset="UTF-8">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <p class="submit">
                    <input type="submit" name="commit" value="Join!">
                </p>
            </form>
        </div>
        <div id="room" style="display: none;">
            <h2 id="room-header"></h2>
            <div id="participants"></div>
            <input type="button" id="button-leave" onmouseup="leaveRoom();"
                   value="Leave room">
        </div>
    </div>
</div>
</body>
</html>
