package mirari.site

import mirari.morphia.Page

class SiteController extends SiteUtilController {

    static defaultAction = "index"

    Page.Dao pageDao

    def index() {
        String pageNum = params.pageNum ?: "-0-"
        int pg = Integer.parseInt(pageNum.substring(1, pageNum.size()-1))

        Page.FeedQuery feed = pageDao.feed(currentSite, currentProfile?.id == currentSite.id).paginate(pg)

        [
                feed: feed
        ]
    }
}

