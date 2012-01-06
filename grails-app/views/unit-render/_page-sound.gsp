<%--
  By alari
  Since 12/21/11 9:35 PM
--%>
<%// TODO: remove viewModel usage from template! %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="unit-audio unit">
    <div class="unit-audio-player">
        <audio data-bind="audio: '<g:resource dir="/js/mediaelement/"/>'"
               src="${unit.viewModel.params.mpeg}"></audio>

        <g:if test="${unit.title}">
            <br/><em>${unit.title}</em>
        </g:if>
    </div>

    <div class="unit-credits">
        <span class="dateCreated"><mk:datetime date="${unit.lastUpdated}"/></span>
        <g:link for="${unit.owner}">${unit.owner}</g:link>
    </div>

    <g:render template="/unit-render/inners" model="${unit}"/>
</div>
<r:require module="mediaelement"/>
