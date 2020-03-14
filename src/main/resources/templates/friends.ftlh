<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka: my friends</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function addFriend(o) {
            var text = o.innerText;
            location.href = "https://" + location.host + "/friends/add/" + text;
        }

        function deleteFriend(o) {
            var text = o.innerText;
            location.href = "https://" + location.host + "/friends/delete/" + text;
        }
    </script>
</head>
<body>

<a href="/main">Main page</a>
<div>
    Push the button to add User as Friend
    <div>
        Users list:
        <#list usersName as user>
            <tr>
                <td>
                    <button type="button" onclick="addFriend(this)">${user} </button>
                </td>
            </tr>
        <#else>
            EMPTY USERS LIST
        </#list>
    </div>

    Push the button to Delete User from Friends
    <div>
        Friend list:
        <#list friendList as friend>
            <tr>
                <td>
                    <button type="button" onclick="deleteFriend(this)">${friend} </button>
                </td>
            </tr>
        <#else>
            Looks like you haven't any friends:(
        </#list>
    </div>
</div>
</body>
</html>
