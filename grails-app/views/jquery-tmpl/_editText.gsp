<%--
 * @author alari
 * @since 11/22/11 9:25 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="editText">

    <div style="text-align: center">
        <input type="text" data-bind="value: title" placeholder="Заголовок текста"/>
        <br/>
        <textarea data-bind="value: text"></textarea>
    </div>
</mk:tmpl>