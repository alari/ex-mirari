<!doctype html>
<html>
<head>
    <g:if test="${_portal}"><meta name="layout" content="mono"/></g:if>
    <title>Runtime Exception</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css">
</head>

<body>
<g:renderException exception="${exception}"/>
</body>
</html>