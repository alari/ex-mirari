<%--
 * @author alari
 * @since 10/27/11 10:00 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title><g:message code="unit.add.title" args="[(_portal ?: _site).toString()]"/></title>

</head>

<body>

<script type="text/javascript">
    var pageEditVM;

    $().ready(function () {
        pageEditVM = new PageVM();
        pageEditVM.type("${type.name}");
        pageEditVM.thumbSrc("${thumbSrc}");
    });
</script>

<r:script disposition="bottom">
    $().ready(function () {
        <g:if test="${addText}">
    pageEditVM.innersAct.addTextUnit();
</g:if>
    });
</r:script>

<div id="unit" data-bind="template: { name: 'pageEdit', data: pageEditVM }">
    LOADING
</div>

<r:require module="mirariUnitAdd"/>

<g:render template="/jquery-tmpl/edit-page/edit"/>
</body>
</html>