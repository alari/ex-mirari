<%--
  By alari
  Since 12/23/11 12:10 PM
--%>

<div class="pages-feed">

    <g:each in="${feed}" var="p">
        <article class="feed-page">
            <g:if test="${p.title}">
                <h2><site:link for="${p}"/></h2>
            </g:if>

            <g:if test="${p.inners.size()}">
                <unit:renderPage for="${p.inners?.first()}"/>
            </g:if>

            <div class="page-credits">
                <span class="dateCreated"><mk:datetime date="${p.dateCreated}"/></span>

                <site:link for="${p}"/>
            </div>
        </article>
    </g:each>
</div>

<mk:pagination pagination="${feed.pagination}">
    <site:link for="${_site}" params="[pageNum: (num ? '-' + num + '-' : '')]">${text}</site:link>
</mk:pagination>