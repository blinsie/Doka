<#import "parts/common.ftlh" as c>

<@c.page>
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
</@c.page>