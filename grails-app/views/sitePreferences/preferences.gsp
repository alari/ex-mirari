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

<mk:pageHeader>Настройки сайта <g:link forSite="1">${_site}</g:link></mk:pageHeader>

<mk:twoBigColumns>
    <mk:content>

        <g:render template="/sitePreferences/sitesTabs"
                  model="[account: account, profiles: profiles, currSite: _site]"/>

        <g:render template="changeDisplayName" model="[site: _site]"/>

        <form action="<g:createLink action="setFeedBurner" controller="sitePreferences" forSite="1"/>" method="post"
              class="form-horizontal">
            <fieldset>
                <legend>
                    FeedBurner
                </legend>
            </fieldset>
            <mk:formLine field="feedBurnerName" label="FeedBurner feed name:">
                <input type="text" name="feedBurnerName" value="${_site.feedBurnerName}"/>
            </mk:formLine>

            <p>
                Текущий адрес: <code><site:atomFeedUrl/></code>
            </p>

            <mk:formActions>
                <input type="submit" value="Сохранить" class="btn-primary btn"/>
            </mk:formActions>
        </form>

        <form action="<g:createLink action="changeName" controller="sitePreferences" forSite="1"/>" method="post"
              class="form-horizontal">
            <fieldset>
                <legend>Сменить имя (адрес) сайта</legend>
                <mk:formLine label="Имя:">
                    <input type="text" name="name" value="${_site.name}"/>
                </mk:formLine>
                <mk:formActions>
                    <input type="submit" value="Изменить" class="btn btn-warning"/>
                </mk:formActions>
                <p>Будьте внимательны! После смены имени старое может занять кто-то другой. Мы не организуем перенаправления со старых адресов страниц на новые, поэтому старые ссылки могут быть сломаны.</p>
            </fieldset>
        </form>

    </mk:content>


    <mk:sidebar>
        <div data-bind="avatarUpload: {url: 'uploadAvatar', size: 'medium', enabled: true}" class="avatar-holder">
            <avatar:large for="${_site}"/>

            <label class="fileinput-button btn btn-info"><input type="file"
                                                                name="avatar"/>${message(code: "avatar.upload")}
            </label>
            <br clear="all"/>

            <div class="avatar-progressbar ui-progressbar"></div>
        </div>

        <r:require modules="ko_avatarUpload,css_siteAvatarUpload"/>


        <g:if test="${!isMain}">
            <br/>

            <g:link action="makeMain" controller="sitePreferences" forSite=""
                    class="btn">Сделать профилем по умолчанию</g:link>
        </g:if>

    </mk:sidebar>
</mk:twoBigColumns>

</body>
</html>