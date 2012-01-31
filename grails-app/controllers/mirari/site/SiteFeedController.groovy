package mirari.site

import mirari.repo.PageRepo
import mirari.repo.TagRepo
import mirari.UtilController
import mirari.model.Tag
import mirari.model.Page
import ru.mirari.infra.feed.FeedQuery
import mirari.model.page.PageType

class SiteFeedController extends UtilController{

    PageRepo pageRepo
    TagRepo tagRepo
    def rightsService

    def root(String pageNum) {
        int pg = pageNum ? Integer.parseInt(pageNum.substring(1, pageNum.size()-1)) : 0

        FeedQuery<Page> feed = pageRepo.feed(_site).paginate(pg, _site.isPortalSite() ? 100 : 5)
        FeedQuery<Page> drafts = null
        if (rightsService.canSeeDrafts(_site)) {
            drafts = pageRepo.drafts(_site)
        }

        render view: (_site.isPortalSite() ? "/root/index" : "/siteFeed/root"), model: [
                feed: feed,
                drafts: drafts
        ]
    }

    def type(String type) {
        PageType pageType = PageType.getByName(type)
        FeedQuery<Page> feed = pageRepo.feed(_site, pageType)
        feed.paginate(0)

        FeedQuery<Page> drafts = null
        if (rightsService.canSeeDrafts(_site)) {
            drafts = pageRepo.drafts(_site, pageType)
        }

        [feed: feed, type: pageType, drafts: drafts]
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
