package mirari.act

import eu.medsea.mimeutil.MimeType
import eu.medsea.mimeutil.MimeUtil
import mirari.AddFileCommand
import mirari.AddUnitCommand
import mirari.ServiceResponse
import mirari.morphia.Space
import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit
import mirari.util.image.ImageStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile

class UnitActService {

    @Autowired Unit.Dao unitDao
    @Autowired ImageStorage imageStorage

    private String getRandomName() {
        UUID.randomUUID().toString().replaceAll('-', '').substring(0, 5)
    }

    ServiceResponse addUnit(AddUnitCommand command, Space space) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
            return resp
        }

        Unit u = unitDao.getById(command.unitId)

        u.draft = command.draft
        u.title = command.title
        unitDao.save(u)

        if (u.id) {
            resp.success("Unit added successfully")
            resp.redirect controller: "spaceUnit", action: "show", params: [unitName: u.name,
                    spaceName: space.name]
        } else {
            resp.error "Cannot save unit"
            resp.model command as Map
        }

    }

    private Unit addFileImage(File file, Space space, ServiceResponse resp) {
        ImageUnit u = new ImageUnit()
        u.draft = true
        u.space = space
        u.name = randomName

        unitDao.save(u)

        if (!u.id) {
            resp.error "Cannot save unit"
            return u
        }
        try {
            imageStorage.storeFormatted(ImageUnit.FORMAT_PAGE, file, u.path)
            resp.model.imageSrc = imageStorage.getUrl(ImageUnit.FORMAT_PAGE, u.path)
            resp.model.id = u.id.toString()
            resp.success("Uploaded ok!")
        } catch (Exception e) {
            unitDao.delete u
            u.id = null
            resp.error "Failed to upload an image: " + e
            log.error "Image uploading failed", e
        }
        u
    }

    ServiceResponse addFile(AddFileCommand command, MultipartFile file, Space space) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
            return resp
        }
        String fileExt = file.originalFilename.lastIndexOf(".") >= 0 ? file.originalFilename.substring(file.originalFilename.lastIndexOf(".")) : "tmp"
        File tmp = File.createTempFile("uploadImageUnit", "." + fileExt)
        file.transferTo(tmp)

        try {

            MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
            MimeType mimeType = MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(tmp))
            if (mimeType.mediaType == "image") {
                addFileImage(tmp, space, resp)
            } else {
                resp.error("Unknown media type: ${mimeType.mediaType}/${mimeType.subType}")
            }

        } finally {
            tmp.delete()
        }
        resp
    }
}
