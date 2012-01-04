package mirari.model.face

import mirari.model.Avatar

/**
 * @author alari
 * @since 12/13/11 5:10 PM
 */
interface AvatarHolder {
    Avatar getAvatar()

    void setAvatar(Avatar o)
}