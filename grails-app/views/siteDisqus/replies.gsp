<%--
  By alari
  Since 2/14/12 6:58 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>Ответы :: ${_site}</title>
</head>

<body>
<mk:pageHeader>Ответы на комментарии <small><site:link for="${_site}"/></small></mk:pageHeader>

<div>
    <g:each in="${feed}" var="reply">
        <div class="row">
            <div class="span2" style="text-align: center">
                <site:link for="${reply.owner}"/>
                <br/>
                <img src="${reply.owner.avatar.srcThumb}"/>
            </div>

            <div class="span8">

                <div>
                    <g:link for="${reply.page}">${reply.page.title}</g:link>
                </div>

                <div>${reply.html}</div>
                <span class="pull-right"><mk:datetime date="${reply.dateCreated}"/></span>
            </div>
        </div>
    </g:each>
</div>

<mk:pagination pagination="${feed.pagination}">
    <g:link for="${_site}" controller="siteDisqus" action="replies" params="[page: num]">${text}</g:link>
</mk:pagination>

</body>
</html>