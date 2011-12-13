package mirari

import mirari.morphia.Unit
import org.apache.log4j.Logger
import mirari.morphia.RightsControllable

class RightsService {

    static transactional = false
    static final Logger log = Logger.getLogger(RightsService)

    def securityService

    boolean canEdit(RightsControllable unit) {
        securityService.profile?.id == unit.owner.id
    }

    boolean canView(RightsControllable unit) {
        if (!unit.draft) return true
        securityService.profile?.id == unit.owner.id
    }

    boolean canDelete(RightsControllable unit) {
        securityService.profile?.id == unit.owner.id
    }

    boolean canAdd() {
        true
    }
}
