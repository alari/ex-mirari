package mirari

import eu.medsea.mimeutil.MimeType
import mirari.model.Site
import mirari.model.Unit
import mirari.model.strategy.content.ContentPolicy
import mirari.repo.UnitRepo
import mirari.util.ServiceResponse
import org.apache.log4j.Logger

class UnitProducerService {

    static final Logger log = Logger.getLogger(UnitProducerService)
    static transactional = false

    UnitRepo unitRepo
    def mimeUtilService

    ServiceResponse produce(File file, Site owner) {
        Unit u = null
        ServiceResponse resp = new ServiceResponse()
        try {
            MimeType mimeType = mimeUtilService.getMimeType(file)
            ContentPolicy contentPolicy = ContentPolicy.findForMime(mimeType)
            if(contentPolicy) {
                u = new Unit()
                u.owner = owner
                u.contentPolicy = contentPolicy
                unitRepo.save(u)
                u.setContentFile(file, mimeType)
                unitRepo.save(u)
            } else {
                resp.error("Unknown mime: "+mimeType.mediaType+"/"+mimeType.subType)
            }
            
            if (u) {
                resp.model(u.viewModel)
            }
        }catch(Exception e) {
            log.error(e)
            resp.error(e.message)
        } finally {
            file.delete()
        }
        resp
    }
}
