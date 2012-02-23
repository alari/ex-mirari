<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${page.title}</title>
</head>

<body>

<pageType:listPills active="${type}"/>

<g:if test="${page.title}">
    <mk:pageHeader>${page.title}</mk:pageHeader>
</g:if>


<mk:withSmallSidebar>
    <mk:content>

        <g:each in="${page.inners}" var="unit">
            <unit:renderPage for="${unit.viewModel}" only="${page.inners.size() == 1}"/>
        </g:each>

    </mk:content>

    <mk:sidebar>
        <g:link for="${page}"><img src="${page.thumbSrc}"/></g:link>
    </mk:sidebar>
</mk:withSmallSidebar>

<g:if test="${page.owner.isProfileSite()}">
    <div class="row">

        <div class="span2" style="text-align: center">
            <g:link for="${page}"><img src="${page.owner.avatar.srcThumb}"/></g:link>
        </div>

        <div class="span8">
            <div class="well">
                <rights:ifCanEdit unit="${page}">

                    <g:link for="${page}" action="edit">
                        <button class="btn info">
                            Редактировать страничку</button></g:link>

                </rights:ifCanEdit>
            </div>
        </div>

        <div class="span2" style="text-align: right">
            Автор: <b><g:link for="${page.owner}">${page.owner}</g:link></b>
        </div>
    </div>
</g:if>

</body>
</html>