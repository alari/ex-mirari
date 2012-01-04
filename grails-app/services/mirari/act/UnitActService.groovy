package mirari.act

import mirari.site.AddFileCommand

import mirari.util.ServiceResponse
import mirari.model.Site
import mirari.model.Unit

import org.springframework.web.multipart.MultipartFile

//import mirari.ko.UnitBuilder


import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.file.FileStorage
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import mirari.repo.UnitRepo

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
        unit.draft = draft
        unitRepo.save(unit)
        new ServiceResponse().redirect(grailsLinkGenerator.link(for: unit))
    }
}
