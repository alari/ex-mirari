<%--
  By alari
  Since 2/3/12 4:48 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="unit">

    <!--params.renderStyle-->

    <div class="carousel slide" data-bind="carousel: true">
<div class="carousel-inner">
        <g:each in="${viewModel.inners}" var="u">
            <div class="item">
                <unit:renderPage for="${u}" only="1"/>
                <g:if test="${u.title}">
                    <div class="carousel-caption">
                        <h4>${u.title}</h4>
                    </div>
                </g:if>
            </div>
        </g:each>
    </div>
    </div>


    <div class="unit-credits">
        <a class="dateCreated" href="${viewModel.url}">${viewModel.lastUpdated}</a>
        <a href="${viewModel.owner.url}">${viewModel.owner.displayName}</a>
    </div>

</div>

<r:require module="ko_carousel"/>