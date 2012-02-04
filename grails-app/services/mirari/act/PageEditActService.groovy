package mirari.act

import mirari.util.ServiceResponse
import mirari.site.EditPageCommand
import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.repo.PageRepo
import mirari.site.AddPageCommand
import mirari.model.Site

class PageEditActService {
    static transactional = false
    
    PageRepo pageRepo

    ServiceResponse saveAndContinue(Page page, EditPageCommand command) {
        PageViewModel vm = PageViewModel.forString(command.ko)
        page.viewModel = vm
        pageRepo.save(page)

        new ServiceResponse().info("Сохранили: ".concat(new Date().toString())).model(page.viewModel)
    }
    
    ServiceResponse getViewModel(Page page) {
        new ServiceResponse().model(page.viewModel)
    }
    
    ServiceResponse setDraft(Page page, boolean draft) {
        if(page.head.draft != draft) {
            pageRepo.setPageDraft(page, draft)
        }
        new ServiceResponse().redirect(page.url)
    }
    
    ServiceResponse delete(Page page) {
        ServiceResponse resp = new ServiceResponse().redirect(page.head.site.url)
        pageRepo.delete(page)
        resp.success("Deleted")
    }
    
    ServiceResponse createAndSave(AddPageCommand command, Site site, Site owner) {
        Page page = createPage(command, site, owner)
        new ServiceResponse().redirect(page.url)
    }
    
    ServiceResponse saveAndContinue(AddPageCommand command, Site site, Site owner) {
        Page page = createPage(command, site, owner, true)
        new ServiceResponse().redirect(page.getUrl(action: "edit"))
    }
    
    private Page createPage(AddPageCommand command, Site site, Site owner, boolean  asDraft = false) {
        PageViewModel viewModel = PageViewModel.forString(command.ko)
        Page page = new Page()
        page.head.setSites(site: site, owner: owner)
        if(asDraft) {
            viewModel.draft = true
        }
        page.viewModel = viewModel
        pageRepo.save(page)
        page
    }
}