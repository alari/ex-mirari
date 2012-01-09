package mirari

import mirari.model.Site
import mirari.model.face.RightsControllable
import mirari.model.site.Profile
import org.apache.log4j.Logger

class RightsService {

    static transactional = false
    static final Logger log = Logger.getLogger(RightsService)

    def securityService

    boolean canEdit(RightsControllable unit) {
        if(unit.owner instanceof Profile) {
            return securityService.account == ((Profile)unit.owner).account
        }
        false
    }

    boolean canView(RightsControllable unit) {
        if (!unit.draft) return true
        if(unit.owner instanceof Profile) {
            return securityService.account == ((Profile)unit.owner).account
        }
        false
    }

    boolean canDelete(RightsControllable unit) {
        if(unit.owner instanceof Profile) {
            return securityService.account == ((Profile)unit.owner).account
        }
        false
    }

    boolean canAdd() {
        true
    }

    boolean canAdmin(Site site) {
        if(site instanceof Profile) {
            return securityService.account == ((Profile)site).account
        }
        false
    }
}
