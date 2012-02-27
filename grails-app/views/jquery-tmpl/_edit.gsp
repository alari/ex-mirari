<%--
 * @author alari
 * @since 11/22/11 9:28 PM
--%>

<%@ page import="mirari.model.page.PageType; mirari.model.page.PageType" contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="pageEdit">
    <div class="unit-envelop">
        <h1><input class="page-title" type="text" placeholder="${g.message(code: 'unit.add.titlePlaceholder')}"
                   name="title" data-bind="value: title" maxlength="128"/></h1>

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

        <div class="unit-adder row">
            <div class="span4 unit-adder-drop" data-bind="pageFileUpload: true">
                <form method="post" enctype="multipart/form-data">
                    <g:message code="unit.add.drop"/>
                    <input type="file" name="unitFile" multiple/>
                </form>
            </div>

            <div class="span4">
                <ul>
                    <li>
                        <a href="#" data-bind="click: innersAct.addTextUnit">Добавить текстовый блок</a>
                    </li>
                    <li>
                        <a href="#" data-bind="click: innersAct.addExternalUnit">Добавить по ссылке</a>
                    </li>
                    <li>
                        <a href="#" data-bind="click: innersAct.addRenderInnersUnit">Добавить блок-оформление</a>
                    </li>
                    <li>
                        <a href="#" data-bind="click: innersAct.addFeedUnit">Добавить поток</a>
                    </li>
                </ul>
            </div>

            <div class="span2" data-bind="avatarUpload: {url: 'uploadAvatar', size: 'Thumb', enabled: id}">
                <img data-bind="attr: {src: thumbSrc}"/>
                <input type="file" name="avatar"/>
            </div>
        </div>

        <div class="ui-progressbar"></div>

        <div>
            <span data-bind="template: { name: 'tag', foreach: tags }"></span>
            <input type="text" id="tags-input" style="border: 0;"
                   data-bind="event: {blur: tagAct.addNewTag, keypress: tagAct.tagInputKey}, autocomplete: '/s/tagsAutocomplete'"
                   placeholder="Добавить тег"/>
        </div>

        <mk:formActions>
            <button class="btn btn-primary" data-bind="click: editAct.submitPub">
                <g:message code="unit.add.submit.publish"/></button>
            <button class="btn btn-info" data-bind="click: editAct.submitDraft">
                <g:message code="unit.add.submit.draft"/></button>
            <a class="btn" href="." data-bind="visible: id">
                Вернуться без изменений</a>
        </mk:formActions>



    </div>
</mk:tmpl>

<mk:tmpl id="tag">
    <span class="label"><span data-bind="text: displayName"></span> <a href="#" data-bind="click:remove">&times;</a>
    </span>&nbsp;
</mk:tmpl>


<mk:tmpl id="fixFloatMenu">
    <ul class="unstyled">
        <li><a href="#" data-bind="click: editAct.saveAndContinue">
            Сохранить и продолжить работу</a></li>
        <li>
            Вложенные: <a href="#" data-bind="click: innersAct.hideAllInners">-</a> <a href="#"
                                                                                       data-bind="click: innersAct.showAllInners">+</a>
        </li>
        <li>
            Содержимое: <a href="#" data-bind="click: innersAct.hideAllContent">-</a> <a href="#"
                                                                                         data-bind="click: innersAct.showAllContent">+</a>
        </li>
    </ul>

</mk:tmpl>

<g:render template="/jquery-tmpl/editUnit"/>
<r:require modules="ko_autocomplete,ko_fixFloat,ko_avatarUpload"/>
