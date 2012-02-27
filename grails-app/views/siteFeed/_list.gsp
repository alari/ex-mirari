<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
    <g:each in="${feed}" var="p">
            <div class="page-announce">
                <span class="pull-left">
                    <g:link for="${p}">
                        <img src="${p.thumbSrc}"/></g:link>
                </span>

                <div>
                    <g:if test="${p.title}">
                        <h3><g:link for="${p}">${p.title}</g:link></h3>
                    </g:if>

                    <i class="page-announce-owner"><g:link for="${p.owner}">${p.owner}</g:link></i>

                    <g:if test="${showTypes}">
                        <small class="page-announce-type"><g:message code="pageType.${p.type.name}"/></small>
                    </g:if>
                </div>
            </div>

    </g:each>

<r:require module="css_announcesGrid"/>