<%--
 * @author alari
 * @since 11/22/11 9:28 PM
--%>

<%@ page import="mirari.model.page.PageType; mirari.model.page.PageType" contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="pageEdit">
    <div class="row">

        <mk:withSmallSidebar>
            <mk:content>

                <h1 data-bind="fixFloat: 0"><input class="page-title" type="text"
                                                   placeholder="${g.message(code: 'unit.add.titlePlaceholder')}"
                                                   name="title" data-bind="value: title" maxlength="128"/></h1>

                <div style="height: 40px"></div>

                <div data-bind="template: { name: 'unitEdit', foreach: inners }, sortableInners: $data"
                     class="unit-content sortable"></div>

                <div class="edit-empty" data-bind="visible: !innersCount()">
                    <h6>Добавьте картинки, тексты с помощью штуки, расположенной снизу</h6>
                </div>

                <div data-bind="fixFloatBottom: true, template: 'bottomEditMenu'" class="page-bottom-edit-menu"></div>

                <div style="height: 40px"></div>

            </mk:content>
            <mk:sidebar>
                <div>
                    <div class="edit-float-menu" data-bind="fixFloat: 60, template: 'fixFloatMenu'">
                    </div>
                </div>
            </mk:sidebar>
        </mk:withSmallSidebar>


        <div class="span4">

        </div>

    </div>
</mk:tmpl>


<mk:tmpl id="bottomEditMenu">
    <div>

        <div>
            <ul class="nav">
                <li>
                    <a href="#" data-bind="click: innersAct.addRenderInnersUnit">Добавить блок-оформление</a>
                </li>
                <li data-bind="visible: type == 'page'">
                    <a href="#" data-bind="click: innersAct.addFeedUnit">Добавить поток</a>
                </li>
            </ul>
        </div>

        <div>
            <span data-bind="template: { name: 'tag', foreach: tags }"></span>
            <input type="text" id="tags-input" style="border: 0;"
                   data-bind="event: {blur: tagAct.addNewTag, keypress: tagAct.tagInputKey}, autocomplete: '/s/tagsAutocomplete'"
                   placeholder="Добавить тег"/>
        </div>

        <div class="progress"><div class="bar"></div></div>

        <span class="btn btn-mini">
            <table>
                <tr>
                    <td>
                        <div class="page-file-upload" data-bind="pageFileUpload: true">
                            <form method="post" enctype="multipart/form-data">
                                <i class="icon-plus"></i>
                                Файл
                                <i class="icon-upload"></i>
                                <input type="file" name="unitFile" multiple/>
                            </form>
                        </div>
                    </td>
                    <td>
                        <i class="icon-info-sign" data-bind="popover: {placement:'top'}" title="Добавьте текст"></i>
                    </td>
                </tr>
            </table>
        </span>



        <span class="btn btn-mini" data-bind="click: innersAct.addTextUnit">
            <i class="icon-plus"></i>
            Текст
            <i class="icon-font"></i>
            <i class="icon-info-sign" data-bind="popover: {placement:'top'}" title="Добавьте текст"></i>
        </span>


        <span class="btn btn-mini" data-bind="click: innersAct.addExternalUnit">
            <i class="icon-plus"></i>
            По ссылке
            <i class="icon-info-sign" data-bind="popover: {placement:'top'}" title="Добавьте текст"></i>
        </span>


        <span class="btn btn-mini">
            <i class="icon-plus"></i>
            Ещё
        </span>


        <span class="btn btn-mini">
            <i class="icon-tags"></i>
        </span>

        <span class="pull-right">
            <span class="btn btn-mini" data-bind="click: editAct.saveAndContinue">
                Сохранить
            </span>
            <span class="btn btn-mini" data-bind="click: editAct.submitDraft">
                В черновики
            </span>
            <span class="btn btn-mini btn-success" data-bind="click: editAct.submitPub">
                Опубликовать
            </span>
        </span>

    </div>
</mk:tmpl>

<mk:tmpl id="tag">
    <span class="label"><span data-bind="text: displayName"></span> <a href="#" data-bind="click:remove">&times;</a>
    </span>&nbsp;
</mk:tmpl>


<mk:tmpl id="fixFloatMenu">
    <div data-bind="avatarUpload: {url: 'uploadAvatar', size: 'Thumb', enabled: id}">
        <img data-bind="attr: {src: thumbSrc}"/>
        <input type="file" name="avatar"/>
    </div>

</mk:tmpl>

<g:render template="/jquery-tmpl/editUnit"/>
<r:require modules="ko_autocomplete,ko_fixFloat,ko_avatarUpload,ko_popover"/>
