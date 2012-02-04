@Typed package mirari.model

import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Indexed
import mirari.model.face.NamedThing
import mirari.model.site.SiteHead
import mirari.model.site.SiteType
import mirari.util.ApplicationContextHolder
import mirari.util.LinkAttributesFitter
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import ru.mirari.infra.mongo.MorphiaDomain

/**
 * @author alari
 * @since 10/27/11 8:06 PM
 */
@Entity("site")
class Site extends MorphiaDomain implements NamedThing, LinkAttributesFitter {

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
        type?.setSiteName(this)
    }

    void setType(SiteType type) {
        this.type = type
        this.type.setSiteName(this)
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
        if (!attributes.controller) {
            attributes.controller = "siteFeed"
            attributes.action = "root"
        }
        attributes.base = "http://".concat(host)
    }
}
