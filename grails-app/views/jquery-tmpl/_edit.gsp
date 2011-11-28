<%--
 * @author alari
 * @since 11/22/11 9:28 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="unitEdit">
<div class="unit-envelop">
    <h1><input class="unit-title" type="text" placeholder="${g.message(code: 'unit.add.titlePlaceholder')}"
               name="title" data-bind="value: title"/></h1>

    <div data-bind="template: { name: unitTmpl, foreach: units }"
         class="unit-content"></div>

    <div class="unit-adder row" data-bind="pageFileUpload: true">
        <div class="span6 unit-adder-drop">
            <form method="post" enctype="multipart/form-data"
                  action="<space:url for="${space}" controller="spaceUnitStatic"
                                     action="addFile"/>">
                <g:message code="unit.add.drop"/>
                <input type="file" name="unitFile" multiple/>
                <input type="hidden" name="ko" data-bind="value:toJSON()"/>
            </form>
        </div>

        <div class="span6">
            <a href="#" data-bind="click: addTextUnit">add text</a>
        </div>
    </div>

    <div class="ui-progressbar"></div>

    <br clear="all"/>
    <mk:formActions>
        <button class="btn primary unit-pub" data-bind="click: submit">
            <g:message code="unit.add.submit.publish"/></button>
        <button class="btn info unit-draft" data-bind="click: submitDraft">
            <g:message code="unit.add.submit.draft"/></button>
    </mk:formActions>
</div>
</mk:tmpl>