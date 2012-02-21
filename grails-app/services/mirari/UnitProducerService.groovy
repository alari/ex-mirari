package mirari

import mirari.model.Site
import mirari.model.Unit
import mirari.model.unit.content.ContentPolicy
import mirari.repo.UnitRepo
import mirari.util.ServiceResponse
import org.apache.log4j.Logger
import ru.mirari.infra.file.FileInfo

class UnitProducerService {

    static final Logger log = Logger.getLogger(UnitProducerService)
    static transactional = false

    UnitRepo unitRepo

    ServiceResponse produce(File file, String originalFilename, Site owner) {
        Unit u = null
        ServiceResponse resp = new ServiceResponse()
        try {
            FileInfo fileInfo = new FileInfo(file, originalFilename)
            ContentPolicy contentPolicy = ContentPolicy.findForFileInfo(fileInfo)
            if (contentPolicy) {
                u = new Unit()
                u.owner = owner
                u.contentPolicy = contentPolicy
                unitRepo.save(u)
                u.setContentFile(fileInfo)
                unitRepo.save(u)
            } else {
                resp.error("Unknown mime: " + fileInfo.mediaType + "/" + fileInfo.subType)
            }

            if (u) {
                resp.model(unit: u.viewModel)
            }
        } catch (Exception e) {
            log.error("Error in Unit Producer Service while producing an unit by file", e)
            resp.error(e.message)
        } finally {
            file.delete()
        }
        resp
    }
}
