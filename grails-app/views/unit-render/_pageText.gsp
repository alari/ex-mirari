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

    <g:if test="${unit.inners.size() > 0}">
            <g:each in="${unit.inners}" var="u">
                <unit:renderPage for="${u}" only=""/>
            </g:each>
    </g:if>
</blockquote>