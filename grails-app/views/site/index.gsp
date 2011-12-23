<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${site}</title>
    
    <link  rel="alternate" title="ATOM" type="application/atom+xml"  href="<site:feedUrl for="${site}"/>"/>
</head>

<body>
<mk:pageHeader><site:link for="${site}"/></mk:pageHeader>
<mk:withLeftSidebar>
    <mk:content>

        <g:render template="feed" model="[feed:feed,site:site]"/>

    </mk:content>


    <mk:leftSidebar>
        <avatar:large for="${site}"/>
        <br/>
        <ul>
            <li><a href="<site:feedUrl for="${site}"/>">Atom Feed</a></li>
            <rights:ifCanAdmin site="${site}">
                <li><site:link action="preferences">Настройки сайта</site:link></li>
            </rights:ifCanAdmin>
        </ul>

    </mk:leftSidebar>
</mk:withLeftSidebar>
</body>
</html>