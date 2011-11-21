package mirari

import grails.plugins.springsecurity.Secured
import groovy.json.JsonSlurper
import org.apache.log4j.Logger

class SpaceUnitStaticController extends SpaceUtilController {

    def unitRightsService
    def unitActService

    Logger log = Logger.getLogger(this.getClass())

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
    String ko
    boolean draft

    static constraints = {
        unitId blank: false, nullable: false
    }
}

class AddFileCommand {
    String container
}