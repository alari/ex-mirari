<%--
  By alari
  Since 12/26/11 8:52 PM
--%>

<%@ page import="mirari.model.Site" contentType="text/html;charset=UTF-8" %>

<ul class="nav nav-tabs">
    <li${currSite == null ? ' class="active"' : ''}><g:link controller="settings">Аккаунт</g:link></li>
    <g:each in="${profiles}" var="profile">
        <li${currSite == profile ? ' class="active"' : ''}><g:link
                for="${profile}" controller="sitePreferences" action="preferences">${profile}</g:link></li>
    </g:each>
    <g:if test="${profiles.iterator().size() < 3}">
        <li${currSite == '+' ? ' class="active"' : ''}><g:link controller="settings" action="createSite">+</g:link></li>
    </g:if>
</ul>