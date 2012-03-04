package mirari.pageStatic

import grails.plugins.springsecurity.Secured
import mirari.UtilController
import mirari.model.avatar.Avatar
import mirari.model.page.PageType
import mirari.repo.AvatarRepo
import mirari.repo.PageRepo
import mirari.util.ServiceResponse
import org.apache.log4j.Logger
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile

/**
 * @author alari
 * @since 12/13/11 4:34 PM
 */
class SitePageStaticController extends UtilController {

    def rightsService
    def unitActService
    def pageEditActService

    PageRepo pageRepo
    AvatarRepo avatarRepo

    Logger log = Logger.getLogger(this.getClass())

    @Secured("ROLE_USER")
    def add(String type) {
        PageType pageType = PageType.getByName(type)
        if (hasNoRight(rightsService.canAdd(_site, pageType))) return;

        Avatar avatar = avatarRepo.getBasic(pageType.name)
        [type: pageType, thumbSrc: avatar.srcThumb, addText: pageType in [PageType.PROSE, PageType.POETRY, PageType.POST, PageType.ARTICLE]]
    }

    @Secured("ROLE_USER")
    def save(AddPageCommand command) {
        if (hasNoRight(rightsService.canAdd(_site))) return;

        renderJson pageEditActService.createAndSave(command, _site, _profile)
    }

    @Secured("ROLE_USER")
    def saveAndContinue(AddPageCommand command) {
        // TODO: it shouldn't redirect, only rewrite action
        if (hasNoRight(rightsService.canAdd(_site))) return;

        renderJson pageEditActService.saveAndContinue(command, _site, _profile)
    }

    @Secured("ROLE_USER")
    def addFile(AddFileCommand command) {
        if (hasNoRight(rightsService.canAdd(_site))) return;
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mpr = (MultipartHttpServletRequest) request;
            CommonsMultipartFile f = (CommonsMultipartFile) mpr.getFile("unitFile");
            renderJson unitActService.addFile(command, f, _profile)
        } else {
            renderJson new ServiceResponse().error("Not a Multipart Request")
        }
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