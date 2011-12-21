<%--
  By alari
  Since 12/21/11 9:35 PM
--%>

<%@ page import="mirari.morphia.unit.single.AudioUnit" contentType="text/html;charset=UTF-8" %>
<div class="unit-audio unit">
    <div class="unit-audio-player">
        <audio data-bind="audio: '<g:resource dir="/js/mediaelement/"/>'" src="${unit.getSoundUrl(AudioUnit.Type.MP3)}"></audio>

        <g:if test="${unit.title}">
            <br/><em>${unit.title}</em>
        </g:if>
    </div>

    <div class="unit-credits">
        <span class="dateCreated"><mk:datetime date="${unit.dateCreated}"/></span>
        <site:link for="${unit.owner}"/>
    </div>

    <g:render template="/unit-render/inners" model="${unit}"/>
</div>
<r:require module="mediaelement"/>
