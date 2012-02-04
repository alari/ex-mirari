package mirari.act

//import mirari.ko.UnitBuilder


import mirari.model.Site
import mirari.model.Unit
import mirari.model.strategy.content.ContentPolicy
import mirari.repo.UnitRepo
import mirari.site.AddFileCommand
import mirari.util.ServiceResponse
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile
import ru.mirari.infra.file.FileStorage

class UnitActService {

    static transactional = false

    @Autowired UnitRepo unitRepo
    def imageStorageService
    @Autowired FileStorage fileStorage

    def unitProducerService

    LinkGenerator grailsLinkGenerator

    ServiceResponse addFile(AddFileCommand command, MultipartFile file, Site site) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
            return resp
        }
        String fileExt = file.originalFilename.lastIndexOf(".") >= 0 ? file.originalFilename.substring(file.originalFilename.lastIndexOf(".")) : "tmp"
        File tmp = File.createTempFile("uploadUnit", "." + fileExt)
        file.transferTo(tmp)



        return unitProducerService.produce(tmp, site)
    }

    ServiceResponse setDraft(Unit unit, boolean draft) {
        unitRepo.save(unit)
        new ServiceResponse().redirect(grailsLinkGenerator.link(for: unit))
    }

    ServiceResponse getByUrl(String uri, Site site) {
        ServiceResponse resp = new ServiceResponse()
        try {

            ContentPolicy contentPolicy = ContentPolicy.findForUrl(uri)
            if (contentPolicy) {
                Unit unit = new Unit()
                unit.owner = site
                unit.contentPolicy = contentPolicy
                unit.setContentUrl(uri)
                unitRepo.save(unit)
                resp.success("OK!")
                resp.model(unit.viewModel)
            } else {
                resp.error("Wrong (unsupported) URL")
            }
        } catch (MalformedURLException e) {
            resp.error(e.message)
        }
        resp
    }
}
