<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
<g:each in="${pages}" var="p">
    <li><space:link for="${p}"/></li>
</g:each>
</ul>