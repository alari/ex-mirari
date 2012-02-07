package mirari.site

import mirari.UtilController
import mirari.event.EventMediator
import mirari.event.EventType
import mirari.model.Page
import mirari.model.Tag
import mirari.model.page.PageType
import mirari.repo.PageRepo
import mirari.repo.TagRepo
import ru.mirari.infra.feed.FeedQuery

class SiteFeedController extends UtilController {

    PageRepo pageRepo
    TagRepo tagRepo
    def rightsService

    def root(String pageNum) {
        EventMediator.instance.fire(EventType.TEST)
        int pg = pageNum ? Integer.parseInt(pageNum.substring(1, pageNum.size() - 1)) : 0

        FeedQuery<Page> feed = pageRepo.feed(_site).paginate(pg, _site.isPortalSite() ? 100 : 24)
        FeedQuery<Page> drafts = null
        if (rightsService.canSeeDrafts(_site)) {
            drafts = pageRepo.drafts(_site)
        }
        Iterable<Tag> tags = tagRepo.listBySite(_site)

        render view: (_site.isPortalSite() ? "/root/index" : "/siteFeed/root"), model: [
                feed: feed,
                drafts: drafts,
                tags: tags
        ]
    }

    def type(String type, int page) {
        PageType pageType = PageType.getByName(type)
        FeedQuery<Page> feed = pageRepo.feed(_site, pageType)
        feed.paginate(page ?: 0, pageType.renderAsGrid() ? 24 : 10)

        FeedQuery<Page> drafts = null
        if (rightsService.canSeeDrafts(_site)) {
            drafts = pageRepo.drafts(_site, pageType)
        }

        [feed: feed, type: pageType, drafts: drafts, asGrid: pageType.renderAsGrid()]
    }

    def tag(String id, int page) {
        Tag tag = tagRepo.getById(id)
        if (isNotFound(tag)) return;
        if (tag.site != _site) {
            redirect url: tag.site.getUrl(controller: controllerName, action: actionName, id: tag.stringId)
            return;
        }
        FeedQuery<Page> feed = pageRepo.feed(tag).paginate(page)
        FeedQuery<Page> drafts = null
        if (rightsService.canSeeDrafts(_site)) {
            drafts = pageRepo.drafts(tag)
        }


        [
                tag: tag,
                feed: feed,
                drafts: drafts
        ]
    }
}
