package mirari

import org.apache.log4j.Logger
import mirari.model.face.RightsControllable
import mirari.model.Site
import mirari.model.site.Profile

class RightsService {

    static transactional = false
    static final Logger log = Logger.getLogger(RightsService)

    def securityService

    boolean canEdit(RightsControllable unit) {
        if(unit.owner instanceof Profile) {
            return securityService.account?.id == ((Profile)unit.owner).account.id
        }
        false
    }

    boolean canView(RightsControllable unit) {
        if (!unit.draft) return true
        if(unit.owner instanceof Profile) {
            return securityService.account?.id == ((Profile)unit.owner).account.id
        }
        false
    }

    boolean canDelete(RightsControllable unit) {
        if(unit.owner instanceof Profile) {
            return securityService.account?.id == ((Profile)unit.owner).account.id
        }
        false
    }

    boolean canAdd() {
        true
    }

    boolean canAdmin(Site site) {
        if(site instanceof Profile) {
            return securityService.account?.id == ((Profile)site).account.id
        }
        false
    }
}
