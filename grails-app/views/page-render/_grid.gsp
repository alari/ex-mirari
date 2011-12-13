<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
<g:each in="${pages}" var="p">
    <li><site:link for="${p}"/> /${p.type}/ - <site:link for="${p.site}"/></li>
</g:each>
</ul>