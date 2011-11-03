package mirari

import grails.plugins.springsecurity.Secured

import mirari.morphia.Unit
import org.springframework.beans.factory.annotation.Autowired

@Secured("ROLE_USER")
class SpaceUnitController extends SpaceUtilController {

    @Autowired Unit.Dao unitDao
    def unitActService

    def show = {
        Unit unit = unitDao.getByName(currentSpace, params.unitName)
        [unit: unit]
    }

    def add = {
    }

    def addUnit = {AddUnitCommand command ->
        renderJson unitActService.addUnit(command, currentSpace)
    }

    def addFile = {AddFileCommand command ->
        renderJson unitActService.addFile(command, request.getFile("unitFile"), currentSpace)
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