<%--
  By alari
  Since 1/5/12 12:34 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="unit-image unit">
    <div class="unit-image-pic">
        <embed name="playerblog" src="http://www.russia.ru/player/main.swf?103" flashvars="name=${unit.videoId}&from=blog&blog=true" width="448" height="252" bgcolor="#000000" allowScriptAccess="always" allowFullScreen="true"></embed>
    </div>

    <div class="unit-credits">
        <span class="dateCreated"><mk:datetime date="${unit.lastUpdated}"/></span>
        <g:link for="${unit.owner}">${unit.owner}</g:link>
    </div>

    <g:render template="/unit-render/inners" model="${unit}"/>
</div>