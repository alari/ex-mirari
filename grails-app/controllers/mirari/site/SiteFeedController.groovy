package mirari.site

import mirari.UtilController
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

    private final int countGrid = 24
    private final int countFeed = 10
    private final int countPortal = 48

    def root(String pageNum) {
        Page page = _site.index
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canView(page))) return;

        render view: "/sitePage/page", model: [page: page]
        return
    }

    def type(String type, int page) {
        PageType pageType = PageType.getByName(type)
        FeedQuery<Page> feed = pageRepo.feed(_site, pageType)
        feed.paginate(page ?: 0, pageType.renderAsGrid() ? countGrid : countFeed)

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
        FeedQuery<Page> feed = pageRepo.feed(tag).paginate(page, countGrid)
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
