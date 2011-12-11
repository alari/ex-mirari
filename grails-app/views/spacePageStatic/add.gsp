<%--
 * @author alari
 * @since 10/27/11 10:00 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title><g:message code="unit.add.title" args="[space.toString()]"/></title>

</head>

<body>


<script type="text/javascript">
    var pageEditVM;

    $().ready(function() {
        pageEditVM = new PageEditVM();
        pageEditVM._action = "<space:url for="${space}" controller="spacePageStatic" action="addPage"/>";
        ko.applyBindings(pageEditVM);
    });
</script>

<div id="unit" data-bind="template: { name: 'pageEdit', data: pageEditVM }">
    LOADING
</div>

<r:require module="mirariUnitAdd"/>

<g:render template="/jquery-tmpl/editImage"/>
<g:render template="/jquery-tmpl/edit"/>
<g:render template="/jquery-tmpl/editText"/>
</body>
</html>