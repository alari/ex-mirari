<ul class="nav nav-pills">

    <li><site:link for="${forSite}"/></li>

    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li><g:link for="${forSite}" controller="siteDisqus" action="comments">Комментарии</g:link></li>
            <li><g:link for="${forSite}" controller="siteDisqus" action="replies">Ответы</g:link></li>
            <rights:ifCanAdmin site="${forSite}">
                <li><g:link for="${forSite}" controller="sitePreferences"
                            action="preferences">Настройки сайта</g:link></li>
            </rights:ifCanAdmin>
        </ul></li>

    <g:each in="${typedPageFeeds}" var="tpf">
        <li${tpf.active ? ' class="active"' : ''}>
            <g:link for="${tpf.page}">${tpf.title}</g:link>
        </li>
    </g:each>

    <g:if test="${secondaryFeeds.size()}">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                Ещё
                <b class="caret"></b>
            </a>

            <ul class="dropdown-menu">
                <g:each in="${secondaryFeeds}" var="tpf">
                    <li${tpf.active ? ' class="active"' : ''}>
                        <g:link for="${tpf.page}">${tpf.title}</g:link>
                    </li>
                </g:each>
            </ul>
        </li>
    </g:if>

    <rights:ifCanAdd site="${forSite}">
        <li class="dropdown">
            <pageType:pageDropdown for="${forSite}"/>
        </li>
    </rights:ifCanAdd>

</ul>