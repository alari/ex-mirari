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

class UnitActService {

    static transactional = false

    @Autowired UnitRepo unitRepo
    def imageStorageService
    @Autowired FileStorage fileStorage

    def unitProducerService

    ServiceResponse addFile(AddFileCommand command, MultipartFile file, Site owner) {
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
