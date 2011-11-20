<%--
 * @author alari
 * @since 11/14/11 3:28 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<mk:tmpl id="unitEditImage">

 <div style="text-align: center">
        <img src="{{= params.srcPage}}"/>
        <br/>
    <input type="text" data-bind="value: title" placeholder="Заголовок / подпись к картинке (placeholder)"/>
    </div>
</mk:tmpl>

<mk:tmpl id="unitTinyImageEdit">

 <div style="text-align: center;display:inline-block">
        <img src="{{= params.srcFeed}}"/>
        <br/>
    <input type="text" data-bind="value: title" placeholder="Заголовок / подпись к картинке (placeholder)"/>
    </div>
</mk:tmpl>