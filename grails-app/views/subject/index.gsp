<%--
  @author Dmitry Kurinskiy
  @since 02.09.11 13:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${subject.domain}</title>
</head>

<body>
<mk:pageHeader><sbj:link subject="${subject}"/></mk:pageHeader>

<p>Tell you a secret: email is <tt>${subject.email}</tt></p>

<p>Locked: <g:formatBoolean boolean="${subject.accountLocked}"/></p>

<hr/>
${info.frontText}

</body>
</html>