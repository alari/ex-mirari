<%--
  By alari
  Since 2/14/12 6:15 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>Комментарии :: ${_site}</title>
</head>

<body>
<mk:pageHeader>Комментарии <small><g:link forSite="1">${_site}</g:link></small></mk:pageHeader>

<div>
    <g:each in="${feed}" var="comment">
        <div class="row">
            <div class="span2" style="text-align: center">
                <site:link for="${comment.owner}"/>
                <br/>
                <img src="${comment.owner.avatar.mediumSrc}"/>
            </div>

            <div class="span10">
                <div>
                    <g:link for="${comment.page}">${comment.page.title}</g:link>
                </div>
                <g:if test="${comment.title}">
                    <div><h3>${comment.title.encodeAsHTML()}</h3></div>
                </g:if>
                <div>
                    ${comment.html}
                </div>
                <span class="pull-right"><mk:datetime date="${comment.dateCreated}"/></span>
            </div>
        </div>
    </g:each>
</div>

<mk:pagination pagination="${feed.pagination}">
    <g:link for="${_site}" controller="siteDisqus" action="comments" params="[page: num]">${text}</g:link>
</mk:pagination>

</body>
</html>