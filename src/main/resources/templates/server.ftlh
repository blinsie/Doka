<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka server</title>
    <script>
        function addTextToInput(anElement) {
            var text = anElement.innerText;
            host = window.location.pathname;
            location.href = "http://" + location.host + "/text-channel/swap/" + host.split("/")[2] + "/" + text;
        }

        function newTextChannel() {
            var text = document.getElementById('channel_name').value
            host = window.location.pathname;
            if (host.split("/")[1] == "server"){
                location.href = "http://" + location.host + "/text-channel/new/" + host.split("/")[2] + "/" + text;
            } else {
                location.href = "http://" + location.host + "/text-channel/new/" + host.split("/")[3] + "/" + text;
            }
        }
    </script>
</head>
<body>
<a href="/main">Main page</a>
<div>
    <form method="post">
        <input type="text" id="channel_name" name="textname"
               placeholder="new text channel name"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="button" onclick="newTextChannel()">Add text channel </button>
    </form>
</div>
<b>Text Channel list:</b>
<#list channels as channel>
    <tr>
        <td>
            <button type="button" onclick="addTextToInput(this)">${channel.text_channel_name} </button>
        </td>
    </tr>
<#else>

</#list>


</body>
</html>
