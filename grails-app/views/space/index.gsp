<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${space}</title>
</head>

<body>
<mk:pageHeader><space:link for="${space}"/></mk:pageHeader>
<mk:withLeftSidebar>
    <mk:leftSidebar>
        <avatar:large for="${space}"/>
    </mk:leftSidebar>
    <mk:content>

        <g:render template="/unit-render/tinyImageGrid" model="[units:allUnits]"/>

    </mk:content>
</mk:withLeftSidebar>
</body>
</html>