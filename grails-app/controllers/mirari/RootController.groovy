package mirari

import mirari.morphia.Page
import mirari.morphia.Site

class RootController extends UtilController {

    Page.Dao pageDao

    def index() {
        // TODO: fix it!!!!!
        if (request._site) {
            String pageNum = params.pageNum ?: "-0-"
            int pg = Integer.parseInt(pageNum.substring(1, pageNum.size()-1))

            Site site = request._site

            Page.FeedQuery feed = pageDao.feed(site, _profile?.id == site.id).paginate(pg)

            render view: "/site/index", model: [
                    feed: feed
            ]
        } else {
            [allPages: pageDao.list(100)]
        }
    }
}
