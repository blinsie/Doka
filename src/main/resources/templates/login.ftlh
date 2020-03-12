<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Doka Login</title>

</head>
<body>

<form action="/login" method="post" class="form-horizontal">
    <fieldset>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <!-- Form Name -->
        <legend>Sign In</legend>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="username">Username:</label>
            <div class="col-md-4">
                <input id="username" name="username" type="text" placeholder="username" class="form-control input-md">

            </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="password">Password:</label>
            <div class="col-md-4">
                <input id="password" name="password" type="password" placeholder="password" class="form-control input-md">
            </div>
        </div>

        <!-- Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="sign_in">Single Button</label>
            <div class="col-md-4">
                <button type="submit" value="Sign In" id="sign_in" name="sign_in" class="btn btn-primary">Sign In</button>
            </div>
        </div>

    </fieldset>
</form>

<a href="/reg">Registration</a>

</body>
</html>