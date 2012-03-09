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
    <link rel="SHORTCUT ICON" href="http://ya.ru/favicon.ico"/>
    <g:layoutHead/>
    <r:require module="jquery"/>
    <r:require module="vendor_bootstrap"/>
    <r:require module="mirariAlerts"/>
    <r:require module="css_default"/>
    <r:layoutResources/>
</head>

<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="http://${_portal.host}">${_portal.displayName}</a>

            <ul class="nav pull-right <sec:ifLoggedIn>logged-in</sec:ifLoggedIn>">

                <sec:ifLoggedIn>
                    <li><site:profileLink/></li>

                    <site:switchProfilesLi/>
                    
                    <li class="dropdown">
                        <pageType:pageDropdown/>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle"
                           data-toggle="dropdown">${message(code: "layout.preferencesDropdown")}
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><site:profileLink
                                    controller="sitePreferences" action="preferences">${message(code: "layout.personPreferences")}</site:profileLink></li>
                            <li>
                                <site:profileLink controller="digest">Сводка</site:profileLink>
                            </li>
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
        </div>
    </div>
</div>

<div class="container">
    <div class="alerts-container" data-bind="template: { name: 'alerts', foreach: alertsVM.alerts }"></div>

    <g:if test="${_site}">
        <pageType:listPills for="${_site}"/>
    </g:if>

    <g:layoutBody/>

    <footer class="footer">
        <div class="container">
            <div class="row">

                <div class="span3">
                    ${message(code: "layout.footer.copyright")}
                </div>

                <div class="span6">
                    <test:echo><span
                            id="test-page">${webRequest.controllerName}:${webRequest.actionName}</span>
                    <em>${request.getHeader("Host")}</em>
                    <br/></test:echo>
                    <em>${System.currentTimeMillis() - startTime} &mu;</em>
                </div>

                <div class="span3">
                    <em>${message(code: "layout.footer.version", args: [g.meta(name: "app.version")])}</em>
                </div>
            </div>
        </div>
    </footer>

</div>


<r:layoutResources/>

<script type="text/javascript">
    $(function () {
        <g:alerts/>
        ko.applyBindings();
    });
</script>

<mk:tmpl id="alerts">
    <div data-bind="attr: {class: 'alert alert-'+level}, fadeOut: {delay: '4', after: remove}">
        <a class="close" href="#" data-bind="click:remove">&times;</a>

        <p data-bind="html:message"></p></div>
</mk:tmpl>

<r:layoutResources disposition="bottom"/>

</body>
</html>