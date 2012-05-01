<!doctype html>
<html>
<head>
    <g:if test="${!_portal}"><meta name="layout" content="blanc"/></g:if>
    <title>Runtime Exception</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'errors.css')}" type="text/css">
</head>

<body>

<mk:pageHeader>
    Произошла ошибка
</mk:pageHeader>

<p>Что-то на сервере произошло не так. Сожалеем, постараемся исправить.</p>

<test:echo>
    <g:renderException exception="${exception}"/>
</test:echo>
</body>
</html>