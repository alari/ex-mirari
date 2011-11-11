<%--
 * @author Dmitry Kurinskiy
 * @since 22.10.11 18:21  
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:each in="${alerts}" var="alert">
    <div data-alert="alert" class="alert-message ${alert.level}">
        <a class="close" href="#">&times;</a>

        <p>${message(code: alert.code, args: alert.params)}</p></div>
</g:each>