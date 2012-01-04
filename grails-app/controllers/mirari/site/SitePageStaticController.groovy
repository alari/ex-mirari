package mirari.site

import mirari.model.Page
import org.apache.log4j.Logger
import grails.plugins.springsecurity.Secured
import mirari.ko.PageViewModel
import mirari.util.ServiceResponse
import mirari.repo.PageRepo

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
        Page page = pageRepo.buildFor(viewModel, _site, _profile)
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