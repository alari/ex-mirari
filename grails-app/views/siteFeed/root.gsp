<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${_site}</title>

    <link rel="alternate" title="ATOM" type="application/atom+xml" href="<site:feedUrl for="${_site}"/>"/>
</head>

<body>
<mk:pageHeader><g:link for="${_site}">${_site}</g:link></mk:pageHeader>

<site:typeListPills/>

<mk:twoBigColumns>
    <mk:content>

        <g:render template="/siteFeed/drafts" model="[drafts: drafts]"/>

        <g:render template="/siteFeed/feed" model="[feed: feed, site: _site]"/>

        <mk:pagination pagination="${feed.pagination}">
            <g:link for="${_site}" params="[pageNum: (num ? '-' + num + '-' : '')]">${text}</g:link>
        </mk:pagination>

    </mk:content>


    <mk:sidebar>
        <avatar:large for="${_site}"/>
        <br/>
        <ul>
            <li><a href="<site:feedUrl for="${_site}"/>">Atom Feed</a></li>
            <rights:ifCanAdmin site="${_site}">
                <li><g:link action="preferences" forSite="1">Настройки сайта</g:link></li>
            </rights:ifCanAdmin>
        </ul>

    </mk:sidebar>
</mk:twoBigColumns>
</body>
</html>