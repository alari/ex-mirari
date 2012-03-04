package mirari.vm

import mirari.model.Page
import mirari.util.JsonUtil

/**
 * @author alari
 * @since 2/16/12 5:15 PM
 */
class PageVM extends InnersHolderVM {
    String id

    boolean draft

    boolean getDraft() {
        draft
    }

    AvatarVM avatar

    String thumbSrc
    
    String title
    String type

    List<TagVM> tags

    static PageVM build(final Page page) {
        new PageVM(page)
    }

    static PageVM build(final String string) {
        JsonUtil.stringToObj(string, this)
    }

    private PageVM(final Page page) {
        id = page.stringId
        draft = page.draft

        avatar = AvatarVM.build(page.avatar)

        title = page.title
        type = page.type.name

        tags = page.tags*.viewModel

        inners = page.inners*.viewModel
        
        thumbSrc = page.thumbSrc
    }

    PageVM(){}
}
