<%--
 * @author alari
 * @since 12/2/11 2:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="row">
    <g:each in="${pages}" var="p">
        <span class="span4">
            <center>
            <g:link for="${p}">
            <r:img uri="/images/pagetype/feather.png"/></g:link>
                <br/>
        <g:link for="${p}">${p}</g:link>
        <br/>
            <g:link for="${p.head.owner}">${p.head.owner}</g:link>
            </center>
        </span>

    </g:each>
</div>