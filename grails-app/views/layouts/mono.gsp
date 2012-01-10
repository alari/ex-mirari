<%--
  @author Dmitry Kurinskiy
  @since 09.09.11 10:42
--%>

<%@ page import="grails.util.Environment" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"/>
    <title><g:layoutTitle default="${message(code: 'layout.title')}"/></title>
    <site:hostAuthJs/>
    <g:layoutHead/>
    <r:require module="jquery"/>
    <r:require module="twitterBootstrap"/>
    <r:require module="twitterDropdown"/>
    <r:require module="mirariAlerts"/>
    <r:require module="mirariStyles"/>
    <r:layoutResources/>
</head>

<body>
<div class="topbar">
    <div class="topbar-inner">
        <div class="container">
            <h3><a href="http://${_portal.host}">${_portal.displayName}</a></h3>
            <ul class="nav secondary-nav <sec:ifLoggedIn> logged-in</sec:ifLoggedIn>" data-dropdown="dropdown">

                <sec:ifLoggedIn>
                    <li><site:profileLink/></li>

                    <li>
                    <site:profileLink controller="sitePageStatic" action="add">${message(code: "layout.addUnit")}</site:profileLink>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle">${message(code: "layout.preferencesDropdown")}</a>
                        <ul class="dropdown-menu">
                            <li><g:link
                                    controller="settings">${message(code: "layout.personPreferences")}</g:link></li>
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
    <div data-bind="template: { name: 'alerts', foreach: alertsVM.alerts }"></div>
    <g:layoutBody/>
</div>

<footer class="footer">
    <div class="container">
        <div class="row">

            <div class="span4">
                ${message(code: "layout.footer.copyright")}
            </div>

            <div class="span6">
                <test:echo><span id="test-page">${webRequest.controllerName}:${webRequest.actionName}</span></test:echo>
                <em>${request.getHeader("Host")}</em>
            </div>

            <div class="span4">
                <em>${message(code: "layout.footer.version", args: [g.meta(name: "app.version")])}</em>
            </div>
        </div>
    </div>
</footer>

<r:layoutResources/>

<script type="text/javascript">
    $(function () {
        <g:alerts/>
        ko.applyBindings();
    });
</script>

<mk:tmpl id="alerts">
    <div class="alert-message {{= level}}">
        <a class="close" href="#" data-bind="click:remove">&times;</a>

        <p data-bind="html:message"></p></div>
</mk:tmpl>

</body>
</html>