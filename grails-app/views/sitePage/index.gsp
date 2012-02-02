<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${page.head.title}</title>
</head>

<body>

<g:if test="${page.head.title}">
<mk:pageHeader>${page.head.title}</mk:pageHeader>
</g:if>

<g:each in="${page.body.inners}" var="unit">
    <unit:renderPage for="${unit}" only="${page.body.inners.size() == 1}"/>
</g:each>

<div>
    <g:each in="${page.head.tags}" var="t">
        <g:link for="${t}" class="label">${t}</g:link>
    </g:each>
</div>

<div class="page-credits">
    <g:message code="pageType.${page.head.type.name}"/>,
    <mk:datetime date="${page.head.publishedDate ?: page.head.lastUpdated}"/>
</div>

<mk:formActions>

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
</mk:formActions>

<r:require module="vm_comment"/>

<script type="text/javascript">
    var pageCommentsVM;
    $(function(){
        pageCommentsVM = new PageCommentsVM('${page.url}'<rights:ifCanComment page="${page}"> , true</rights:ifCanComment>);
    });
</script>

<div data-bind="template:  { name: 'pageComments', data: pageCommentsVM }"></div>

<g:render template="/jquery-tmpl/comment"/>

</body>
</html>