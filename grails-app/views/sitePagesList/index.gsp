<%--
  By alari
  Since 1/19/12 2:48 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title><g:message code="pageType.${type.name}"/> :: ${_site}</title>
</head>

<body>
<mk:pageHeader><g:message code="pageType.${type.name}"/> <small><g:link for="${_site}">${_site}</g:link></small></mk:pageHeader>

<site:typeListPills active="${type}"/>

<mk:withLeftSidebar>
    <mk:content>

        <g:render template="/site/feed" model="[feed: feed, site: _site]"/>

        <mk:pagination pagination="${feed.pagination}">
            <g:link controller="sitePagesList" params="[type: type.name, page: num]">${text}</g:link>
        </mk:pagination>

    </mk:content>


    <mk:leftSidebar>
        <avatar:large for="${_site}"/>

        <br/>

        <rights:ifCanAdd site="${_site}" type="${type}">
            <g:link controller="sitePageStatic" action="add" params="[type:type.name]">Добавить</g:link>
        </rights:ifCanAdd>

    </mk:leftSidebar>
</mk:withLeftSidebar>
</body>
</html>