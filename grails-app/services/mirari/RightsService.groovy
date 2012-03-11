package mirari

import mirari.model.Page
import mirari.model.Site
import mirari.model.disqus.Comment
import mirari.model.disqus.Reply
import mirari.model.face.RightsControllable
import mirari.model.page.PageType
import org.apache.log4j.Logger
import mirari.model.digest.Notice

class RightsService {

    static transactional = false
    static final Logger log = Logger.getLogger(RightsService)

    def securityService

    boolean canEdit(final RightsControllable page) {
        if (page.owner.isProfileSite()) {
            return securityService.account == page.owner.account
        }
        if(page.owner.isPortalSite()) {
            return securityService.hasRole("PORTAL")
        }
        false
    }

    boolean canView(final RightsControllable unit) {
        if (!unit.draft) return true
        if (unit.owner.isProfileSite()) {
            return securityService.account == unit.owner.account
        }
        if(unit.owner.isPortalSite()) {
            return canAdmin(unit.owner)
        }
        false
    }

    boolean canComment(final Page page) {
        canView(page)
    }

    boolean canRemove(final Comment comment) {
        comment.page.owner == profile || comment.owner == profile
    }

    boolean canRemove(final Reply reply) {
        reply.page.owner == profile || reply.owner == profile
    }

    boolean canDelete(final RightsControllable unit) {
        if (unit.owner.isProfileSite()) {
            return securityService.account == unit.owner.account
        }
        false
    }

    boolean canAdd(final Site site, PageType pageType = null) {
        if(!securityService.hasRole("ADD_PAGES")) return false;
        if(site.isPortalSite()) return true
        if(site.isProfileSite()) return site == securityService.profile
        false
    }

    boolean canAdmin(final Site site) {
        if (site.isProfileSite()) {
            return securityService.account == site.account
        }
        if(site.isPortalSite()) {
            return securityService.hasRole("PORTAL")
        }
        false
    }

    boolean canSeeDrafts(final Site site) {
        canAdmin(site)
    }

    boolean canReact(final Notice notice) {
        canComment(notice.page)
    }

    private Site getProfile() {
        securityService.profile
    }
}
