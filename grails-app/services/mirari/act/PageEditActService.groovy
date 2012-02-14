package mirari.act

import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.model.Site
import mirari.repo.PageRepo
import mirari.site.AddPageCommand
import mirari.site.EditPageCommand
import mirari.util.ServiceResponse

class PageEditActService {
    static transactional = false

    PageRepo pageRepo

    ServiceResponse saveAndContinue(Page page, EditPageCommand command) {
        PageViewModel vm = PageViewModel.forString(command.ko)
        page.viewModel = vm
        if(page.isEmpty()) {
            Site site = page.owner
            pageRepo.delete(page)
            return new ServiceResponse().info("Страничка была опустошена и потому удалена").redirect(site.url)
        }
        pageRepo.save(page)

        new ServiceResponse().info("Сохранили: ".concat(new Date().toString())).model(page.viewModel)
    }

    ServiceResponse getViewModel(Page page) {
        new ServiceResponse().model(page.viewModel)
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
            new ServiceResponse().error("Пустую страничку нельзя сохранить").redirect(site.getUrl())
        }
    }

    ServiceResponse saveAndContinue(AddPageCommand command, Site site, Site owner) {
        Page page = createPage(command, site, owner, true)
        if(page) {
            new ServiceResponse().redirect(page.getUrl(action: "edit"))
        } else {
            new ServiceResponse().error("Пустую страничку нельзя сохранить").redirect(site.getUrl())
        }
    }

    private Page createPage(AddPageCommand command, Site site, Site owner, boolean asDraft = false) {
        PageViewModel viewModel = PageViewModel.forString(command.ko)
        Page page = new Page()

        page.owner = owner
        page.site = site

        if (asDraft) {
            viewModel.draft = true
        }
        page.viewModel = viewModel
        if(page.isEmpty()) return null
        pageRepo.save(page)
        page
    }
}
