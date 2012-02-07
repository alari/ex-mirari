package mirari.model.site

import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.annotations.Reference
import mirari.model.Account
import mirari.model.Avatar
import mirari.model.Site
import mirari.model.face.AvatarHolder
import mirari.repo.AvatarRepo
import mirari.util.ApplicationContextHolder
import com.google.code.morphia.annotations.Transient

/**
 * @author alari
 * @since 1/28/12 2:15 PM
 */
@Embedded
class SiteHead implements AvatarHolder {
    @Indexed
    @Reference(lazy = true)
    Account account

    @Indexed
    @Reference(lazy = true)
    Site portal

    @Reference(lazy = true) Avatar avatar

    Avatar getAvatar() {
        if(avatar) return avatar
        // TODO: clean it up!
        ((AvatarRepo)ApplicationContextHolder.getBean("avatarRepo")).getBasic(site?.type?.name)
    }

    @Transient
    transient private Site site
    void setSite(Site s) {
        if(site == null) {
            site = s
        }
    }

    String feedBurnerName

    Date dateCreated = new Date()
    Date lastUpdated

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }
}
