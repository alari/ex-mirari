package mirari

import eu.medsea.mimeutil.MimeType
import eu.medsea.mimeutil.MimeUtil
import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit
import mirari.morphia.space.subject.Person
import mirari.morphia.Space
import mirari.ko.UnitViewModel
import mirari.morphia.unit.single.TextUnit

class UnitProducerService {

    static transactional = false

    Unit.Dao unitDao
    def imageStorageService
    def mimeUtilService

    ServiceResponse produce(File file, Space space) {
        Unit u = null
        ServiceResponse resp = new ServiceResponse()
        try {
            MimeType mimeType = mimeUtilService.getMimeType(file)

            switch (mimeType.mediaType) {
                case "image":
                    u = produceImage(file, space, resp)
                    break;
                default:
                    resp.error("unitProducer.file.error.mediaUnknown", [mimeType.mediaType + "/" + mimeType.subType])
            }
            if (u) {
                resp.model(
                        id: u.id.toString(),
                        title: u.title,
                        type: u.type,
                )
            }

        } finally {
            file.delete()
        }
        resp
    }

    private ImageUnit produceImage(File file, Space space, ServiceResponse resp) {
        ImageUnit u = new ImageUnit()
        u.draft = true
        u.space = space

        unitDao.save(u)

        if (!u.id) {
            resp.error("unitProducer.error.cannotSave")
            return u
        }

        try {
            imageStorageService.format(u, file)
            resp.model(params: [
                    srcPage: imageStorageService.getUrl(u, ImageUnit.FORMAT_PAGE),
                    srcFeed: imageStorageService.getUrl(u, ImageUnit.FORMAT_FEED),
                    srcMax: imageStorageService.getUrl(u, ImageUnit.FORMAT_MAX),
                    srcTiny: imageStorageService.getUrl(u, ImageUnit.FORMAT_TINY)]
            ).success("unitProducer.image.success")
            return u
        } catch (Exception e) {
            unitDao.delete u
            u.id = null
            resp.error("unitProducer.image.failed")
            log.error "Image uploading failed", e
        }
        u
    }
}
