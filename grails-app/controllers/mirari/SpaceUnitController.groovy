package mirari

import grails.plugins.springsecurity.Secured
import mirari.morphia.unit.single.ImageUnit
import org.apache.commons.lang.StringUtils
import mirari.morphia.Unit
import org.springframework.beans.factory.annotation.Autowired

@Secured("ROLE_USER")
class SpaceUnitController extends SpaceUtilController {

    @Autowired Unit.Dao unitDao

    def add = {
        if(request.post) {

            ServiceResponse resp = new ServiceResponse()

            ImageUnit u = new ImageUnit()
            u.title = params.title
            u.name = UUID.randomUUID().toString().replaceAll('-', '').substring(0, 5)
            u.draft = params.boolean("draft")
            u.space = currentSpace

            unitDao.save(u)

            if(u.id) {
                resp.success("Unit added successfully")
                resp.redirect controller: "spaceUnit", action: "show", params: [unitName: u.name,
                        spaceName: currentSpaceName]
                alert resp
            } else {
                resp.error "Cannot save unit"
                resp.model params
            }

            renderJson resp
        }
    }

    def show = {
        Unit unit = unitDao.getByName(currentSpace, params.unitName)
        [unit: unit]
    }
}
