<%--
 * @author alari
 * @since 11/26/11 1:04 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<unit:withPageReferenceUnit unit="${viewModel}">
    <div class="unit">
        
        <g:if test="${page}">
            <div class="page-announce">
                <span class="pull-left">
                    <g:link for="${page}">
                        <img src="${page.thumbSrc}"/></g:link>
                </span>

                <div>
                    <g:if test="${page.title}">
                        <h3><g:link for="${page}">${page.title}</g:link></h3>
                    </g:if>

                    <i class="page-announce-owner"><g:link for="${page.owner}">${page.owner}</g:link></i>

                    <small class="page-announce-type"><g:message code="pageType.${page.type.name}"/></small>
                </div>
            </div>
        </g:if>
        <g:else>
            <h3>Страничка недоступна</h3>
        </g:else>

    </div>
</unit:withPageReferenceUnit>

<r:require module="css_announcesGrid"/>