<link rel="stylesheet" href="/css/navbar.css">

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-header">
        <a href="/" class="navbar-left"><img src="/img/logo_1.png"></a>
    </div>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/private-chat">Private chat</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/friends">Friends</a>
            </li>
        </ul>

        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="submit" value="Sign Out"/>
        </form>
    </div>
</nav>
