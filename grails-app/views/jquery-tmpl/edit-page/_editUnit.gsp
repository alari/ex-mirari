<%--
  By alari
  Since 1/15/12 11:25 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="unitEdit">
    <div class="unit unit-edit" data-bind="sortableItem: $data">
        <span class="sort float-left-box" data-bind="visible: canSort()"><i class="icon-resize-vertical"></i></span>

        <span class="unit-delete float-right-box" data-bind="click: remove, visible: canRemove()"><i class="icon-remove"></i></span>

        <div class="unit-body" data-bind="template: {name: pageEditVM.unitTmpl, item: $data}"></div>

        <div data-bind="if: canHoldInners()">
            <span data-bind="click: toggleInnersVisibility, visible: innersCount" class="inners-visibility"><span
                    data-bind="text: innersVisible() ? 'Спрятать' : 'Показать'"></span> вложенные (<span
                    data-bind="text:innersCount"></span>)</span>

            <div class="unit-inners">
                <div class="sortable"
                     data-bind="template: { name: 'unitEdit', foreach: inners }, sortableInners: $data, visible: innersVisible">
                </div>
                <span class="unit-subadd">
                    Добавить вложенный: <span data-bind="click: addTextUnit">Текст</span>
                </span>
            </div>
        </div>

    </div>
</mk:tmpl>

<g:render template="/jquery-tmpl/edit-page/editSound"/>
<g:render template="/jquery-tmpl/edit-page/editImage"/>
<g:render template="/jquery-tmpl/edit-page/editHtml"/>
<g:render template="/jquery-tmpl/edit-page/editExternal"/>
<g:render template="/jquery-tmpl/edit-page/editText"/>
<g:render template="/jquery-tmpl/edit-page/editRenderInners"/>
<g:render template="/jquery-tmpl/edit-page/editFeed"/>
<g:render template="/jquery-tmpl/edit-page/editPageReference"/>