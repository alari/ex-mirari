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

                <div class="page-bottom-spacer" id="editBottom"></div>

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

<mk:tmpl id="fixFloatMenu">
    <div data-bind="avatarUpload: {url: 'uploadAvatar', size: 'Thumb', enabled: id}" class="file-upload">
        <img data-bind="attr: {src: thumbSrc}"/>
        <input type="file" name="avatar"/>
    </div>
    <div style="text-align: right">
        <i class="icon-info-sign" data-bind="popover: {placement: 'left'}"
           title="Картинка страницы"
            data-content="Эта картинка (или Ваша аватарка) будет отображаться всюду, где это уместно. Чтобы заменить картинку сохранённой страницы, кликните на неё"></i>
    </div>

</mk:tmpl>

<g:render template="/jquery-tmpl/edit-page/bottomEditMenu"/>
<g:render template="/jquery-tmpl/edit-page/editUnit"/>
<r:require modules="ko_fixFloat,ko_avatarUpload"/>
