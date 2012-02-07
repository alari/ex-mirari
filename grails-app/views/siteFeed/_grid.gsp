<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="row">
    <g:each in="${pages}" var="p">
        <div class="span4 page-announce">
            <span class="pull-left">
                <g:link for="${p}">
                    <img src="${p.tinyImageSrc}"/></g:link>
            </span>
            <div>
                <g:if test="${p.head.title}">
                    <strong class="page-announce-title"><g:link for="${p}">${p.head.title}</g:link></strong>
                </g:if>
                <i class="page-announce-owner"><g:link for="${p.head.owner}">${p.head.owner}</g:link></i>
                <small class="page-announce-type"><g:message code="pageType.${p.head.type.name}"/></small>
            </div>
        </div>


    </g:each>
</div>

<r:require module="css_announcesGrid"/>