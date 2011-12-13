package mirari.site

import grails.plugins.springsecurity.Secured
import mirari.morphia.Unit
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Page

class SitePageController extends SiteUtilController {

    @Autowired Unit.Dao unitDao
    def unitActService
    def rightsService
    def siteLinkService
    Page.Dao pageDao

    private Page getCurrentPage() {
        pageDao.getByName(currentSite, params.pageName)
    }

    def index() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canView(page))) return;
        [page: page]
    }

    @Secured("ROLE_USER")
    def setDraft() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;
        page.draft = params.boolean("draft")
        pageDao.save(page)
        redirect uri: siteLinkService.getUrl(page, [absolute: true])
    }

    @Secured("ROLE_USER")
    def delete() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;
        pageDao.delete(page)
        successCode = "Deleted OK"
        redirect uri: siteLinkService.getUrl(currentSite)
    }
}