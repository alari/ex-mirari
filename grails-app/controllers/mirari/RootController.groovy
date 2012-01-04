package mirari

import mirari.model.Page
import mirari.model.Site
import mirari.repo.PageRepo
import ru.mirari.infra.feed.FeedQuery

class RootController extends UtilController {

    PageRepo pageRepo

    def index() {
        // TODO: fix it!!!!!
        if (request._site) {
            String pageNum = params.pageNum ?: "-0-"
            int pg = Integer.parseInt(pageNum.substring(1, pageNum.size()-1))

            Site site = request._site

            FeedQuery<Page> feed = pageRepo.feed(site, _profile?.id == site.id).paginate(pg)

            render view: "/site/index", model: [
                    feed: feed
            ]
        } else {
            [allPages: pageRepo.list(100)]
        }
    }
}
