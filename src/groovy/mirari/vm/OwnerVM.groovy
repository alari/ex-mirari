package mirari.vm

import mirari.model.Site

/**
 * @author alari
 * @since 2/16/12 4:00 PM
 */
class OwnerVM {
    String id
    String displayName
    String url
    AvatarVM avatar

    static OwnerVM build(final Site site) {
        new OwnerVM(site)
    }

    private OwnerVM(final Site site) {
        id = site.stringId
        displayName = site.displayName
        url = site.url
        avatar = AvatarVM.build(site.avatar)
    }

    OwnerVM(){}
}
