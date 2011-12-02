package mirari

import grails.plugins.springsecurity.Secured

import org.apache.log4j.Logger
import mirari.ko.PageViewModel
import mirari.morphia.Page

class SpacePageStaticController extends SpaceUtilController {

    def rightsService
    def unitActService

    def spaceLinkService

    Page.Dao pageDao

    Logger log = Logger.getLogger(this.getClass())

    @Secured("ROLE_USER")
    def add = {
        if (hasNoRight(rightsService.canAdd())) return;
    }

    @Secured("ROLE_USER")
    def addPage = {AddPageCommand command ->
        if (hasNoRight(rightsService.canAdd())) return;

        println command.ko

        PageViewModel viewModel = PageViewModel.forString(command.ko)
        Page page = pageDao.buildFor(viewModel, currentSpace)
        pageDao.save(page)
        println spaceLinkService.getUrl(page, [absolute: true])
        renderJson new ServiceResponse().redirect(spaceLinkService.getUrl(page, [absolute: true]))

        //renderJson unitActService.addUnit(command, currentSpace)
    }

    @Secured("ROLE_USER")
    def addFile = {AddFileCommand command ->
        if (hasNoRight(rightsService.canAdd())) return;
        renderJson unitActService.addFile(command, request.getFile("unitFile"), currentSpace)
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