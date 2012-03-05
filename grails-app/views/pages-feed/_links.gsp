<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
    <div class="page-links">
    <g:each in="${feed}" var="p">
        <span class="page-link">
        <g:link for="${p}">${p}</g:link>

                    <g:if test="${p.owner != notShowOwner}">
                        &ndash; <i><site:link for="${p.owner}"/></i>
                    </g:if>

                    <g:if test="${showTypes}">
                        <small><g:message code="pageType.${p.type.name}"/></small>
                    </g:if>
        </span>
    </g:each>
    </div>
<r:require module="css_announcesGrid"/>