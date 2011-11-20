package mirari

import eu.medsea.mimeutil.MimeType
import eu.medsea.mimeutil.MimeUtil
import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit
import mirari.morphia.space.subject.Person
import mirari.morphia.Space

class UnitProducerService {

    static transactional = false

    Unit.Dao unitDao
    def imageStorageService

    ServiceResponse produce(File file, Space space, Person person) {
        Unit u = null
        ServiceResponse resp = new ServiceResponse()
        try {
            // TODO: move MimeUtil to a bean (or even to a plugin)
            MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
            MimeType mimeType = MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(file))

            switch (mimeType.mediaType) {
                case "image":
                    u = produceImage(file, space, person, resp)
                    break;
                default:
                    resp.error("unitProducer.file.error.mediaUnknown", [mimeType.mediaType + "/" + mimeType.subType])
            }
            if (u) {
                resp.model(
                        id: u.id.toString(),
                        container: u.container?.id?.toString(),
                        title: u.title,
                        type: u.type,
                )
            }

        } finally {
            file.delete()
        }
        resp
    }

    private ImageUnit produceImage(File file, Space space, Person person, ServiceResponse resp) {
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
