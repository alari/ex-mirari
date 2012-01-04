@Typed package mirari.model

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.annotations.Reference
import mirari.util.ApplicationContextHolder
import mirari.model.face.AvatarHolder
import mirari.model.face.NamedThing
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import ru.mirari.infra.mongo.Domain

/**
 * @author alari
 * @since 10/27/11 8:06 PM
 */
@Entity("site")
abstract class Site extends Domain implements NamedThing, AvatarHolder {

    static protected transient LinkGenerator grailsLinkGenerator

    static {
        grailsLinkGenerator = (LinkGenerator) ApplicationContextHolder.getBean("grailsLinkGenerator")
    }

    String getUrl(Map args = [:]) {
        args.put("for", this)
        grailsLinkGenerator.link(args)
    }

    @Reference Avatar avatar

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
    }
}
