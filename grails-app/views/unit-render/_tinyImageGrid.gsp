<%--
 * @author alari
 * @since 11/21/11 8:17 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<r:require module="twitterTwipsy"/>
<ul class="media-grid">
    <g:each in="${units}" var="u">
        <li><unit:link for="${u}" data-placement="below" rel='twipsy'
                       title='${(u.title ? "«"+u?.title?.encodeAsHTML()+"» - " : "")+u.site.toString().encodeAsHTML()}'>
            <unit:tinyImage for="${u}"/></unit:link></li>
    </g:each>
</ul>
<script type="text/javascript">
    $(function () {
        $("a[rel=twipsy]").twipsy({
            live: true
        })
    })
</script>