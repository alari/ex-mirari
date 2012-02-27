package mirari

import mirari.model.Page
import mirari.model.Site
import mirari.model.disqus.Comment
import mirari.model.disqus.Reply
import mirari.model.face.RightsControllable
import mirari.model.page.PageType
import org.apache.log4j.Logger

class RightsService {

    static transactional = false
    static final Logger log = Logger.getLogger(RightsService)

    def securityService

    boolean canEdit(RightsControllable page) {
        if (page.owner.isProfileSite()) {
            return securityService.account == page.owner.account
        }
        if(page.owner.isPortalSite()) {
            return securityService.hasRole("PORTAL")
        }
        false
    }

    boolean canView(RightsControllable unit) {
        if (!unit.draft) return true
        if (unit.owner.isProfileSite()) {
            return securityService.account == unit.owner.account
        }
        if(unit.owner.isPortalSite()) {
            return canAdmin(unit.owner)
        }
        false
    }

    boolean canComment(Page page) {
        canView(page)
    }

    boolean canRemove(Comment comment) {
        comment.page.owner == profile || comment.owner == profile
    }

    boolean canRemove(Reply reply) {
        reply.page.owner == profile || reply.owner == profile
    }

    boolean canDelete(RightsControllable unit) {
        if (unit.owner.isProfileSite()) {
            return securityService.account == unit.owner.account
        }
        false
    }

    boolean canAdd(Site site, PageType pageType = null) {
        if(!securityService.hasRole("ADD_PAGES")) return false;
        if(site.isPortalSite()) return true
        if(site.isProfileSite()) return site == securityService.profile
        false
    }

    boolean canAdmin(Site site) {
        if (site.isProfileSite()) {
            return securityService.account == site.account
        }
        if(site.isPortalSite()) {
            return securityService.hasRole("PORTAL")
        }
        false
    }

    boolean canSeeDrafts(Site site) {
        canAdmin(site)
    }

    private Site getProfile() {
        securityService.profile
    }
}
