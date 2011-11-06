<%--
  @author Dmitry Kurinskiy
  @since 09.09.11 10:42
--%>

<%@ page import="grails.util.Environment" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"/>
    <title><g:layoutTitle default="${message(code:'layout.title')}"/></title>
    <g:layoutHead/>
    <r:require module="jquery"/>
    <r:require module="twitterBootstrap"/>
    <r:require module="twitterDropdown"/>
    <r:require module="twitterAlerts"/>
    <r:layoutResources/>
    <style type="text/css">
    body {
        padding-top: 54px
    }
    </style>
</head>

<body>
<div class="topbar">
    <div class="topbar-inner">
        <div class="container">
            <h3><g:link uri="/">${message(code: "layout.title")}</g:link></h3>
            <ul class="nav secondary-nav${sec.ifLoggedIn({/ logged-in/})}" data-dropdown="dropdown">

                <sec:ifLoggedIn>
                    <li><space:personLink/></li>

                    <li><g:link controller="spaceUnit" action="add" params="[spaceName:sec.username()]"><b>+</b></g:link>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">${message(code: "layout.preferencesDropdown")}</a>
                        <ul class="dropdown-menu">
                            <li><g:link
                                    controller="personPreferences">${message(code: "layout.personPreferences")}</g:link></li>
                            <li class="divider"></li>
                            <li><g:link name="logout"
                                        controller="logout">${message(code: "layout.logout")}</g:link></li>
                        </ul>
                    </li>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <li><g:link controller="register">${message(code: "layout.register")}</g:link></li>
                    <li><g:link controller="login">${message(code: "layout.login")}</g:link></li>
                </sec:ifNotLoggedIn>

            </ul>
        </div></div></div>


<div class="container">
    <div id="alerts"><g:alerts/></div>
    <g:layoutBody/>
</div>

<footer class="footer">
    <div class="container">

        <div class="span4">
            ${message(code: "layout.footer.copyright")}
        </div>

        <div class="span6">
            <test:echo><span id="test-page">${webRequest.controllerName}:${webRequest.actionName}</span></test:echo>
        </div>

        <div class="span4">
            <em>${message(code: "layout.footer.version", args: [g.meta(name: "app.version")])}</em>
        </div>
    </div>
</footer>

<r:layoutResources/>

</body>
</html>