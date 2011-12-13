package mirari.site

import mirari.morphia.Page
import org.apache.log4j.Logger
import grails.plugins.springsecurity.Secured
import mirari.ko.PageViewModel
import mirari.ServiceResponse

/**
 * @author alari
 * @since 12/13/11 4:34 PM
 */
class SitePageStaticController extends SiteUtilController {

    def rightsService
    def unitActService

    def siteLinkService

    Page.Dao pageDao

    Logger log = Logger.getLogger(this.getClass())

    @Secured("ROLE_USER")
    def add() {
        if (hasNoRight(rightsService.canAdd())) return;
    }

    @Secured("ROLE_USER")
    def addPage(AddPageCommand command){
        if (hasNoRight(rightsService.canAdd())) return;

        PageViewModel viewModel = PageViewModel.forString(command.ko)
        Page page = pageDao.buildFor(viewModel, currentSite, currentProfile)
        pageDao.save(page)
        println siteLinkService.getUrl(page, [absolute: true])
        renderJson new ServiceResponse().redirect(siteLinkService.getUrl(page, [absolute: true]))
    }

    @Secured("ROLE_USER")
    def addFile(AddFileCommand command){
        if (hasNoRight(rightsService.canAdd())) return;
        renderJson unitActService.addFile(command, request.getFile("unitFile"), currentSite)
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