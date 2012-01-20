package mirari.site

import mirari.model.PageType
import mirari.repo.PageRepo
import mirari.UtilController
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Page

class SitePagesListController extends UtilController{

    static defaultAction = "index"
    PageRepo pageRepo

    def index(String type) {
        PageType pageType = PageType.getByName(type)
        FeedQuery<Page> feed = pageRepo.feed(_site, pageType)
        feed.paginate(0)
        [feed: feed, type: pageType]
    }
}
