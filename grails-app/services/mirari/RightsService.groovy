package mirari

import mirari.model.Site
import mirari.model.face.RightsControllable
import mirari.model.page.PageType
import org.apache.log4j.Logger

class RightsService {

    static transactional = false
    static final Logger log = Logger.getLogger(RightsService)

    def securityService

    boolean canEdit(RightsControllable unit) {
        if (unit.owner.isProfileSite()) {
            return securityService.account == unit.owner.head.account
        }
        false
    }

    boolean canView(RightsControllable unit) {
        if (!unit.draft) return true
        if (unit.owner.isProfileSite()) {
            return securityService.account == unit.owner.head.account
        }
        false
    }

    boolean canDelete(RightsControllable unit) {
        if (unit.owner.isProfileSite()) {
            return securityService.account == unit.owner.head.account
        }
        false
    }

    boolean canAdd(Site site, PageType pageType = null) {
        securityService.isLoggedIn()
    }

    boolean canAdmin(Site site) {
        if (site.isProfileSite()) {
            return securityService.account == site.head.account
        }
        false
    }

    boolean canSeeDrafts(Site site) {
        canAdmin(site)
    }
}
