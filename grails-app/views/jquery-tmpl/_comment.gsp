<%--
  By alari
  Since 2/2/12 2:52 PM
--%>

<r:require module="ko_autoResize"/>
<r:require module="vm_pageComments"/>

<mk:tmpl id="pageComments">
    <div data-bind="template: { name: 'comment', foreach: comments }"></div>

    <div data-bind="if: canPostComment">
        <form class="well">
            <div>
                <input type="text" class="span7" data-bind="value: newTitle" placeholder="Заголовок комментария (не обязательно)"/>
            </div>
            <div>
                <textarea class="span9" data-bind="autoResize: {maxHeight: 2000}, valueUpdate: 'afterkeydown', value: pageCommentsVM.newText"></textarea>
            </div>
            <mk:formActions>
                <button class="btn" data-bind="click: postComment">Сохранить комментарий</button>
            </mk:formActions>
        </form>
    </div>
</mk:tmpl>

<mk:tmpl id="comment">
    <div class="row">
        <div class="span2" style="text-align: center">
            <a data-bind="text: owner.displayName, attr: {href: owner.url}"></a>
            <br/>
            <img data-bind="attr: {src: owner.avatarFeed}"/>
        </div>
        <div class="span10">
            <div data-bind="visible: title"><h3 data-bind="text: title"></h3></div>
            <div data-bind="html: html"></div>
            <span class="pull-right" data-bind="text: dateCreated"></span>
        </div>
    </div>
    <!-- replies -->
    <div class="row">
        <div class="span10 offset2">
            <div data-bind="template: { name: 'reply', foreach: replies }"></div>
            <!-- Post reply -->
            <div class="row" data-bind="if: canPostReply">
                <div class="offset3 span5">
                    <textarea class="span5" data-bind="autoResize: {minHeight: 10, extraSpace: 5}, valueUpdate: 'afterkeydown', value: newText"></textarea>
                </div>
                <div class="span2">
                    <button class="btn" data-bind="click: postReply">Ответить</button>
                </div>
            </div>
        </div>
    </div>

    <hr/>
</mk:tmpl>

<mk:tmpl id="reply">
    <div class="row">
        <div class="span2" style="text-align: center">
            <a data-bind="text: owner.displayName, attr: {href: owner.url}"></a>
            <br/>
            <img data-bind="attr: {src: owner.avatarTiny}"/>
        </div>
        <div class="span8">
            <div data-bind="html: html"></div>
            <span class="pull-right" data-bind="text: dateCreated"></span>
        </div>
    </div>
</mk:tmpl>