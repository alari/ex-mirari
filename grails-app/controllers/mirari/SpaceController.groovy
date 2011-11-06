package mirari

import mirari.morphia.Unit

class SpaceController extends SpaceUtilController {

    static defaultAction = "index"

    Unit.Dao unitDao

    def index = {
        [
                allUnits: unitDao.getBySpace(currentSpace, currentSpace.id == currentPerson?.id)
        ]
    }
}

