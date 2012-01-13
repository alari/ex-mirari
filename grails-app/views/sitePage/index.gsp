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

<mk:pageHeader>${page.title}</mk:pageHeader>

<g:each in="${page.inners}" var="unit">
    <unit:renderPage for="${unit}" only="${page.inners.size() == 1}"/>
</g:each>

<div>
    <g:each in="${page.tags}" var="t">
        <span class="label">${t.displayName}</span>
    </g:each>
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

</body>
</html>