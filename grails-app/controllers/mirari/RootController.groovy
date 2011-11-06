package mirari

import mirari.morphia.Unit

class RootController extends UtilController {

    Unit.Dao unitDao

    def index = {
        [allUnits: unitDao.getPublished(30)]
    }
}
