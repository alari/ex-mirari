<%--
 * @author alari
 * @since 11/26/11 1:05 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${unit.title && !only}">
    <h2>${unit.title}</h2>
</g:if>
<g:render template="/unit-render/tinyImageGrid" model="[units: unit.units]"/>