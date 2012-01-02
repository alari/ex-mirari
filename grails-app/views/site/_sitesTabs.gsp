<%--
  By alari
  Since 12/26/11 8:52 PM
--%>

<%@ page import="mirari.morphia.Site" contentType="text/html;charset=UTF-8" %>

<ul class="tabs">
    <li${currSite == null ? ' class="active"' : ''}><g:link controller="settings">Аккаунт</g:link></li>
    <g:each in="${profiles}" var="profile">
        <li${currSite instanceof Site && currSite.id == profile?.id ? ' class="active"' : ''}><site:link
                for="${profile}" action="preferences"/></li>
    </g:each>
    <g:if test="${profiles.iterator().size() < 3}">
        <li${currSite == '+' ? ' class="active"' : ''}><g:link controller="settings" action="createSite">+</g:link></li>
    </g:if>
</ul>