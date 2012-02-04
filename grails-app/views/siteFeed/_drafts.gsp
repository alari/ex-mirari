<%--
  By alari
  Since 1/31/12 11:15 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${drafts && drafts.total}">
<h6>Черновики: ${drafts.total}</h6>
<div>
    <g:render template="/siteFeed/grid" model="[pages: drafts]"/>
</div>
<hr/>
</g:if>