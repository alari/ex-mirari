<a href="#" class="dropdown-toggle" data-toggle="dropdown">Добавить<b class="caret"></b></a>
<ul class="dropdown-menu">

    <g:each in="${portalTypes}" var="type">
        <g:render template="/includes/addPageTypeLink" model="[li: true, forSite: forSite, type: type]"/>
    </g:each>

    <g:if test="${portalTypes.size() && profileTypes.size()}">
        <li class="divider"></li>
    </g:if>

    <g:each in="${profileTypes}" var="type">
        <g:render template="/includes/addPageTypeLink" model="[li: true, forSite: forSite, type: type]"/>
    </g:each>

    <g:if test="${restTypes.size()}">
        <li class="divider"></li>
    </g:if>

    <g:each in="${restTypes}" var="type">
        <g:render template="/includes/addPageTypeLink" model="[li: true, forSite: forSite, type: type]"/>
    </g:each>
</ul>