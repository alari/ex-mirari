package mirari

import mirari.model.Site
import mirari.model.page.PageType
import mirari.model.site.PageFeed
import mirari.repo.PageFeedRepo

class PageTypeTagLib {
    static namespace = "pageType"

    def securityService
    def siteService
    def rightsService

    PageFeedRepo pageFeedRepo

    def pageDropdown = {attrs ->

        List<PageType> portalTypes = []
        List<PageType> profileTypes = []
        List<PageType> restTypes = []

        Site profile = securityService.profile
        Site site = attrs.for ?: request._site
        if (site == profile) site = profile.portal
        Site forSite = attrs.for ?: securityService.profile

        portalTypes = pageFeedRepo.listDisplayBySite(site).collect {it.type}
        profileTypes = pageFeedRepo.listDisplayBySite(profile).collect {it.type}
        profileTypes -= portalTypes
        restTypes = PageType.baseValues() - profileTypes - portalTypes

        out << /<a href="#" class="dropdown-toggle" data-toggle="dropdown">Добавить<b class="caret"><\/b><\/a>/
        out << /<ul class="dropdown-menu">/

        for (PageType type in portalTypes) {
            out << typeLink(for: forSite, type: type, li: true)
        }
        if (portalTypes.size() && profileTypes.size()) {
            out << /<li class="divider"><\/li>/
        }
        for (PageType type in profileTypes) {
            out << typeLink(for: forSite, type: type, li: true)
        }
        if (restTypes.size()) {
            out << /<li class="divider"><\/li>/
        }
        for (PageType type in restTypes) {
            out << typeLink(for: forSite, type: type, li: true)
        }

        out << /<\/ul>/
    }

    def typeLink = {attrs ->
        if (attrs.li) out << /<li>/
        out << g.link(for: attrs.for, controller: "sitePageStatic", action: "add", params: [type: attrs.type.name], message(code: "pageType." + attrs.type.name))
        if (attrs.li) out << /<\/li>/
    }

    def listPills = {attrs ->

        PageType active = attrs.active
        Site forSite = attrs.for ?: request._site

        Iterable<PageFeed> pageFeeds
        if (rightsService.canSeeDrafts(forSite)) {
            pageFeeds = pageFeedRepo.listDraftsBySite(forSite)
        } else {
            pageFeeds = pageFeedRepo.listDisplayBySite(forSite)
        }

        out << /<ul class="nav nav-pills">/

        out << /<li>/ + g.link(for: forSite, forSite.toString()) + /<\/li>/


        for (PageFeed pageFeed: pageFeeds) {
            if (pageFeed.page) {
                if (!rightsService.canView(pageFeed.page)) continue
            }

            out << /<li/ + (pageFeed.type == active ? / class="active"/ : "") + />/
            if (pageFeed.page) {
                out << g.link(for: pageFeed.page, pageFeed.title ?: message(code: "pageType.s." + pageFeed.type.name))
            } else {
                out << g.link(for: forSite, controller: "siteFeed", action: 'type', params: [type: pageFeed.type.name], pageFeed.title ?: message(code: "pageType.s." + pageFeed.type.name))
            }

            out << /<\/li>/
        }
        if (!attrs.hideAddLink && securityService.isLoggedIn()) {
            out << /<li class="dropdown">/
            out << pageDropdown([:])
            out << /<\/li>/
        }
        out << /<\/ul>/
    }
}
