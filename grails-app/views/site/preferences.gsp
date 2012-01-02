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
    <mk:content>

        <g:render template="/site/sitesTabs" model="[account: account, profiles: profiles, currSite: _site]"/>

        <g:render template="changeDisplayName" model="[site: _site]"/>


        <form action="<site:url action="setFeedBurner"/>" method="post">
            <fieldset>
                <legend>
                    FeedBurner
                </legend>
            </fieldset>
            <mk:formLine field="feedBurnerName" label="FeedBurner feed name:">
                <input type="text" name="feedBurnerName" value="${_site.feedBurnerName}"/>
            </mk:formLine>

            <p>
                Текущий адрес: <code><site:feedUrl/></code>
            </p>

            <mk:formActions>
                <input type="submit" value="Сохранить" class="primary btn"/>
            </mk:formActions>
        </form>

        <form action="<site:url action="changeName"/>" method="post">
            <fieldset>
                <legend>Сменить имя (адрес) сайта</legend>
                <mk:formLine label="Имя:">
                    <input type="text" name="name" value="${_site.name}"/>
                </mk:formLine>
                <mk:formActions>
                    <input type="submit" value="Изменить" class="btn warning"/>
                </mk:formActions>
                <p>Будьте внимательны! После смены имени старое может занять кто-то другой. Мы не организуем перенаправления со старых адресов страниц на новые, поэтому старые ссылки могут быть сломаны.</p>
            </fieldset>
        </form>

    </mk:content>


    <mk:leftSidebar>

        <avatar:large for="${_site}"><site:url action="uploadAvatar"/></avatar:large>


        <g:if test="${!isMain}">
            <br/>

            <site:link action="makeMain" class="btn">Сделать профилем по умолчанию</site:link>
        </g:if>

    </mk:leftSidebar>
</mk:withLeftSidebar>

</body>
</html>