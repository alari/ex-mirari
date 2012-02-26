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

        <div data-bind="fixFloat:60">
            <r:require module="ko_fixFloat"/>

            <div style="text-align: center">
                <g:link for="${page}"><img src="${page.thumbSrc}"/></g:link>
            </div>

            <div style="text-align: right">
                Автор: <b><g:link for="${page.owner}">${page.owner}</g:link></b>

                <br/>
                <em><g:message code="pageType.${page.type.name}"/></em>
                <br/>
                <i><mk:datetime date="${page.publishedDate ?: page.lastUpdated}"/></i>
            </div>

            <div>
                <g:each in="${page.tags}" var="t">
                    <g:link for="${t}" class="label">${t}</g:link>
                </g:each>
            </div>

            <div>
                <small>

                    <rights:ifCanEdit unit="${page}">
                        <g:link for="${page}" action="setDraft" params="[draft: !page.draft]"><g:message
                                code="unit.edit.setDraftTo.${page.draft ? 'false' : 'true'}"/></g:link>

                        <g:link for="${page}" action="edit">
                            <g:message code="unit.edit.button"/></g:link>

                    </rights:ifCanEdit>
                    <rights:ifCanDelete unit="${page}">
                        <g:link for="${page}" action="delete">
                            <g:message code="unit.delete.button"/>
                        </g:link>
                    </rights:ifCanDelete>

                </small>
            </div>

        </div>

    </mk:sidebar>
</mk:withSmallSidebar>


<div class="well">
    <h4>Комментарии:</h4>
</div>

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