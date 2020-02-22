<#import "parts/login.ftlh" as l>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka</title>



</head>
<body>

    <div>
        <@l.logout />
    </div>
    <div>
        <a href="/test.html">test</a>
    </div>
    <div>
        <form method="post">
            <input type="text" , name="msg" , placeholder="input pls message"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">AddMsg</button>
        </form>
    </div>

    <div>
        <#list messages as message>
            <div>
                <b>${message.message_id}</b>
                <span>${message.text}</span>
            </div>
        <#else>
            No message
        </#list>
    </div>


</body>
</html>
