<!DOCTYPE html>
<html>
<head>
    <title>Hello WebSocket</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/main.css" rel="stylesheet">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="/group.js"></script>
    <script>
        function addTextToInput(anElement) {
            var text = anElement.innerText;
            document.getElementById('receiver').value = text;
        }

        function goToPage(o) {
            var text = o.innerText;
            host = window.location.pathname;
            location.href = "https://" + location.host + "/text-channel/swap/" + host.split("/")[3] + "/" + text;
        }


    </script>
</head>
<body>
<noscript><h2 style="color: #ff0e36">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
        enabled. Please enable
        Javascript and reload this page!</h2></noscript>
<a href="/main">Main page</a>
<div>
    <b>Text channel name:</b>
    <span id="textChannelName">${text_name}</span>
    <b>Text channel Id:</b>
    <span id="textChannelId">${text_id}</span>

</div>
<div>
    <div>
        <b>Text channels list:</b>
        <#list channels as channel>
            <tr>
                <td>
                    <button type="button" onclick="goToPage(this)">${channel.text_channel_name} </button>
                </td>
            </tr>
        <#else>

        </#list>
    </div>
</div>
<div id="main-content" class="container">
    <div class="row">
        <b>Your name: </b>
        <span id="username">${username}</span>

        <div class="col-md-6">
            <form class="form-inline">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <label for="connect">WebSocket connection:</label>
                    <button id="connect" class="btn btn-default" type="submit">Connect</button>
                    <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
                    </button>
                </div>
            </form>
        </div>
        <div class="col-md-6">
            <form class="form-inline">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <label for="receiver">Enter a text channel name:</label>
                    <input type="text" id="receiver" class="form-control" placeholder="Text channel name">
                </div>

                <div class="form-group">
                    <label for="msg">Enter a message:</label>
                    <input type="text" id="msgContent" class="form-control" placeholder="msgContent">
                </div>
                <button id="send" class="btn btn-default" type="submit">Send</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>Sender</th>
                    <th>Message</th>
                </tr>
                </thead>
                <tbody id="greetings">
                <#list messages as message>
                    <tr>
                        <td> ${message.author} </td>
                        <td> ${message.text} </td>
                    </tr>
                <#else>
                    <tr>
                        <td> empty</td>
                        <td> empty</td>
                    </tr>
                </#list>

                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    connect();
    host = window.location.pathname;
    var textChannel = host.split("/")[4]
    document.getElementById('receiver').value = textChannel;
</script>
</body>
</html>