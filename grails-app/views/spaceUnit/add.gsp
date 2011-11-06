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

<div class="unit-envelop" id="unit" data-unit-action="<space:url for="${space}" action="addUnit"/>">
    <h1><input class="unit-title" type="text" placeholder="${g.message(code:'unit.add.titlePlaceholder')}"
               name="title"/></h1>

    <div class="unit-content"></div>

    <div class="unit-adder">
        <div class="span6 unit-adder-drop">
            <form method="post" enctype="multipart/form-data" action="<space:url for="${space}" action="addFile"/>">">
                <g:message code="unit.add.drop"/>
                <input type="file" name="unitFile"/>

            </form>
        </div>

        <div class="span6">
            ***
        </div>
    </div>


    <br clear="all"/>
    <mk:formActions>
        <button class="btn primary unit-pub"><g:message code="unit.add.submit.publish"/></button>
        <button class="btn info unit-draft"><g:message code="unit.add.submit.draft"/></button>
    </mk:formActions>
</div>

<r:require module="mirariUnitAdd"/>

</body>
</html>