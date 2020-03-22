<#import "parts/login.ftlh" as l>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function goToPage(o) {
            var text = o.innerText;
            location.href = "https://" + location.host + "/server/" + text;
        }

        function newServer() {
            var text = document.getElementById('server_name').value
            host = window.location.pathname;
            location.href = "https://" + location.host + "/server/new/" + text;
        }

    </script>
</head>
<body>

<a href="/private-chat">Private chat</a>
<a href="/friends">Friends list</a>

<div>
    <form action="/server/new" method="post">
        <input type="text" id="server_name" name="server_name"
               placeholder="new server name"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" >Add server </button>
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
