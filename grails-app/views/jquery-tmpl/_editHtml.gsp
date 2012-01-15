<%--
 * @author alari
 * @since 11/22/11 9:25 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="edit_html">
    <div class="unit-head">
        <input type="text" data-bind="value: title" placeholder="Заголовок текста"/>
    </div>

    <div data-bind="html: params.text, aloha: true" class="unit-text"></div>
</mk:tmpl>

<r:require module="aloha"/>