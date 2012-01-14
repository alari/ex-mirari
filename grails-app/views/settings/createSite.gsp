<%--
  By alari
  Since 12/26/11 9:05 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Создать сайт</title>
    <meta name="layout" content="mono"/>
</head>

<body>

<mk:pageHeader>Создать сайт</mk:pageHeader>

<mk:withLeftSidebar>
    <mk:content>

        <g:render template="/site/sitesTabs" model="[account: account, profiles: profiles, currSite: '+']"/>

        <form method="post">
            <fieldset>
                <legend>Ещё один профиль</legend>
            </fieldset>
            <mk:formLine labelCode="profile.name.label" bean="${command}" field="name">
                <g:textField class="medium" type="text" size="16" name="name" bean="${command}"/>
            </mk:formLine>

            <mk:formLine labelCode="profile.displayName.label" bean="${command}" field="displayName">
                <g:textField class="medium" type="text" size="16" name="displayName" bean="${command}"/>
            </mk:formLine>

            <mk:formActions>
                <input type="submit" value="Создать" class="btn primary"/>
            </mk:formActions>
        </form>

    </mk:content>
    <mk:leftSidebar>

        <p>Вы можете создать несколько независимых профилей. Это удобно, если вы занимаетесь разнородной деятельностью, например, пишете стихи и ведёте журналистский блог.</p>

    </mk:leftSidebar>
</mk:withLeftSidebar>

</body>
</html>