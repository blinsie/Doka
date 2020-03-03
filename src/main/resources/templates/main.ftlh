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


        function newServer() {
            var text = document.getElementById('server_name').value
            host = window.location.pathname;
            location.href = "http://" + location.host + "/server/new/" + text;
        }

    </script>
</head>
<body>

<a href="/private-chat">Private chat</a>

<div>
    <form method="post">
        <input type="text" id="server_name" name="textname"
               placeholder="new server name"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="button" onclick="newServer()">Add server </button>
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
