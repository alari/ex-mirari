package mirari

import mirari.morphia.Space
import mirari.morphia.space.subject.Person
import org.apache.log4j.Logger

class SpaceTagLib {
    static namespace = "space"

    static final Logger log = Logger.getLogger(this)

    def securityService
    def spaceLinkService

    def link = {attrs, body ->
        def s = attrs.for
        if (!s) s = request.space
        if (!s) {
            log.error "Cannot get space link for unknown space"
            return
        }

        out << g.link(url: url(attrs), body ? (body() ?: s.toString()) : s.toString())
    }

    def url = {attrs ->
        attrs.for
        def s = attrs.remove("for")

        out << spaceLinkService.getUrl(s, attrs)
    }

    def personLink = {attrs, body ->
        if (!attrs.for) attrs.for = securityService.person
        if (!attrs.for instanceof Person) {
            log.error "Cannot get person link for unknown person"
            return
        }
        out << this.link(attrs, body)
    }
}
