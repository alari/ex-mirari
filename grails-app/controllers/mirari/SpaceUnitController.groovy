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

    def index = {
        Unit unit = currentUnit
        if (isNotFound(unit)) return;
        if (hasNoRight(unitRightsService.canView(unit))) return;

        [unit: unit]
    }

    @Secured("ROLE_USER")
    def setDraft = {
        Unit unit = currentUnit
        if (isNotFound(unit)) return;
        if (hasNoRight(unitRightsService.canEdit(unit))) return;
        redirect alert(unitActService.setDraft(unit, params.boolean("draft"))).redirect
    }

    @Secured("ROLE_USER")
    def delete = {
        Unit unit = currentUnit
        if (isNotFound(unit)) return;
        if (hasNoRight(unitRightsService.canEdit(unit))) return;
        redirect alert(unitActService.delete(unit)).redirect
    }
}
