<%--
  By alari
  Since 1/5/12 12:23 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="unit-image unit">
    <div class="unit-image-pic">
        <iframe width="420" height="315" src="http://www.youtube.com/embed/${viewModel.params.externalId}" frameborder="0" allowfullscreen></iframe>
    </div>

    <div class="unit-credits">
        <g:link for="${unit}" class="dateCreated"><mk:datetime date="${unit.lastUpdated}"/></g:link>
        <g:link for="${unit.owner}">${unit.owner}</g:link>
    </div>

    <g:render template="/unit-render/inners" model="[unit: unit]"/>
</div>