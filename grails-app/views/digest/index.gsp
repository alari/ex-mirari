<%--
  By alari
  Since 3/9/12 12:46 AM
--%>

<%@ page import="mirari.model.digest.NoticeType" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta content="mono" name="layout"/>
  <title>Дайджест обновлений</title>
</head>
<body>
<mk:pageHeader>
    Дайджест обновлений (${feed.total})
</mk:pageHeader>

<g:each in="${feed}" var="notice">
    <g:if test="${notice.type == NoticeType.PAGE_COMMENT}">
        <h6>comment</h6>
    </g:if>
    <g:else>
        <h6>reply</h6>
    </g:else>
</g:each>
</body>
</html>