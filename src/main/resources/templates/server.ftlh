<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka server</title>
    <script>
        function addTextToInput(anElement) {
            var text = anElement.innerText;
            location.href = "http://" + location.host + "/server/text-channel/swap/" + text;
            document.getElementById('receiver').value = text;
        }
        function goToPage(o) {
            var url = o.value;
            location.href = "http://" + location.host + "/server/text-channel/" + url;
        }
    </script>
</head>
<body>
<a href="/main">Main page</a>
<a href="/server/text-channel/swap/default">Text channel</a>
<div>
    <form method="post">
        <input type="text" id="channel_name" name="textname" placeholder="input text channel name" onchange="goToPage(this);"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
