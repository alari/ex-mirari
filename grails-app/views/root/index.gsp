<!doctype html>
<html>
<head>
    <meta name="layout" content="mono"/>
    <title></title>

</head>

<body>

<mk:pageHeader>Мирари. Альфа</mk:pageHeader>

<p>Это &ndash; альфа-версия проекта Мирари. Работоспособность, безопасность, устойчивость,
сохранность данных не гарантируются.</p>

<p>Пожалуйста, используйте сайт для тестирования, сообщайте об ошибках и пожеланиях разработчику.</p>

<p>Вы сможете сделать вклад в создание нового нужного сервиса.</p>

<h2>В чём нужность?</h2>

<p>Творческие задачи, реализуемые, в том числе, в Сети, можно разделить на несколько областей:</p>
<ol>
    <li><b>Публикация</b>. Произведение, творческая работа, часто требует более сложных,
    специализированных и удобных инструментов и форматов для публикации.</li>
    <li><b>Репрезентация</b>. Творческую работу нужно правильно подать, организовать восприятие личности, инициативы,
    коллектива.</li>
    <li><b>Распространение</b>. Работа должна найти своего адресата &ndash; через подписки, тематические ленты, срезы,
    традиционные социальные сети.</li>
    <li><b>Коммуникация</b>. В плане творчества. Хорошо устроенные комментарии, рекомендации, рецензии, обзоры.</li>
    <li><b>Коллаборация</b>. Тематические сообщества, проекты, конкурсы и тому подобное.</li>
</ol>

<p>Это &ndash; основные фокусы внимания при разработке проекта Мирари. Вы сможете создать личную страницу,
страницу сообщества, творческого коллектива или инициативы. Вы сможете легко публиковать адекватно оформленные
произведения разных видов, в том числе смешивая их. Вы сможете выгодно презентовать творческие начинания с
разных сторон, в том числе и показав свою личность в разных ипостасях. Вы сможете легко налаживать взаимодействие
своей новой странички с традиционными социальными сетями и другими участниками проекта.</p>
<p>Обратитесь к разработчику, чтобы получить дорожную карту работ и узнать даты релизов.</p>

<h2>Последние юниты</h2>

<br/>

<ul class="media-grid">
    <g:each in="${allUnits}" var="u">
        <li><unit:link for="${u}"><unit:tinyImage for="${u}"/></unit:link></li>
    </g:each>
</ul>

</body>
</html>
