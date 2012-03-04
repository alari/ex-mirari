package mirari

import mirari.model.Page
import mirari.model.Site
import mirari.model.page.PageType
import mirari.model.unit.content.internal.FeedContentStrategy
import mirari.model.unit.content.internal.PageReferenceContentStrategy
import mirari.repo.SiteRepo
import mirari.vm.UnitVM
import ru.mirari.infra.feed.FeedQuery

class UnitTagLib {
    static namespace = "unit"

    SiteRepo siteRepo
    def rightsService

    FeedContentStrategy feedContentStrategy
    PageReferenceContentStrategy pageReferenceContentStrategy

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

        out << body(feedParams: [feed: feedQuery, drafts: drafts, num: num, owner: owner])
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
        Site notShowOwner = feedParams.owner

        if (drafts != null) {
            out << render(template: "/pages-feed/drafts", model: [drafts: drafts, showTypes: showTypes])
        }

        Iterator<Page> feed = feedQuery.iterator()

        if (u.params.last && u.params.last != FeedContentStrategy.STYLE_NONE) {
            Page first = feed.next()
            Map lastModel = [feed: [first], showTypes: showTypes, notShowOwner: notShowOwner]
            switch (u.params.last) {
                case FeedContentStrategy.STYLE_BLOG:
                    out << g.render(template: "/pages-feed/blog", model: lastModel)
                    break
                case FeedContentStrategy.STYLE_FULL:
                    out << g.render(template: "/pages-feed/full", model: lastModel)
                    break;
                case FeedContentStrategy.STYLE_WIDE:
                default:
                    out << g.render(template: "/pages-feed/announcesWide", model: lastModel)
                    break;
            }
        }

        Map feedModel = [feed: feed, showTypes: showTypes, notShowOwner: notShowOwner]

        switch (u.params.style) {
            case FeedContentStrategy.STYLE_BLOG:
                out << g.render(template: "/pages-feed/blog", model: feedModel)
                break
            case FeedContentStrategy.STYLE_FULL:
                out << g.render(template: "/pages-feed/full", model: feedModel)
                break;
            case FeedContentStrategy.STYLE_WIDE:
                out << g.render(template: "/pages-feed/announcesWide", model: feedModel)
                break;
            case FeedContentStrategy.STYLE_LINKS:
                out << g.render(template: "/pages-feed/links", model: feedModel)
                break;
            case FeedContentStrategy.STYLE_SMALL:
            default:
                out << g.render(template: "/pages-feed/announcesSmall", model: feedModel)
                break;
        }
    }

    def withPageReferenceUnit = {attrs, body ->
        UnitVM u = (UnitVM) attrs.unit
        Page page = pageReferenceContentStrategy.getPage(u)

        if (!page || !rightsService.canView(page)) {
            out << body()
            return
        }
        out << body(page: page)
    }

}
