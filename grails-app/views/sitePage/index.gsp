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

    <div style="text-align: center;">
        <site:profileLink for="${unit.owner}"/>
        &nbsp;
        <g:if test="${unit.outer != null}">
            <unit:link for="${unit.outer}"/>
            &nbsp;
        </g:if>

        <g:if test="${unit.type == 'Image'}">
            <unit:fullImageLink for="${unit}"/>
        </g:if>
    </div>
</g:each>

<mk:formActions>

    <rights:ifCanEdit unit="${page}">
        <site:link for="${page}" action="setDraft" params="[draft:!page.draft]">
            <button class="btn primary"><g:message
                    code="unit.edit.setDraftTo.${page.draft ? 'false' : 'true'}"/></button></site:link>
    </rights:ifCanEdit>
    <rights:ifCanDelete unit="${page}">
        <site:link for="${page}" action="delete"><button class="btn danger">
            <g:message code="unit.delete.button"/>
        </button></site:link>
    </rights:ifCanDelete>
</mk:formActions>

</body>
</html>