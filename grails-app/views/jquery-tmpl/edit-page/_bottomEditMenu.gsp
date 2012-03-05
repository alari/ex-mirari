<mk:tmpl id="bottomEditMenu">


    <div class="progress"><div class="bar"></div></div>

    <div class="add-more-block">
        <ul class="nav nav-tabs">
            <li>
                <a href="#" data-bind="click: innersAct.addRenderInnersUnit, clickScroll:'#editBottom'">
                    <i class="icon-plus"></i>
                    ${message(code:"bottomEditMenu.more.renderBlock.title")}
                    <i class="icon-info-sign" data-bind="popover: {placement:'top'}"
                       title="${message(code:"bottomEditMenu.more.renderBlock.info.title").encodeAsHTML()}"
                       data-content="${message(code:"bottomEditMenu.more.renderBlock.info.content").encodeAsHTML()}"></i>
                </a>
            </li>
            <li>
                <a href="#" data-bind="click: innersAct.addFeedUnit, clickScroll:'#editBottom'">
                    <i class="icon-plus"></i>
                    ${message(code:"bottomEditMenu.more.feed.title")}
                    <i class="icon-info-sign" data-bind="popover: {placement:'top'}"
                       title="${message(code:"bottomEditMenu.more.feed.info.title").encodeAsHTML()}"
                       data-content="${message(code:"bottomEditMenu.more.feed.info.content").encodeAsHTML()}"></i>
                </a>

            </li>
        </ul>
    </div>


    <div class="tags-block">
        <span data-bind="template: { name: 'tag', foreach: tags }"></span>
        <input type="text" id="tags-input" style="border: 0;"
               data-bind="event: {blur: tagAct.addNewTag, keypress: tagAct.tagInputKey}, autocomplete: '/s/tagsAutocomplete'"
               placeholder="${message(code:"bottomEditMenu.tags.title").encodeAsHTML()}"/>
        <i class="icon-info-sign" data-bind="popover: {placement:'top'}"
           title="${message(code:"bottomEditMenu.tags.info.title").encodeAsHTML()}"
           data-content="${message(code:"bottomEditMenu.tags.info.content").encodeAsHTML()}"></i>
    </div>

    <div>

        <span class="btn btn-mini">
            <table>
                <tr>
                    <td>
                        <div class="page-file-upload" data-bind="pageFileUpload: true, clickScroll:'#editBottom'">
                            <form method="post" enctype="multipart/form-data">
                                <i class="icon-plus"></i>
                                ${message(code:"bottomEditMenu.file.title")}
                                <i class="icon-upload"></i>
                                <input type="file" name="unitFile" multiple/>
                            </form>
                        </div>
                    </td>
                    <td>
                        <i class="icon-info-sign" data-bind="popover: {placement:'top'}"
                           title="${message(code:"bottomEditMenu.file.info.title").encodeAsHTML()}"
                           data-content="${message(code:"bottomEditMenu.file.info.content").encodeAsHTML()}"></i>
                    </td>
                </tr>
            </table>
        </span>



        <span class="btn btn-mini" data-bind="click: innersAct.addTextUnit, clickScroll:'#editBottom'">
            <i class="icon-plus"></i>
            ${message(code:"bottomEditMenu.text.title")}
            <i class="icon-font"></i>
            <i class="icon-info-sign" data-bind="popover: {placement:'top'}"
               title="${message(code:"bottomEditMenu.text.info.title").encodeAsHTML()}"
               data-content="${message(code:"bottomEditMenu.text.info.content").encodeAsHTML()}"></i>
        </span>


        <span class="btn btn-mini" data-bind="click: innersAct.addExternalUnit">
            <i class="icon-plus"></i>
            ${message(code:"bottomEditMenu.external.title")}
            <i class="icon-info-sign" data-bind="popover: {placement:'top'}"
               title="${message(code:"bottomEditMenu.external.info.title").encodeAsHTML()}"
               data-content="${message(code:"bottomEditMenu.external.info.content").encodeAsHTML()}"></i>
        </span>


        <span class="btn btn-mini" data-bind="click: function(){$('.add-more-block').toggle()}">
            <i class="icon-plus"></i>
            ${message(code:"bottomEditMenu.more.title")}
        </span>


        <span class="btn btn-mini" data-bind="click: function(){$('.tags-block').toggle()}">
            <i class="icon-tags"></i>
            <span data-bind="text: tags().length"></span>
        </span>

        <span class="pull-right">
            <span class="btn btn-mini" data-bind="click: editAct.saveAndContinue">
                ${message(code:"bottomEditMenu.action.save")}
            </span>
            <span class="btn btn-mini" data-bind="click: editAct.submitDraft">
                ${message(code:"bottomEditMenu.action.draft")}
            </span>
            <span class="btn btn-mini btn-success" data-bind="click: editAct.submitPub">
                ${message(code:"bottomEditMenu.action.pub")}
            </span>
        </span>

    </div>
</mk:tmpl>

<mk:tmpl id="tag">
    <span class="label"><i class="icon-tag"></i><span data-bind="text: displayName"></span> <a href="#" data-bind="click:remove">&times;</a>
    </span>&nbsp;
</mk:tmpl>

<r:require modules="ko_popover,ko_autocomplete,ko_clickScroll"/>