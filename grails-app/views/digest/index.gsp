<%--
  By alari
  Since 3/9/12 12:46 AM
--%>

<%@ page import="mirari.model.digest.NoticeType" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta content="mono" name="layout"/>
  <title>Сводка обновлений</title>
</head>
<body>
<mk:pageHeader>
    Сводка обновлений
</mk:pageHeader>

<r:require module="vm_digest"/>

<script type="text/javascript">
    var digestVM = {};
    $(function(){
        digestVM = new DigestVM();
        digestVM.load();
    });

</script>

<g:render template="/jquery-tmpl/digest/reasons"/>
<g:render template="/jquery-tmpl/digest/digest"/>

<div data-bind="template: { name: 'digest', data: digestVM }"></div>

</body>
</html>