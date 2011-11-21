<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${unit}</title>
</head>

<body>
<g:if test="${unit.title}">
    <h1>${unit}</h1>
</g:if>

<g:if test="${unit.type == 'Image'}">
    <center>
        <g:if test="${unit.container?.type == 'ImageColl' && unit.container.units.size() > 1}">
            <unit:link for="${unit.container.getNext(unit)}">
                <unit:pageImage for="${unit}"/>
            </unit:link>
        </g:if>
        <g:else>
            <unit:pageImage for="${unit}"/>
        </g:else>
    </center>
</g:if>
<g:if test="${unit.type == "ImageColl"}">
    <g:render template="/unit-render/tinyImageGrid" model="[units:unit.units]"/>
</g:if>

<div>
    <space:personLink for="${unit.space}"/>
    &nbsp;
    <g:if test="${unit.container != null}">
        <unit:link for="${unit.container}"/>
        &nbsp;
    </g:if>

    <g:if test="${unit.type == 'Image'}">
        <unit:fullImageLink for="${unit}"/>
    </g:if>
</div>

<mk:formActions>
    <unit:ifCanEdit unit="${unit}">
        <unit:link for="${unit}" action="setDraft" params="[draft:!unit.draft]">

            <button class="btn primary"><g:message
                    code="unit.edit.setDraftTo.${unit.draft ? 'false' : 'true'}"/></button></unit:link>
    </unit:ifCanEdit>
    <unit:ifCanDelete unit="${unit}">
        <unit:link for="${unit}" action="delete"><button class="btn danger">
            <g:message code="unit.delete.button"/>
        </button></unit:link>
    </unit:ifCanDelete>
</mk:formActions>

</body>
</html>