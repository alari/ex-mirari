package mirari

import mirari.model.Site
import mirari.repo.PageFeedRepo
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class SiteTagLib {
    static namespace = "site"

    static final Logger log = Logger.getLogger(this)

    def securityService
    def siteService
    def rightsService

    PageFeedRepo pageFeedRepo

    LinkGenerator grailsLinkGenerator

    def hostAuthJs = {attrs ->
        if (request._site == siteService.mainPortal) return;
        out << /<script type="text\/javascript" src="/
        out << siteService.mainPortal.getUrl(controller: "hostAuth", action: "js", id: securityService.id)
        out << /"><\/script>/
    }

    def url = {attrs ->
        out << grailsLinkGenerator.link(attrs)
    }

    def profileLink = {attrs, body ->
        if (!attrs.for) attrs.for = securityService.profile
        if (!attrs.for instanceof Site || !((Site) attrs.for).isProfileSite()) {
            log.error "Cannot get person link for unknown person"
            return
        }
        out << g.link(attrs, body() ?: "<i class=\"icon-user\"></i>"+attrs.for.displayName)
    }
    
    def link = {attrs->
        if (!attrs.for) attrs.for = securityService.profile
        if (!attrs.for instanceof Site) {
            out << "@Error"
            return;
        }
        Site s = attrs.for
        String inner = ""
        if (s.isProfileSite()) {
            inner = "<i class=\"icon-user\"></i>"
        } else if (s.isPortalSite()) {
            inner = "<i class=\"icon-leaf\"></i>"
        } else if (s.isSubSite()) {
            inner = "<i class=\"icon-asterisk\"></i>"
        }
        inner = inner.concat s.displayName
        out << g.link(attrs, inner)
    }
    
    def switchProfilesLi = {attrs ->
        if (!securityService.loggedIn) return;
        List<Site> rest = securityService.restProfiles
        if (!rest.size()) return;

        out << render(template: "/includes/switchProfileDropdown", model: [sites: rest])
    }

    def profileId = {attrs ->
        out << securityService.profile?.stringId
    }

    def atomFeedUrl = {attrs ->
        attrs.for
        Site s = attrs.remove("for")
        if (!s) s = request._site
        if (!s) {
            log.error "Cannot get space link for unknown space"
            return
        }

        if (s.feedBurnerName) {
            out << "http://feeds.feedburner.com/" + s.feedBurnerName.encodeAsURL()
        } else {
            out << g.createLink(controller: "feed", action: "site", id: s.stringId, absolute: true)
        }
    }
}
