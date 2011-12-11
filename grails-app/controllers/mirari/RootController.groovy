package mirari

import mirari.morphia.Page

class RootController extends UtilController {

    Page.Dao pageDao

    def index() {
        [allPages: pageDao.list(100)]
    }
}
