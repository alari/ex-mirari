<%--
 * @author Dmitry Kurinskiy
 * @since 20.10.11 19:23  
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${message(code: "personPreferences.title")}</title>
    <meta name="layout" content="mono"/>
</head>

<body>

<mk:pageHeader>Настройки сайта <site:link/></mk:pageHeader>

<mk:withLeftSidebar>
    <mk:leftSidebar>

        <avatar:large for="${site}"><site:url action="uploadAvatar"/></avatar:large>

    </mk:leftSidebar>
    <mk:content>

        <form action="<site:url action="setFeedBurner"/>" method="post">
            <fieldset>
                <legend>
                    FeedBurner
                </legend>
            </fieldset>
            <mk:formLine field="feedBurnerName" label="FeedBurner feed name:">
                <input type="text" name="feedBurnerName" value="${site.feedBurnerName}"/>
            </mk:formLine>

            <p>
                Текущий адрес: <code><site:feedUrl/></code>
            </p>
            
            <mk:formActions>
                <input type="submit" value="Сохранить" class="primary btn"/>
            </mk:formActions>
        </form>

    </mk:content>
</mk:withLeftSidebar>

</body>
</html>