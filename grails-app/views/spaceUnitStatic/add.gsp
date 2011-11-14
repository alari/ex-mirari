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

<r:require module="ko"/>

<script src="/mirari/js/UnitEditViewModel.js"></script>
<script type="text/javascript">
    var unitEditViewModel;
    var unitEditContext;
    $(function(){
        unitEditContext = new UnitEditContext("#unit");
        unitEditViewModel = new UnitEditViewModel();
        ko.applyBindings(unitEditViewModel);
    });
</script>





<div class="unit-envelop" id="unit"
     data-unit-action="<space:url for="${space}" controller="spaceUnitStatic" action="addUnit"/>">
    <h1><input class="unit-title" type="text" placeholder="${g.message(code: 'unit.add.titlePlaceholder')}"
               name="title" data-bind="value: unitEditViewModel.title"/></h1>

    <div data-bind="template: { name: unitEditViewModel.unitTmpl, foreach: unitEditViewModel.contents }" class="unit-content"></div>

    <div class="unit-adder row">
        <div class="span6 unit-adder-drop">
            <form method="post" enctype="multipart/form-data"
                  action="<space:url for="${space}" controller="spaceUnitStatic"
                                     action="addFile"/>">
                <g:message code="unit.add.drop"/>
                <input type="file" name="unitFile"/>

            </form>
        </div>

        <div class="span6">
            ***
        </div>
    </div>

    <div class="ui-progressbar"></div>

    <br clear="all"/>
    <mk:formActions>
        <button class="btn primary unit-pub"><g:message code="unit.add.submit.publish"/></button>
        <button class="btn info unit-draft"><g:message code="unit.add.submit.draft"/></button>
    </mk:formActions>
</div>

<r:require module="mirariUnitAdd"/>
<r:require module="koMapping"/>

<g:render template="/jquery-tmpl/editImage"/>
</body>
</html>