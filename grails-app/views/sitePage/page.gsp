<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${page.title}</title>
    <link rel="alternate" title="ATOM" type="application/atom+xml" href="<site:atomFeedUrl for="${_site}"/>"/>
</head>

<body>

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
        <div style="text-align: center">
            <g:link for="${page}"><img src="${page.notInnerImage.smallSrc}"/></g:link>
        </div>

        <g:if test="${!page.owner.isPortalSite()}">
        <div style="text-align: right">
            <b><g:link for="${page.owner}">${page.owner}</g:link></b>

            <br/>
            <i><mk:datetime date="${page.publishedDate ?: page.lastUpdated}"/></i>

        </div>

        </g:if>
            <rights:ifCanEdit unit="${page}">
                <small>
                    <br/>
                    <g:link for="${page}" action="edit">
                        Править эту страничку</g:link>
                </small>

            </rights:ifCanEdit>



    </mk:sidebar>
</mk:withSmallSidebar>

</body>
</html>