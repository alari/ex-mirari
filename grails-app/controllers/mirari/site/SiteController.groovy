package mirari.site

import mirari.morphia.Page

class SiteController extends SiteUtilController {

    static defaultAction = "index"

    Page.Dao pageDao

    def index = {
        Iterable<Page> allPages = (currentProfile?.id == currentSite.id) ? pageDao.listWithDrafts(currentSite) :
            pageDao.list(currentSite)
        [
                allPages: allPages
        ]
    }
}

