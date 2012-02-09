@Typed package mirari.model

import mirari.event.EventType
import mirari.model.avatar.Avatar
import mirari.model.avatar.AvatarHolder
import mirari.model.avatar.AvatarHolderDomain
import mirari.model.avatar.DomainAvatarHolderBehaviour
import mirari.model.face.NamedThing
import mirari.model.site.SiteType
import mirari.util.link.LinkAttributesFitter
import mirari.util.link.LinkUtil
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.*

/**
 * @author alari
 * @since 10/27/11 8:06 PM
 */
@Entity("site")
class Site extends MorphiaDomain implements NamedThing, LinkAttributesFitter, AvatarHolderDomain {
    private transient boolean recomputeSiteName = false

    @Typed
    String getUrl(Map args = [:]) {
        args.put("for", this)
        LinkUtil.getUrl(args)
    }

    @Indexed
    @Reference(lazy = true)
    Account account

    @Indexed
    @Reference(lazy = true)
    Site portal

    // Avatar behaviour
    @Reference(lazy = true) Avatar _avatar
    @Transient
    @Delegate
    transient private AvatarHolder avatarBehaviour = new DomainAvatarHolderBehaviour(this, EventType.SITE_AVATAR_CHANGED)

    String getBasicAvatarName() {type.name}

    SiteType type

    @Indexed(unique = true)
    String name

    @Indexed(unique = true)
    String host

    @Indexed(unique = true)
    String displayName

    String toString() {
        "@" + (displayName ?: name)
    }

    void setName(String name) {
        this.name = name.toLowerCase()
        recomputeSiteName = true
    }

    void setType(SiteType type) {
        this.type = type
        recomputeSiteName = true
    }

    boolean isProfileSite() {
        type == SiteType.PROFILE
    }

    boolean isPortalSite() {
        type == SiteType.PORTAL
    }

    boolean isSubSite() {
        !isPortalSite()
    }

    String feedBurnerName

    Date dateCreated = new Date()
    Date lastUpdated

    @PrePersist
    void prePersist() {
        if (recomputeSiteName) {
            type.setSiteName(this)
            recomputeSiteName = false
        }
        lastUpdated = new Date();
    }

    @Override
    @Typed
    void fitLinkAttributes(Map attributes) {
        if (!attributes.controller) {
            attributes.controller = "siteFeed"
            attributes.action = "root"
        }
        attributes.base = "http://".concat(host)
    }
}
