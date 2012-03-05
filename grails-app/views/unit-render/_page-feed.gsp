<%--
 * @author alari
 * @since 11/26/11 1:04 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<unit:withFeedUnit unit="${viewModel}">
    <div class="unit">
        <g:if test="${viewModel.title}">
            <g:if test="${viewModel.outerId}">
                <h3>${viewModel.title}</h3>
            </g:if>
            <g:else>
                <h2>${viewModel.title}</h2>
            </g:else>
        </g:if>

        <unit:renderFeed feedParams="${feedParams}" unit="${viewModel}"/>

        <div class="unit-credits">
            <a class="dateCreated" href="${viewModel.url}">${viewModel.lastUpdated}</a>
            <a href="${viewModel.owner.url}">${viewModel.owner.displayName}</a>
        </div>

    </div>
</unit:withFeedUnit>