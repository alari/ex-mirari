<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
    <g:each in="${pages}" var="p">
        <li><g:link for="${p}">${p}</g:link> /${p.type}/ - <g:link for="${p.site}">${p.site}</g:link></li>
    </g:each>
</ul>