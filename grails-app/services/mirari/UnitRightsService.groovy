package mirari

import mirari.morphia.Unit
import org.apache.log4j.Logger

class UnitRightsService {

    static transactional = false
    static final Logger log = Logger.getLogger(UnitRightsService)

    def securityService

    boolean canEdit(Unit unit) {
        securityService.id == unit.space.id.toString()
    }

    boolean canView(Unit unit) {
        if(!unit.draft) return true
        securityService.id == unit.space.id.toString()
    }

    boolean canDelete(Unit unit) {
        securityService.id == unit.space.id.toString()
    }

    boolean canAdd() {
        true
    }
}
