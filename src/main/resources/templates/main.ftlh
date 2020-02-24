<#import "parts/login.ftlh" as l>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka</title>

    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="/app.js"></script>

</head>
<body>

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

                <span id="msg_text">${message.text}</span>
            </div>
        <#else>
            No message
        </#list>
        <table id="conversation" class="table table-striped">
            <thead>
            </thead>
            <tbody id="greetings">
            </tbody>
        </table>

    </div>



    <div>
        <div id="main-content" class="container">
            <div class="row">
                <div class="col-md-6">
                    <form class="form-inline">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="connect">WebSocket connection:</label>
                            <button id="connect" class="btn btn-default" type="submit">Connect</button>
                            <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
                            </button>
                        </div>
                    </form>
                </div>
                <div class="col-md-6">
                    <form class="form-inline">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="text">Input message:</label>
                            <input type="text" id="text" class="form-control" placeholder="Your message here...">
                        </div>
                        <button id="send" class="btn btn-default" type="submit">Send</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</body>
</html>
