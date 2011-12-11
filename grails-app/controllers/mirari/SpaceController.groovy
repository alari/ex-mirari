package mirari

import mirari.morphia.Page

class SpaceController extends SpaceUtilController {

    static defaultAction = "index"

    Page.Dao pageDao

    def index = {
        Iterable<Page> allPages = (currentPerson?.id == currentSpace.id) ? pageDao.listWithDrafts(currentSpace) :
            pageDao.list(currentSpace)
        [
                allPages: allPages
        ]
    }
}

