<%--
  By alari
  Since 2/2/12 2:52 PM
--%>

<r:require module="ko_autoResize"/>
<r:require module="vm_pageComments"/>

<mk:tmpl id="pageComments">
    <div data-bind="template: { name: 'comment', foreach: comments }"></div>

    <div data-bind="visible: showAddForm">
        <form class="well">
            <div>
                <input type="text" class="span7" data-bind="value: newTitle" placeholder="Заголовок комментария (не обязательно)"/>
            </div>
            <div>
                <textarea class="span9" data-bind="value: newText, autoResize: true, valueUpdate: 'afterkeydown'"></textarea>
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
        <div class="span9 offset3">
            <div data-bind="template: { name: 'reply', foreach: replies }"></div>
            <form class="form-inline">
                <div>
                    <textarea class="span6" data-bind="value: newText, autoResize: true, valueUpdate: 'afterkeydown'"></textarea>
                </div>
                <mk:formActions>
                    <button class="btn" data-bind="click: postReply">Ответить</button>
                </mk:formActions>
            </form>
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
        <div class="span7">
            <div data-bind="html: html"></div>
            <span class="pull-right" data-bind="text: dateCreated"></span>
        </div>
    </div>
</mk:tmpl>