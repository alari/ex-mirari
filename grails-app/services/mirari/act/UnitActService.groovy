package mirari.act

import mirari.AddFileCommand

import mirari.ServiceResponse
import mirari.morphia.Space
import mirari.morphia.Unit

import ru.mirari.infra.file.FileHolder
import ru.mirari.infra.image.ImageHolder

import org.springframework.web.multipart.MultipartFile
import mirari.ko.UnitViewModel

//import mirari.ko.UnitBuilder
import mirari.morphia.space.subject.Person
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.file.FileStorage
import mirari.AddPageCommand

class UnitActService {

    static transactional = false

    @Autowired Unit.Dao unitDao
    def imageStorageService
    @Autowired FileStorage fileStorage

    def spaceLinkService
    def unitProducerService

    /*ServiceResponse addUnit(AddPageCommand command, Space space) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            return resp.error(command.errors.toString())
        }

        UnitViewModel vm = UnitViewModel.forString(command.ko)

        UnitBuilder builder = new UnitBuilder(space, (Person)space, unitProducerService)
        builder.buildFor(vm, command.draft)

        resp = builder.resp
        if(resp.isOk()) {
            resp.redirect(url: spaceLinkService.getUrl(builder.unit))
        }
        resp
    }         */

    ServiceResponse addFile(AddFileCommand command, MultipartFile file, Space space) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
            return resp
        }
        String fileExt = file.originalFilename.lastIndexOf(".") >= 0 ? file.originalFilename.substring(file.originalFilename.lastIndexOf(".")) : "tmp"
        File tmp = File.createTempFile("uploadUnit", "." + fileExt)
        file.transferTo(tmp)

        return unitProducerService.produce(tmp, space)
    }

    ServiceResponse setDraft(Unit unit, boolean draft) {
        unit.draft = draft
        unitDao.save(unit)
        new ServiceResponse().redirect(spaceLinkService.getUrl(unit, [absolute: true]))
    }

    ServiceResponse delete(Unit unit) {
        List<Unit> toDelete = []
        // Images
        if (unit instanceof ImageHolder) {
            imageStorageService.delete((ImageHolder) unit)
        }
        // Files
        if (unit instanceof FileHolder) {
            fileStorage.delete((FileHolder) unit)
        }
        // From the outer
        if(unit.outer) {
            unit.outer.inners.removeAll {it.id == unit.id}
            unitDao.save(unit.outer)
            if(unit.outer.inners.size() == 0) {
                toDelete.add(unit.outer)
            }
            unit.outer = null
        }
        // Delete children
        if(unit.inners.size() > 0) {
            unit.inners.each{
                it.outer = null
                unitDao.save(it)
                toDelete.add it
            }
        }
        unitDao.delete(unit)

        toDelete.each {
            delete(it)
        }

        new ServiceResponse().success("unitAct.delete.success").redirect(spaceLinkService.getUrl(unit.space,
                [absolute: true]))
    }
}
