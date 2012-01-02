<%--
 * @author alari
 * @since 11/26/11 1:03 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="unit-image unit">
    <div class="unit-image-pic">
        <g:if test="${unit.outer?.type == 'ImageColl' && unit.outer.units.size() > 1}">
            <unit:link for="${unit.outer.getNext(unit)}">
                <unit:pageImage for="${unit}"/>
            </unit:link>
        </g:if>
        <g:else>
            <unit:pageImage for="${unit}"/>
        </g:else>

        <g:if test="${unit.title}">
            <br/><em>${unit.title}</em>
        </g:if>
    </div>

    <div class="unit-credits">
        <span class="dateCreated"><mk:datetime date="${unit.lastUpdated}"/></span>
        <unit:fullImageLink for="${unit}"/>
        <site:link for="${unit.owner}"/>
    </div>

    <g:render template="/unit-render/inners" model="${unit}"/>
</div>