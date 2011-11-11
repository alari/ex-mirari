<%--
 * @author alari
 * @since 11/8/11 6:51 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:formRemote name="changeDisplayName" update="changeDisplayName" url="[action:'changeDisplayName']" method="post">
    <fieldset>
        <legend>${message(code: "personPreferences.changeDisplayName.title")}</legend>

        <mk:formLine labelCode="personPreferences.changeDisplayName.label" field="displayName"
                     bean="${changeDisplayNameCommand}">
            <g:textField name="displayName" value="${person.displayName?.encodeAsHTML()}"
                         placeholder="${person.name}"/>
        </mk:formLine>

        <mk:formActions>
            <g:submitButton
                    name="submit" class="btn info"
                    value="${message(code:'personPreferences.changeDisplayName.submit')}"/>
        </mk:formActions>
    </fieldset>
</g:formRemote>