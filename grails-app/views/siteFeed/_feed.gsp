<%--
  By alari
  Since 12/23/11 12:10 PM
--%>

<div class="pages-feed">

    <g:each in="${feed}" var="p">
        <article class="feed-page">
            <g:if test="${p.head.title}">
                <h2><g:link for="${p}">${p}</g:link></h2>
            </g:if>
            <g:if test="${p.body.inners.size()}">
                <unit:renderPage for="${p.body.inners?.first()}"/>
            </g:if>
            <div class="page-credits">
                <g:link for="${p}" class="dateCreated"><mk:datetime date="${p.head.publishedDate}"/></g:link>

                <g:link for="${p}">${p}</g:link>
            </div>
        </article>
    </g:each>
</div>
