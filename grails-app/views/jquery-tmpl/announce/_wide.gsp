<mk:tmpl id="page_wide">
    <div class="page-announce">
        <span class="pull-left">
            <a data-bind="attr:{href:url}"><img data-bind="attr:{src:image.thumbSrc}"/></a>
        </span>

        <div>
            <div data-bind="if:title">
                <h3><a data-bind="attr:{href:url}, text:title"></a></h3>
            </div>

            <i class="page-announce-owner" data-bind="template:{name:'site_link', data:owner}"></i>

            <small class="page-announce-type" data-bind="text:typeString"></small>
        </div>
    </div>
</mk:tmpl>
<r:require module="css_announcesGrid"/>

<g:render template="/jquery-tmpl/site"/>