<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
    <g:each in="${pages}" var="p">
        <li><g:link for="${p}">${p}</g:link> - <g:link for="${p.owner}">${p.owner}</g:link></li>
    </g:each>
</ul>