<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${site}</title>
</head>

<body>
<mk:pageHeader><site:link for="${site}"/></mk:pageHeader>
<mk:withLeftSidebar>
    <mk:leftSidebar>
        <avatar:large for="${site}"/>
    </mk:leftSidebar>
    <mk:content>

        <g:render template="/page-render/grid" model="[pages:allPages]"/>

    </mk:content>
</mk:withLeftSidebar>
</body>
</html>