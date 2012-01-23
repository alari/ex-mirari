<%--
 * @author alari
 * @since 11/26/11 1:04 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<div class="unit">

    <g:if test="${!only && unit.title}">
        <g:if test="${unit.outer}">
            <h3>${unit.title}</h3>
        </g:if>
        <g:else>
            <h2>${unit.title}</h2>
        </g:else>
    </g:if>

    <div class="unit-text">
        ${viewModel.params.html}
    </div>

    <div class="unit-credits">
        <g:link for="${unit}" class="dateCreated"><mk:datetime date="${unit.lastUpdated}"/></g:link>
        <g:link for="${unit.owner}">${unit.owner}</g:link>
    </div>

    <g:render template="/unit-render/inners" model="[unit: unit]"/>

</div>