package mirari

import org.apache.log4j.Logger
import mirari.morphia.site.Profile

class SiteTagLib {
    static namespace = "site"

    static final Logger log = Logger.getLogger(this)

    def securityService
    def siteLinkService

    def link = {attrs, body ->
        def s = attrs.for
        if (!s) s = request.site
        if (!s) {
            log.error "Cannot get space link for unknown space"
            return
        }

        out << g.link(url: url(attrs), body ? (body() ?: s.toString()) : s.toString())
    }

    def url = {attrs ->
        attrs.for
        def s = attrs.remove("for")

        out << siteLinkService.getUrl(s, attrs)
    }

    def profileLink = {attrs, body ->
        if (!attrs.for) attrs.for = securityService.profile
        if (!attrs.for instanceof Profile) {
            log.error "Cannot get person link for unknown person"
            return
        }
        out << this.link(attrs, body)
    }
}
