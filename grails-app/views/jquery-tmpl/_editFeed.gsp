<%--
 * @author alari
 * @since 23.02.12
--%>


<%@ page import="mirari.model.page.PageType" contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_feed">
    <div class="unit-title">
        <input type="text" data-bind="value: title" maxlength="128"/>
    </div>


    <div class="row">
        <div class="span3 offset1">
            <h6>Источник страниц:</h6>

            <select data-bind="value: params.source, disable: params.locked" class="span2">
                <option value="all">Все страницы</option>
                <g:each in="${PageType.values()}" var="type">
                    <g:if test="${type != PageType.PAGE}">
                        <option value="${type.name}"><g:message code="pageType.s.${type.name}"/></option>
                    </g:if>
                </g:each>
                <option value="tag">Тег</option>
            </select>
        </div>

        <div class="span2">
            <h6>Количество:</h6>

            <select data-bind="value: params.num" class="span1">
                <g:each in="${0..20}" var="n">
                    <option value="${n}">${n}</option>
                </g:each>
            </select>
        </div>

        <div class="span3">
            <h6>Отображение:</h6>

            <select data-bind="value: params.style">
                <option value="grid">Анонсы</option>
                <option value="blog">Блог (1-й фрагмент полностью)</option>
                <option value="full">Страницы полностью</option>
                <option value="blog_grid">Последняя как блог, остальные анонсы</option>
                <option value="full_grid">Последняя полностью, остальные анонсы</option>
            </select>
        </div>
    </div>
</mk:tmpl>