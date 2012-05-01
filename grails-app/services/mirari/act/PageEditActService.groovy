package mirari.act

import mirari.model.Page
import mirari.model.Site
import mirari.repo.PageRepo
import mirari.pageStatic.AddPageCommand
import mirari.page.EditPageCommand
import mirari.util.ServiceResponse
import mirari.vm.PageVM

class PageEditActService {
    static transactional = false

    PageRepo pageRepo

    ServiceResponse saveAndContinue(Page page, EditPageCommand command) {
        PageVM pageVM = PageVM.build(command.ko)
        page.viewModel = pageVM
        
        if(page.isEmpty()) {
            Site site = page.owner
            pageRepo.delete(page)
            return new ServiceResponse().info("Страничка была опустошена и потому удалена").redirect(site.url)
        }
        pageRepo.save(page)

        new ServiceResponse().info("Сохранили: ".concat(new Date().toString())).model(page: page.viewModel)
    }

    ServiceResponse getViewModel(Page page) {
        new ServiceResponse().model(page: page.viewModel)
    }

    ServiceResponse setDraft(Page page, boolean draft) {
        if (page.draft != draft) {
            pageRepo.setPageDraft(page, draft)
        }
        new ServiceResponse().redirect(page.url)
    }

    ServiceResponse delete(Page page) {
        ServiceResponse resp = new ServiceResponse().redirect(page.site.url)
        pageRepo.delete(page)
        resp.success("Deleted")
    }

    ServiceResponse createAndSave(AddPageCommand command, Site site, Site owner) {
        Page page = createPage(command, site, owner)
        if(page) {
            new ServiceResponse().redirect(page.url)
        } else {
            new ServiceResponse().error("Пустую страничку нельзя сохранить")
        }
    }

    ServiceResponse saveAndContinue(AddPageCommand command, Site site, Site owner) {
        Page page = createPage(command, site, owner, true)
        if(page) {
            new ServiceResponse().redirect(page.getUrl(action: "edit"))
        } else {
            new ServiceResponse().error("Пустую страничку нельзя сохранить")
        }
    }

    ServiceResponse save(EditPageCommand command, Page page) {
        PageVM pageVM = PageVM.build(command.ko)
        page.viewModel = pageVM
        pageRepo.save(page)

        new ServiceResponse().redirect(page.url)
    }

    private Page createPage(AddPageCommand command, Site site, Site owner, boolean asDraft = false) {
        PageVM pageVM = PageVM.build(command.ko)
        Page page = new Page()

        page.owner = owner
        page.site = site

        if (asDraft) {
            pageVM.draft = true
        }
        page.viewModel = pageVM
        if(page.isEmpty()) return null
        pageRepo.save(page)
        page
    }
}
