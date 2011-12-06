<%--
 * @author alari
 * @since 11/26/11 1:04 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${!only && unit.title}">
    <h2>${unit.title}</h2>
</g:if>
<blockquote>
    ${unit.content.text}
</blockquote>