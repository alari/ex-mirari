package mirari.vm

import mirari.model.Page

/**
 * @author alari
 * @since 2/23/12 7:59 PM
 */
class PageAnnounceVM {
    String id

    String thumbSrc

    String title
    String type

    String url
    
    OwnerVM owner

    static PageAnnounceVM build(final Page page) {
        new PageAnnounceVM(page)
    }

    private PageAnnounceVM(final Page page) {
        id = page.stringId

        title = page.title
        type = page.type.name

        thumbSrc = page.thumbSrc
        
        owner = OwnerVM.build(page.owner)
        
        url = page.url
    }

    PageAnnounceVM(){}
}