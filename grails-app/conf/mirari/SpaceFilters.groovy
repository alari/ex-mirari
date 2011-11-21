package mirari

import mirari.morphia.Space
import org.springframework.beans.factory.annotation.Autowired

class SpaceFilters {

    @Autowired Space.Dao spaceDao
    def alertsService

    def filters = {
        all(controller: 'space*', action: '*') {
            before = {
                params.space = spaceDao.getByName(params.spaceName)
                if(!params.space) {
                    alertsService.warning(flash, "error.spaceNotFound")
                    redirect(uri: "/")
                    return false
                }
            }
            after = { Map model ->
                if (model) {
                    model.spaceName = params.spaceName
                    model.space = params.space
                }
            }
            afterView = { Exception e ->

            }
        }
    }
}
