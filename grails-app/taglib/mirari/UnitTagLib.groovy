package mirari

import mirari.repo.UnitRepo
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import mirari.vm.UnitVM
import mirari.repo.SiteRepo
import mirari.repo.PageRepo
import mirari.model.Site
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Page
import mirari.model.page.PageType

class UnitTagLib {
    static namespace = "unit"

    UnitRepo unitRepo
    def imageStorageService
    SiteRepo siteRepo
    PageRepo pageRepo

    LinkGenerator grailsLinkGenerator

    def renderPage = {attrs->
        UnitVM u = (UnitVM)attrs.for
        boolean isOnly = attrs.containsKey("only") ? attrs.only : true
        if (u != null)
            out << g.render(template: "/unit-render/page-".concat(u.type), model: [viewModel: u, only: isOnly])
    }
    
    def renderFeed = {attrs->
        UnitVM u = (UnitVM)attrs.unit
        if (!u) {
            out << "no unit!"
            return
        }
        Site owner = siteRepo.getById u.owner.id

        FeedQuery<Page> feedQuery
        if (u.params.source == "all") {
            feedQuery = pageRepo.feed(owner)
        } else {
            PageType type = PageType.getByName(u.params.source)
            feedQuery = pageRepo.feed(owner, type)
        }
        int num = Integer.parseInt(u.params.num)
        if (u.params.style in ["blog_grid", "full_grid"]) {
            if (num == 0) num = 1
        }
        feedQuery.paginate(0, num)
        
        Iterator<Page> feed = feedQuery.iterator()

        if (u.params.style in ["blog_grid", "full_grid"]) {
            Page first = feed.next()
            out << g.render(template: "/siteFeed/feed", feed: [first])
        }

        if (u.params.style in ["blog_grid", "full_grid", "grid"]) {
            out << g.render(template: "/siteFeed/grid", model: [feed: feed])
        } else if (u.params.style in ["blog", "full"]) {
            out << g.render(template: "/siteFeed/feed", model: [feed: feed])
        }
    }

}
