<#import "parts/common-login-reg.ftlh" as c>

<@c.page>
    <form action="/reg" method="post" class="form-horizontal">
        <fieldset>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <!-- Form Name -->
            <legend>Sign Up</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="username">Username:</label>
                <div class="col-md-4">
                    <input id="username" name="username" type="text" placeholder="username"
                           class="form-control input-md">
                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password:</label>
                <div class="col-md-4">
                    <input id="password" name="password" type="password" placeholder="password"
                           class="form-control input-md">
                </div>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="sign_up"></label>
                <div class="col-md-4">
                    <button type="submit" value="Sign Up" id="sign_up" name="sign_up" class="btn btn-primary">Sign Up
                    </button>
                </div>
            </div>

        </fieldset>
    </form>

    <a href="/login">Login</a>

</@c.page>