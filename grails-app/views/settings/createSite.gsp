<%--
  By alari
  Since 12/26/11 9:05 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Site</title>
    <meta name="layout" content="mono"/>
</head>

<body>

<mk:pageHeader>create site</mk:pageHeader>

<mk:withLeftSidebar>
    <mk:content>

        <g:render template="/site/sitesTabs" model="[account: account, profiles: profiles, currSite: null]"/>

        form site


    </mk:content>
    <mk:leftSidebar>

        avatar afterwards

    </mk:leftSidebar>
</mk:withLeftSidebar>

</body>
</html>