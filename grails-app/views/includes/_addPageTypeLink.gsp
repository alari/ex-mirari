<g:if test="${li}">
    <li>
</g:if>
<g:link for="${forSite}" controller="sitePageStatic" action="add" params="[type: type.name]">${message(code: "pageType." +type.name)}</g:link>
<g:if test="${li}">
    </li>
</g:if>