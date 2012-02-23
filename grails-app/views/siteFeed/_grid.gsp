<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="row">
    <g:each in="${feed}" var="p">
        <div class="span4">
            <div class="page-announce">
                <span class="pull-left">
                    <g:link for="${p}">
                        <img src="${p.thumbSrc}"/></g:link>
                </span>
                <div>
                    <g:if test="${p.title}">
                        <strong class="page-announce-title"><g:link for="${p}">${p.title}</g:link></strong>
                    </g:if>
                    <i class="page-announce-owner"><g:link for="${p.owner}">${p.owner}</g:link></i>
                    <small class="page-announce-type"><g:message code="pageType.${p.type.name}"/></small>
                </div>
            </div>
        </div>


    </g:each>
</div>

<r:require module="css_announcesGrid"/>