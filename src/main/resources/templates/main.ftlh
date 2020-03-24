<#import "parts/common.ftlh" as c>

<@c.page>

    <div>
        <form action="/server/new" method="post">
            <input type="text" id="server_name" name="server_name"
                   placeholder="new server name"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add server</button>
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

</@c.page>