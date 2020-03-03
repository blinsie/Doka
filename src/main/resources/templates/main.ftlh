<#import "parts/login.ftlh" as l>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka</title>
    <script>
        function goToPage(o) {
            var text = o.innerText;
            location.href = "http://" + location.host + "/server/" + text;
        }

        function newServer(o) {
            var url = o.value;
            host = window.location.pathname;
            location.href = "http://" + location.host + "/server/new/" + url;
        }

    </script>
</head>
<body>

<a href="/private-chat">Private chat</a>

<div>
    <form method="post">
        <input type="text" id="channel_name" name="textname"
               placeholder="new server name"
               onchange="newServer(this);"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</div>

<div>
    <b>Server list:</b>
    <#list servers as server>
        <tr>
            <td>
                <button type="button" onclick="goToPage(this)">${server.server_name} </button>
            </td>
        </tr>
    <#else>

    </#list>
</div>

<div>
    <@l.logout />
</div>


</body>
</html>
