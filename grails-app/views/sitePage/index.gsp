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

<g:if test="${page.title}">
<mk:pageHeader>${page.title}</mk:pageHeader>
</g:if>

<g:each in="${page.inners}" var="unit">
    <unit:renderPage for="${unit.viewModel}" only="${page.inners.size() == 1}"/>
</g:each>

<div>
    <g:each in="${page.tags}" var="t">
        <g:link for="${t}" class="label">${t}</g:link>
    </g:each>
</div>

<div class="page-credits">

</div>

<div class="row">
    <div class="span8">
        <div class="well">
            <rights:ifCanEdit unit="${page}">
                <g:link for="${page}" action="setDraft" params="[draft: !page.draft]">
                    <button class="btn primary"><g:message
                            code="unit.edit.setDraftTo.${page.draft ? 'false' : 'true'}"/></button></g:link>

                <g:link for="${page}" action="edit">
                    <button class="btn info"><g:message code="unit.edit.button"/></button></g:link>

            </rights:ifCanEdit>
            <rights:ifCanDelete unit="${page}">
                <g:link for="${page}" action="delete"><button class="btn danger">
                    <g:message code="unit.delete.button"/>
                </button></g:link>
            </rights:ifCanDelete>
        </div>
    </div>
    <div class="span2" style="text-align: center">
        <g:link for="${page}"><img src="${page.thumbSrc}"/></g:link>
    </div>
    <div class="span2" style="text-align: right">
        Автор: <b><g:link for="${page.owner}">${page.owner}</g:link></b>

                    <br/>
        <em><g:message code="pageType.${page.type.name}"/></em>
            <br/>
        <i><mk:datetime date="${page.publishedDate ?: page.lastUpdated}"/></i>
    </div>
</div>

<r:require module="vm_comment"/>

<script type="text/javascript">
    var pageCommentsVM = {newText: ""};
    $(function(){
        pageCommentsVM = new PageCommentsVM('${page.url}', '${page.owner.stringId}'<sec:ifLoggedIn>, '<site:profileId/>'<rights:ifCanComment page="${page}"> , true</rights:ifCanComment></sec:ifLoggedIn>);
    });
</script>

<div data-bind="template:  { name: 'pageComments', data: pageCommentsVM }"></div>

<g:render template="/jquery-tmpl/comment"/>

</body>
</html>