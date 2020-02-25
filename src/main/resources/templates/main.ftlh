<#import "parts/login.ftlh" as l>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka</title>

</head>
<body>

<a href="/chat">{Test multichat here}</a>
<div>
    <@l.logout />
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
            <b id="msg_author">${message.autor + ":\t"}</b>
            <span id="msg_text">${message.text}</span>

        </div>
    <#else>
        No message
    </#list>


</div>

</body>
</html>
