<%--
  By alari
  Since 12/23/11 12:10 PM
--%>

<div class="pages-feed">

    <g:each in="${feed}" var="p">
        <article class="feed-page">


            <g:if test="${p.title}">
                <h2><g:link for="${p}">${p}</g:link></h2>
            </g:if>

            <div class="pull-right" style="text-align: center">

                <g:link for="${p}">
                    <img src="${p.notInnerImage.thumbSrc}"/></g:link>

                <div style="text-align: right">
                    <g:if test="${p.owner != notShowOwner}">
                        Автор: <b><site:link for="${p.owner}"/></b>
                    </g:if>

                    <g:if test="${showTypes}">
                        <br/>
                        <em><g:message code="pageType.${p.type.name}"/></em>
                    </g:if>
                    
                    <br/>
                    <i><mk:datetime date="${p.publishedDate ?: p.lastUpdated}"/></i>
                </div>
            </div>


                <g:if test="${p.inners.size()}">
                    <unit:renderPage for="${p.inners?.first()?.viewModel}"/>
                </g:if>
                <div class="page-credits">
                    <g:link for="${p}" class="dateCreated"><mk:datetime date="${p.publishedDate}"/></g:link>

                    <g:link for="${p}">${p}</g:link>
                </div>
            <br clear="all"/>
        </article>
    </g:each>
</div>
