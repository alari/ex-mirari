<%--
  @author Dmitry Kurinskiy
  @since 29.08.11 12:48
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <g:if test="${!_portal}"><meta name="layout" content="blanc"/></g:if>
    <title>Error404</title>
</head>

<body>
<h1>404 <small>Page Not Found</small></h1>
<br/>

<h2>${request.forwardURI}</h2>
</body>
</html>