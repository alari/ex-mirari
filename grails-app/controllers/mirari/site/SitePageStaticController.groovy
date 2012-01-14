package mirari.site

import grails.plugins.springsecurity.Secured
import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.repo.PageRepo
import mirari.util.ServiceResponse
import org.apache.log4j.Logger

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
    def add() {
        if (hasNoRight(rightsService.canAdd())) return;
    }

    @Secured("ROLE_USER")
    def addPage(AddPageCommand command){
        if (hasNoRight(rightsService.canAdd())) return;

        PageViewModel viewModel = PageViewModel.forString(command.ko)
        Page page = new Page(site: _site, owner: _profile)
        page.viewModel = viewModel
        // TODO: it shouldnt be here
        page.draft = command.draft
        pageRepo.save(page)
        renderJson new ServiceResponse().redirect(page.url)
    }

    @Secured("ROLE_USER")
    def addFile(AddFileCommand command){
        if (hasNoRight(rightsService.canAdd())) return;
        renderJson unitActService.addFile(command, request.getFile("unitFile"), _site)
    }

    @Secured("ROLE_USER")
    def addExternal(String url) {
        if (hasNoRight(rightsService.canAdd())) return;
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