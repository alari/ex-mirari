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
        Site forSite = site.isPortalSite() ? profile : site

        if (!rightsService.canAdd(forSite)) {
            forSite = profile
        }

        portalTypes = pageFeedRepo.listDisplayBySite(site).collect {it.type}
        profileTypes = pageFeedRepo.listDisplayBySite(profile).collect {it.type}
        profileTypes -= portalTypes
        restTypes = PageType.baseValues() - profileTypes - portalTypes

        out << render(template: "/includes/addPageDropdown", model: [portalTypes: portalTypes, profileTypes: profileTypes, restTypes: restTypes, forSite: forSite])
    }

    def listPills = {attrs ->

        PageType active = attrs?.active ?: pageScope.type
        Site forSite = attrs.for ?: request._site

        def pageFeeds
        def secondaryFeeds = []

        // TODO: portal feeds drafted -> move to caret dropdown menu!
        boolean canAdminPortal = forSite.isPortalSite() && rightsService.canAdmin(forSite)
        if(canAdminPortal) {
            pageFeeds = []
            for (PageFeed pf in pageFeedRepo.listAllBySite(forSite)) {
                if (pf.forceDisplay) {
                    pageFeeds.add pf
                } else {
                    secondaryFeeds.add pf
                }
            }
        } else if (!forSite.isPortalSite() && rightsService.canSeeDrafts(forSite)) {
            pageFeeds = pageFeedRepo.listDraftsBySite(forSite)
        } else {
            pageFeeds = pageFeedRepo.listDisplayBySite(forSite)
        }

        List pfs = []
        List sfs = []
        
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
        for (PageFeed pageFeed: secondaryFeeds) {
            sfs.add(
                    active: pageFeed.type == active,
                    title: pageFeed.title ?: message(code: "pageType.s." + pageFeed.type.name),
                    page: pageFeed.page
            )
        }

        out << render(template: "/includes/pills", model: [forSite: forSite, typedPageFeeds: pfs, secondaryFeeds: sfs])
    }
}
