<%--
 * @author alari
 * @since 11/22/11 9:28 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="pageEdit">
<div class="unit-envelop">
    <h1><input class="page-title" type="text" placeholder="${g.message(code: 'unit.add.titlePlaceholder')}"
               name="title" data-bind="value: title"/></h1>

    <div data-bind="template: { name: 'unitEdit', foreach: inners }, sortableInners: $data"
         class="unit-content sortable"></div>

    <div class="edit-empty" data-bind="visible: !innersCount()">
        <h6>Добавьте картинки, тексты с помощью штуки, расположенной снизу</h6>
    </div>

    <div class="unit-adder row" data-bind="pageFileUpload: true">
        <div class="span6 unit-adder-drop">
            <form method="post" enctype="multipart/form-data"
                  action="<site:url for="${site}" controller="sitePageStatic"
                                     action="addFile"/>">
                <g:message code="unit.add.drop"/>
                <input type="file" name="unitFile" multiple/>
                <input type="hidden" name="ko" data-bind="value:toJSON()"/>
            </form>
        </div>

        <div class="span6">
            <ul>
                <li>
                    <a href="#" data-bind="click: addTextUnit">Добавить текстовый блок</a>
                </li>
            </ul>
        </div>
        <br clear="all"/>
        <div class="ui-progressbar"></div>
    </div>


    <br clear="all"/>
    <mk:formActions>
        <button class="btn primary unit-pub" data-bind="click: submit">
            <g:message code="unit.add.submit.publish"/></button>
        <button class="btn info unit-draft" data-bind="click: submitDraft">
            <g:message code="unit.add.submit.draft"/></button>
    </mk:formActions>
</div>
</mk:tmpl>

<mk:tmpl id="unitEdit">
    <div class="unit unit-edit" data-bind="sortableItem: $data">

        <div class="unit-credits unit-head">
            <span class="unit-sort sort">SORT</span>
            <span class="unit-delete" data-bind="click: remove">DELETE</span>
        </div>

        <div class="unit-head" data-bind="visible: titleVisible">
            <input type="text" data-bind="value: title" placeholder="Заголовок текста"/>
        </div>

        <div class="unit-body" data-bind="template: {name: pageEditVM.unitTmpl, item: $data}"></div>

        <div class="unit-inners sortable" data-bind="template: { name: 'unitEdit', foreach: inners }, sortableInners: $data">
        </div>

    </div>
</mk:tmpl>

<g:render template="/jquery-tmpl/editAudio"/>
<g:render template="/jquery-tmpl/editImage"/>
<g:render template="/jquery-tmpl/editText"/>

<r:require module="aloha"/>
