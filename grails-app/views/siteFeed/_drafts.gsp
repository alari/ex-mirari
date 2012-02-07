<%--
  By alari
  Since 1/31/12 11:15 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:if test="${drafts && drafts.total}">

    <script type="text/javascript">
        var draftsShown, toggleDraftsShown;
        $(function(){
            draftsShown = ko.observable(false);
            toggleDraftsShown = function() {
                draftsShown(!draftsShown());
            }
        });
    </script>

<h6 data-bind="click: toggleDraftsShown" style="cursor: pointer">Черновики: ${drafts.total}</h6>
<div data-bind="if: draftsShown">
    <g:render template="/siteFeed/grid" model="[pages: drafts]"/>
</div>
<hr/>
</g:if>