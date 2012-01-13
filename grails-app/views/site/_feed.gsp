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
            <g:if test="${p.inners.size()}">
                <unit:renderPage for="${p.inners?.first()}"/>
            </g:if>
            <div class="page-credits">
                <g:link for="${p}" class="dateCreated"><mk:datetime date="${p.dateCreated}"/></g:link>

                <g:link for="${p}">${p}</g:link>
            </div>
        </article>
    </g:each>
</div>
