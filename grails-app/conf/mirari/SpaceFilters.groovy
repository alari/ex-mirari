package mirari

import mirari.morphia.Site
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Site

class SpaceFilters {

    @Autowired Site.Dao spaceDao
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
