@Typed package mirari.model

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.annotations.Reference
import mirari.model.face.AvatarHolder
import mirari.model.face.NamedThing
import mirari.util.ApplicationContextHolder
import mirari.util.LinkAttributesFitter
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import ru.mirari.infra.mongo.MorphiaDomain
import mirari.model.site.SiteType
import mirari.model.site.SiteHead
import com.google.code.morphia.annotations.Embedded

/**
 * @author alari
 * @since 10/27/11 8:06 PM
 */
@Entity("site")
class Site extends MorphiaDomain implements NamedThing, AvatarHolder, LinkAttributesFitter {

    static protected transient LinkGenerator grailsLinkGenerator

    static {
        grailsLinkGenerator = (LinkGenerator) ApplicationContextHolder.getBean("grailsLinkGenerator")
    }

    @Typed
    String getUrl(Map args = [:]) {
        args.put("for", this)
        grailsLinkGenerator.link(args)
    }

    SiteType type
    @Embedded SiteHead head = new SiteHead()
    
    @Reference(lazy = true) Avatar avatar

    @Indexed(unique = true)
    String name

    @Indexed(unique = true)
    String host

    @Indexed(unique = true)
    String displayName

    Date dateCreated = new Date()
    Date lastUpdated

    String feedBurnerName

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    String toString() {
        "@" + (displayName ?: name)
    }

    void setName(String name) {
        this.name = name.toLowerCase()
        type.setSiteName(this)
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

    @Override
    @Typed
    void fitLinkAttributes(Map attributes) {
        attributes.action = attributes.action ?: ""
        attributes.controller = attributes.controller ?: "site"
        attributes.base = "http://".concat(host)
    }
}
