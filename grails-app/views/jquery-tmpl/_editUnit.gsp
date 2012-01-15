<%--
  By alari
  Since 1/15/12 11:25 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="unitEdit">
    <div class="unit unit-edit" data-bind="sortableItem: $data">
        <div class="unit-credits unit-head">
            <span class="unit-sort sort">: :</span>
            <span class="unit-delete" data-bind="click: remove">&times;</span>
        </div>

        <div class="unit-body" data-bind="template: {name: pageEditVM.unitTmpl, item: $data}"></div>

        <span data-bind="click: toggleInnersVisibility, visible: innersCount"><span
                data-bind="text: innersVisible() ? 'Спрятать' : 'Показать'"></span> вложенные (<span
                data-bind="text:innersCount"></span>)</span>

        <div class="unit-inners sortable"
             data-bind="template: { name: 'unitEdit', foreach: inners }, sortableInners: $data, visible: innersVisible">
        </div>

    </div>
</mk:tmpl>

<g:render template="/jquery-tmpl/editSound"/>
<g:render template="/jquery-tmpl/editImage"/>
<g:render template="/jquery-tmpl/editHtml"/>
<g:render template="/jquery-tmpl/editExternal"/>