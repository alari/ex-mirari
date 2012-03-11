<mk:tmpl id="reason_page_comment">
    <h6 data-bind="click: notice.toggle">
        <span data-bind="template:{name:'site_link',data:comment.owner}"></span>:
    Комментарий на страницу:
        <a data-bind="attr:{href:notice.page.url}, text: notice.page.title"></a>
    </h6>
    <div data-bind="visible: notice.visible, template: {name:'comment', data:comment}"></div>
</mk:tmpl>

<mk:tmpl id="reason_comment_reply">
    <h6 data-bind="click: notice.toggle">
        <span data-bind="template:{name:'site_link',data:reply.owner}"></span>:
    Ответ на комментарий на странице:
        <a data-bind="attr:{href:notice.page.url}, text: notice.page.title"></a>
    </h6>
    <div data-bind="visible: notice.visible, template: {name:'comment', data:comment}"></div>
</mk:tmpl>

<mk:tmpl id="reason_page_reply">
    <h6 data-bind="click: notice.toggle">
        <span data-bind="template:{name:'site_link',data:reply.owner}"></span>:
    Ответ на странице:
        <a data-bind="attr:{href:notice.page.url}, text: notice.page.title"></a>
    </h6>
    <div data-bind="visible: notice.visible, template: {name:'comment', data:comment}"></div>
</mk:tmpl>

<g:render template="/jquery-tmpl/comment"/>
<g:render template="/jquery-tmpl/site"/>