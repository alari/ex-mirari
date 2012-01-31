package mirari.model.site

import mirari.model.Account
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.Reference
import mirari.model.Site
import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.annotations.PrePersist
import mirari.model.face.AvatarHolder
import mirari.model.Avatar

/**
 * @author alari
 * @since 1/28/12 2:15 PM
 */
@Embedded
class SiteHead implements AvatarHolder{
    @Indexed
    @Reference(lazy=true)
    Account account

    @Indexed
    @Reference(lazy=true)
    Site portal

    @Reference(lazy = true) Avatar avatar

    String feedBurnerName

    Date dateCreated = new Date()
    Date lastUpdated

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }
}
