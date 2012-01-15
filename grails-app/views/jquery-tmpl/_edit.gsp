<%--
 * @author alari
 * @since 11/22/11 9:28 PM
--%>

<%@ page import="mirari.model.PageType" contentType="text/html;charset=UTF-8" %>
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
                      action="${_site.getUrl(controller: 'sitePageStatic', action: 'addFile')}">
                    <g:message code="unit.add.drop"/>
                    <input type="file" name="unitFile" multiple/>
                    <input type="hidden" name="ko" data-bind="value:toJSON()"/>
                </form>
            </div>

            <div class="span6">
                <ul>
                    <li>
                        <a href="#" data-bind="click: addHtmlUnit">Добавить текстовый блок</a>
                    </li>
                    <li>
                        <a href="#" data-bind="click: addExternalUnit">Добавить по ссылке</a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="ui-progressbar"></div>


        <br clear="all"/>
        <mk:formActions>
            <button class="btn primary unit-pub" data-bind="click: submit">
                <g:message code="unit.add.submit.publish"/></button>
            <button class="btn info unit-draft" data-bind="click: submitDraft">
                <g:message code="unit.add.submit.draft"/></button>
            <a class="btn" href="." data-bind="visible: id">
                Вернуться без изменений</a>

            <br/> <br/>
            <a href="#" data-bind="click: saveAndContinue">
                Сохранить и продолжить работу</a>
        </mk:formActions>


    </div>



    <mk:formLine field="tags-input" label="Теги">
        <span data-bind="template: { name: 'tag', foreach: tags }"></span>
        <input type="text" id="tags-input" style="border: 0;"
               data-bind="event: {blur: addNewTag, keypress: tagInputKey}, autocomplete: '<g:createLink
                       for="${_site}" action="tagsAutocomplete"/>'" placeholder="Добавить тег"/>
    </mk:formLine>

    <mk:formLine field="type" label="Type">

        <select name="type" data-bind="value: type">
            <g:each in="${PageType.values()}" var="t">
                <option value="${t.name}">${t.name}</option>
            </g:each>
        </select>
    </mk:formLine>

</mk:tmpl>

<mk:tmpl id="tag">
    <span class="label">{{= displayName}} <a href="#" data-bind="click:remove">&times;</a></span>&nbsp;
</mk:tmpl>
<g:render template="/jquery-tmpl/editUnit"/>
<r:require module="autocomplete"/>
