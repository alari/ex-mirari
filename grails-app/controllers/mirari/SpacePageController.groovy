package mirari

import grails.plugins.springsecurity.Secured
import mirari.morphia.Unit
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Page

class SpacePageController extends SpaceUtilController {

    @Autowired Unit.Dao unitDao
    def unitActService
    def rightsService
    def spaceLinkService
    Page.Dao pageDao

    private Page getCurrentPage() {
        pageDao.getByName(currentSpace, params.pageName)
    }

    def index = {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canView(page))) return;
        [page: page]
    }

    @Secured("ROLE_USER")
    def setDraft = {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;
        page.draft = params.boolean("draft")
        pageDao.save(page)
        redirect url: spaceLinkService.getUrl(page)
    }

    @Secured("ROLE_USER")
    def delete = {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;
        errorCode = "not implemented yet"
        redirect url: spaceLinkService.getUrl(page)
        //ServiceResponse resp = unitActService.delete(unit)
        //alert resp
        //redirect resp.redirect
    }
}
