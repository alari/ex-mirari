<%--
  By alari
  Since 1/13/12 10:09 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${tag.displayName} :: ${_site}</title>
</head>

<body>
<mk:pageHeader>${tag.displayName} <small><g:link for="${_site}">${_site}</g:link></small></mk:pageHeader>
<mk:withLeftSidebar>
    <mk:content>

        <g:render template="/site/feed" model="[feed: feed, site: _site]"/>

        <mk:pagination pagination="${feed.pagination}">
            <g:link for="${tag}" params="[page: num]">${text}</g:link>
        </mk:pagination>

    </mk:content>


    <mk:leftSidebar>
        <avatar:large for="${_site}"/>
        <br/>

    </mk:leftSidebar>
</mk:withLeftSidebar>
</body>
</html>