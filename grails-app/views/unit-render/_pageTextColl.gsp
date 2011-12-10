<%--
 * @author alari
 * @since 11/26/11 1:07 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${unit.title && !only}">
    <h2>${unit.title}</h2>
</g:if>
<g:each in="${unit.units}" var="u">
    <unit:renderPage for="${u}"/>
</g:each>