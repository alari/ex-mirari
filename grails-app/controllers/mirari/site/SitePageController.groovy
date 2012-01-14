package mirari.site

import grails.plugins.springsecurity.Secured
import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.repo.PageRepo
import mirari.repo.UnitRepo
import mirari.util.ServiceResponse
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.TagRepo

class SitePageController extends SiteUtilController {

    @Autowired UnitRepo unitRepo
    def unitActService
    def rightsService
    PageRepo pageRepo
    TagRepo tagRepo

    private Page getCurrentPage() {
        pageRepo.getByName(_site, params.pageName)
    }

    def index() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canView(page))) return;
        [page: page]
    }

    @Secured("ROLE_USER")
    def edit() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canEdit(page))) return;

        [page: page, tags: tagRepo.listBySite(_profile)]
    }

    @Secured("ROLE_USER")
    def save(EditPageCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canEdit(page))) return;
        
        PageViewModel vm = PageViewModel.forString(command.ko)
        
        page.viewModel = vm
        // TODO: it shouldnt be here
        page.draft = command.draft
        pageRepo.save(page)
        
        renderJson(new ServiceResponse().redirect(page.url))
    }

    @Secured("ROLE_USER")
    def saveAndContinue(EditPageCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if(hasNoRight(rightsService.canEdit(page))) return;

        PageViewModel vm = PageViewModel.forString(command.ko)
        System.out.println("saving page...")
        page.viewModel = vm
        pageRepo.save(page)
        
        infoCode = "Сохранили: ".concat(new Date().toString())

        renderJson(new ServiceResponse().model(page.viewModel))
    }

    @Secured("ROLE_USER")
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
