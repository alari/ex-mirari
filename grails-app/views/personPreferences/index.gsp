<%--
 * @author Dmitry Kurinskiy
 * @since 20.10.11 19:23  
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>person preferences</title>
    <meta name="layout" content="mono"/>
</head>

<body>
<mk:pageHeader><sbj:link/> preferences</mk:pageHeader>

<g:form action="changePassword" method="post">
    <fieldset>
        <mk:formLine label="pwd">
            <g:passwordField name="password"/>
        </mk:formLine>
        <mk:formLine label="newpwd">
            <g:passwordField name="newPwd"/>
        </mk:formLine>
        <mk:formLine label="newpwd2">
            <g:passwordField name="newPwd2"/>
        </mk:formLine>
    </fieldset>
</g:form>

<g:formRemote update="changeEmail" name="changeEmail" url="[action:'changeEmail']" action="changeEmail" method="post">
    <mk:formLine labelCode="personPreferences.changeEmail.title">
        <g:textField name="email"/> <g:submitButton name="submit" class="btn" value="${message(code:'personPreferences.changeEmail.submit')}"/>
    </mk:formLine>
</g:formRemote>


<g:form action="uploadAvatar" enctype="multipart/form-data" method="post">
    <mk:formLine label="avatar">
        <input type="file" name="avatar"/><input type="submit" class="btn primary" value="sbmit"/>
    </mk:formLine>
    <mk:formLine label="crop">
        <g:checkBox name="crop" value="yes"/>
    </mk:formLine>
</g:form>


<center><img src="${imageUrl}"/></center>

</body>
</html>