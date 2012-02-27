package mirari

import mirari.model.Page
import mirari.model.Site
import mirari.model.unit.content.internal.FeedContentStrategy
import mirari.repo.SiteRepo
import mirari.vm.UnitVM
import ru.mirari.infra.feed.FeedQuery
import mirari.model.page.PageType

class UnitTagLib {
    static namespace = "unit"

    SiteRepo siteRepo
    def rightsService

    FeedContentStrategy feedContentStrategy

    def renderPage = {attrs ->
        UnitVM u = (UnitVM) attrs.for
        boolean isOnly = attrs.containsKey("only") ? attrs.only : true
        if (u != null)
            out << g.render(template: "/unit-render/page-".concat(u.type), model: [viewModel: u, only: isOnly])
    }

    def withFeedUnit = {attrs, body ->
        UnitVM u = (UnitVM) attrs.unit

        Site owner = siteRepo.getById u.owner.id

        FeedQuery<Page> feedQuery = feedContentStrategy.feed(u)
        FeedQuery<Page> drafts = null
        if (rightsService.canSeeDrafts(owner)) {
            drafts = feedContentStrategy.drafts(u)
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

        boolean showTypes = !u.params.source in PageType.values()*.name

        if (drafts != null) {
            out << render(template: "/siteFeed/drafts", model: [drafts: drafts, showTypes: showTypes])
        }

        Iterator<Page> feed = feedQuery.iterator()

        if (u.params.style in ["blog_grid", "full_grid"]) {
            Page first = feed.next()
            out << g.render(template: "/siteFeed/feed", model: [feed: [first], showTypes: showTypes])
        }

        if (u.params.style in ["blog_grid", "full_grid", "grid"]) {
            out << g.render(template: "/siteFeed/grid", model: [feed: feed, showTypes: showTypes])
        } else if (u.params.style in ["blog", "full"]) {
            out << g.render(template: "/siteFeed/feed", model: [feed: feed, showTypes: showTypes])
        }
    }

}
