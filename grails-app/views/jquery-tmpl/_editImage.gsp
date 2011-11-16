<%--
 * @author alari
 * @since 11/14/11 3:28 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<script language="text/html" type="text/x-jquery-tmpl" id="unitEditImage">

 <div style="text-align: center">
        <img src="{{= params.srcPage}}"/>
        <br/>
    <input type="text" data-bind="value: title" placeholder="Заголовок / подпись к картинке (placeholder)"/>
    </div>
</script>

<script language="text/html" type="text/x-jquery-tmpl" id="unitTinyImageEdit">

 <div style="text-align: center;display:inline-block">
        <img src="{{= params.srcFeed}}"/>
        <br/>
    <input type="text" data-bind="value: title" placeholder="Заголовок / подпись к картинке (placeholder)"/>
    </div>
</script>