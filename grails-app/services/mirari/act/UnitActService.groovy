package mirari.act

import mirari.model.Site
import mirari.model.Unit
import mirari.model.unit.content.ContentPolicy
import mirari.repo.UnitRepo
import mirari.pageStatic.AddFileCommand
import mirari.util.ServiceResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile
import ru.mirari.infra.file.FileStorage
import mirari.model.Page
import grails.gsp.PageRenderer
import mirari.vm.UnitVM

class UnitActService {

    static transactional = false

    @Autowired UnitRepo unitRepo
    def imageStorageService
    @Autowired FileStorage fileStorage

    def unitProducerService

    PageRenderer groovyPageRenderer

    ServiceResponse addFile(AddFileCommand command, MultipartFile file, final Site owner) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
            return resp
        }
        String fileExt = file.originalFilename.lastIndexOf(".") >= 0 ? file.originalFilename.substring(file.originalFilename.lastIndexOf(".")) : "tmp"
        File tmp = File.createTempFile("uploadUnit", "." + fileExt)
        file.transferTo(tmp)

        return unitProducerService.produce(tmp, file.originalFilename, owner)
    }

    ServiceResponse setDraft(Unit unit, boolean draft) {
        unitRepo.save(unit)
        new ServiceResponse().redirect(unit.getUrl())
    }

    ServiceResponse getByUrl(String uri, final Site site) {
        ServiceResponse resp = new ServiceResponse()
        try {

            ContentPolicy contentPolicy = ContentPolicy.findForUrl(uri)
            if (contentPolicy) {
                Unit unit = new Unit()
                unit.owner = site
                unit.contentPolicy = contentPolicy
                unit.setContentUrl(uri)
                unitRepo.save(unit)
                resp.model(unit: unit.viewModel)
            } else {
                resp.error("Wrong (unsupported) URL")
            }
        } catch (MalformedURLException e) {
            resp.error(e.message)
        }
        resp
    }

    ServiceResponse renderFirst(final Page page) {
        ServiceResponse resp = new ServiceResponse()
        Unit first = page.inners?.first()
        if(first) {
            UnitVM u = first.viewModel
            resp.model html: groovyPageRenderer.render(template: "/unit-render/page-".concat(u.type), model: [viewModel: u, only: true])
        }
        resp
    }

    ServiceResponse renderFull(final Page page) {

        ServiceResponse resp = new ServiceResponse()
        
        String html = ""
        for(UnitVM u : page.viewModel.inners){
            html = html.concat(groovyPageRenderer.render(template: "/unit-render/page-".concat(u.type), model: [viewModel: u]))
        }
        resp.model html: html
    }
}
