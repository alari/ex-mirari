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
<mk:pageHeader><sbj:link subject="${space}"/></mk:pageHeader>
<mk:withLeftSidebar>
    <mk:leftSidebar>
        <avatar:large subject="${space}"/>
    </mk:leftSidebar>
    <mk:content>

        <test:echo><p>Tell you a secret: email is <tt>${space.email}</tt></p></test:echo>

        <p>Locked: <g:formatBoolean boolean="${space.accountLocked}"/></p>

        <hr/>
        ${info.frontText}

    </mk:content>
</mk:withLeftSidebar>
</body>
</html>