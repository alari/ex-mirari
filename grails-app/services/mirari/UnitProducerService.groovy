package mirari

import eu.medsea.mimeutil.MimeType

import mirari.model.Unit
import mirari.model.unit.single.ImageUnit

import mirari.model.Site

import mirari.model.unit.single.AudioUnit
import mirari.repo.UnitRepo
import mirari.util.ServiceResponse

class UnitProducerService {

    static transactional = false

    UnitRepo unitRepo
    def imageStorageService
    def mimeUtilService
    def fileStorageService

    ServiceResponse produce(File file, Site owner) {
        Unit u = null
        ServiceResponse resp = new ServiceResponse()
        try {
            MimeType mimeType = mimeUtilService.getMimeType(file)

            switch (mimeType.mediaType) {
                case "image":
                    u = produceImage(file, owner, resp)
                    break;
                case "audio":
                    u = produceAudio(file, owner, resp, mimeType.subType)
                    break
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
    
    private AudioUnit produceAudio(File file, Site owner, ServiceResponse resp, String subType) {
        AudioUnit.Type type = AudioUnit.Type.forName(subType)
        if(type == null) {
            resp.error("unitProducer.file.error.mediaUnknown", [subType])
            return
        }
        
        AudioUnit u = new AudioUnit()
        u.draft = true
        u.owner = owner

        unitRepo.save(u)

        if (!u.id) {
            resp.error("unitProducer.error.cannotSave")
            return u
        }

        try {
            fileStorageService.store(file, u, type.filename)
            u.attachMedia(type, file)
            unitRepo.save(u)

            resp.model(u.viewModel).success("unitProducer.audio.success")
            return u
        } catch (Exception e) {
            log.error "Audio uploading failed", e
            unitRepo.delete u
            u.id = null
            resp.error("unitProducer.audio.failed")
        }
        
        u
    }

    private ImageUnit produceImage(File file, Site owner, ServiceResponse resp) {
        ImageUnit u = new ImageUnit()
        u.draft = true
        u.owner = owner

        unitRepo.save(u)

        if (!u.id) {
            resp.error("unitProducer.error.cannotSave")
            return u
        }

        try {
            imageStorageService.format(u, file)
            resp.model(u.viewModel).success("unitProducer.image.success")
            return u
        } catch (Exception e) {
            unitRepo.delete u
            u.id = null
            resp.error("unitProducer.image.failed")
            log.error "Image uploading failed", e
        }
        u
    }
}
