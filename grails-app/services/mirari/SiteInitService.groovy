package mirari

import mirari.repo.PageRepo
import mirari.repo.PageFeedRepo
import mirari.model.Site
import mirari.model.page.PageType
import mirari.model.site.PageFeed
import mirari.model.Page
import mirari.vm.UnitVM
import mirari.vm.PageVM
import mirari.repo.AvatarRepo
import mirari.util.I18n
import mirari.repo.SiteRepo
import mirari.model.unit.content.internal.FeedContentStrategy

class SiteInitService {

    static transactional = false
    
    PageRepo pageRepo
    PageFeedRepo pageFeedRepo
    AvatarRepo avatarRepo
    I18n i18n
    SiteRepo siteRepo

    void initSite(Site site) {
        createPageFeeds(site)
        createIndexPage(site)
        siteRepo.save(site)
    }
    
    private createIndexPage(Site site) {
        Page page = new Page()
        page.owner = site
        page.site = site
                
        PageVM pageVM = new PageVM()
        pageVM.draft = false
        pageVM.type = PageType.PAGE
        pageVM.inners = []

            UnitVM unitVM = new UnitVM()
            unitVM.type = "feed"
            unitVM.title = i18n.m("pageType.s.last")
            unitVM.params = [
                    locked: "",
                    source: "all",
                    num: "6",
                    style: FeedContentStrategy.STYLE_SMALL
            ]
            pageVM.inners.add(unitVM)
        
        page.viewModel = pageVM
        page.name = "index"
        page.title = site.displayName
        pageRepo.save page
        // then we have to save the page!
        site.index = page
    }
    
    private createPageFeeds(final Site site) {
        for(PageType type : PageType.baseValues()) {
            pageFeedRepo.save new PageFeed(
                    site: site,
                    type: type,
                    forceDisplay: site.isPortalSite(),
                    page: getTypePage(site, type)
            )
        }
    }

    private Page getTypePage(final Site site, final PageType type) {
        Page page = new Page()
        page.owner = site
        page.site = site
        page.viewModel = getTypePageVM(site, type)
        page.avatar = avatarRepo.getBasic(type.name)
        pageRepo.save(page)
        page
    }

    private UnitVM getTypeUnitVM(final Site site, PageType type) {
        UnitVM unitVM = new UnitVM()
        unitVM.type = "feed"
        unitVM.params = [
                source: type.name,
                locked: "1",
                style: type.defaultRenderStyle,
                num: type.defaultRenderNum
        ]
        unitVM
    }

    private PageVM getTypePageVM(final Site site, PageType type) {
        PageVM pageVM = new PageVM()
        pageVM.draft = false
        pageVM.type = PageType.PAGE.name
        pageVM.title = i18n.m("pageType.s.".concat(type.name))

        pageVM.inners = [getTypeUnitVM(site, type)]
        pageVM
    }
}
