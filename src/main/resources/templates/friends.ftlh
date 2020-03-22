<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka: my friends</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function addFriendToInput(o) {
            var text = o.innerText;
            document.getElementById('friend_name').value = text;
        }

        function addFriendToDeleteInput(o) {
            var text = o.innerText;
            document.getElementById('friend_name_delete').value = text;
        }
    </script>
</head>
<body>

<a href="/main">Main page</a>
<div>
    Push the button to add User to input
    <div>
        Users list:
        <#list usersName as user>
            <tr>
                <td>
                    <button type="button" onclick="addFriendToInput(this)">${user} </button>
                </td>
            </tr>
        <#else>
            EMPTY USERS LIST
        </#list>
    </div>

    <form action="/friends/add" method="post">
        <input type="text" id="friend_name" name="friend_name" placeholder="Friend name"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Add friend</button>
    </form>

    Push the button to add Friend to input
    <div>
        Friend list:
        <#list friendList as friend>
            <tr>
                <td>
                    <button type="button" onclick="addFriendToDeleteInput(this)">${friend} </button>
                </td>
            </tr>
        <#else>
            Looks like you haven't any friends:(
        </#list>
    </div>

    <form action="/friends/delete" method="post">
        <input type="text" id="friend_name_delete" name="friend_name" placeholder="Friend name"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Delete friend</button>
    </form>
</div>
</body>
</html>
