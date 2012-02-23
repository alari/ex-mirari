package mirari

import mirari.model.Page
import mirari.model.Site
import mirari.model.page.PageType
import mirari.repo.PageRepo
import mirari.repo.SiteRepo
import mirari.repo.UnitRepo
import mirari.vm.UnitVM
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import ru.mirari.infra.feed.FeedQuery
import mirari.repo.TagRepo
import mirari.model.Tag

class UnitTagLib {
    static namespace = "unit"

    UnitRepo unitRepo
    def imageStorageService
    SiteRepo siteRepo
    PageRepo pageRepo
    def rightsService
    TagRepo tagRepo

    LinkGenerator grailsLinkGenerator

    def renderPage = {attrs ->
        UnitVM u = (UnitVM) attrs.for
        boolean isOnly = attrs.containsKey("only") ? attrs.only : true
        if (u != null)
            out << g.render(template: "/unit-render/page-".concat(u.type), model: [viewModel: u, only: isOnly])
    }

    def withFeedUnit = {attrs, body ->
        UnitVM u = (UnitVM) attrs.unit
        if (!u) {
            return
        }
        Site owner = siteRepo.getById u.owner.id

        FeedQuery<Page> drafts = null

        FeedQuery<Page> feedQuery

        if (u.params.source == "all") {
            feedQuery = pageRepo.feed(owner)
            if (rightsService.canSeeDrafts(owner)) {
                drafts = pageRepo.drafts(owner)
            }
        } else if (u.params.source == "tag") {
            Tag tag = tagRepo.getById(u.params.feedId)
            if (!tag || tag.site != owner) {
                return;
            }
            feedQuery = pageRepo.feed(tag)
        } else {
            PageType type = PageType.getByName(u.params.source)
            feedQuery = pageRepo.feed(owner, type)
            if (rightsService.canSeeDrafts(owner)) {
                drafts = pageRepo.drafts(owner, type)
            }
        }

        int num = Integer.parseInt(u.params.num)
        if (u.params.style in ["blog_grid", "full_grid"]) {
            if (num == 0) num = 1
        }
        feedQuery.paginate(0, num)

        if (!feedQuery.total && (!drafts || !drafts.total)) {
            return;
        }

        out << body(feedParams: [feed: feedQuery, drafts: drafts, num: num])
    }

    def renderFeed = {attrs ->
        Map feedParams = attrs.feedParams
        if (!feedParams) return;
        UnitVM u = (UnitVM) attrs.unit
        if (!u) {
            return
        }
        FeedQuery<Page> drafts = feedParams.drafts

        FeedQuery<Page> feedQuery = feedParams.feed

        int num = feedParams.num

        if (drafts != null) {
            out << render(template: "/siteFeed/drafts", model: [drafts: drafts])
        }

        Iterator<Page> feed = feedQuery.iterator()

        if (u.params.style in ["blog_grid", "full_grid"]) {
            Page first = feed.next()
            out << g.render(template: "/siteFeed/feed", model: [feed: [first]])
        }

        if (u.params.style in ["blog_grid", "full_grid", "grid"]) {
            out << g.render(template: "/siteFeed/grid", model: [feed: feed])
        } else if (u.params.style in ["blog", "full"]) {
            out << g.render(template: "/siteFeed/feed", model: [feed: feed])
        }
    }

}
