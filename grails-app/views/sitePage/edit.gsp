<%--
  By alari
  Since 12/14/11 5:36 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title>${page.title}</title>
</head>
<body>

<script type="text/javascript">
    var pageEditVM;

    $().ready(function() {
        pageEditVM = new PageEditVM();
        pageEditVM._action = "<site:url for="${page}" action="save"/>";

        $.ajax({
            dataType: "json",
            url: '<site:url for="${page}" action="viewModel"/>',
            success: function(data, textStatus, jqXHR) {
                serviceReact(data, "#alerts", function(mdl) {
                    pageEditVM.fromJSON(mdl);
                });
            }
        });

        ko.applyBindings(pageEditVM);
    });
</script>

<div id="unit" data-bind="template: { name: 'pageEdit', data: pageEditVM }">
    LOADING
</div>

<r:require module="mirariUnitAdd"/>

<g:render template="/jquery-tmpl/edit"/>

</body>
</html>