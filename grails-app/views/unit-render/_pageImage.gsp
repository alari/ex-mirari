<%--
 * @author alari
 * @since 11/26/11 1:03 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<center>
    <g:if test="${unit.container?.type == 'ImageColl' && unit.container.units.size() > 1}">
        <unit:link for="${unit.container.getNext(unit)}">
            <unit:pageImage for="${unit}"/>
        </unit:link>
    </g:if>
    <g:else>
        <unit:pageImage for="${unit}"/>
    </g:else>
    <g:if test="${unit.title}">
        <strong>${unit.title}</strong>
    </g:if>
</center>