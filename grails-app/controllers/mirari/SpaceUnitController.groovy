package mirari

import grails.plugins.springsecurity.Secured

import mirari.morphia.Unit
import org.springframework.beans.factory.annotation.Autowired


class SpaceUnitController extends SpaceUtilController {

    @Autowired Unit.Dao unitDao
    def unitActService
    def unitRightsService

    private Unit getCurrentUnit() {
        unitDao.getByName(currentSpace, params.unitName)
    }

    def show = {
        Unit unit = currentUnit
        if(!unit) {
            errorCode = "unit not found"
            redirect(uri: "/")
            return
        }
        if(hasNoRight(unitRightsService.canView(unit))) return;

        [unit: unit]
    }

    @Secured("ROLE_USER")
    def add = {
        if(hasNoRight(unitRightsService.canAdd())) return;
    }

    @Secured("ROLE_USER")
    def addUnit = {AddUnitCommand command ->
        if(hasNoRight(unitRightsService.canAdd())) return;
        renderJson unitActService.addUnit(command, currentSpace)
    }

    @Secured("ROLE_USER")
    def addFile = {AddFileCommand command ->
        if(hasNoRight(unitRightsService.canAdd())) return;
        renderJson unitActService.addFile(command, request.getFile("unitFile"), currentSpace)
    }

    @Secured("ROLE_USER")
    def setDraft = {
        Unit unit = currentUnit
        if(!unit) {
            errorCode = "unit not found"
            redirect(uri: "/")
            return
        }
        if(hasNoRight(unitRightsService.canEdit(unit))) return;
        redirect unitActService.setDraft(unit, params.boolean("draft")).redirect
    }

    @Secured("ROLE_USER")
    def delete = {
        Unit unit = currentUnit
        if(!unit) {
            errorCode = "unit not found"
            redirect(uri: "/")
            return
        }
        if(hasNoRight(unitRightsService.canEdit(unit))) return;
        redirect unitActService.delete(unit).redirect
    }
}

class AddUnitCommand{
    String unitId
    String title
    boolean draft

    static constraints = {
        unitId blank: false
    }
}

class AddFileCommand {
    String container
}