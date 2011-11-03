package mirari.act

import mirari.ServiceResponse
import mirari.morphia.Space
import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit

import org.springframework.beans.factory.annotation.Autowired
import mirari.AddUnitCommand
import org.springframework.web.multipart.MultipartFile

import mirari.util.image.ImageStorage
import mirari.AddFileCommand

class UnitActService {

    @Autowired Unit.Dao unitDao
    @Autowired ImageStorage imageStorage

    ServiceResponse addUnit(AddUnitCommand command, Space space) {


        ServiceResponse resp = new ServiceResponse()
        if(command.hasErrors()) {
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

    ServiceResponse addFile(AddFileCommand command, MultipartFile file, Space space) {
        ServiceResponse resp = new ServiceResponse()
        if(command.hasErrors()) {
            resp.error(command.errors.toString())
            return resp
        }

        // TODO: at first understand file type with Mime-Util
        // Do image handling only for image files
        if(file.contentType in ["image/jpeg", "image/jpg", "image/png"]) {
            return resp.error("wrong file type: "+file.contentType)
        }

        ImageUnit u = new ImageUnit()
        u.draft = true
        u.space = space
        u.name = UUID.randomUUID().toString().replaceAll('-', '').substring(0, 5)

        unitDao.save(u)

        if(!u.id) {
            resp.error "Cannot save unit"
            resp.model command as Map
            return resp
        }

        File tmpIm = File.createTempFile("uploadImageUnit", ".im")
        file.transferTo(tmpIm)

        try {
            imageStorage.storeFormatted(ImageUnit.FORMAT_PAGE, tmpIm, u.path)
            resp.model.imageSrc = imageStorage.getUrl(ImageUnit.FORMAT_PAGE, u.path)
            resp.model.id = u.id.toString()
            resp.success("Uploaded ok!")
        }catch(Exception e) {
            unitDao.delete u
            u.id = null
            resp.error "Failed to upload an image: "+e
            log.error "Image uploading failed", e
        } finally {
            tmpIm.delete()
        }

        resp
    }
}
