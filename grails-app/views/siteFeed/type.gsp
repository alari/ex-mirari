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

<mk:twoBigColumns>
    <mk:content>

        <g:render template="/siteFeed/drafts" model="[drafts: drafts]"/>

        <g:if test="${asGrid}">
            <g:render template="/siteFeed/grid" model="[pages: feed]"/>
        </g:if>
        <g:else>
            <g:render template="/siteFeed/feed" model="[feed: feed, site: _site]"/>
        </g:else>

        <mk:pagination pagination="${feed.pagination}">
            <g:link controller="siteFeed" action="type" params="[type: type.name, page: num]">${text}</g:link>
        </mk:pagination>

    </mk:content>


    <mk:sidebar>
        <avatar:large for="${_site}"/>

        <br/>

        <rights:ifCanAdd site="${_site}" type="${type}">
            <g:link forSite="1" controller="sitePageStatic" action="add" params="[type:type.name]">Добавить</g:link>
        </rights:ifCanAdd>

    </mk:sidebar>
</mk:twoBigColumns>
</body>
</html>