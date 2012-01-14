<%--
  By alari
  Since 12/21/11 9:35 PM
--%>
<%// TODO: remove viewModel usage from template! %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="unit-audio unit">
    <div class="unit-audio-player">
        <audio data-bind="audio: '/js/mediaelement/'"
               src="${viewModel.params.mpeg}"></audio>

        <g:if test="${unit.title}">
            <br/><em>${unit.title}</em>
        </g:if>
    </div>

    <div class="unit-credits">
        <g:link for="${unit}" class="dateCreated"><mk:datetime date="${unit.lastUpdated}"/></g:link>
        <g:link for="${unit.owner}">${unit.owner}</g:link>
    </div>

    <g:render template="/unit-render/inners" model="[unit: unit]"/>
</div>
<r:require module="mediaelement"/>
