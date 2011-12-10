<%--
 * @author alari
 * @since 11/26/11 1:03 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<center>
    <g:if test="${unit.outer?.type == 'ImageColl' && unit.outer.units.size() > 1}">
        <unit:link for="${unit.outer.getNext(unit)}">
            <unit:pageImage for="${unit}"/>
        </unit:link>
    </g:if>
    <g:else>
        <unit:pageImage for="${unit}"/>
    </g:else>
    <g:if test="${unit.title}">
        <br/><strong>${unit.title}</strong>
    </g:if>
</center>