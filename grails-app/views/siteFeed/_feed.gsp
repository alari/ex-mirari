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
                    <img src="${p.notInnerThumbSrc}"/></g:link>

                <div style="text-align: right">
                    Автор: <b><g:link for="${p.owner}">${p.owner}</g:link></b>

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

        </article>
    </g:each>
</div>
