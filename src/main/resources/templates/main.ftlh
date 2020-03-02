<#import "parts/login.ftlh" as l>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka</title>
    <script>
        function goToPage(o) {
            var url = o.value;
            location.href = url;
        }
    </script>
</head>
<body>

<a href="/private-chat">Private chat</a>
<a href="/server/">Server</a>


<div>
    <b>Server list:</b>
    <#list servers as server>
        <tr>
            <td>
                <button type="button" onclick="goToPage(this)">${server.text_channel_name} </button>
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
