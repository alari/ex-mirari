<%--
  By alari
  Since 1/15/12 11:25 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="unitEdit">
    <div class="unit unit-edit" data-bind="sortableItem: $data">
        <span class="sort float-left-box">: :</span>
        <span class="unit-delete float-right-box" data-bind="click: remove">&times;</span>

        <div class="unit-body" data-bind="template: {name: pageEditVM.unitTmpl, item: $data}"></div>

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
</mk:tmpl>

<g:render template="/jquery-tmpl/editSound"/>
<g:render template="/jquery-tmpl/editImage"/>
<g:render template="/jquery-tmpl/editHtml"/>
<g:render template="/jquery-tmpl/editExternal"/>
<g:render template="/jquery-tmpl/editText"/>