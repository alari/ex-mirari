<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<ul class="thumbnails">
<g:each in="${feed}" var="p">
    <li class="span2">
        <div class="thumbnail" style="text-align: center">
            <g:link for="${p}">
                <img src="${p.image.smallSrc}"/></g:link>
            <g:if test="${p.title}">
                <h5><g:link for="${p}">${p.title}</g:link></h5>
            </g:if>
            <g:if test="${p.owner != notShowOwner}">
                <i class="page-announce-owner"><site:link for="${p.owner}"/></i>
            </g:if>
        </div>
    </li>
    </g:each>
</ul>