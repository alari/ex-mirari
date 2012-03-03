package mirari.vm

import mirari.model.avatar.Avatar

/**
 * @author alari
 * @since 2/16/12 3:36 PM
 */
class AvatarVM {
    String id
    String name
    boolean basic

    boolean getBasic() {
        basic
    }

    String srcFeed
    String srcLarge
    String srcThumb

    static public AvatarVM build(final Avatar avatar) {
        new AvatarVM(avatar)
    }

    private AvatarVM(final Avatar avatar) {
        id = avatar.stringId
        name = avatar.name
        basic = avatar.basic
        srcThumb = avatar.srcThumb
        srcFeed = avatar.srcFeed
        srcLarge = avatar.srcLarge
    }

    AvatarVM(){}
}
