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

<mk:pageHeader>${message(code: "personPreferences.title")} <site:profileLink/></mk:pageHeader>

<mk:twoBigColumns>
    <mk:content>

        <g:render template="/sitePreferences/sitesTabs" model="[account: account, profiles: profiles, currSite: null]"/>

        <g:render template="changePassword"/>

        <g:formRemote update="changeEmailUpdate" name="changeEmail" url="[controller: 'settings',action: 'changeEmail']"
                      method="post" class="form-horizontal">
            <fieldset>
                <legend>${message(code: "personPreferences.changeEmail.title")}</legend>

                <mk:formLine labelCode="personPreferences.changeEmail.current">
                    <span class="uneditable-input">${account.email}</span>
                </mk:formLine>
                <div id="changeEmailUpdate"></div>
                <mk:formLine labelCode="personPreferences.changeEmail.field">
                    <g:textField name="email"/> <g:submitButton name="submit" class="btn btn-info"
                                                                value="${message(code: 'personPreferences.changeEmail.submit')}"/>
                </mk:formLine>

            </fieldset>
        </g:formRemote>

    </mk:content>
    <mk:sidebar>

        <avatar:large/>

    </mk:sidebar>
</mk:twoBigColumns>

</body>
</html>