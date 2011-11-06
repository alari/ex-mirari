package mirari

import grails.plugins.springsecurity.Secured

class SpaceUnitStaticController extends SpaceUtilController {

    def unitRightsService
    def unitActService

    @Secured("ROLE_USER")
    def add = {
        if (hasNoRight(unitRightsService.canAdd())) return;
    }

    @Secured("ROLE_USER")
    def addUnit = {AddUnitCommand command ->
        if (hasNoRight(unitRightsService.canAdd())) return;
        renderJson unitActService.addUnit(command, currentSpace)
    }

    @Secured("ROLE_USER")
    def addFile = {AddFileCommand command ->
        if (hasNoRight(unitRightsService.canAdd())) return;
        renderJson unitActService.addFile(command, request.getFile("unitFile"), currentSpace)
    }
}


class AddUnitCommand {
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