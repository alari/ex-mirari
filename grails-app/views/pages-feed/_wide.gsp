<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
    <g:each in="${feed}" var="p">
            <div class="page-announce">
                <span class="pull-left">
                    <g:link for="${p}">
                        <img src="${p.image.thumbSrc}"/></g:link>
                </span>

                <div>
                    <g:if test="${p.title}">
                        <h3><g:link for="${p}">${p.title}</g:link></h3>
                    </g:if>

                    <g:if test="${p.owner != notShowOwner}">
                        <i class="page-announce-owner"><site:link for="${p.owner}"/></i>
                    </g:if>

                    <g:if test="${showTypes}">
                        <small class="page-announce-type"><g:message code="pageType.${p.type.name}"/></small>
                    </g:if>
                </div>
            </div>

    </g:each>

<r:require module="css_announcesGrid"/>