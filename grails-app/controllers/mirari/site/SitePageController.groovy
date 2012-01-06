package mirari.site

import grails.plugins.springsecurity.Secured
import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.repo.PageRepo
import mirari.repo.UnitRepo
import mirari.util.ServiceResponse
import org.springframework.beans.factory.annotation.Autowired

class SitePageController extends SiteUtilController {

    @Autowired UnitRepo unitRepo
    def unitActService
    def rightsService
    PageRepo pageRepo

    private Page getCurrentPage() {
        pageRepo.getByName(_site, params.pageName)
    }

    def index() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canView(page))) return;
        [page: page]
    }
    
    def edit() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canEdit(page))) return;
        
        [page: page]
    }
    
    def save(EditPageCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canEdit(page))) return;
        
        PageViewModel vm = PageViewModel.forString(command.ko)
        
        pageRepo.buildFor(vm, page)
        // TODO: it shouldnt be here
        page.draft = command.draft
        pageRepo.save(page)
        
        renderJson(new ServiceResponse().redirect(page.url))
    }

    def viewModel() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canView(page))) return;
        
        ServiceResponse resp = new ServiceResponse()
        
        resp.model = currentPage.viewModel
        
        renderJson resp
    }

    @Secured("ROLE_USER")
    def setDraft() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;
        
        page.draft = params.boolean("draft")
        pageRepo.save(page)
        redirect url: page.url
    }

    @Secured("ROLE_USER")
    def delete() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;
        
        pageRepo.delete(page)
        successCode = "Deleted OK"
        redirect url: _site.url
    }
}

class EditPageCommand {
    String title
    String ko
    boolean draft

    static constraints = {
        ko nullable: false, blank: false
    }
}
