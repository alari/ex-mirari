package mirari

import mirari.morphia.Unit
import org.apache.log4j.Logger
import mirari.morphia.RightsControllable

class RightsService {

    static transactional = false
    static final Logger log = Logger.getLogger(RightsService)

    def securityService

    boolean canEdit(RightsControllable unit) {
        securityService.id == unit.owner.id.toString()
    }

    boolean canView(RightsControllable unit) {
        if (!unit.draft) return true
        securityService.id == unit.owner.id.toString()
    }

    boolean canDelete(RightsControllable unit) {
        securityService.id == unit.owner.id.toString()
    }

    boolean canAdd() {
        true
    }
}
