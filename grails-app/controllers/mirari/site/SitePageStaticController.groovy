package mirari.site

import grails.plugins.springsecurity.Secured
import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.repo.PageRepo
import mirari.util.ServiceResponse
import org.apache.log4j.Logger
import mirari.model.site.Profile
import mirari.dao.PageDao
import mirari.model.PageType

/**
 * @author alari
 * @since 12/13/11 4:34 PM
 */
class SitePageStaticController extends SiteUtilController {

    def rightsService
    def unitActService

    PageRepo pageRepo

    Logger log = Logger.getLogger(this.getClass())

    @Secured("ROLE_USER")
    def add(String type) {
        PageType pageType = PageType.getByName(type)
        if (hasNoRight(rightsService.canAdd(_site, pageType))) return;
        [type: pageType, addHtml: pageType in [PageType.PROSE, PageType.POETRY, PageType.POST, PageType.ARTICLE]]
    }

    @Secured("ROLE_USER")
    def addPage(AddPageCommand command){
        if (hasNoRight(rightsService.canAdd(_site))) return;

        PageViewModel viewModel = PageViewModel.forString(command.ko)
        Page page = new Page(site: _site, owner: _profile)
        setSites(page)
        page.viewModel = viewModel
        // TODO: it shouldnt be here
        page.draft = command.draft
        pageRepo.save(page)
        renderJson new ServiceResponse().redirect(page.url)
    }
    
    @Secured("ROLE_USER")
    def saveAndContinue(AddPageCommand command) {
        // TODO: it shouldn't redirect, only rewrite action
        if (hasNoRight(rightsService.canAdd(_site))) return;

        PageViewModel viewModel = PageViewModel.forString(command.ko)
        Page page = new Page(site: _site, owner: _profile)
        setSites(page)
        page.viewModel = viewModel
        page.draft = true
        pageRepo.save(page)
        renderJson new ServiceResponse().redirect(page.getUrl(action: "edit"))
    }
    
    private void setSites(Page page) {
        // TODO: move it somewhere
        page.sites = []
        page.sites.addAll(page.site, page.owner)
        if (page.site instanceof Profile) {
            page.sites.add( ((Profile)page.site).portal )
        }
    }

    @Secured("ROLE_USER")
    def addFile(AddFileCommand command){
        if (hasNoRight(rightsService.canAdd(_site))) return;
        renderJson unitActService.addFile(command, request.getFile("unitFile"), _site)
    }

    @Secured("ROLE_USER")
    def addExternal(String url) {
        if (hasNoRight(rightsService.canAdd(_site))) return;
        renderJson unitActService.getByUrl(url, _site)
    }
}


class AddPageCommand {
    String title
    String ko
    boolean draft

    static constraints = {
        ko nullable: false, blank: false
    }
}

class AddFileCommand {
    String container
}