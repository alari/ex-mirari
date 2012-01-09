package mirari

import mirari.model.Site
import mirari.model.site.Profile
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class SiteTagLib {
    static namespace = "site"

    static final Logger log = Logger.getLogger(this)

    def securityService

    LinkGenerator grailsLinkGenerator

    def url = {attrs ->
        out << grailsLinkGenerator.link(attrs)
    }

    def profileLink = {attrs, body ->
        if (!attrs.for) attrs.for = securityService.profile
        if (!attrs.for instanceof Profile) {
            log.error "Cannot get person link for unknown person"
            return
        }
        out << g.link(attrs, body() ?: attrs.for.toString())
    }
    
    def feedUrl = {attrs->
        attrs.for
        Site s = attrs.remove("for")
        if (!s) s = request._site
        if (!s) {
            log.error "Cannot get space link for unknown space"
            return
        }
        
        if (s.feedBurnerName) {
            out << "http://feeds.feedburner.com/"+s.feedBurnerName.encodeAsURL()
        } else {
            out << g.createLink(controller: "feed", action: "site", id: s.stringId, absolute: true)
        }
    }
}
