<%--
 * @author alari
 * @since 11/26/11 1:05 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${unit.title}">
    <h1>${unit.title}</h1>
</g:if>
<g:render template="/unit-render/tinyImageGrid" model="[units:unit.units]"/>