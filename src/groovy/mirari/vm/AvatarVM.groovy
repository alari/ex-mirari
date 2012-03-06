package mirari.vm

import mirari.model.avatar.Avatar
import mirari.model.image.CommonImage

/**
 * @author alari
 * @since 2/16/12 3:36 PM
 */
class AvatarVM implements CommonImage{
    String id
    String name
    boolean basic

    boolean getBasic() {
        basic
    }

    static public AvatarVM build(final Avatar avatar) {
        new AvatarVM(avatar)
    }

    private AvatarVM(final Avatar image) {
        id = image.stringId
        name = image.name
        basic = image.basic
        thumbSrc = image.thumbSrc
        smallSrc = image.smallSrc
        mediumSrc = image.mediumSrc
        standardSrc = image.standardSrc
        maxSrc = image.maxSrc
    }

    AvatarVM(){}

    String thumbSrc
    String smallSrc
    String mediumSrc
    String standardSrc
    String maxSrc
}
