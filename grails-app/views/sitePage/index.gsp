<%--
 * @author alari
 * @since 11/3/11 2:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${page.title}</title>
</head>

<body>

<mk:withSmallSidebar>
    <mk:content>
        <article>
            <g:if test="${page.title}">
                <mk:pageHeader>${page.title}</mk:pageHeader>
            </g:if>

            <g:each in="${page.inners}" var="unit">
                <unit:renderPage for="${unit.viewModel}" only="${page.inners.size() == 1}"/>
            </g:each>

        </article>
    </mk:content>
    <mk:sidebar>

        <div>
            <div style="text-align: center">
                <g:link for="${page}"><img src="${page.notInnerImage.thumbSrc}"/></g:link>
            </div>

            <div style="text-align: right">
                Автор: <b><g:link for="${page.owner}">${page.owner}</g:link></b>

                <br/>
                <em><g:message code="pageType.${page.type.name}"/></em>
                <br/>
                <i><mk:datetime date="${page.publishedDate ?: page.lastUpdated}"/></i>
            </div>

            <div style="padding: 1em">
                <g:each in="${page.tags}" var="t">
                    <g:link for="${t}" class="label" style="white-space: nowrap;"><i class="icon-tag"></i>${t}</g:link>
                </g:each>
            </div>
        <rights:ifCanEdit unit="${page}">
            <div class="btn-group">
                <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                    Действия
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><g:link for="${page}" action="setDraft" params="[draft: !page.draft]"><g:message
                            code="unit.edit.setDraftTo.${page.draft ? 'false' : 'true'}"/></g:link></li>

                    <li><g:link for="${page}" action="edit">
                        <g:message code="unit.edit.button"/></g:link></li>

                    <rights:ifCanDelete unit="${page}">
                        <li><g:link for="${page}" action="delete" onclick="return confirm('Уверены?')">
                            <g:message code="unit.delete.button"/>
                        </g:link></li>
                    </rights:ifCanDelete>
                </ul>
            </div>
            </rights:ifCanEdit>
        </div>

    </mk:sidebar>
</mk:withSmallSidebar>

<r:require module="vm_comment"/>

<script type="text/javascript">
    var pageCommentsVM = {newText:""};
    $(function () {
        pageCommentsVM = new PageCommentsVM('${page.url}', '${page.owner.stringId}'<sec:ifLoggedIn>, '<site:profileId/>'<rights:ifCanComment page="${page}">, true</rights:ifCanComment></sec:ifLoggedIn>);
    });
</script>

<div data-bind="template:  { name: 'pageComments', data: pageCommentsVM }"></div>

<g:render template="/jquery-tmpl/comment"/>

</body>
</html>