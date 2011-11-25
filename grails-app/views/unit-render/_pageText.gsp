<%--
 * @author alari
 * @since 11/26/11 1:04 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${unit.title}">
    <h1>${unit.title}</h1>
</g:if>
<blockquote>
    ${unit.content.text}
</blockquote>