package mirari

import mirari.model.Site
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import mirari.model.page.PageType
import mirari.repo.PageFeedRepo
import mirari.model.site.PageFeed

class SiteTagLib {
    static namespace = "site"

    static final Logger log = Logger.getLogger(this)

    def securityService
    def siteService
    def rightsService
    
    PageFeedRepo pageFeedRepo

    LinkGenerator grailsLinkGenerator

    def hostAuthJs = {attrs->
        if (request._site == siteService.mainPortal) return;
        out << /<script type="text\/javascript" src="/
        out << siteService.mainPortal.getUrl(controller:"hostAuth", action:"js", id: securityService.id)
        out << /"><\/script>/
    }

    def url = {attrs ->
        out << grailsLinkGenerator.link(attrs)
    }

    def profileLink = {attrs, body ->
        if (!attrs.for) attrs.for = securityService.profile
        if (!attrs.for instanceof Site || !((Site)attrs.for).isProfileSite()) {
            log.error "Cannot get person link for unknown person"
            return
        }
        out << g.link(attrs, body() ?: attrs.for.toString())
    }

    def profileId = {attrs->
        out << securityService.profile?.stringId
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

    def addPage = {attrs->
        
        out << /<a href="#" class="dropdown-toggle" data-toggle="dropdown">Добавить<b class="caret"><\/b><\/a>/
        out << /<ul class="dropdown-menu">/
        for (PageType pageType : PageType.values()) {
            out << /<li>/
            out << g.link(for: attrs.for ?: securityService.profile, controller: "sitePageStatic", action: "add", params: [type: pageType.name], message(code: "pageType."+pageType.name))
            out << /<\/li>/
        }
        out << /<\/ul>/
    }

    def typeListPills = {attrs->

        PageType active = attrs.active
        Site forSite = attrs.for ?: request._site
        
        Iterable<PageFeed> pageFeeds
        if (rightsService.canSeeDrafts(forSite)) {
            pageFeeds = pageFeedRepo.listDraftsBySite(forSite)
        } else {
            pageFeeds = pageFeedRepo.listDisplayBySite(forSite)
        }
        
        out << /<ul class="nav nav-pills">/
        for (PageFeed pageFeed : pageFeeds) {
            out << /<li/ + (pageFeed.type == active ? / class="active"/ : "") + />/
            out << g.link(for: forSite, controller: "siteFeed", action: 'type', params: [type: pageFeed.type.name], pageFeed.title ?: message(code: "pageType."+pageFeed.type.name))
            out << /<\/li>/
        }
        out << /<\/ul>/
    }
}
