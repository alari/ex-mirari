<%--
 * @author alari
 * @since 11/22/11 9:28 PM
--%>

<%@ page import="mirari.model.page.PageType; mirari.model.page.PageType" contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="pageEdit">
    <div class="unit-envelop">
        <h1><input class="page-title" type="text" placeholder="${g.message(code: 'unit.add.titlePlaceholder')}"
                   name="title" data-bind="value: title"/></h1>

        <mk:withSmallSidebar>
            <mk:content>
                <div data-bind="template: { name: 'unitEdit', foreach: inners }, sortableInners: $data"
                     class="unit-content sortable"></div>

                <div class="edit-empty" data-bind="visible: !innersCount()">
                    <h6>Добавьте картинки, тексты с помощью штуки, расположенной снизу</h6>
                </div>
            </mk:content>
            <mk:sidebar>
                <div>
                    <div class="edit-float-menu" data-bind="fixFloat: 60, template: 'fixFloatMenu'">
                    </div>
                </div>
            </mk:sidebar>
        </mk:withSmallSidebar>

        <div class="unit-adder row" data-bind="pageFileUpload: true">
            <div class="span4 unit-adder-drop">
                <form method="post" enctype="multipart/form-data"
                      action="/p/addFile">
                    <g:message code="unit.add.drop"/>
                    <input type="file" name="unitFile" multiple/>
                    <input type="hidden" name="ko" data-bind="value:toJSON()"/>
                </form>
            </div>

            <div class="span5">
                <ul>
                    <li>
                        <a href="#" data-bind="click: addTextUnit">Добавить текстовый блок</a>
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
            <button class="btn btn-primary" data-bind="click: submitPub">
                <g:message code="unit.add.submit.publish"/></button>
            <button class="btn btn-info" data-bind="click: submitDraft">
                <g:message code="unit.add.submit.draft"/></button>
            <a class="btn" href="." data-bind="visible: id">
                Вернуться без изменений</a>
        </mk:formActions>

        <div>
            <span data-bind="template: { name: 'tag', foreach: tags }"></span>
            <input type="text" id="tags-input" style="border: 0;"
                   data-bind="event: {blur: addNewTag, keypress: tagInputKey}, autocomplete: '<g:createLink
                           for="${_site}" action="tagsAutocomplete"/>'" placeholder="Добавить тег"/>
        </div>

        <mk:formLine field="type" label="Что это ">
            &nbsp;
            <select name="type" data-bind="value: type">
                <g:each in="${mirari.model.page.PageType.values()}" var="t">
                    <option value="${t.name}"><g:message code="pageType.${t.name}"/></option>
                </g:each>
            </select>
        </mk:formLine>

    </div>





</mk:tmpl>

<mk:tmpl id="tag">
    <span class="label"><span data-bind="text: displayName"></span> <a href="#" data-bind="click:remove">&times;</a></span>&nbsp;
</mk:tmpl>


<mk:tmpl id="fixFloatMenu">
    <ul class="unstyled">
    <li><a href="#" data-bind="click: saveAndContinue">
        Сохранить и продолжить работу</a></li>
        <li>
            Вложенные: <a href="#" data-bind="click: hideAllInners">-</a> <a href="#" data-bind="click: showAllInners">+</a>
        </li>
        <li>
            Содержимое: <a href="#" data-bind="click: hideAllContent">-</a> <a href="#" data-bind="click: showAllContent">+</a>
        </li>
    </ul>
    
</mk:tmpl>

<g:render template="/jquery-tmpl/editUnit"/>
<r:require modules="autocomplete,ko_fixFloat"/>
