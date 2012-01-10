<%--
  By alari
  Since 1/5/12 12:34 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="unit-image unit">
    <div class="unit-image-pic">
        <embed name="playerblog${unit.stringId}" src="http://www.russia.ru/player/main.swf?103" flashvars="name=${viewModel.params.externalId}&from=blog&blog=true" width="448" height="252" bgcolor="#000000" allowScriptAccess="always" allowFullScreen="true"></embed>
    </div>

    <div class="unit-credits">
        <g:link for="${unit}" class="dateCreated"><mk:datetime date="${unit.lastUpdated}"/></g:link>
        <g:link for="${unit.owner}">${unit.owner}</g:link>
    </div>

    <g:render template="/unit-render/inners" model="${unit}"/>
</div>