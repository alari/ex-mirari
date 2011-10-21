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

<form>
    <mk:formLine label="pwd">
        <g:passwordField name="password"/>
    </mk:formLine>
    <mk:formLine label="email">
        <g:textField name="email"/>
    </mk:formLine>
</form>


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