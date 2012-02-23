package mirari

import mirari.model.Site
import mirari.model.page.PageType
import mirari.model.site.PageFeed
import mirari.repo.PageFeedRepo

class PageTypeTagLib {
    static namespace = "pageType"

    def securityService
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

        out << render(template: "/includes/addPageDropdown", model: [portalTypes: portalTypes, profileTypes: profileTypes, restTypes: restTypes, forSite: forSite])
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

        List pfs = []
        
        for (PageFeed pageFeed: pageFeeds) {
            if (pageFeed.page) {
                if (!rightsService.canView(pageFeed.page)) continue
            }
            pfs.add(
                    active: pageFeed.type == active,
                    title: pageFeed.title ?: message(code: "pageType.s." + pageFeed.type.name),
                    page: pageFeed.page 
                    )
        }

        out << render(template: "/includes/pills", model: [forSite: forSite, typedPageFeeds: pfs])
    }
}
