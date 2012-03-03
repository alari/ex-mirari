<mk:tmpl id="pageAnnounce">
    <div class="span4">
        <div class="page-announce">
            <span class="pull-left">
                <a data-bind="attr:{href:url}"><img data-bind="attr:{src:thumbSrc}"/></a>
                <g:link for="${p}">
                    <img src="${p.thumbSrc}"/></g:link>
            </span>

            <div>
                <strong data-bind="if:title" class="page-announce-title">
                    <a data-bind="text: title, attr: {href: url}"></a>
                </strong>

                <i class="page-announce-owner">
                    <a data-bind="text: owner.displayName, attr: {href: owner.url}"></a>
                </i>

                <small class="page-announce-type" data-bind="text:typeString"></small>
            </div>
        </div>
    </div>
</mk:tmpl>

<r:require module="css_announcesGrid"/>