<%--
 * @author alari
 * @since 11/22/11 9:25 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="editText">

    <div>
        <div data-bind="visible: titleVisible">
        <input type="text" data-bind="value: title" placeholder="Заголовок текста"/>
        </div>
        <div data-bind="text: text, aloha: true" class="unit-text"></div>
    </div>
</mk:tmpl>
<style type="text/css">
    .unit-text{
    min-height: 100px; cursor: text;
        text-align: left;
        border: 1px dashed gray
    }
</style>